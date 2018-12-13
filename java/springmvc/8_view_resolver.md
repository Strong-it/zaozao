## 视图和视图解析器
1. 请求处理方法执行完成后，最终返回一个`ModelAndView`对象。对于那些返回String，View或ModelMap等类型到处理方法，Spring MVC也会在内部将它们装配成一个`ModelAndView`对象，它包含类逻辑名和模型对象的视图
2. SpingMVC借助`视图解析器(ViewResolver)`得到最终到视图对象(View)，最终到视图可以时JSP，也可以时Excel、JFreeChart等各种表现形式的视图

## JstlView
1. 若项目中使用了`JSTL`标签，则SpringMVC会自动把视图由InternalResourceView转为`JstlView`
2. 若使用了JSTL的fmt标签则需要在SpringMVC的配置文件中配置国际资源化文件
3. 若直接响应通过`SpringMVC`渲染到页面，可以直接使用`<mvc:view-controller>`标签实现 `<mvc:view-controller path="springmvc/testJstlView" view-name="success">`
4. 配置文件
```
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basename" value="i18n"></property>
</bean>
```
5. 使用mvc标签需要添加下面引用
```
xmlns:mvc="http://www.springframework.org/schema/mvc"
xsi:schemaLocation="http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc.xsd"
```
6. 在实际开发中需配置标签`<mvc:annotation-driven />`，相当于`@EnableWebMVC`

## 重定向
1. 如果返回的字符串中带`forward:`或`redirect:`前缀时SpringMVC会对他们进行特殊处理：将`forward:`或`redirect:`当成指示符，其后的字符串作为URL来处理
    1. `redirect:success.jsp` 会完成一个到success.jsp的重定向的操作
    2. `redirect:success.jsp` 会完成一个到success.jsp的转发操作

#### java代码展示
```
    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }
```

#### html代码展示
````
<a href="springmvc/testRedirect">Test Redirect</a>
````