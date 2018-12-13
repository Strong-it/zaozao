## Maven创建spring工程
#### Maven创建Webxiangm
`mvn archetype:generate -DgroupId=com.spring.test -DartifactId=SpringTest -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false`<br>
> 在创建工程是让输入spring的版本号和包名，直接回车确认
#### Maven创建Java工程
`mvn archetype:generate -DgroupId=com.spring.test -DartifactId=SpringTest -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`<br>

* `mvn archetype:generate` 固定格式
* `-DgroupId` 组织标识
* `-DartifactId` 项目名称
* `-DarchetypeArtifactId` 项目骨架
    * `maven-archetype-quickstart` 创建一个Java Project
    * `maven-archetype-webapp` 创建一个Web Project
    * `maven-archetype-archetype `
    * `maven-archetype-j2ee-simple`
    * `maven-archetype-mojo`
    * `maven-archetype-portlet `
    * `maven-archetype-profiles`
    * `maven-archetype-simple`
    * `maven-archetype-site`
    * `maven-archetype-site-simple `
* `-DinteractiveMode` 是否使用交互模式

## Maven 常用命令
* `mvn complie` 编译源码
* `mvn test-complie` 编译测试代码
* `mvn clean` 清除
* `mvn test` 运行测试
* `mvn install` 安装当前工程的输出文件到本地仓库.resposity
* `mvn package` 打包
* `mvn clean package` 先清除再打包
* `mvn jar:jar` 打成jar包
* `mvn eclipse:eclipse` 生成eclipse项目
* `mvn exec:java -Dexec.mainClass="com.spring.test.App"` 执行java的main方法
* `mvn exec:java -Dexec.mainClass="com.vineetmanohar.module.Main" -Dexec.args="arg0 arg1 arg2"` 执行main方法需要传递参数

## 标准的Maven项目结构
* `src/main/java` 存放项目的源代码
* `src/test/java` 存放测试源代码
* `src/main/resource` 存放配置文件 

## 节点元素说明
* `<project>` pom文件的顶级节点, "project object model"
* `<modelVersion>` object model版本，对Maven2和Maven3来说，只能是4.0.0
* `<groupId>` 项目创建组织的标识符(包名)，一般是域名的倒写
* `<artifactId>`  项目名
* `<packaging>` 打包的方式，有jar、war、ear等
* `<version>` 当前项目的版本，SNAPSHOT，表示是快照版本，在开发中
* `<name>` 项目的名称
* `<url>` 项目的地址
* `<dependencies>` 构建项目依赖的jar
* `<description>` 项目的描述

## 在maven/conf/settings.xml文件里添加aliyun镜像
```
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
```