## @Configuration的使用
@Configuratioh用于定义配置类，可替换xml配置文件，被注解的类内部包含一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext(Web开发使用)类进行扫描，并用于构建Bean定义。
* `@Configuration` 不可以是final类型
* `@Configuration` 不可以是匿名类
* 嵌套的configuration必须是静态类
### @Configuration配置Spring并启动spring容器
* `@Configuration`标注在类上，相当于把该类作为Srping的XML配置文件中的\<beans>，作用于Spring容器(应用上下文)
### @Configuration容器启动器+@Bean注册Bean，@Bean下管理bean的生命周期
* @Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的\<bean>，作用为：注册bean对象
* @Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同
* @Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域
* 既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描
```
@Configuration
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化。。。");
    }

    // @Bean注解注册bean,同时可以指定初始化和销毁方法
    // @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    @Bean
    @Scope("prototype")
    public TestBean testBean() {
        return new TestBean();
    }
}
```
### @Configuration启动容器+@Component注册Bean
```
@Configuration
//添加自动扫描注解，basePackages为TestBean包路径
@ComponentScan(basePackages = "com.dxz.demo.configuration")
public class TestConfiguration
```
### 使用 AnnotationConfigApplicationContext 注册 AppContext 类的两种方法
1. 配置类的注册方式是将其传递给 AnnotationConfigApplicationContext 构造函数
```
// @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

//获取bean
TestBean tb = (TestBean) context.getBean("testBean");
tb.sayHello();
```
2. AnnotationConfigApplicationContext 的register 方法传入配置类来注册配置类，`不常用`
```
ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.register(AppContext.class)
```
### @Configuation总结
1. Configuation等价于<Beans>\</Beans>
2. @Bean等价于<Bean>\</Bean>
3. @ComponentScan等价于\<context:component-scan base-package="com.dxz.demo"/>
   
### @Configuration中引入spring的xml配置文件
```
@Configuration
@ImportResource("classpath:applicationContext-configuration.xml")
public class WebConfig {}
```

### @Configuration中引入其它注解配置
```
@Configuration
@ImportResource("classpath:applicationContext-configuration.xml")
@Import(TestConfiguration.class)
public class WebConfig {}
```
> 内容来自： https://www.cnblogs.com/duanxz/p/7493276.html