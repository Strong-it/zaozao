## Bean配置及初始化
```
<!-- 
    配置bean 
    class:bean 的全类名，通过反射的方式在IOC容器中创建Bean，所以要求Bean中必须有无参构造函数
    id: 标识容器中id 唯一，可以有多个用,隔开。 如果没有给定id，则根据全限定的类名进行命名
-->
<bean id="helloWorld" class="com.spring.test.HelloWorld"> 
    <!-- name名和set后面保持一致 -->
    <property name="name" value="Spring"> </property>
</bean>
```

## IOC容器的创建
`ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");`
<br>
> `ApplicationContext ctx = new FileSystemXmlApplicationContext("F:\\Code\\SpringTest\\src\\applicationContext.xml");`
* `BeanFactory` IOC 容器的基本实现
* `ApplicationContext` 提供了更多的高级特性，是BeanFactory的子接口
* `BeanFactory` 是Spring框架的基础设施，面向Spring本身 
* `ApplicationContext` 面向使用Spring框架的开发者，**计划所有的应用场合都直接使用ApplicationContext而非底层的BeanFactory**
* 无论使用何种方式，配置文件相同 
* `ApplicatonContext的主要实现类`
    * `ClassPathXmlApplicationContext` 从类路径下加载配置文件
    * `FileSystemXmlApplicationContext` 从文件系统中加载配置文件
    * `ConfigurableApplicationContext` 扩展于ApplicationContext，新增2个方法，refresh()和close()，让IOC容器酱油启动，刷新，关闭的功能
* ApplicationContext在初始化上下文时就实例化所有单例的Bean
* `WebApplicationContext` 是专门为WEB应用准备的，它允许从相当于WEB根目录的路径中完成初始化工作

## 属性注入
* 属性注入即通过setter方法注入Bean的属性值或依赖的对象
* 属性注入使用\<property>元素，使用name属性指定Bean的属性名称，value属性或\<value>子节点指定属性值
* 属性注入是实际应用中最常用的注入方式<br>
* `<property name="name" value="Spring"> </property>` 

## 构造方法注入
* 通过构造方法注入Bean的属性值或依赖的对象，它保证Bean实例在实例化后就可以是使用
* 构造器注入在\<constructor-arg>元素里声明属性，\<constructor-arg>中没有name属性
```
<!--通过构造方法来配置bean的属性，默认通过顺序匹配，也可以用index标记-->
<bean id="car" class="com.spring.test.Car">
    <constructor-arg value="Audi" index="0"></constructor-arg>
    <constructor-arg value="Shanghai" index="1"></constructor-arg>
    <constructor-arg value="3000" index="2" type="double"></constructor-arg>
</bean>

<!-- 使用构造器注入属性值可以指定参数的位置和参数的类型，以区分重载构造器-->
<bean id="car2" class="com.spring.test.Car">
    <constructor-arg value="Baoma" type="java.lang.String"></constructor-arg>
    <!-- 也可以把value子节点配置 如果包含特殊字符，可以用<![CDATA[*]]>包裹起来 -->
    <constructor-arg type="java.lang.String">
        <value><![CDATA[Changchun^]]></value>
    </constructor-arg>
    <constructor-arg value="240"  type="int"></constructor-arg>
</bean>
```

## Bean之间的注入
```
<bean id="person" class="com.spring.test.Person">
    <property name="name" value="Tom"> </property>
    <property name="age" value="25"> </property>
    <!--可以使用Property的ref属性建立bean之间的引用-->
    <property name="car" ref="car2"> </property>
</bean>
```
##### 内部bean，在bean内创建bean，不能被外部引用，只能在内部使用
```
<property name="car">
    <constructor-arg value="Audi"></constructor-arg>
    <constructor-arg value="Shanghai"></constructor-arg>
    <constructor-arg value="3000"></constructor-arg>
</property>
```
##### 给Bean添加null值
`<constructor-arg><null/></constructor-arg>`

##### 级联属性的赋值，方法里需要有set和get方法.属性需要先初始化后才可以为级联属性赋值
`<property name="car.price" value="25000"> </property>`

## 集合属性
* java.util.List通过\<list>标签
```
<bean id="person" class="com.spring.test.Person">
    <property name="name" value="Tom"> </property>
    <property name="age" value="25"> </property>
    <property name="cars">
        <list>
            <ref bean="car" />
            <ref bean="car2" />
            <ref bean="car3" />
        </list>
    </property>
</bean>
```
* java.util.Map 通过\<map>标签定义
```
<bean id="person" class="com.spring.test.Person">
    <property name="name" value="Tom"> </property>
    <property name="age" value="25"> </property>
    <property name="cars">
        <map>
            <entry key="AA" value-ref="car">
            <entry key="BB" value-ref="car2">
        </map>
    </property>
</bean>
```
* java.util.Properties 通过\<props>标签定义
```
<bean id="dataSource" class="com.spring.test.DataSource">
    <property name="properties"> 
        <props>
            <prop key="user">root</prop>
            <prop key="password">root</prop>
            <prop key="jdbcUrl">jdbc:mysql:///test</prop>
            <prop key="driverClass">com.mysql.jdbc.Driver</prop>
        </props>
    </property>
</bean>
```
#### 配置单例的集合bean，以供多个bean进行引用，需要导入util命名空间
```
<util:list id="cars">
    <ref bean="car">
    <ref bean="car2">
</util:list>
```
#### 通过 P 命名控件为bean的属性赋值，需要导入P命名空间
`<bean id="" class="" p:age="30" p:name="Queen" p:cars-ref="cars"></bean>`
* 命名空间
`xmlns:p="http://www.springframework.org/schema/p"`