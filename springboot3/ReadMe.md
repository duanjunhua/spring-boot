### SpringBoot3.x
1. 集成Web
2. 集成JPA持久化
    - 相关注解
      - `@Entity`：注解在类上，表示为JPA实体类，对应数据库表映射的对象
        - name:(可选)实体名称。默认为实体类的非限定名称。此名称用于引用查询中的实体
      - `@Table`：当实体类与其映射的数据库表名不同名时需要使用 @Table
        - name:(可选)表名，默认为实体名称
        - schema:(可选)表的schema，默认为用户的默认schema
      - `@Column`：指定持久化属性字段到数据库列的映射。属性和数据库字段一致可不指定
        - name：数据库列名称，默认持久化类属性字段名称
        - unique：(可选)列是否为唯一键
        - nullable: (可选)数据库列是否能为null,默认true
        - insertable: (可选)SQL INSERT是否包含该列,默认true
        - updatable: (可选)SQL UPDATE是否包含该列,默认true
        - table：(可选)表示当映射多个表时，指定表的表中的字段。默认值为主表的表名
        - length: (可选)列长度(仅当 使用字符串值列),默认255
        - precision：decimal字段长度, 默认0
        - scale：decimal字段,         小数点位数,默认0
      - `@Id`：指定实体主键，可以是任何Java基本类型
      - `@GeneratedValue`：提供主键值的生成策略的说明。主键生成策略`strategy`，默认`GenerationType.AUTO`，支持以下策略
          - `GenerationType.TABLE`：通过数据库表生成主键
          - `GenerationType.SEQUENCE`：使用数据库序列为实体分配主键
          - `GenerationType.IDENTITY`：使用数据库ID自增长为实体分配主键
          - `GenerationType.AUTO`：JPA为特定数据库选择适当的策略(默认)
      - `@Transient`：指定属性或字段不是持久的
      - `@Enumerated`：指定持久属性或字段作为枚举类型持久保存。若未指定@Enumerated注解，则EnumType值为ORDINAL，映射时的序号是从0开始的
      - `@MappedSuperclass`：指定一个类，该类的映射信息应用于从其继承的实体。用MappedSuperclass注解指定的类可以以与实体相同的方式进行映射，只是映射将只应用于它的子类
      - `@Embeddable`：指定一个类，其实例存储为所属实体的内在部分，并共享该实体的标识。嵌入对象的每个持久属性或字段都映射到实体的数据库表
      - 以及其他`@OneToOne`、`OneToMany`等
    - 持久化层
      - `PagingAndSortingRepository`: 继承Repository，实现了分页排序相关的方法
      ```
      @NoRepositoryBean
      public interface PagingAndSortingRepository<T, ID> extends Repository<T, ID> {
        Iterable<T> findAll(Sort sort);
        Page<T> findAll(Pageable pageable);
      }
      ```
      - `QueryByExampleExecutor`: 不属于Repository体系,接口允许通过实例Example执行动态查询
      ```
      public interface QueryByExampleExecutor<T> {
        <S extends T> Optional<S> findOne(Example<S> example);
        <S extends T> Iterable<S> findAll(Example<S> example);
        <S extends T> Iterable<S> findAll(Example<S> example, Sort sort);
        <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);
        <S extends T> long count(Example<S> example);
        <S extends T> boolean exists(Example<S> example);
        <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
      }
      ```
      - `JpaSpecificationExecutor`:不属于Repository体系，实现一组JPA Criteria查询相关的方法
      ```
      public interface JpaSpecificationExecutor<T> {
        Optional<T> findOne(@Nullable Specification<T> spec);
        List<T> findAll(@Nullable Specification<T> spec);
        Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
        List<T> findAll(@Nullable Specification<T> spec, Sort sort);
        long count(@Nullable Specification<T> spec);
        boolean exists(Specification<T> spec);
      }
      ```
      - `SimpleJpaRepository`：CrudRepository默认实现
    - 事务
      - 默认情况下，从SimpleJpaRepository继承的存储库实例上的CRUD方法是事务性的。对于读操作，事务配置readOnly标志设置为true，其他默认配置普通的@Transactional
    - 命名策略
      - 物理策略（`spring.jpa.hibernate.naming.physical-strategy`）和隐式策略（`spring.jpa.hibernate.naming.implicit-strategy`）
      - 默认Spring Boot使用CamelCaseToUnderscoresNamingStrategy配置物理命名策略，使用这种策略，所有的点都被下划线取代，驼峰大小写也被下划线取代。默认所有表名都是小写的
    - 动态数据源
      - `AbstractRoutingDataSource`：抽象DataSource实现，基于查找键(determineCurrentLookupKey())将getConnection()调用路由到目标数据源之一
      - 如查询走从库，增删改走主库
        - 定义数据源类型
        ```
        public class DsType {

           // 如：primary为主库、secondary为从库
           public final static String PRIMARY = "primary";
           public final static String SECONDARY = "secondary";
        
        }
        ```
        - 自定义动态数据源
        ```
        public class DynamicDs extends AbstractRoutingDataSource {

            /**
             * ThreadLocal 用于提供线程局部变量，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。
             */
            private static final ThreadLocal<String> DS_HOLDER = new ThreadLocal<>();
        
            public DynamicDs(DataSource defaultDs, Map<Object, Object> targetDs) {
              //默认目标数据源
              super.setDefaultTargetDataSource(defaultDs);
              //目标数据源集合。数据源切换时从此列表选择
              super.setTargetDataSources(targetDs);
              //属性设置
              super.afterPropertiesSet();
           }
        
           @Override
           protected Object determineCurrentLookupKey() {
              // 更具数据源key。获取选择的数据源。
              return getDataSource();
           }
        
           public static void setDataSource(String dataSource) {
              DS_HOLDER.set(dataSource);
           }
        
           public static String getDataSource() {
              return DS_HOLDER.get();
           }
        
           public static void clearDataSource() {
              DS_HOLDER.remove();
           }
        }
        ```
        - 数据源注入
        ```
        @Configuration
        public class DynamicDsConfig {
        
            //创建第一个主库数据源
            @Bean(name = "primaryDynamicDs")
            @ConfigurationProperties(prefix = "app.dynamicds.primary")
            public DataSource primaryDynamicDs() {
                return DataSourceBuilder.create().build();
            }
        
            //创建第二个从库数据源
            @Bean(name = "secondaryDynamicDs")
            @ConfigurationProperties(prefix = "app.dynamicds.second")
            public DataSource secondaryDynamicDs() {
                return DataSourceBuilder.create().build();
            }
        
            @Bean(name = "dynamicDs")   //动态数据源，实际使用的数据源时数据源路由根据key 选择的。默认数据源为第一个数据源(主库)
            @Primary    // 指定主数据源，使用的是此数据源
            public DynamicDs dataSource(DataSource primaryDynamicDs, DataSource secondaryDynamicDs) {
                Map<Object, Object> targetDataSources = new HashMap<>(2);
                targetDataSources.put(DsType.PRIMARY, primaryDynamicDs);
                targetDataSources.put(DsType.SECONDARY, secondaryDynamicDs);
                //默认返回的也是一个datasource
                return new DynamicDs(primaryDynamicDs, targetDataSources);
            }
        }        
        ```
        - 定义切面自动选择数据源
        ```
        @Slf4j
        @Aspect
        @Component
        public class DynamicDsAspect {
        
            @Autowired
            private DynamicDs dynamicDs;
        
            /**
             * 定义切面
             */
            @Pointcut("execution(* org.springframework.data.repository.CrudRepository.*(..))||execution(* com.duanjh.dynamicds.repository.*.*(..))")
            private void aspect(){
        
            }
        
            /**
             * 打印数据源引入
             */
            @Around("aspect()")
            public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
                String method = joinPoint.getSignature().getName();
                if (method.startsWith("find") || method.startsWith("select") || method.startsWith("query") || method.startsWith("search")) {
                    DynamicDs.setDataSource(DsType.SECONDARY);
                } else {
                    DynamicDs.setDataSource(DsType.PRIMARY);
                    log.info("Switch to Primary datasource...");
                }
                log.info("aop当前使用的数据源是:{}", dynamicDs.getConnection().getCatalog());
                try {
                    return joinPoint.proceed();
                } finally {
                    log.info("清除 datasource router...");
                    dynamicDs.clearDataSource();
                }
            }
        }      
        ```
