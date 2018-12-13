## XML配置里的Bean自动装配
* Spring IOC容器可以自动装配Bean，需要做的仅仅是 **在\<Bean>的autowired属性里指定自动装配的模式** 
* `byType`(根据类型自动装配): 若IOC容器中有多个与目标Bean类型一致的Bean，在这种情况下，Spring将无法判断那个Bean最适合该属性，所以不能执行自动装备
* `byName`(根据名称自动装配): 必须将目标Bean的名称和属性名设置的完全相同
* `constructor`(通过构造器自动装配): 当Bean中存在多个构造器时，此种自动装配很复杂，不推荐使用
* before
```
<bean id="address" class="com.spring.autowired.Address"
    p:city="Beijing" p:street="HuiLongGuan"></bean>
<bean id="autocar" class="com.spring.autowired.Car"
    p:brand="Audi" p:price="300000"></bean>
<bean id="autoperson" class="com.spring.autowired.Person"
    p:name="Tom" p:address-ref="address" p:car-ref="autocar"> </bean>
```
* after
```
<!-- 可以使用autowired属性指定自动装配方式，
        byName 根据bean名字和当前bean的setter风格的属性名进行自动装配
        若有匹配，则进行自动装配，若没有匹配的，则不装配
        byType 根据bean的类型自动装备
        2中装配方法不能同时使用-->
<bean id="address" class="com.spring.autowired.Address"
    p:city="Beijing" p:street="HuiLongGuan"></bean>
<bean id="autocar" class="com.spring.autowired.Car"
    p:brand="Audi" p:price="300000"></bean>
<bean id="autoperson" class="com.spring.autowired.Person"
    p:name="Tom" autowired="byName" autowired="byType"> </bean>
```
## Bean之间的继承(parent)
* before
```
<bean id="address" class="com.spring.autowired.Address"
    p:city="Beijing" p:street="HuiLongGuan"></bean>
<bean id="address2" class="com.spring.autowired.Address"
    p:city="Beijing" p:street="WuDaoKou"></bean>
```
* after
```
<bean id="address" class="com.spring.autowired.Address"
    p:city="Beijing" p:street="HuiLongGuan"></bean>
<bean id="address2" p:street="WuDaoKou" parent="address"></bean>
```
> 设置属性 abstract="true" 为抽象bean不能被实例化
 
## Bean之间的依赖(depends-on)
* Spring允许用户通过`depends-on`属性设定Bean前置依赖的Bean，前置依赖的Bean会在本Bean实例化之前创建好
* 如果前置依赖于多个Bean，则可以通过逗号，空格的方式配置Bean的名称
```
<!-- 要求配额制Person时，必须要有一个关联的car! 换句话说person这个bean依赖于Car这个bean->
<bean id="person" class="com.spring.autowired.Person"
    p:address-p="adress" depends-on="car"></bean>
```

## Bean的作用域
* 使用Bean的`scope`属性来配置Bean的作用域，默认为`singleton`
* `singleton` 默认值，在整个容器的生命周期内只创建这一个Bean，单例的
* `prototype:` 原型的，容器初始化时不创建bean实例，而在每次请求时都创建一个新的Bean实例，并返回
* `request`
* `scope` 域对象，用的比较少

## 使用外部属性文件
* 例如使用系统部署的细节信息，如：文件路径，数据源配置信息
* Spring提供了一个PropertyPlaceholderCoinfigurer的`BeanFactory后置处理器`，这个处理器允许将Bean配置的部分内容移动到属性文件中，在Bean中`${var}`的变量，PropertyPlaceholderCoinfigurer从属性文件中加载属性，并使用这些属性来替换变量
> 1. 创建`db.properties`的配置文件，
> 2. 导入配置文件 `<context:property-placeholder location="classpath:db.properties">`
> 3. 引用变量 `value="${user}"`

### Spring从两个角度实现自动化装配
* 组件扫描(Component scanning)：Spring会自动发现应用上下文所创建的Bean
* 自动装配(autowiring): Spring自动满足bean之间的依赖关系
```
@Configuration

// @ComponentScan("包路径") 会自动扫描包路径下面的所有@Controller、@Service、@Repository、@Component 的类
// includeFilters 指定包含扫描的内容
// excludeFilters 指定不包含的内容
// @Filter 指定过滤规则，type指定扫描的规则（注解，正则，自定义，ASPECTJ表达式），classes指定的扫描的规则类
@ComponentScan(basePackages = {"com.guang"},
        includeFilters = @Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = {Repository.class}),
        includeFilters = @Filter(type = FilterType.CUSTOM, classes = {FilterCustom.class}),
        useDefaultFilters = false)
```