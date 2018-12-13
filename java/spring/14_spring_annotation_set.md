## Spring中注解的说明集合
#### 1.@ContextConfiguration注解说明
* @ContextConfiguration Spring整合JUnit4测试时，使用注解引入多个配置文件
* 单个文件 `@ContextConfiguration(Locations="../applicationContext.xml")` 或者 `@ContextConfiguration(classes = SimpleConfiguration.class)`
* 多个文件 `@ContextConfiguration(locations = { "classpath*:/spring1.xml", "classpath*:/spring2.xml" })`
#### 2.@Component 泛指各种组件  (@Named 和@Component有微小差距，来自Java依赖注入规范)
* `@Component("lonelyHeartsClub")` 默认设置id为类名第一个字母小写，也可自己命名

#### 3.@Resource 按byName自动注入 @Autowired 按byType自动注入
#### 4.@Import注解是引入带有@Configuration的java类
#### 5.@ImportResource是引入spring配置文件.xml
#### 3.
#### 3.
#### 3.
#### 3.