3. 集成Redis
4. 集成Thymeleaf
    ```
    特点：
        1、在有网络和无网络的环境下皆可运行
        2、开箱即用，可以直接套用模板实现JSTL、OGNL表达式效果
        3、提供Spring标准方言和一个与SpringMVC完美集成的可选模块，可快速的实现表单绑定、属性编辑器、国际化
    
    标准表达式语法：
        1、变量表达式（即OGNL表达式或Spring EL表达式），如：
            <span th:text="${book.author.name}"></span>  
            <li th:each="book : ${books}"></li>
        2、选择或星号表达式：用一个预先选择的对象来代替上下文变量容器(map)来执行
            <div th:object="${book}">  
              ...  
              <span th:text="*{title}">...</span>  
              ...  
            </div>  
        3、文字国际化表达式：允许我们从一个外部文件获取区域文字信息(.properties)，用 Key 索引 Value，还可以提供一组参数(可选).
            <table>  
              ...  
              <th th:text="#{header.address.city}">...</th>  
              <th th:text="#{header.address.country}">...</th>  
              ...  
            </table>  
        4、URL 表达式：指的是把一个有用的上下文或回话信息添加到URL，这个过程经常被叫做URL重写，如：
            不带参数：@{/bootuser/list}
            设置参数：@{/bootuser/get(id=${orderId})}
            相对路径：@{../documents/report}
           
            <form th:action="@{/bootUser/create}">  
                <a href="main.html" th:href="@{/main}">
                ...
            </form>
        
    表达式支持的语法:
        1. 字面：
            文本文字: 'one text', 'Another one!',…
            数字文本: 0, 34, 3.0, 12.3,…
            布尔文本: true, false
            空: null
            文字标记: one, sometext, main,…
        2. 文本操作：
            字符串连接: +
            文本替换: |The name is ${name}|
        3. 算术运算
            二元运算符: +, -, *, /, %
            减号（单目运算符）: -
        4. 布尔操作
            二元运算符: and, or
            布尔否定（一元运算符）: !, not
        5. 比较和等价
            比较: >,<, >=,<= (gt, lt, ge, le)
            等值运算符: ==, != (eq, ne)
        6. 条件运算符
            f-then: (if) ? (then)
            If-then-else: (if) ? (then) : (else)
            Default:  (value) ?:(defaultvalue)
            
         如：
            'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
    ```
    - Thymeleaf 常用标签
    
    | 关键字         | 功能                     | 示例                                                                                                                                                   |
    |-------------|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
    | th:id       | 替换id                   | \<input th:id="'' + ${user.id}"/>                                                                                                                    |
    | th:text     | 文本替换                   | \<p th:text="${collect.description}" />                                                                                                              |
    | th:utext    | 支持html的文本替换            | \<p th:utext="${htmlcontent}" />                                                                                                                     |
    | th:object   | 替换对象                   | \<div th:object="${session.user}">                                                                                                                   |
    | th:value    | 属性赋值                   | \<input th:value="${user.name}" />                                                                                                                   |
    | th:with     | 变量赋值运算                 | \<div th:with="isEven=${prodStat.count}%2==0"></div>                                                                                                 |
    | th:style    | 设置样式                   | th:style="'display:' + @{(${sitrue} ? 'none' : 'inline-block')} + ''"                                                                                |
    | th:onclick  | 点击事件                   | th:onclick="'getCollect()'"                                                                                                                          |
    | th:each     | 属性赋值                   | \<tr th:each="user,userStat:${users}">                                                                                                               |
    | th:if       | 判断条件                   | \<a th:if="${'male' == data.sex}" >                                                                                                                  |
    | th:unless   | 和th:if判断相反             | \<a th:href="@{/login}" th:unless=${session.user != null}>Login</a>                                                                                  |
    | th:href     | 链接地址                   | \<a th:href="@{/login}" th:unless=${session.user != null}>Login</a> />                                                                               |
    | th:switch   | 多路选择 配合th:case 使用	     | \<p th:case="'admin'">User is an administrator</p>                                                                                                   |
    | th:case     | th:switch的一个分支         | \<input th:id="'' + ${user.id}"/>                                                                                                                    |
    | th:fragment | 布局标签，定义一个代码片段，方便其它地方引用 | \<div th:fragment="alert">                                                                                                                           |
    | th:include  | 布局标签，替换内容到引入的文件        | \<head th:include="layout :: htmlhead" th:with="title='xx'"></head> />                                                                               |
    | th:replace  | 布局标签，替换整个标签到引入的文件      | \<div th:replace="fragments/header :: title"></div>                                                                                                  |
    | th:selected | selected选择框 选中         | th:selected="(${xxx.id} == ${configObj.dd})"                                                                                                         |
    | th:src      | 图片类地址引入	               | \<img class="img-responsive" alt="App Logo" th:src="@{/img/logo.png}" />                                                                             |
    | th:inline   | 定义js脚本可以使用变量	          | \<script type="text/javascript" th:inline="javascript"><br/>                                                                                         |
    | th:action   | 表单提交的地址	               | \<form action="subscribe.html" th:action="@{/subscribe}">                                                                                            |
    | th:remove   | 删除某个属性	                | \<tr th:remove="all"> 1.all:删除包含标签和所有的孩子。2.body:不包含标记删除,但删除其所有的孩子。3.tag:包含标记的删除,但不删除它的孩子。4.all-but-first:删除所有包含标签的孩子,除了第一个。5.none:什么也不做。这个值是有用的动态评估。 |
    | th:attr     | 设置标签属性，多个属性可以用逗号分隔	    | 如 th:attr="src=@{/image/aa.jpg},title=#{logo}"                                                                                                       |
    
        标签的生效顺序：
            include > each > if/unless/switch/case > with > attr/attrprepend/attrappend > value/href > src > etc > text/utext > fragment > remove
    - Thymeleaf内嵌变量：可通过`#`符直接访问：
      - `dates` ： java.util.Date的功能方法类。
      ```
      如：
        ${#dates.format(date, 'dd/MMM/yyyy HH:mm')}
        ${#dates.setFormat(datesSet, 'dd/MMM/yyyy HH:mm')}
        ${#dates.createToday()}
        ...
      ```
      - `calendars` : 类似#dates，面向java.util.Calendar
      - `numbers` : 格式化数字的功能方法类
      - `strings` : 字符串对象的功能类，contains,startWiths,prepending/appending等等。
      ```
      如：
        ${#strings.isEmpty(name)}
        ${#strings.arrayIsEmpty(nameArr)}
        ${#strings.listIsEmpty(nameList)}
        ${#strings.startsWith(name,'Don')}
        ${#strings.endsWith(name,endingFragment)}
        ...
      ```
      - `objects`: 对objects的功能类操作。
      - `bools`: 对布尔值求值的功能方法。
      - `arrays`：对数组的功能类方法。
      - `lists`: 对lists功能类方法
      - ...
      - 常用的后台页面布局
    ```
    将整个页面分为头部，尾部、菜单栏、隐藏栏，点击菜单只改变 content 区域的页面
    
      <body class="layout-fixed">
        <div th:fragment="navbar"  class="wrapper"  role="navigation">
          <div th:replace="~{fragments :: head(title=~{::title/text()})}">Header</div>
          <div th:replace="~{fragments/left :: left}">left</div>
          <div th:replace="~{fragments/sidebar :: sidebar}">sidebar</div>
          <div layout:fragment="content" id="content" ></div>
          <div th:replace="~{fragments/footer :: footer}">footer</div>
        </div>
      </body>
    
    
    任何页面想使用这样的布局值只需要替换中见的 content 模块即可
      <html xmlns:th="http://www.thymeleaf.org" layout:decorator="layout">
       <body>
          <section layout:fragment="content">
        ...
      </html>
    也可以在引用模版的时候传参
      <head th:include="layout :: htmlhead" th:with="title='Hello'"></head>
    layout 是文件地址，如果有文件夹可以这样写fileName/layout:htmlhead，htmlhead 是指定义的代码片段
    ```
