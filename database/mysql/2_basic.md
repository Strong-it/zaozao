## 数据库权限的操作
1. 修改数据库密码
    - `set password for root@localhost = password('123');`
    - `FLUSH PRIVILEGES`
2. 修改远程访问权限
    - `select host, user from user;`
    - `update user set host = '%' where user = 'root' and host = 'localhost';`
    - `FLUSH PRIVILEGES`

## 数据库的基本操作
1. 连接到mysql
    - mysql -h 主机 -u 用户名 -p [回车]
    - 默认情况下不写`-h`是连接本地
2. linux启动mysql5.5
    - service mysqld start|stop
3. windows启动
    - net start|stop mysql
4. 数据存储位置
    - `my.ini`
    - `datadir="C:/ProgramData/MySQL/MySQL Server 5.5/Data/"`