## 处理模型数据有以下几种途径
1. `ModelAndView` 处理方法返回值类型为`ModelAndView`时，方法体即可通过该对象添加模型数据
2. `Map及Model` 入参为`org.springframework.ui.Model`、`org.springframework.ui.ModelMap`或`java.util.Map`时，处理方法返回时，Map中到数据会自动添加到模型中
3. `@SessionAttributes` 将模型中到某个属性暂存到`HttpSession`中，以便多个请求之间可以共享这个属性
4. `@ModelAttribute` 方法入参标注该注解后，入参的对象会放到数据模型中。优先执行

### ModelAndView
即包含视图信息，也包含模型数据信息，数据保存在`requestScope`
1. 添加模型数据
    1. ModelAndView addObject(String attributeName, Object attributeValue)
    2. ModelAndView addAllObject(Map<String, ?> modelMap)
2. 设置视图
    1. void setView(View view)
    2. void setViewName(String viewName)

#### java代码展示
```
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        String viewName = SUCCESS;
        ModelAndView modelAndView = new ModelAndView(viewName);

        // 添加模型数据到 ModelAndView 中
        modelAndView.addObject("time", new Date());
        return modelAndView;
    }
```
#### html代码展示
```
<a href="springmvc/testModelAndView">Test ModelAndView</a>
<h4>Success Page</h4>

time: ${requestScope.time}
<br>
```

## Map及Model
1. SpringMVC在调用方法前会创建一个隐含的模型对象作为模型数据的存储容器
2. 如果方法到入参为`Map`或`Model`类型，SpringMVC会将隐含模型到引用传递给这些入参。在方法体内，开发者可以通过这个入参对象访问到模型中的所有数据，也可以向模型中添加新的属性数据。

#### java代码展示
```
    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map) {
        map.put("names", Arrays.asList("Tom", "Jary"));
        return SUCCESS;
    }
```
#### html代码展示
```
<a href="springmvc/testMap">Test Map</a>

<h4>Success Page</h4>
names: ${requestScope.names}
<br>
```

## SessionAttributes
1. 若希望在多个请求之间共用某个模型属性数据，则可以在控制器类上标注一个`SessionAttributes`
2. `SessionAttributes`除了可以通过`属性名`指定需要放到会话中到属性外，还可以通过模型属性`对象类型`指定那些模型属性需要放到会话中
    1. `@SessionAttributes(types=User.class)` 会将隐含模型中所有类型为User.class的属性添加到会话中
    2. `@SessionAttributes(value={"user1","user2"})`
    3. `@SessionAttributes(types={User.class, Dept.class})`
    4. `@SessionAttributes(value={"user1", "user2"}, types={Dept.class)}`

#### java代码展示
```
// @SessionAttributes({"user"})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
    
    private final String SUCCESS = "success";

    @RequestMapping("/testSessionAttributes")
    public String testSessionAttributes(Map<String, Object> map) {
        User user = new User(1, "Tom", "123", "tom@atguigu.com", 18, null);
        map.put("user", user);
        return SUCCESS;
    }
}
```
#### html代码展示
```
<a href="springmvc/testMap">Test Map</a>

<h4>Success Page</h4>
names: ${requestScope.names}
<br>
```

## @ModelAttribute
1. 在`方法`定义上使用`@ModelAttribute`注解：Spring MVC在调用目标处理方法前，会先逐个调用在方法上标注类`@ModelAttribute`的方法
2. 在方法到入参前使用了`@ModelAttribute`
    1. 可以从隐含对象(`implicitModel`)中获取隐含到模型数据中获取对象，再将请求参数绑定到对象中，再传入入参
    2. 将方法入参对象添加到模型中，存入到request中

#### java代码展示
```
    /**
     * 运行流程:
     * 1. 执行`@ModelAttribute`注解修饰到方法：从数据库中取出对象，把对象放入到Map中，键为"user"
     * 2. SpringMVC 从Map中取出User对象，并把表单到请求参数赋给该User对象的对应数据
     * 3. SpringMVC 把上述对象传入到目标方法的参数
     * 
     * 注意：1. 在@ModelAttribute 修饰的方法中放入到Map时的键需要和目标方法入参类型的第一个字母小写的字符串一致
     *      2. 如果不一致需要在入参声明public String testModelAttribute`(@ModelAttribute("abc")` User user)
     **/   
    @ModelAttribute
    public void getUser(@RequestParam(value="id") Integer id, Map<String, Object> map) {
        if(id!=null) {
            // 模拟从数据库获取对象
            User user = new User(1, "Tom", "123", "tom@atguigu.com", 18, null);
            System.out.println("从数据库中获取的对象：" +user);
            map.put("user", user);
        }    
    }

    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user) {
        System.out.println("修改：" + user);
        return SUCCESS;
    }
```
#### html代码展示
```
    <!-- 模拟修改操作
    1. 原始数据为： 1, Tom, 123456, tom@guigu.com, 12
    2. 密码不能修改
    3. 表单回显，模拟操作直接在表单填写对应的属性 -->
    <br><br>
    <form action="springmvc/testModelAttribute" method="post">
        <input type="hidden" name="id" value="1">
        username: <input type="text" name="username" value="Tom" />
        <br>
        Email: <input type="text" name="email" value="tom@guigu.com" />
        <br>
        Age: <input type="text" name="age" value="12" />
        <br>
        <input type="submit" value="提交">
    </form>
```