5. 集成 MybatisPlus
   - MybatisPlus 基于 mybatis开发，主要用于增强mybatis
   - springboot启动时将会加载MybatisPlusAutoConfiguration类
   - 常用注解
     - `@TableName`：标注实体对应的表
     - `@TableId`：字段注解（用于主键）
     - `@TableField`：字段注解（用于非主键）
     - `@Version`：乐观锁注解
     - `@EnumValue`：普通枚举类注解(注解在枚举字段上)
     - `@TableLogic`：表字段逻辑处理注解（逻辑删除）
     - `@OrderBy`：内置SQL默认指定排序，优先级低于wrapper条件查询
6. 集成消息队列RabbitMQ
   - RabbitMQ即一个消息队列，主要是用来实现应用程序的异步和解耦，同时也能起到消息缓冲，消息分发的作用
   - RabbitMQ高可用、高性能、灵活
   - AMQP的主要特征：面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全
   - RabbitMQ交换机有四种类型：
     - Direct（默认模式）：是”先匹配, 再投送”。即在绑定时设定一个routing_key, 消息的routing_key匹配时, 才会被交换器投送到绑定的队列中去
     - Topic：按规则转发消息（最灵活）
       - 路由键必须是一串字符，用句号（.）隔开，比如说`agreements.us`，或者`agreements.eu.stockholm`等
       - 路由模式必须包含一个星号（*），主要用于匹配路由键指定位置的一个单词，比如说，一个路由模式是这样子：`agreements..b.*`
       - 发送消息格式
       ```
       // 第一个参数表示交换机，第二个参数表示 routing key，第三个参数即消息
       rabbitTemplate.convertAndSend("testTopicExchange","key1.a.c.key2", " this is  RabbitMQ!");
       ```
     - Headers：设置`header attribute`参数类型的交换机
     - Fanout（广播模式或者订阅模式）：转发消息到所有绑定队列
7. 集成定时任务
8. 集成邮件服务
   - 针对主业务、副业务可以采用MQ实现异步发送，如：
     - 1.接收到发送邮件请求，首先记录请求并且进行主业务操作
     - 2.调用邮件发送接口发送邮件，并且将发送结果记录入库
     - 3.启动定时任务扫描时间段内，未发送成功的邮件，进行再次发送
9. 集成MongoDB
    - 非关系数据库
    - 基于分布式文件存储的数据库
    - 一般用做离线数据分析来使用
    - 数据层级：
      - 关系型：数据库（`database`）、表（`table`）、记录（`record`）
      - MongoDB：数据库（`database`）、集合（`collection`）、文档对象（`document`）
    - 一条记录就是一个文档(与JSON对象类似)，是一个数据结构，由字段和值对组成
    - 适合对大量或者无固定格式的数据进行存储。如：日志、缓存
    - 不适用复杂的多文档（多表）的级联查询
