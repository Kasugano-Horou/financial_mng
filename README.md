<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-d3d0a9303e11d522a06cd263f3079027715.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">帆楼财务管理系统</h1>
<h4 align="center">基于SpringBoot+Vue前后端分离的若依Java快速开发框架</h4>

[comment]: <> (<p align="center">)

[comment]: <> (	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://gitee.com/y_project/RuoYi-Vue/badge/star.svg?theme=dark"></a>)

[comment]: <> (	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/RuoYi-v3.8.1-brightgreen.svg"></a>)

[comment]: <> (	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>)

[comment]: <> (</p>)

## 项目简介

[comment]: <> (若依是一套全部开源的快速开发平台，毫无保留给个人及企业免费使用。)

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。  
* 流程引擎使用Flowable，包含表单配置和流程定义。  

## 内置功能

1.  合同管理：项目从合同开始，此模块包括合同基础信息的维护和合同文件的上传下载与提交申请。
2.  发票管理：资金流动靠发票作为凭证，对员工提交的发票信息进行核查并录入，添加合同收入发票。
3.  报销管理：员工申请报销，提交发票并申请报销流程。
4.  工资管理：记录每月员工的工资，包括五险一金和奖惩。
5.  采购管理：申请报销项目采购，提交发票并申请报销流程。
6.  项目成本：对正在进行或已完成的项目进行成本核算，核算内容有账期总收入、人员成本、采购成本、其他支出和总计利润。
7.  项目管理：对项目信息进行维护。
8.  工时管理：根据项目添加员工的工时信息，为项目人员成本核算提供数据。
9.  员工管理：一个员工对应一个用户，一个员工只属于一个部门。
10. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
11. 流程管理：完成合同申请、报销申请、采购申请等需要审核的任务。    
12. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
13. 岗位管理：配置系统用户所属担任职务。
14. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
15. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
16. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
17. 参数管理：对系统动态配置常用参数。
18. 通知公告：系统通知公告信息发布维护。
19. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
20. 登录日志：系统登录日志记录查询包含登录异常。
21. 在线用户：当前系统中活跃用户状态监控。
22. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
23. 缓存监控：对系统的缓存信息查询，命令统计等。
24. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

[comment]: <> (22. 定时任务：在线（添加、修改、删除&#41;任务调度包含执行结果日志。)
[comment]: <> (13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。)
[comment]: <> (24. 系统接口：根据业务代码自动生成相关的api接口文档。)
[comment]: <> (27. 在线构建器：拖动表单元素生成相应的HTML代码。)


## 演示图

<table>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/cd1f90be5f2684f4560c9519c0f2a232ee8.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/1cbcf0e6f257c7d3a063c0e3f2ff989e4b3.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8074972883b5ba0622e13246738ebba237a.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-9f88719cdfca9af2e58b352a20e23d43b12.png"/></td>
    </tr>
</table>