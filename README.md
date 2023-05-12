# 招聘数据可视化分析系统

## 项目介绍

本系统是通过使用**Python + Selenium**爬虫程序采集**BOSS**直聘网站的招聘数据，并将采集到的招聘数据存储到**MySQL**数据库中，然后将存储在数据库中的招聘数据进行数据清洗，其中包括数据去重、统一字段类型和内容、删除不相关的数据等操作，之后对清洗后的数据进行分析，其中包括某一类岗位的招聘数量与学历、工作经验、公司类型、公司规模、城市分布等角度的分析；从学历、工作经验、公司类型、公司规模等角度分析某一类岗位的薪资水平；计算某一类岗位所出现的高频技能词并通过分析结果得出所要掌握的技能，最后为了直观的展示分析结果，设计并实现了一个招聘数据可视化分析系统将分析结果以可视化图表的形式展现。技术上采用**SpringBoot**框架搭建后台访问，使用**RESTful**类型的**API**为前台提供数据，系统前台界面采用**Vue + Element-UI**框架搭建，可视化图表采用**v-charts + echarts**图表库生成可视化图表。

## 项目运行

### 数据采集程序环境准备

- **Python 3.8.6**
- **Python**第三方库 **selenium**
- 下载与谷歌浏览器对应版本的 **chromedriver**
- **Pycharm** **2022.2.1**

### 运行爬虫

将**bosszp-spider**目录下爬虫程序导入到**Pycharm**中，打开**spiderMain**文件，找到程序里面的**main**函数，并对**main**函数里面的**spiderObj = spider('文案', city, 1)**这段代码进行修改，将文案修改为待爬取的岗位，然后使用终端进入到谷歌浏览器的安装目录并运行**./chrome.exe -remote-debugging-port=9222**这条命令，之后在启动好的谷歌浏览器打开**BOSS**直聘网站并扫码登录，完成上述步骤后即可运行爬虫程序。

### 后台环境准备

- **jdk 18.0.2**
- **maven 3.8.6**
- **nginx 1.23.1**
- **MySQL 8.0.31**
- **Redis 5.0.14**
- **IDEA 2022.2.1**
- **Navicat 16**

### nginx配置介绍

- 在**C**盘根目录创建**upload**文件夹，然后在**upload**文件夹里面分别创建**avatar**和**voice**文件夹
- **nginx**的安装目录需在**C**盘，安装完成并启动成功后在**nginx**安装目录下的**conf**文件夹里面修改**nginx.conf**配置文件，具体修改如下：

```xml
找到listen 80,然后在它下面添加或替换如下配置

        listen       80;

        server_name  localhost;

        sendfile        on;

        keepalive_timeout  65;

        charset utf-8;

        #access_log  logs/host.access.log  main;

        location / {

              add_header 'Access-Control-Allow-Origin' $http_origin;
              add_header 'Access-Control-Allow-Credentials' 'true';
              add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
              add_header 'Access-Control-Allow-Headers' 'DNT,web-token,app-token,Authorization,Accept,Origin,Keep-Alive,User-Agent,X-Mx-ReqToken,X-Data-Type,X-Auth-Token,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
              add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
              if ($request_method = 'OPTIONS') {
                  add_header 'Access-Control-Max-Age' 1728000;
                  add_header 'Content-Type' 'text/plain; charset=utf-8';
                  add_header 'Content-Length' 0;
                  return 204;
              }

	      root /upload/;
              index  index.html index.htm;	# 需要转发的url地址
        }

        location ^~/apm/ {
                proxy_pass http://localhost:8890/;
       }

        location ^~/apj/ {
                proxy_pass http://localhost:8890/admin/;
       }
```

- 配置修改完成重启一下**nginx**，若没有出现错误则**nginx**配置完成。

### 后台运行

使用**IDEA**导入**analyse**目录下所有的后台代码，待所有依赖下载完成后，根据自身情况修改**application.yml**文件里面的配置内容，修改完成后使用**Navicat**创建名为**bosszp**的数据库并导入与配置文件同级下的**bosszp.sql**文件，导入完数据库表后将采集到的招聘数据用**Navicat**导入到创建好的数据库的**job**表中，运行后台代码前还需对数据库中的数据进行清洗，首先数据去重和删除不相关的数据，然后是根据岗位名称中出现的关键词对每条岗位信息进行分类，最后是统一字段的类型或内容，下面给出两条处理后的示例数据：（只展示要处理的字段信息）

| address | handledAddress | transformAddress | type       | handledType        | dist   |
| ------- | -------------- | ---------------- | ---------- | ------------------ | ------ |
| 北京    | 北京-顺义区    | Beijing          | 运维工程师 | operationsEngineer | 顺义区 |
| 深圳    | 深圳-龙岗区    | Shenzhen         | 运维工程师 | operationsEngineer | 龙岗区 |

| workTag                                                      | handledWorkTag                                   | salary         | handledSalary  | avgSalary | salaryMonth |
| ------------------------------------------------------------ | ------------------------------------------------ | -------------- | -------------- | --------- | ----------- |
| ["服务器配置", "多进程", "多线程", "Linux", "算法基础", "数据结构", ""] | 服务器配置 多进程 多线程 Linux 算法基础 数据结构 | [9000, 11000]  | 9-11K/月       | 10000     | 0薪         |
| ["Python", "Java", "Go", "TypeScript", "分布式技术", "容器技术", "", ""] | Python Java Go TypeScript 分布式技术 容器技术    | [15000, 25000] | 15-25K/月·13薪 | 20000     | 13薪        |

| companyTags                                                  | handledCompanyTags                                           | companyPeople | handledCompanyPeople |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------- | -------------------- |
| 无                                                           |                                                              | [0, 20]       | 0-20人               |
| ["定期体检", "补充医疗保险", "零食下午茶", "员工旅游", "加班补助", "股票期权", "餐补", "节日福利", "年终奖", "五险一金"] | 定期体检 补充医疗保险 零食下午茶 员工旅游 加班补助 股票期权 餐补 节日福利 年终奖 五险一金 | [0, 10000]    | 10000人以上          |

数据处理完成后后台的数据的准备工作也就完成了，最后启动一下后台代码的主程序，若没有发生异常错误则后台运行成功。

### 前台环境准备

- **nodejs 16.16.0**
- **WebStorm 2022.2.1**

### 前台运行

首先使用**npm**命令全局安装**yarn**软件包管理器，然后使用**WebStorm**导入**recruitment-data-analysis**目录下的所有前台代码，导入完成使用**yarn install**命令安装所需模块，模块安装完成后运行**yarn run build**命令将项目进行打包，打包完成后会生成一个**dist**文件夹，将该文件夹下的所有文件全部放入到上面创建好**upload**文件夹中，完成后前台在**windows11**本地的访问地址为：**http://localhost/**