10. 打包及报数
    - 打`war`包
      - 修改`pom`
      ```
      // 打包方式由jar改为war
      <packaging>war</packaging>
      
      // 排除tomcat
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-tomcat</artifactId>
          <!-- scope属性改为provide -->
          <scope>provided</scope>
      </dependency>
      ```
      - 注册启动类
      ```
      // 创建ServletInitializer.java，继承 SpringBootServletInitializer ，覆盖 configure()，把启动类 Application 注册进去
      public class ServletInitializer extends SpringBootServletInitializer {
          @Override
          protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
              return application.sources(Application.class);
          }
      }
      ```
    - 生产运维
      - 查看JVM参数（用的是什么gc、新生代、老年代分批的内存都是多少等）：`jinfo -flags {pid}`
        - `-XX:CICompilerCount` ：最大的并行编译数
        - `-XX:InitialHeapSize与-XX:MaxHeapSize`：指定 JVM 的初始和最大堆内存大小
        - `-XX:MaxNewSize`：JVM 堆区域新生代内存的最大可分配大小
    - 启动配置
      - 直接运行
      ```
      // 第一步：配置
      <plugin>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
          <configuration>
              <executable>true</executable>
          </configuration>
      </plugin>
      
      // 第二步：打包运行
      ./appName.jar
      ```
      - 注册服务
      ```
      // 1、做一个软链接指向jar包并加入到init.d中，然后用命令来启动
      ln -s /home/app/appName.jar /etc/init.d/appName
      chmod +x /etc/init.d/appName

      // 2、启动运行
      service appName start|stop|restart
      ```
    - 自定义`Favicon`
    ```
    // 1、在application.propertie 中关闭原有的logo
    spring.mvc.favicon.enable=false 
    
    // 2、将自己的favicon.ico 放到src/main/resources/static下
    ```
