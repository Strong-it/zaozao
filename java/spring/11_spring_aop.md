## AOP注解配置
* AOP: 是一种新的方法论，是对传统OOP的补充
* 切面(Aspect): 横切关注点(扩月应用程序多个模块的功能)被模块化的特殊对象
* 通知(Advice): 切面需要完成的工作
* 目标(Target): 被通知的对象
* 代理(Proxy): 向目标对象应用通知之后创建的对象
* 连接点(Joinpoint): `程序执行的某个特定位置`，如类某个方法调用前、调用后、方法抛出异常后等。连接点由两个信息确定：`方法表示的程序执行点，相对点表示的方位`。例如 ArithmethicCalculator#add()方法执行前的连接点执行点为ArithmethicCalculator#add()，方位为该方法执行前的位置
* 切点(pointcut): 每个类都有连接点，例如 ArithmethicCalculator的所有方法都是`连接点`, 即连接点是程序类中客观存在的事务。`AOP通过切点定位到特定的连接点。类比：连接点相当于数据库中的记录，切点相当于查询条件`。切点和连接点不是一对一的关系，一个切点匹配多个连接点，切点通过`org.springframework.aop.Pointcut`接口进行描述，它使用类和方法作为连接点的查询条件
  
## AspectJ
* Java社区里最完整最流行的框架，可以使用基于AspectJ注解或者基于XML配置的AOP
* 要使用AspectJ注解，需要添加类库: `aopalliance.jar、 aspectj.weaver.jar和spring-aspects.jar`
* `将AOP的 schema添加到<beans>根元素`
* 要在Spring IOC容器中启用AspectJ注解支持，只要在Bean的配置文件中定义一个空的XML元素`<aop:aspectj-autoproxy>`
* 当Spring IOC容器中侦测到Bean配置文件中的`<aop:aspectj-autoproxy>`，会自动为AspectJ切面匹配的Bean创建代理
* 要在Spring中声明AspectJ切面，只需要在IOC容器中将切面声明为Bean实例，当在Spring IOC容器中初始化为AspectJ切面之后，Spring IOC容器会为那些与AspectJ切面相匹配的Bean创建代理
* `在AspectJ注解中，切面只是一个带有@Aspect注解的java类`
* `通知是标注有某种注解的简单的Java方法`
* AspectJ支持5种类型的通知
    * @Before: 前置通知，在方法执行之前执行
    * @After: 后置通知，在方法执行之后执行
    * @AfterReturnning: 返回通知，在方法返回结果之后执行，可以访问到返回值
    * @AfterThrowing: 异常通知，在方法抛出异常之后，可以访问到异常值
    * @Around: 环绕通知，围绕着方法执行

## 使用步骤
1. 导入相应的jar包，maven则添加依赖
```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>${org.springframework.version}</version>
</dependency>

<dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.1</version>
</dependency>
```
2. 在bean配置文件中添加`aop`的命名空间
`xmlns:aop="http://www.springframework.org/schema/aop"`

3. 基于注解的方式
   * 在配置文件中加入如下配置，使AspectJ注解起作用，自动为匹配的类生成代理对象
   `<aop:aspectj-autoproxy></aop:aspectj-autoproxy>`
   * 把横切关注点的代码抽象到切面类中
     * 切面首先是IOC中的bean，即加入@Component注解
     * 切面还需要加入@Aspect注解，表明是注解
   * 在类中声明各种通知
     * 利用`方法签名`编写AspectJ切入点表达式
     * 最典型的切入点表达式是根据方法的签名来匹配各种方法
      `@Before("execution(public int com.exspring.aop.CalculatorImpl.add(int, int))")` 
     * 可以利用占位符`*`  可以代表任意 修饰符、任意 返回值、任意 方法名等
   * 可以在`通知方法`中声明一个类型为`JointPoint`的参数，然后就能访问到连接细节，比如方法名和参值
   * 返回通知可以获取方法的返回值
   * `@AfterReturnning(value="execution(public int com.exspring.aop.CalculatorImpl.add(int, int))", result="result")`
   * `public void AfterReturnningMethod(JoinPoint joinpoint, Object result)`
```
/**
 * 把该类声明为一个切面，需要把该类放入到IOC容器中，再声明为一个切面
 */
@Aspect
@Component
public class LoggingAspect {
    // 声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("execution(public int com.exspring.aop.CalculatorImpl.add(int, int))")
    public void beforeMethod(JoinPoint joinpoint) {
        String methodname = joinpoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinpoint.getArgs());
        System.out.println("the method: " + methodname + " begins with " + args);
    }

    @After("execution(public int com.exspring.aop.CalculatorImpl.add(int, int))")
    public void afterMethod() {
        System.out.println("the method finished");
    }
}
```

## 切面优先级
* `@Order(1)` 值越小，优先级越高
  

## 重用切点表达式
* 定义一个方法，用于声明切点表达式，一般地，该方法中再不需要填入其它代码
* 使用@Pointcut来声明切入点表达式
* 后面的其它通知直接使用方法名来引用当前的切入店表达式
* `@Pointcut("execution(public int com.exspring.aop.CalculatorImpl.add(int, int))")`
* `public void declareJointPointExpress(){}`
* `@Before("declareJointPointExpress")` 
  
## 基于XML的AOP配置
1. * \<!-- 配置bean -->
   *  `<bean id="calculator" class=""></bean>`
2. * \<1-- 配置切面的bean ---> 
   * `<bean id="validationAspect" = class="">`
3. * \<!-- 配置AOP --->
```
<!-- 配置普通的bean -->
<bean id="loggingAspect" class=""></bean>

<!-- 配置AOP -->
<aop:config>
    <!-- 配置切点表达式 -->
    <aop:pointcut expression="executeion()" id="pointcut">
    <!-- 配置切面及通知 -->
    <aop:aspect ref="loggingAspect" order="2">

        <aop:before method="beforeMethod" pointcut-ref="pointcut">
        <aop:after method="afterMethod" pointcut-ref="pointcut">
        <aop:after-throwing method="afterThrowing" pointcut-ref="pointcut" throwing="e">
        <aop:after-returning method="afterReturning" pointcut-ref="pointcut" returning="result">
    </apo:aspect>
    <aop:aspect ref="validationAspect" order="1">
        <aop:before method="validateArgs" pointcut-ref="pointcut">
    </apo:aspect>
</aop:config>
```