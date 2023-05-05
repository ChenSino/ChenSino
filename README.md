![ChenSino's GitHub stats](https://github-readme-stats.vercel.app/api?username=ChenSino&show_icons=true&theme=radical)


#### 环境搭建

> jdk11+
> 
> maven3.8+
> 
> hosts改成自己的  
> 127.0.0.1 chensino-mysql  
> 127.0.0.1 chensino-minio  
> 127.0.0.1 chensino  
> 127.0.0.1 chensino-redis

#### 导入项目

1. 用ide工具导入项目，等待maven下载依赖


#### 数据库

1. 新建数据库chensino,编码utf8mb4_bin
2. 导入ChenSino/sql/chensino.sql文件

#### 配置修改

- Mysql数据库帐号密码修改
- redis帐号密码修改
- minio配置修改
- github第三方登录信息修改