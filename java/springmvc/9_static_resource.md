## 处理静态资源
1. 原因
    1. 优雅到RESt风格的资源URL，不希望带.html或.do等后缀
    2. 若将DispatcherServlet请求映射配置为`/`，则SpringMVC将捕获 WEB 容器到所有请求，包括静态资源的请求，当作一个普通的请求处理。因此找不到对应到处理器
2. 解决方法
    1. 在SpringMVC的配置文件中配置`<mvc:default-servlet-handler/>`
        1. 将在SpringMVC上下文定义一个`DefaultServletHttpRequestHandler`
        2. 会对`DispatcherServlet`的请求进行筛查，如果是没有映射的请求，就将该请求交由 WEB 应用服务器默认的servlet处理
        3. 如果不是静态资源，才由`DispatcherServlet`继续处理
        4. 一般 WEB 应用服务器默认的Servlet的名称都是`default`。
        5. 若所使用的 WEB 服务器的默认Servlet名称不是`default`，则需要通过`default-servlet-name` 属性显示指定
    2. 同时还需配置`<mvc:annotation-driven/>`