11. 集成`Shiro`
    - `Shiro`功能架构
      - `Authentication`（认证）：用户身份识别，也即是用户“登录”
      - `Authorization`（授权）：访问控制。如用户是否具有某个操作的使用权限
      - `Session Management`（会话管理）：特定于用户的会话管理
      - `Cryptography`（加密）：在对数据源使用加密算法加密的同时，保证易于使用
    - 功能支持：
      - Web支持：提供的 Web 支持 api ，可以很轻松的保护 Web 应用程序的安全
      - 缓存：是Apache Shiro保证安全操作快速、高效的重要手段
      - 并发：支持多线程应用程序的并发特性
      - 测试：支持单元测试和集成测试
      - “Run As”：允许用户假设另一个用户的身份(在许可的前提下)
      - “Remember Me”：跨session记录用户的身份，只有在强制需要时才需要登录
    - 三大概念：
      - `Subject`：当前用户
      - `SecurityManager`：管理所有Subject
      - `Realm`：用于进行权限信息的验证，需要具体实现。本质上是一个特定的安全`DAO`，封装与数据源连接的细节，得到Shiro所需的相关的数据。必须指定至少一个Realm来实现认证和（或）授权
        - 实现Realms的`Authentication`（用来验证用户身份）和`Authorization`（用于对用户进行的操作授权）
    - `FilterChain`
      - 定义
        - 一个URL可以配置多个Filter，使用逗号分割
        - 当配置多个过滤器时，全部验证通过，才视为通过
        - 部分过滤器可指定参数，如：roles
      - `Shiro`内置的`FilterChain`
      
        | Filter Name | Class                                                            |
        |-------------|------------------------------------------------------------------|
        | anon        | org.apache.shiro.web.filter.authc.AnonymousFilter                |
        | authc       | org.apache.shiro.web.filter.authc.FormAuthenticationFilter       |
        | authcBasic  | org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter  |
        | perms       | org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter |
        | port        | org.apache.shiro.web.filter.authz.PortFilter                     |
        | rest        | org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter     |
        | roles       | org.apache.shiro.web.filter.authz.RolesAuthorizationFilter       |
        | ssl         | org.apache.shiro.web.filter.authz.SslFilter                      |
        | user        | org.apache.shiro.web.filter.authc.UserFilter                     |
        - `anon`：所有url都可以匿名访问
        - `authc`：需要认证才能进行访问
        - `user`：配置记住我或认证通过可以访问
    - 登录认证实现：
      - 在Shiro中，是通过`Realm`来获取应用程序中的用户、角色及权限信息。Shiro的认证过程最终会交由Realm执行，会调用Realm的getAuthenticationInfo(token)方法
        - 1、检查提交的进行认证的令牌信息
        - 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
        - 3、对用户信息进行匹配验证
        - 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例；验证失败则抛出AuthenticationException异常信息
      - 需自定义一个Realm类，继承AuthorizingRealm 抽象类，重载doGetAuthenticationInfo()，重写获取用户信息的方法
      ```java
      @Slf4j
      @Configuration
      public class PersonalRealm extends AuthorizingRealm {
      
          @Autowired
          SysUserMapper sysUserMapper;
      
          /**
           * 自定义授权
           *    当访问到页面的时候，链接配置了相应的权限或者Shiro标签才会执行此方法否则不会执行
           *    主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加，也可以添加 set 集合：roles 是从数据库查询的当前用户的角色，strPermissions是从数据库查询的当前用户对应的权限
           *      authorization.setRoles(roles);
           *      authorization.setStringPermissions(strPermissions);
           *
           *    如：
           *      filterChainDefinitionMap.put(“/add”, “perms[权限添加]”)表示访问/add这个链接必须要有“权限添加”这个权限才可以访问
           *      filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”)表示访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问
           *
           */
          @Override
          protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      
              log.info("权限配置");
      
              SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
      
              // 获取当前用户信息
              SysUser user = (SysUser) principals.getPrimaryPrincipal();
      
              /**
               * 角色及权限配置
               */
      
      
              // 如果有管理员角色直接全员
              if(CollectionUtils.isNotEmpty(user.getRoles()) && user.getRoles().parallelStream().filter(r -> "admin".equals(r.getRole())).findAny().isPresent()) {
                  authorization.addRole("admin");
                  authorization.addStringPermission("*:*:*");
                  return authorization;
              }
      
              //若没有管理员角色则赋予所有角色权限集合
              for (SysRole role : user.getRoles()) {
                  authorization.addRole(role.getRole());
                  for (SysPermission permission : role.getPermissions()) {
                      authorization.addStringPermission(permission.getPermission());
                  }
              }
      
              return authorization;
          }
      
          /**
           * 自定义认证
           */
          @Override
          protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
      
              // 1. 获取用户输入的用户名
              UsernamePasswordToken upToken = (UsernamePasswordToken) authToken;
              String username = upToken.getUsername();
      
              // 2.根据用户名查询用户
              SysUser user = sysUserMapper.selectByUsername(username);
      
              log.info("查询到的用户：{}", user);
      
              // 3. 将查询到的用户封装为认证信息
              if(ObjectUtils.isEmpty(user)){
                  throw  new UnknownAccountException("账户不存在");
              }
      
      
      
              return new SimpleAuthenticationInfo(
                      user,   // 账号
                      user.getPassword(), // 密码
                      ByteSource.Util.bytes(user.getCredentialsSalt()),  // 自定义认证盐
                      this.getName()  // 当前realm名
              );
          }
      }
      ```
      - Shiro核心配置
        - 拦截配置
        ```java
        @Bean
        public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
            log.info("ShiroConfig filter");

            ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
            filterBean.setSecurityManager(securityManager);

            /*--------------- 拦截器 ---------------*/
            Map<String, String> map = new LinkedHashMap<>();

            // 配置不会拦截的链接，顺序判断
            map.put("/static/**", "anon");
            map.put("/login", "anon");
            map.put("/loginPage", "anon");

            // 配置退出过滤器
            map.put("/logout", "logout");

            /**
             * 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
             * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
             */
            map.put("/**", "authc");

            // 配置登录，若不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
            filterBean.setLoginUrl("/loginPage");

            // 配置登录成功后跳转的链接
            filterBean.setSuccessUrl("/index");

            // 未授权界面
            filterBean.setUnauthorizedUrl("/unauthorized");

            filterBean.setFilterChainDefinitionMap(map);

            return filterBean;
        }
        ```
        - 安全配置
        ```
        /**
         * 方式一：用户登录密码不加密的Realm
         */
        @Bean(name = "customRealm")
        public PersonalRealm personalCustomRealm() {
           return new PersonalRealm();
        }

        
        /**
         * 方式二，用户密码使用MD5加解密的Realm
         */
        @Bean(name = "md5Realm")
        public PersonalRealm personalMd5Realm(){
           PersonalRealm realm = new PersonalRealm();
 
           // 1. 创建hashed的凭证匹配器
           HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
 
           // 2. 设置 md5 加密算法
           matcher.setHashAlgorithmName("md5");
 
           // 3. 设置散列次数
           matcher.setHashIterations(1024);
 
           // 4.  设置hashed的凭证匹配器
           realm.setCredentialsMatcher(matcher);
 
           return realm;
        }
        ```
        - 使用注解（如：`@RequiresPermissions`）时的认证或权限异常处理
        ```
        /**
         * 开启Shiro注解通知器
         */
        @Bean
        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
            AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
            advisor.setSecurityManager(securityManager);
            return advisor;
        }
        
        /**
         * 认证或权限异常处理
         *  Shiro中若使用注释来注入角色或权限的话，无法抛出UnauthorizedException异常，需要使用SimpleMappingExceptionResolver处理，
         *  若不添加，会跳转至默认的error.html，通过自定义可跳转指定页面
         */
        @Bean
        public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
           SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
           Properties properties = new Properties();
           // 注意/unauthorized需要有对应的页面名称，如：unauthorized.html
           properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/unauthorized");
           resolver.setExceptionMappings(properties);
           return resolver;
        }
        ```
    - 开启JPA审计
      - 开启JAP审计，启动类添加`@EnableJpaAuditing`
      ```
      // jpaUserAuditor为自定义配置的AuditorAware
      @EnableJpaAuditing(auditorAwareRef = "jpaUserAuditor")  // 开启JPA审计，使得@CreateDate、@UpdateTimestamp生效，若要@CreateBy、@LastModifiedBy生效需实现AuditorAware并指定auditorAwareRef
      @SpringBootApplication
      // ...
      ```
      - 配置JPA中@CreateBy、@LastModifiedBy自动注入
      ```
      @Slf4j
      @Component(value = "jpaUserAuditor")
      public class JpaUserAuditor implements AuditorAware<String> {
      
          @Override
          public Optional<String> getCurrentAuditor() {
              //此处使用Shiro认证后获取到的用户
              Subject subject = SecurityUtils.getSubject();
              log.info("Current user: {}", subject.getPrincipal());
              if(subject.isAuthenticated()) {
                  SysUser user = (SysUser) subject.getPrincipal();
                  return Optional.ofNullable(user.getUsername());
              }
              return Optional.of("system");
          }
      }
      ```
      - 实体类开启注册监听，实体类添加注解`@EntityListeners(AuditingEntityListener.class)`
      - 实体类创建时的属性需加上只新增时创建的注解`@Column(updatable = false)`，否则更新时会被置空
      ```
      @CreationTimestamp
      @Column(updatable = false)    // 标注只在创建时设置
      private LocalDateTime createTime;
  
      @CreatedBy
      @Column(updatable = false)  // 标注只在创建时设置
      private String createBy;
      ```
12. 自定义动态Banner
    - 在网站`http://www.network-science.de/ascii/` 将自己的文字转换成字符串，如：
    - 在`resources`目录下新建banner.txt文件
13. 项目启动时初始化资源`CommandLineRunner`
    - `CommandLineRunner`接口的`Component`会在所有`Spring Beans`都初始化之后，SpringApplication.run()之前执行，适合在应用程序启动之初进行一些数据初始化
14. 集成Memcached
    - Memcached是一个高性能的分布式内存对象缓存系统，用于动态Web应用以减轻数据库负载
    - Memcached基于一个存储键值对的hashmap
    - `Spymemcached`是一个采用Java开发的异步、单线程的Memcached客户端，使用`NIO`实现
    - 添加依赖
    ```
    <dependency>
        <groupId>net.spy</groupId>
        <artifactId>spymemcached</artifactId>
        <version>2.12.2</version>
    </dependency>
    ```
    - 配置memcached的ip地址与端口
    ```
    memcache.ip=127.0.0.1
    memcache.port=11211
    ```
    - 设置配置对象并注册
    ```
    // 设置资源
    @Data
    @Component
    @ConfigurationProperties(prefix = "memcached")
    public class MemcacheSource {
    
        private String host;
    
        private int port;
    }    
    
    
    // 注册
    @Slf4j
    @Getter
    @Component
    public class MemcachedRunner implements CommandLineRunner {
    
        @Resource
        MemcacheSource source;
    
        MemcachedClient client = null;
    
        @Override
        public void run(String... args) throws Exception {
    
            try {
                log.info("初始化Memcached客户端");
                client = new MemcachedClient(new InetSocketAddress(source.getHost(), source.getPort()));
            }catch (Exception e){
                log.error("初始化MemcachedClient失败，失败信息{}", e.getLocalizedMessage());
            }
        }
    }
    ```
