# CanUI
>此框架支持APP UI自动化，采用appium+maven+testng+spring搭建
 
## 一.结构说明：
### 1.appium服务管理 /appium
*	1.1 ServerManager.java用于控制appium，启动/关闭appium命令在config.properties中配置
*	1.2 RunCommand.java分线程执行控制命令，可获取appium启动日志，启动成功后，开始创建driver
### 2.自动化工具类 /tools
*	2.1 BaseTools.java封装常用工具类，如截图、等待、图片对比等
*	2.2 BMPLoader.java图片对比工具类
*	2.3 Config.java、ElementConfig.java、SpringConfig.java读取配置及spring配置相关
*	2.4 SuiteListener.java、TestListener.java testng监听类，用于用例运行前开启appium以及用例失败截图
*	2.5 Element.java通过element.properties配置文件查找元素，提供常用操作方法，如滑动，判断元素是否存在，判断是否存在字符等
### 3.测试封装类 /testcase
*	3.1 BaseCase.java 封装一些常用的操作，可在用例中直接调用
### 4.资源配置文件 /resources
*	4.1 config.properties 配置运行环境，app属性及appium启动命令
*	4.2 element.properties 配置app元素查找方式
*	4.3 log4j.properties 运行日志配置
### 5.测试用例编写/test
*	5.1 BaseCaseTest.java测试用例父类，测试用例继承此类，提供测试准备及测试收尾工作

## 使用说明
> 详见TestCase.java
> 通过配置文件获取元素查找方式
> 元素查找封装为Element.get(String name)和Element.getList(String name)