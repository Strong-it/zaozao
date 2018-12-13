## 基于注解的方式配置Bean
* 组件扫描(component scanning)：Spring能够从'classpath'下自动扫描，侦测和实例化具有特定注解的组件
* 特定组件包括
    * `@Component`: 基本注解，标识了一个受Spring管理的组件
    * `@Respository`: 标识持久层组件
    * `@Service`: 标识服务层(业务层)组件
    * `@Controller`: 标识表现层组件
* 对于扫描到的组件，Spring有默认的命名策略：`使用非限定类名，第一个字母小写`。也可以在注解中通过`value`属性值标识组件的名称
* 当在组件类上使用了特定的注解之后，还需要在Spring的配置文件中声明`<context:component-scan>`:
    * `base-package` 属性指定一个需要扫描的基类包，Spring容器将会扫描这个基类包及子类中的所有类
    * 当需要扫描多个包时，可以使用 `逗号` 分割
    * 如果仅希望扫描特定的类而非基包下的所有类，可使用`resource-pattern`属性过滤特定类：如
        * `<context-component-scan base-package="com.spring.test.annotation resource-pattern="autowire/*.class">`
        * 只扫描基包下的以`autowire`开头的类
* `<context:include-filter>` 子节点表示要包含的目标类
* 要想使用`include-filter` 需要设置`use-default-filters="false"`
* `<context:exclude-filter>` 子节点要排除在外的目标类
* `context:component-scan>` 下可以拥有若干个`<context:include-filter>` 和 `<context:exclude-filter>`
```
<!-- Spring IOC容器扫描包-->
<context:component-scan base-package="com.spring.annotationbeans"
    use-default-filters="false">
    <!--以注解的形式-->
    <context:exclude-filter type="annotation" express="*.annotationbeans.UserRepository">
    <!--具体类的形式-->
    <context:exclude-filter type="assignable" express="*.annotationbeans.UserRepository">
</context:component-scan>
```

## 利用注解配置Bean的相互关联关系
* `<context-scan>`元素还会自动注册`AutowiredAnnotationBeanPostProcessor`实例，该实例可以自动装配具有`@Autowired 、@Resource 、@Inject`注解的属性
* `Autowired`注解自动装配具有兼容类型的单个bean属性
  * 构造器，普通字段(即使非public)，一切只要有参数的方法都可以应用`@Autowired`注解
  * 默认情况下，所有使用`@Autowired`注解的属性都需要被设置，当Spring找不到匹配的Bean装配时，会自动抛出异常，若某一属性不被设置，可以设置`@Autowired注解的required属性为false`
  * 默认情况下，当IOC容器里存在多个类型兼容的Bean时，通过类型的自动装配无法工作，此时可以在`@Qualifier`注解里提供Bean的名称，Spring允许对方法的入参标注`@Qualifier`已指定注入Bean的名称
  * `Autowired` 注解也可以应用在`数组类型`的属性上，此时Spring将会把所有的匹配的Bean进行自动装配
  * `Autowired` 注解也可以应用在`集合属性`上，此时Spring读取该集合的类型信息，然后自动装配所有与之兼容的Bean
  * `Autowired` 注解用在`java.util.Map`上时，若该Map的键值为String，那么Spring将自动装配与之Map类型兼容的Bean，此时Bean的名称作为键值
  * `@Qualifier("userRepositoryImpl")`
  * `setRepository(@Qualifier("userRepositoryImpl") UserRepository userRepository)` 参数使用

## 泛型依赖注入
* Spring 4.x中可以为子类注入子类对应的泛型类型的成员变量，通过继承实现