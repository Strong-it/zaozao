## HiddenHttpMethodFilter
浏览器form表单只支持`GET`与`POST`请求，而`DELETE`和`PUT`等method并不支持，Spring3.0添加了一个过滤器，可以将这些请求转换为标准到http方法，使得支持`GET、POST、PUT、DELETE`请求
* /order/1 HTTP `GET` 得到id=1的order
* /order/1 HTTP `DELETE` 删除id=1的order
* /order/1 HTTP `PUT` 更新id=1的order
* /order/1 HTTP `POST` 新增order

## 配置web.xml
```
  <filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
  </filter>

  <filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

## java代码展示
由于Tomcat7之后不支持PUT和DELETE相关方法，需要添加注解`@ResponseBody`
```
    @ResponseBody
    @RequestMapping(value="/testRest/{id}", method=RequestMethod.DELETE)
    public String testRestDelete(@PathVariable("id") Integer id) {
        System.out.println("testRest DELETE:" + id);
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value="/testRest/{id}", method=RequestMethod.PUT)
    public String testRestPut(@PathVariable("id") Integer id) {
        System.out.println("testRest PUT:" + id);
        return SUCCESS;
    }

    @RequestMapping(value="/testRest", method=RequestMethod.POST)
    public String testRestPost() {
        System.out.println("testRest POST:");
        return SUCCESS;
    }

    @RequestMapping(value="/testRest/{id}", method=RequestMethod.GET)
    public String testRestGet(@PathVariable("id") Integer id) {
        System.out.println("testRest GET:" + id);
        return SUCCESS;
    }
```

## html代码展示
```
    <br><br>
    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="submit" value="TestRest PUT"/>
    </form>
    <br><br>

    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="submit" value="TestRest DELETE"/>
    </form>
    <br><br>

    <form action="springmvc/testRest" method="post">
        <input type="submit" value="TestRest Post"/>
    </form>
```