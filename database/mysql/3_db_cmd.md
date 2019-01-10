## SQL语句分类
1. DDL语句（数据定义语句）
   - `create 、alter`
2. DML语句（数据操作语句）
    - `update、insert、delete`
3. DQL语句（数据查询语句）
    - `select`
4. DCL语句（数据控制语句）
    - 在数据库事务和mysql用户管理的时候
    - `grant、revoke、commit、rollback、savepoint`等

## 数据库的创建
1. CREATE DATABSE IF NOT EXISTS 数据库名 DEFAULT CHARACTER SET utf8 COLLATE utf8_generic_ci;
    - `CHARACTER SET` 设置默认的字符编码
    - `COLLATE` 设置校验规则
    - `ci` 代表`case insensitive` 即大小写不敏感，`a`和`A`会在字符判断中会被当作一样的
    - `urf8_bin` a和A会区别对待
2. `show character set` 显示字符集
3. `show collation` 排序和校对规则

## 字符集和校验规则的区别
> 字符集是跟存储有关，校验规则是跟查询相关，比如排序

## 查看删除数据库
1. `SHOW DATABASES;` 显示数据库
2. `SHOW CREATE DATABASE DB_NAME` 显示数据库创建语句
    - `CREATE DATABASE `zrlog` /*!40100 DEFAULT CHARACTER SET utf8 */`
    - mysql建议我们关键字使用大写，但不是必须
    - `` 反引号，可以避免使用的数据库名称刚好是关键字
    - `*!40100` 表示当前mysql版本大于mysql4.01就会执行
3. `DROP DATABASE [IF EXISTS] DB_NAME` 删除数据库
4. `SHOW PROCESSLIST;` 查看数据库连接情况

## 数据库备份，恢复数据库
1. `mysqldump -u root -p db_name > d:\tnblog.bak` 备份单个数据库
2. `mysqldump -u root -p db_name person > D:\backup.sql` 备份数据库中的一个表
3. `mysqldump -u username -p --databases dbname2 dbname2 > d:\Backup.sql` 备份多个数据库
4. `mysqldump -u username -p -all-databases > d:\BackupName.sql` 备份所有个数据库
5. `mysql -u root -p < C:\backup.sql` 数据还原
6. `mysql>source 备份文件全路径` 
    - 在使用source恢复数据时，保证use了对应的数据库
7. `-B` 会把创建数据库的命令也备份

## 数据库修改
>ALTER DATABASE [IF EXISTS] db_name
> [DEFAULT] CHARACTER SET charset_name
> [DEFAULT] COLLATE collation_name
`alter database db3 character set uft8`