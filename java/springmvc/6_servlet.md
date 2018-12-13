## 使用Servlet API作为入参
MVC到Handler方法可以接受到ServletAPI类型到参数
1. HttpServletRequest
2. HttpServletResponse
3. HttpSession
4. java.security.Principal
5. Local
6. InputStream
7. OutputStream
8. Reader
9. Writer

#### pom中添加servlet依赖
```
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
      <scope>provided</scope>
    </dependency>
```

#### java代码展示
```
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest reqeust, HttpServletResponse response) {
        System.out.println("testServletAPI, request:" + reqeust);
        return SUCCESS;
    }
```
#### html代码展示
```
<a href="springmvc/testServletAPI">Test ServletAPI</a>
```