15. 响应式编程集成WebFlux
    - `Spring 5.0`及以上提供了`Webflux`组件
    - 响应式编程是一种面向数据流和变化传播的编程范式，计算模型会自动将变化的值通过数据流进行传播
    - 响应式编程是基于异步和事件驱动的非阻塞程序，只需要在程序内启动少量线程扩展
    - Webflux依赖`Reactor`而构建。Reactor是一个基于JVM之上的异步应用基础库，Reactor中有两个非常重要的概念Flux和Mono
      - Flux表示的是包含**0到N个**元素的异步序列。序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息，当消息通知产生时，订阅者中对应的方法`onNext()、onComplete()、onError()`会被调用
      - Mono表示的是包含**0或者1个**元素的异步序列。Flux和Mono之间可以进行转换
    - 服务器端WebFlux支持2种不同的编程模型
      - 基于注解的@Controller和其他注解也支持Spring MVC
      - Functional 、Java8 lambda风格的路由和处理
    - WebFlux模块从上到下依次是`Router Functions、WebFlux、Reactive Streams`三个新组件
      - `Router Functions`：对标准的`@Controller、@RequestMapping`等的Spring MVC注解，提供一套函数式风格的API，用于创建Router、Handler和Filter
      - `WebFlux`：核心组件，协调上下游各个组件提供响应式编程 支持
      - `Reactive Streams`：一种支持背压 (Backpressure)的异步数据流处理标准，主流实现有`RxJava、Reactor`，Spring WebFlux集成的是Reactor
    - **注意**：支持reactive编程的数据库只有MongoDB、redis、Cassandra、Couchbase
    - 一般来说，Spring MVC用于同步处理，Spring Webflux用于异步处理
16. 事件与监听
    - 自定义事件实现`ApplicationEvent`
    - 事件发布
    ```
    @Slf4j
    @Service
    public class PublishPersonalEvent implements ApplicationEventPublisherAware {
    
        private List<String> personalEvents;
    
        private ApplicationEventPublisher publisher;
    
        public void setPersonalEvents(List<String> personalEvents) {
            this.personalEvents = personalEvents;
        }
    
        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.publisher = applicationEventPublisher;
        }
    
        public void sendPersonalEvent(String address, String content){
            log.info("-----------发布事件-------------");
            publisher.publishEvent(new PersonalEvent(this, address, content));
        }
    }
    ```
    - 事件监听
    ```
    // 方式一：实现
    @Slf4j
    @Data
    @Component
    public class BaseApplicationEventListener implements ApplicationListener<PersonalEvent> {
    
        private String notificationAddress;
    
        @Override
        public void onApplicationEvent(PersonalEvent event) {
            log.info("---------------ApplicationListener监听-------");
        }
    }
    
    // 方式二：注解 
    @Slf4j
    @Data
    @Component
    public class BaseAnnotationEventListener {
    
        private String notificationAddress;
    
        /**
         * 异步事件监听器方法不能通过返回值来发布后续事件，多个监听器可以添加@Order(num)注解来设置监听器顺序
         */
        @Async  // 开启异步监听
        @EventListener
        public void onApplicationEvent(PersonalEvent event) {
            log.info("---------------【BaseAnnotationEventListener】监听-------");
        }
    }
    ```
17. 配置与配置源
    - 外部化配置
      - SpringBoot使用了一个非常特殊的PropertySource顺序，其目的是允许合理地重写值，属性优先级如下（排序后的覆盖排序前的）
        - 默认配置：(SpringApplication.setDefaultProperties设置的属性)
        - @PropertySource注解配置在@Configuration类上
        - 配置数据：如application.properties配置文件
        - RandomValuePropertySource，它的属性只有random.*
        - 操作系统环境变量
        - Java系统属性：`System.getProperties()`
        - 来自`java:comp/env`的`JNDI`属性
        - ServletContext初始化参数
        - ServletConfig初始化参数
        - SPRING_APPLICATION_JSON属性
        - 命令行参数
      - 配置文件优先级顺序:
        - 打包在jar中的应用配置`application.properties`和`application.yml`
        - 打包在jar中的特定应用配置`application-{profile}.properties`和`application-{profile}.yml`
        - 打包的jar之外的应用配置`application.properties`和`application.yml`
        - 打包在jar之外的特定应用配置`application-{profile}.properties`和`application-{profile}.yml`
        - 如果配置文件的位置同时包含`properties`和`yml`格式，则`properties`**优先**
18. Logback日志配置
    - 默认当日志文件达到10M时会归档，重新输出到新文件中
    - `logging.file.name`：写入指定的日志文件。名称可以是确切的位置或相对于当前目录
    - `logging.file.path`：写入指定目录。名称可以是确切的位置或相对于当前目录
    - `logging.logback.rollingpolicy.file-name-pattern`：用于创建日志归档的文件名方式
    - `logging.logback.rollingpolicy.clean-history-on-start`：是否应该在应用程序启动时进行日志归档清理。
    - `logging.logback.rollingpolicy.max-file-size`：归档前日志文件的最大大小(默认10m)
    - `logging.logback.rollingpolicy.total-size-cap`：在删除日志文件之前，日志文件的最大容量(默认0)
    - `logging.logback.rollingpolicy.max-history`：保留的归档日志文件的最大数量(默认为7)
