### SpringBoot3.x
1. 集成Web
2. 集成JPA持久化
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
    - 设置配置对象
15. 15

