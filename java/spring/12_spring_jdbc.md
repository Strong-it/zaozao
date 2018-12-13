## JdbcTemplate来执行对数据库的操作
* 使用Sql语句和参数来更新数据库`update`
* `int	update(String sql, Object... args) throws DataAccessException`
* 批量更新数据库`batchUpdate`
* `int[] batchUpdate(String sql, List<Object[]> batchArgs)`
* 查询单行`queryForObject`
* `T
queryForObject(String sql, RowMapper<T> rowMapper, Object... args) `
* JdbcTemplate是线程安全的

## 1. 首先需要在pom.xml中引入jdbc包
```
<!-- jdbc的spring依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.1.0.RELEASE</version>
</dependency>
<!-- 数据源的依赖 -->
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>
<!-- mysql数据库驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.12</version>
</dependency>
```
## 2. Bean.xml文件的配置
```
<!-- 导入资源文件 -->
<context:property-placeholder location="classpath:db.properties"></context:property-placeholder>

<!-- 配置C3P0数据源 -->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="user" value="${jdbc.user}"></property>
    <property name="password" value="${jdbc.password}"></property>
    <property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
    <property name="driverClass" value="${jdbc.driverClass}"></property>
    <property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
    <property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
</bean>

<!-- 配置Spring的JdbcTemplete -->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="dataSource"></property>
</bean>
```
## 3. 配置数据库相关信息
```
jdbc.user=root
jdbc.password=root
#jdbc.driverClass=com.mysql.jdbc.Driver (5.1.25 没有这个问题)
#jdbc.jdbcUrl=jdbc:mysql://localhost:3306/owntest?&characterEncoding=UTF-8&useSSL=false
# 数据驱动用的是8.0.11那么驱动要这样写，否则会报时区错误
jdbc.driverClass=com.mysql.cj.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql://localhost:3306/owntest?serverTimezone=CTT&characterEncoding=UTF-8&useSSL=false

jdbc.initPoolSize=5
jdbc.maxPoolSize=10
```
## 4. java单元测试文件
```
public class JDBCTest {

    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;

    {
        ctx = new ClassPathXmlApplicationContext("beans.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
    }

    /**
     * 从数据库中获取一条记录，实际得到对应的一个对象
     * 注意：不是调用queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)方法
     * 1. 其中的RowMapper指定如何去映射结果集的行，常用的实现类为BeanPropertyRowMapper
     * 2. 支持 使用SQL中列的别名和类的属性名的映射， 例如last_name lastname
     * 3. 不支持级联属性， JdbcTemplate到底是一个JDBC的小工具，而不是ORM框架
     */
    @Test
    public void testQueryForObject() {
        String sql = "select runoob_id as id, runoob_title as title, runoob_author as author, submission_date as date from runoob_tbl where runoob_id=?";
        RowMapper<Runoob> rowMapper = new BeanPropertyRowMapper<Runoob>(Runoob.class);
        Runoob runoob = jdbcTemplate.queryForObject(sql, rowMapper, 1);

        System.out.println(runoob);
    }

    /**
     * 执行批量更新:批量的update, insert, delete
     * 最后一个参数是Object[]的List
     */
    @Test
    public void testBatchUpdate() {
        String sql = "insert into runoob_tbl (runoob_title, runoob_author, submission_date) values (?, ?, ?);";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[]{"AA", "Mary", "20181010"});
        batchArgs.add(new Object[]{"AA2", "Mary2", "20181010"});
        batchArgs.add(new Object[]{"AA3", "Mary3", "20181010"});
        batchArgs.add(new Object[]{"AA4", "Mary4", "20181010"});
        batchArgs.add(new Object[]{"AA5", "Mary5", "20181010"});

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 执行update, insert, delete
     */
    @Test
    public void testUpdate() {
        String sql = "update runoob_tbl set runoob_author = ? where runoob_id = ?";
        jdbcTemplate.update(sql, "Jack", 2);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource);
    }
}
```
## 使用具名参数 NamedParameterJdbcTemplate，代替占位符?
1. 配置bean
```
<!-- 配置NamedParameterJdbcTemplate，该对象可以使用具名参数，其没有无参数的构造器，必须为其构造器制定参数 -->
<bean id="namedJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
 <constructor-arg ref="dataSource"></constructor-arg>
</bean>
```
2. 具体使用方法
```
    /**
     * 可以为参数起名字
     * 1. 好处：若有多个参数，则不用再去对应位置，直接对应参数名，便于维护
     * 2. 缺点：较为麻烦
     */
    @Test
    public void testNamedParaJdbcTem() {
        String sql = "insert into book (title, author, date) values (:title, :author, :date);";

        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("title", "LL0");
        paramMap.put("author", "Lisi");
        paramMap.put("date", "20192020");

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    /**
     * 使用具名参数时，可以使用update(String sql, SqlParameterSource paramSource)来更新操作
     * 1. SQL语句中的参数名和类的属性一致
     * 2. 使用BeanPropertySqlParameterSource作为参数
     * 
     */
    @Test
    public void testNamedParaJdbcTem2() {
        String sql = "insert into book (title, author, date) values(:title, :author, :date)";

        Book book = new Book();
        book.setTitle("LLLL");
        book.setAuthor("Tom Jack");
        book.setDate("2019333");

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(book);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
```