# minio-springboot-starter

#### 介绍
​	在springboot项目中集成minio对象存储。自动配置minioclient，并对基本的文件存储操作做了约定封装，使用更为简单明了。

* gitee: [minio-springboot-starter: springboot项目集成minio对象存储 (gitee.com)](https://gitee.com/FngSGitee/minio-springboot-starter)
* github: [FnSGit/minio-springboot-starter (github.com)](https://github.com/FnSGit/minio-springboot-starter)

#### 软件架构
软件架构说明

- jdk17

- spring-boot 2.7.4

- minio 8.4.5


#### 安装教程

1. 引入此maven依赖

   ```xml
   <dependency>
       <groupId>ink.fengshuai</groupId>
       <artifactId>minio-springboot-starter</artifactId>
       <version>1.0.0</version>
   </dependency>
   ```
   
   

#### 使用说明

1. 注入MinioFileStorage类，即可进行文件相关操作。亦可参见测试样例。

   

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
