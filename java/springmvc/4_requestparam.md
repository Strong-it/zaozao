## @RequestParam 绑定请求参数值
在处理方法入参处使用`@RequestParam`可以把请求参数传递给请求方法
1. `value` 参数名
2. `required` 是必须。默认为`true`，表示请求参数中必须包含对应参数，若不存在，将抛出异常
#### java代码展示
```
    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam(value="username", required=false, defaultValue="atguigu") String username,
                        @RequestParam(value="age") String age) {
        System.out.println("testRequestParam username=" + username + "  age=" + age);
        return SUCCESS;
    }
```
#### html代码展示
```
<a href="springmvc/testRequestParam?username=atguigu&age=18">Test Request Param</a>
```

## @RequestHeader 绑定请求报头属性
请求报头包含若干个属性，服务器可据此获知客户端到信息，通过`@RequestHeader`即可将请求头中到属性值绑定到处理方法到入参中
#### java代码展示
```
    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value="Accept-Language") String lan) {
        System.out.println("testRequestHeader, Accept-Language:" + lan);
        return SUCCESS;
    }
```
#### html代码展示
```
<a href="springmvc/testRequestHeader">Test Request Header</a>
```

## @CookieValue 绑定请求中Cookie值
可让处理方法入参绑定某个Cookie值
#### java代码展示
```
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String sessionid) {
        System.out.println("testCookieValue  sessionid:" + sessionid);
        return SUCCESS;
    }
```
#### html代码展示
```
<a href="springmvc/testCookieValue">Test Cookie Value</a>
```
