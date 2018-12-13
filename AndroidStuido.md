## AS 问题说明

1. Error:java.lang.UnsupportedClassVersionError: com/android/dx/command/Main : 
   --原来 buildToolsVersion "24.0.0 rc3"出现了以上的错误 改为buildToolsVersion "23.0.2"（在app里的 build.gradle）   
2. 提示JVM内存不够
   -- 在project里的gradle.properties，去掉注释:  org.gradle.jvmargs=-Xmx1024m
   -- 此外还可能碰到这种情况 Error:Error: Could not create the Java Virtual Machine. 
   1. 修改 sdk\build-tools\21.1.2\dx.bat 目录下的值修改为： set defaultXmx=-Xmx1024M  
   2. [File]->[Invalidate Caches / Restart...]，即清除缓存, 重启 Gradle  
      参考： http://blog.sina.com.cn/s/blog_4c451e0e0102vh1l.html
                 http://www.cnblogs.com/fenglie/p/4208678.html 
3. 运行studio提示不能创建jvm
   -- 修改studio/bin目录下的studio.exe.vmoptions  
4. AS修改源码关联 
   -- .AndroidStudio2.0\config\options\jdk.table.xml (第一次自动关联，切换SDK版本，可修改此处)
   -- 参考修改： http://blog.csdn.net/android_study_ok/article/details/51859529 
   
   > ```
   >  <sourcePath>
   >   <root type="composite">
   >     <root type="simple" url="file://D:/adt-bundle-windows-x86/sdk/sources/android-24" />
   >   </root>
   > </sourcePath>
   > ```
5. 禁止自动打开上一个工程，这样会显示欢迎界面 
   Settings--> System settings --> (disable)reopen last project on startup 
   (http://www.cnblogs.com/smyhvae/p/4390905.html)

---

插件：  
1. ECTranslation： https://github.com/Skykai521/ECTranslation  调用有道API在线翻译   2. Markdown Support  AS里界面比较友好的Markdown编辑器
2. Statistic 统计代码行数
