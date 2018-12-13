## Spring表达式语言：SpEL
* Spring表达式语言（简称SpEL）：是一个`支持运行时查询和操作对象图的强大的表达式语言`
* 语法类似EL： SpEL使用`#{dd }`作为定界符，所有在大括号中的字符都将被认为时SpEL
* SpEL为bean的属性进行动态赋值提供了便利
* 通过SpEL可以实现：
    * 通过bean的id对bean进行引用
    * `<property anme="prefix" value="#{prefixGenerator}"></property>`
    * 调用方法及引用对象中的属性
    * `<property anme="prefix" value="#{prefixGenerator.suffix}"></property>`
    * `<property anme="prefix" value="#{prefixGenerator.toString()}"></property>`
    * 计算表达式的值
    * 正则表达式的匹配
* String可以使用单引号或者双引号作为字符串的界定符
* 支持算数运算符:+,-,*,/,%,^
* 加号还可以用作字符串连接：
* 比较运算符
* 逻辑运算符 and,or
* 调用静态方法或者静态属性： 通过T()调用一个类的静态方法，它将返回一个Class Object，然后再调用相应的方法或属性
* `<property anme="prefix" value="#{T(java.lang.Math).PI}"></property>`

## IOC容器中Bean的生命周期
* 通过构造器或工厂方法创建Bean实例
* 为Bean的属性设置值和对其他Bean的引用
* 调用Bean的初始化方法
* Bean可以使用
* 当容器关闭时，调用Bean的销毁方法
* 在Bean的生命里设置`init-method`和`destroy-method`属性，为Bean指定初始化和销毁方法
* `<bean id="" class="" init-method="init2" destroy-method="destroy">`

## 创建Bean后置处理器
* Bean后置处理器允许在调用初始化方法前后对Bean进行额外处理
* Bean后置处理器对IOC容器里所有Bean实例逐一处理，而非单一实例。场景：检查Bean属性的正确性或根据特定的标准更改Bean的属性
* `MyA implements BeanPostProcessor` 里面有Before和After方法
* 默认处理所有生命的 bean，但是在里面使用if("aa".equals(beanName))阿里处理
* `<bean calss=""></bean>` 不需要设置ID，IOC容器自动识别