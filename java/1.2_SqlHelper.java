package cn.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;

public class SqlHelper {
	// 定义需要的变量，如果比较少的用，静态
	private static Connection ct = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;

	// 连接数据库的参数
	private static String url = "";
	private static String username = "";
	private static String passwd = "";
	private static String driverName = "";

	private static Properties pp = null;
	private static FileInputStream fis = null;

	// 加载驱动，只需要一次，bin路径也叫类路径
	static {
		try {
			// 从dbinfo.propertis文件中读取配置信息，文件位于src目录下
			pp = new Properties();
			fis = new FileInputStream("dbinfo.propertis");
			pp.load(fis);
			
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			passwd = pp.getProperty("passwd");

            Class.forName(driverName);
            
		} catch (FileNotFoundException e) {
			System.out.println("db config doesn't exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("loading driver failed");
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fis = null;
		}
	}

 	public static Connection getCt() {
		return ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static CallableStatement getCs() {
		return cs;
	}

	// 得到链接
	public static Connection getConnection() {
		
		try {
			ct = DriverManager.getConnection(url, username, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	
	// 需要把ResultSet换成ArrayList
	public static ResultSet executequery(String sql, String[] parameters) {
		
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			
			if (parameters != null && !parameters.equals("")) {
				
				for(int i  = 0; i< parameters.length; i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			
			rs=ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, ps, ct);
		}
		return rs;
	}
	
	// 调用存储过程
	public static void callPro1(String sql, String[] parameters) {
		
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			
			// ?赋值
			if (parameters != null) {
				for(int i=0; i< parameters.length; i++) {
					cs.setObject(i+1, parameters[i]);
				}
			}
			
			cs.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(null, ps, ct);
		}
	}
	
	// 调用存储过程，out输出参数
	public static void CallPro2(String sql, String[] inparameters,
												 Integer[] outparameters) {
		
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			
			// ?赋值
			if (inparameters != null) {
				for(int i=0; i< inparameters.length; i++) {
					cs.setObject(i+1, inparameters[i]);
				}
			}
			
			if (outparameters != null) {
				for(int i = 0; i < outparameters.length; i++) {
					cs.registerOutParameter(inparameters.length+1+i, outparameters[i]);
				}
			}
			
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
//			暂时不需要关闭
//			close(rs, ps, ct);
		}
		
	}
	
	// 先写一个 update / delete / insert
	public void executeUpdate(String sql, String[] parameters) {
		
		// 1. 创建一个ps
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			// 给?赋值
			if (parameters != null) {
				for(int i=0; i < parameters.length; i++) {
					// PreparedStatement赋值从1开始
					ps.setString(i+1, parameters[i]);
				}
			}
			
		} catch (SQLException e) {
			System.out.println("sql statement error");
			e.printStackTrace();
			// 抛出运行时异常，可以给调用该函数的函数一个选择
			// 可以处理，也可以放弃处理
			throw new RuntimeException(e.getMessage());
		} finally {
			// 关闭资源
			close(rs, ps, ct);
		}
	}
	
	// 如果有多个update/ delete/ insert [需要考虑事务]
	public static void executeUpdate2(String sql[], String[][] parameters) {
		
		
		try {
			// 核心获得连接
			ct = getConnection();
			
			// 因为这时，用户传入的可能是多个sql语句
			ct.setAutoCommit(false);
			// ...
			for(int i = 0; i<sql.length; i++) {
				if (parameters[i] != null) {
					ps=ct.prepareStatement(sql[i]);
					
					for(int j=0; j<parameters[i].length; j++) {
						ps.setString(j+1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}
	}
	
	// 关闭资源
	public static void close(ResultSet rs, Statement ps, Connection ct) {
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
		
		if (ct != null) {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ct = null;
		}
	}
}