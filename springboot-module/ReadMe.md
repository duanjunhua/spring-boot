# Getting Started

##  一、RSA加密API，RSA：非对称加密
- 依赖：

```xml
  <dependency>
      <groupId>cn.shuibo</groupId>
      <artifactId>rsa-encrypt-body-spring-boot</artifactId>
      <version>1.0.1.RELEASE</version>
  </dependency>
```
- 配置

```yaml
spring:
    rsa:
        encrypt:
            # 是否开启加密
            open: true
            # 是否打印加密日志
            showLog: true
            # RSA公钥，加密解密方需要
            publicKey: "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ2LFzxdvd6ga1B28iAXylkguRoH8s+Wc7Izkq/3W2eBS+4Fow93gUTK714ixAioZRXW4/XCGB/DqW17Af2BHkMCAwEAAQ=="
            # RSA密钥，加密方需要
            privateKey: "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnYsXPF293qBrUHbyIBfKWSC5Ggfyz5ZzsjOSr/dbZ4FL7gWjD3eBRMrvXiLECKhlFdbj9cIYH8OpbXsB/YEeQwIDAQABAkB0XzvosW4BNwpOQ66SmkA0GgHhAqIPSRlpNsNSDCuOhkDlYU0zC/q/cIF9NqkiLs9VA32/+pcmCnBJTZgOOExxAiEAzXYi7Tv/3Tfecp3WimajjffnGvWIh3+gIr+TYaXk2OcCIQDES48kxRZ/k2fdpygzSFplEBEWAooOLzJEjAIfmPsYRQIgdA8zk4a89Z1FjsESG4P3B47tgREJWmEBeB93hUVMjCkCIEPQWr54PEKMuThRFElLOLdraouxCuLFmVcnC2DBKBYFAiEArwuFIorfYaN4bfG2bIFK+haTzhaNl6F9qR2Zl9XuRsA="
```
- 启用接口加密

```java
@EnableSecurity //开启接口RSA加密
@SpringBootApplication
public class SpringbootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootModuleApplication.class, args);
    }
}
```
- 接口加解密实现
```
接口类：com.duanjh.module.rsa_api.RsaApiController
注解：@Encrypt（加密）、 @Decrypt（解密）
```
##  二、数据库设计文档自动生成
- 依赖
```xml
<!-- Screw -->
<dependency>
    <groupId>cn.smallbun.screw</groupId>
    <artifactId>screw-core</artifactId>
    <version>1.0.5</version>
</dependency>
```
- 配置实现
```
实现类：com.duanjh.module.database_doc.DatabaseDocController
```
##  三、消息推送
- 实现方式包含：轮询、Spring SSE（Server-send Events）、Websocket、netty
- websocket依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency> 
```
- 实现代码
```
实现模块：com.duanjh.module.msg_push.*
```
##  四、接口异步提升效率
- 启用异步
```java
@EnableAsync    //开启异步操作支持
@SpringBootApplication
public class SpringbootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootModuleApplication.class, args);
    }
}
```
- 自定义线程池配置
```java
com.duanjh.module.async.AsyncConfig:

@Bean(name = "taskExecutor")
public Executor taskExecutor(){
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 设置空闲线程数
    executor.setCorePoolSize(2);
    // 设置最大线程数
    executor.setMaxPoolSize(5);
    // 设置查询容量
    executor.setQueueCapacity(500);
    // 自定义线程名
    executor.setThreadNamePrefix("Personal-Thread-");
    // 线程初始化
    executor.initialize();
    return executor;
}

```
- 异步服务
```java
com.duanjh.module.async.AsyncService:

/**
 * 不带返回值的方法
 */
@Async("taskExecutor")  // 标注异步方法，指定使用自定义线程池：taskExecutor
public void asyncMethod(){
    try {
        Thread.sleep(5000);
        log.info("Async method is called.");
    } catch (Exception e) {
        log.error("Async method Exception: {}", e.getLocalizedMessage());
    }
}

/**
 * 带返回值的异步方法
 */
@Async("taskExecutor")
public CompletableFuture<String> asyncMethodWithReturn(){
    try {
        Thread.sleep(5000);
        log.info("Async method is called.");
        return CompletableFuture.completedFuture("Async method is success called");
    } catch (Exception e) {
        log.error("Async method Exception: {}", e.getLocalizedMessage());
    }
    return CompletableFuture.completedFuture("Async method called Failed.");
}

```
##  五、