19. JSON
    - 提供三个JSON映射集成：`Gson`、`Jackson`（默认）、`JSON-B`
    - SpringBoot自带的JSON格式转换，`HttpMessageConverter`实现有如下几种：
      - `MappingJackson2HttpMessageConverter`（默认）
      - `JsonbHttpMessageConverter`
      - `GsonHttpMessageConverter`
      - 具体转换方式配置：`spring.http.converters.preferred-json-mapper=jackson`
    - jackson常用配置属性：
      ```
      # 属性命名策略，PropertyNamingStrategy常量，SNAKE_CASE驼峰转下划线
      spring.jackson.property-naming-strategy=SNAKE_CASE
      # @JsonFormat的格式转换
      spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
      #设置全局时区
      spring.jackson.time-zone=GMT+8
      #属性null值处理方式，非空才序列化
      spring.jackson.default-property-inclusion=non_null
      #枚举类SerializationFeature
      #Date转换成timestamp
      spring.jackson.serialization.write-dates-as-timestamps=true
      #对象为null报错
      spring.jackson.serialization.fail-on-empty-beans=true
      #枚举类DeserializationFeature
      #反序列化不存在属性时是否报错，默认true
      spring.jackson.deserialization.fail-on-unknown-properties=false
      #使用getter取代setter探测属性，如类中含getName()但不包含name属性与setName()，json输出包含name。默认false
      spring.jackson.mapper.use-getters-as-setters=true
      #枚举类JsonParser.Feature
      #是否允许出现单引号，默认false
      spring.jackson.parser.allow-single-quotes=true
      ```
    - Gson使用
      - 需要排除默认的jackson以及引入Gson依赖
        ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-json</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>
        ```
      - 常用配置
      ```
      # 序列化Date对象时使用的格式。
      spring.gson.date-format=yyyy-MM-dd HH:mm:ss
      #是否禁用HTML字符转义，如'<'，'>'等。
      spring.gson.disable-html-escaping=true
      #是否在序列化过程中排除内部类。
      spring.gson.disable-inner-class-serialization=false
      #是否启用复杂映射键(即非原语)的序列化
      spring.gson.enable-complex-map-key-serialization=false
      #在序列化或反序列化时，是否排除所有没有\"Expose\"注释的字段
      spring.gson.exclude-fields-without-expose-annotation=true
      #在序列化和反序列化期间应用于对象字段的命名策略 FieldNamingPolicy
      spring.gson.field-naming-policy=UPPER_CAMEL_CASE
      #是否通过在输出前加上一些特殊文本来生成不可执行的JSON。
      spring.gson.generate-non-executable-json=false
      #是否对不符合RFC 4627的JSON进行宽容的解析。
      spring.gson.lenient=false
      # Long类型和long类型的序列化策略。LongSerializationPolicy
      spring.gson.long-serialization-policy=DEFAULT
      # 是否输出适合页面的序列化JSON以进行漂亮的打印。
      spring.gson.pretty-printing=true
      # 是否序列化空字段。
      spring.gson.serialize-nulls=true
      ```
    - 使用外部Fastjson需要创建`FastJsonHttpMessageConverter`，覆盖默认的`HttpMessageConverter`
    ```
    方式一：
    @Configuration
    public class FastJsonGlobalConfig {
        @Bean
        FastJsonHttpMessageConverter getconvers(){
            FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
            FastJsonConfig jsonConfig = new FastJsonConfig();
            jsonConfig.setCharset(Charset.forName("UTF-8"));
            jsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
            converter.setFastJsonConfig(jsonConfig);
            converter.setDefaultCharset(Charset.forName("Utf-8"));
            return converter;
        }
    }

    方式二：
    @Configuration
    public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
            FastJsonConfig jsonConfig = new FastJsonConfig();
            jsonConfig.setCharset(Charset.forName("UTF-8"));
            jsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
            converter.setFastJsonConfig(jsonConfig);
            converter.setDefaultCharset(Charset.forName("Utf-8"));
            converters.add(0, converter);
        }
    }
    ```
20. Servlet Web应用
    - 提供一种轻量级函数式编程模型，其中函数用于路由和处理请求。它是基于注解的编程模型的替代方案
      - 可以定义任意数量的RouterFunction bean来模块化路由器的定义。如果需要应用优先级，可以对bean进行排序
    ```
    // 第一步：创建处理器
    @Slf4j
    @Component
    public class BootUserWebMvcHandler {
    
        @Autowired
        BootUserReposotory reposotory;
    
        public ServerResponse listUsers(ServerRequest request) {
            log.info("listUsers");
            List<BootUser> list = reposotory.findAll();
            return ServerResponse.ok().body(list);
        }
    
        public ServerResponse findUser(ServerRequest request) {
            String username = request.pathVariable("username");
            log.info("findUser，username: {}", username);
            if(StringUtils.hasText(username)) {
                BootUser user = reposotory.findByUsername(username);
                return ServerResponse.ok().body(user);
            }
            return ServerResponse.ok().build();
        }
    }
    
    // 第二步，构建Route
    @Slf4j
    @Configuration(proxyBeanMethods = false)
    public class RoutingConfiguration {
    
        private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);
    
        @Bean
        public RouterFunction<ServerResponse> routerFunction(BootUserWebMvcHandler handler) {
            return route()
                    .GET("/route/list", ACCEPT_JSON, handler::listUsers)
                    .GET("/route/find/{username}", ACCEPT_JSON, handler::findUser)
                    .build();
        }
    }
    ```
    - Spring MVC支持各种模板技术，包括Thymeleaf、FreeMarker和jsp
    - 跨域
      - 从4.2版开始，Spring MVC支持CORS
      - 全局CORS配置可以通过使用自定义的addCorsMappings (CorsRegistry)方法注册WebMvcConfigurer bean来定义
      ```
      @Configuration(proxyBeanMethods = false)
      public class MyCorsConfiguration {
          @Bean
          public WebMvcConfigurer corsConfigurer() {
              return new WebMvcConfigurer() {
                  @Override
                  public void addCorsMappings(CorsRegistry registry) {
                      registry.addMapping("/api/**");
                  }
              };
          }
      }
      ```
    - 当使用嵌入式容器时，通过使用@ServletComponentScan来启用带有@WebServlet、@WebFilter和@WebListener注释的类的自动注册
    - 常用的服务器设置：
      - 监听端口(server.port)与绑定地址(server.address)
      - Session设置：
        - 会话是否持久(server.servlet.session.persistent)
        - session超时(server.servlet.session.timeout)
        - 会话数据存储位置（server.servlet.session.store-dir）
        - session-cookie配置（server.servlet.session.cookie.*）
      - 错误页面的路径(server.error.path)
    - Undertow不支持jsp
21. 数据连接
    - HikariCP
        - HikariCP是一个“零开销”的生产JDBC连接池。快速、简单、可靠
        - HikariCP包含许多单独的微优化
        - FastList替代ArrayList
          - 避免ArrayList<Statement>每次get()调用都要进行范围检查
          - 避免ArrayList调用remove(Object)时的从头到尾的扫描，支持快速插入和删除
          - CPU的时间片算法进行优化
    - Druid
      - 是Java语言中最好的数据库连接池。Druid能够提供强大的监控和扩展功能
      - Druid集成
        - 引入依赖`druid-spring-boot-starter`
        - 属性配置`spring.datasource.druid.*`（`name、url、username、password、driverClassName、initialSize、maxActive、keepAlive、...`）
    - Spring的JdbcTemplate和NamedParameterJdbcTemplate类在Spring Boot中自动配置，可直接注入使用
22. REST
    - 主流的三种Web服务交互方案
      - REST（Representational State Transfer）
      - SOAP（Simple Object Access protocol）
      - XML-RPC
    - 常见状态码
      - 2xx
        - 200：OK，服务器成功返回请求的数据，该操作是幂等的（Idempotent）
        - 201：CREATED，新建或修改数据成功
        - 202：Accepted，表示一个请求已经进入后台排队（异步任务）
        - 204：NO CONTENT，删除数据成功
      - 4xx
        - 400：INVALID REQUEST，发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
        - 401：Unauthorized，表示没有权限（令牌、用户名、密码错误）
        - 403：Forbidden，表示得到授权（与401错误相对），但是访问是被禁止的
        - 404：NOT FOUND，用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的
        - 406：Not Acceptable，用户请求的格式不可得（如请求JSON格式，但是只有XML格式）
        - 410：Gone，请求的资源被永久删除，且不会再得到的
      - 500： INTERNAL SERVER ERROR，服务器发生错误，用户将无法判断发出的请求是否成功
23. 集成SpringDoc
    - springdoc-openapi帮助使用SpringBoot项目自动化API文档的生成。自动生成JSON/YAML和HTML格式的API文档，文档可以通过使用swagger-api注解来完成
    - SpringBoot3使用的是JakartaEE9，对应的springdoc版本需要升级到v2，v2支持以下内容
      - OpenAPI3（Swagger3是基于OpenAPI3的，Swagger2予2017年停止维护）
      - Spring-boot v3 (Java 17 & Jakarta EE 9)
      - JSR-303 特别注解 @NotNull, @Min, @Max, 和 @Size
      - Swagger-ui
      - OAuth2
      - GraalVM native images
    - 引入依赖
    ```
    <!-- WebMvc支持 -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.14</version>
    </dependency>
    <!-- WebFlux 支持 -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
        <version>2.8.14</version>
    </dependency>    
    ```
    - 自定义配置（可不配置默认）
    ```
    @Configuration
    public class SpringDocConfig {
    
        @Bean
        public OpenAPI openAPI(){
            return new OpenAPI()
                    .info(new Info().title("Personal Spring Doc")
                            .description("自定义SpringDoc配置")
                            .version("v1.0.0")
                    );
        }
    }    
    ```
    - 访问地址
    ```
    方式一：
    http(s)://ip:port/swagger-ui/index.html
    
    方式二（以JSON格式展示）：
    // context-path表示上下文
    http(s)://ip:port/context-path/v3/api-docs
    ```
    - 相关注解
      - `@Tag`：描述整个Controller
      - `@Operation`：描述具体接口信息
      - `@Parameter`：描述参数信息
      - `@ApiRespose`：接口响应描述信息
      - `@Scheme`：描述对象信息
      ```
      @Tag(name = "SpringDocController",description = "SpringDoc样例")
      @RestController
      @RequestMapping("/docs")
      public class SpringDocController {
      
          @Autowired
          BootUserRepository repository;
      
      
          @Operation(summary ="获取用户信息",  description = "返回用户列表", 
            parameters = {  // 用于描述传入参数
                  @Parameter(name = "username", description = "用户名"),
            }
          )
          @GetMapping("/")
          public ResponseEntity<List<BootUser>> list(String username {
              return ResponseEntity.ok(repository.findAll());
          }
      }      
      ```
24. 集成Tess4J图像文字识别
    - `OCR（Optical Character Recognition）`技术的工作流程通常包括以下几个步骤
      - 图像预处理：包括图像的获取、二值化、去噪、旋转校正、分割等，以提高后续识别的准确性
      - 文本检测：在图像中定位文本的位置，通常是通过检测文本的行、单词或字符边界
      - 特征提取：从图像中提取文本的特征，这些特征将用于识别过程
      - 字符识别：将提取的特征与已知字符的模式进行匹配，以识别每个字符
      - 后处理：包括对识别结果进行校对、格式化、布局分析等，以提高整体的可读性和准确性
    -  `Tesseract OCR`：开源OCR引擎，是目前最准确、最流行的开源 OCR 工具之一
    - `Tess4J`：是一个 Java 库，它是Tesseract OCR引擎的Java封装器。通过Tess4J，程序可以调用Tesseract来执行光学字符识别（OCR）任务，即将图像中的文字转换为可编辑和可搜索的文本格式
    - Tess4j的特点：
      - 易于集成：Tess4J提供了简单的API，使得在Java应用程序中使用Tesseract变得非常容易
      - 跨平台：Tess4J可以在任何支持Java的操作系统上运行，包括Windows、macOS和Linux
      - 功能丰富：Tess4J支持Tesseract的所有主要功能，包括多语言识别、自定义训练等
    - 集成步骤
      - 下载字体库：`https://github.com/tesseract-ocr/tessdata`
        - `chi_sim.traineddata`：中文字体库
        - `eng.traineddata`：英文字体库
      - 引入依赖
        ```xml
        <dependency>
          <groupId>net.sourceforge.tess4j</groupId>
          <artifactId>tess4j</artifactId>
          <version>4.1.1</version>
        </dependency>
        ```
      - 配置字体库
        ```properties
        # --- Tess4J图像文字识别 ---
        # 字体库文件目录
        tess4j.data-path=classpath:fontlib
        # 中文字体库
        tess4j.chinese-train-data=chi_sim
        # 英文字体库
        tess4j.english-train-data=eng
        ```
      - 配置类
        ```java
        @Data
        @Configuration
        @ConfigurationProperties(prefix = "tess4j")
        public class Tess4JConfig {
        
            private String dataPath;
        
            private String chineseTrainData;
        
            private String englishTrainData;
        
        }         
        ```
25. 

