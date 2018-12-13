## Bean的配置方式：通过工厂方法
#### 静态工厂方法：直接调用某一个类的静态方法就可以返回Bean的实例
```
/**
 * 静态工厂方法
 */
public class StaticCarFactory {
    private static Map<String, Car> cars = new HashMap<String, Car>();

    static {
        cars.put("audi", new Car("audi", 300000));
        cars.put("audi", new Car("audi", 400000));
    }

    // 静态工厂方法
    public static Car getCar(String brand) {
        return cars.get(brand);
    }
}
```
#### 配置文件
```
<!-- 通过静态工厂方法来配置bean，注意不是配置静态工厂方法实例，而是配置bean的实例 -->
<!--
    class属性：指向静态工厂方法的全类名
    factory-method: 指向静态工厂方法的名字
    constructor-age: 为工厂方法传递参数
-->
<bean id="car1" class="*.StaticCarFactory" factory-method="getCar"> 
    <constructor-arg value="audio"></constructor-arg>
</bean>
```
####实例工厂方法
```
/**
 * 实例工厂方法：实例工厂的方法，即先需要创建工厂本身，再调用工厂实例方法来返回 bean 的实例
 */
public class InstanceCarFactory {
    
    private Map<String, Car> cars = null;

    public InstanceCarFactory() {
        cars = new HashMap<String,Car>();
        cars.put("audi", new Car("audi", 300000));
        cars.put("audi", new Car("audi", 400000));
    }

    public Car getCar(String brand) {
        return cars.get(brand);
    }
}
```
#### 配置文件
```
<!-- 配置工厂实例 -->
<bean id="carFactory" class="*.InstanceCarFactory"></bean>

<!-- 通过实例工厂方法来配置bean-->
<bean id="car2" factory-bean="carFactory" factory-method="getCar">
    <constructor-arg value="audio"></constructor-arg>
</bean>
```
## FactoryBean方式配置bean
*  需要自己实现类extends FactoryBean
```
<!-- 
    通过FactoryBean 来配置Bean的实例
    class: 指向FactoryBean的全类名
    property: 配置FactoryBean 的属性

    但实际返回的实例确实是FactoryBean的 getObject() 方法返回的实例
    -->
<bean id="car" class="*.CarFactoryBean">
    <property name="brand" value="BWM"></property>
</bean>
```
