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
##  五、接口限流防刷
- 实现方式：RateLimiter、Redis缓存
- RateLimiter依赖
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>31.0.1-jre</version>
</dependency> 
```
- 自定义面向切面依赖
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
</dependency> 
```
- 实现方式
```java
@Slf4j
@RestController
@RequestMapping("/api_limiter")
public class ApiRequestLimitController {

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();


    /**------------------------- 方式一：RateLimiter -------------------------*/
    /**
     * @param uniqueKey: 请求唯一标识
     */
    @GetMapping("/limit-openapi")
    public String limitOpenApi(@RequestParam String uniqueKey){
        RateLimiter rateLimiter = limiters.computeIfAbsent(uniqueKey, k -> RateLimiter.create(1.0 / 30));  // 每30秒访问一次
        if(!rateLimiter.tryAcquire()){
            return "Too many requests, Please try again later";
        }

        // 执行业务逻辑
        return "OK";
    }

    // 使用自定义注解的限流方式
    @GetMapping("/limit-aop")
    @Limiting(limtTimes = 1, name = "limit-aop")
    public String limitAop(){
        return "OK";
    }

    /**------------------------- 方式二：使用Redis缓存 -------------------------*/
}
```
- 自定义面向限流切面
```java
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    private ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();
    private RateLimiter limiter;

    @Pointcut("@annotation(com.duanjh.module.aop.Limiting)")
    public void apiLimite(){

    }

    @Around("apiLimite()")
    public Object limitAround(ProceedingJoinPoint point) throws Throwable{
        // 获取拦截的方法
        Signature signature = point.getSignature();

        // 获取拦截的方法名
        MethodSignature methodSignature = (MethodSignature) signature;

        // 返回被织入增加处理目标对象
        Object target = point.getTarget();

        // 获取方法
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        // 获取注解信息
        Limiting annotation = method.getAnnotation(Limiting.class);
        double limitTimes = annotation.limtTimes();
        String functionName = method.getName();

        if(limiters.containsKey(functionName)){
            limiter = limiters.get(functionName);
        } else {
            limiters.put(functionName, RateLimiter.create(limitTimes));
            limiter = limiters.get(functionName);
        }

        if(limiter.tryAcquire()){
            log.info("The api access limit process completed!");
            return point.proceed();
        }else {
            throw new RuntimeException("Too many requests, Please try again later!");
        }
    }
}
```
- 自定义注解
```java
@Target(ElementType.METHOD) //定义注解标注的位置：方法
@Retention(RetentionPolicy.RUNTIME) // 定义注解在运行时加载
public @interface Limiting {

    /**
     * 定义默认放入限流桶中的token数量，每秒的访问次数，默认设置为10次
     */
    double limtTimes() default 10;

    String name() default "";

}
```

## 六、全局异常
```java
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public String runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException with exception message: {}", e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
```

## 七、封装统一返回结果
- 自定义统一响应注解
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResResult {

}
```
- 统一返回状态码枚举
```java
public enum ResultCode {
    // 创建对象，必须在第一行
    /**
     * 接口响应成功
     */
    SUCCESS(200, "成功"),

    /**
     * 接口相应失败
     */
    ERROR(500, "服务器异常！"),
    FAIL(501, "服务请求失败！"),

    /**
     * 参数错误
     */
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BLANK(1002, "参数为空"),
    PARAMS_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAMS_NOT_COMPLETE(1004, "参数缺失"),

    /**
     * 用户错误
     */
    USER_NOT_LOGGER_IN(2001, "用户未登录，请登录！"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIT(2003, "账号被禁用"),
    USER_NOT_EXISTS(2004, "用户不存在"),
    USER_HAS_EXISTED(2006, "用已存在");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
```
- 自定义注解拦截器
```java
@Slf4j
@Configuration
public class ResResultInterceptor implements HandlerInterceptor {

    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    /**
     * 获取此请求，判断是否需要返回值包装，设置一个属性标记
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){

            final HandlerMethod hm = (HandlerMethod)handler;

            final Class<?> clazz = hm.getBeanType();
            final Method method = hm.getMethod();

            // 判断是否在类对象上面有自定义注解
            if(clazz.isAnnotationPresent(ResResult.class)){
                log.debug("Class has specify annotion, handle [ResResult] to return");
                //类上有，设置当前请求返回体，封装往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN, clazz.getAnnotation(ResResult.class));
                return true;
            }

            if(method.isAnnotationPresent(ResResult.class)){
                log.debug("Method has specify annotion, handle [ResResult] to return");
                //方法上有自定义注解
                request.setAttribute(RESPONSE_RESULT_ANN, method.getAnnotation(ResResult.class));
            }
        }
        return true;
    }
}
```
- 自定义注解响应处理逻辑
```java
@Slf4j
@ControllerAdvice
public class ResResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断请求是否包含自定义注解，没有则直接返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        // 判断是否包含自定义返回注解
        ResResult resResult = (ResResult) request.getAttribute(ResResultInterceptor.RESPONSE_RESULT_ANN);

        return ObjectUtils.isEmpty(resResult) ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        log.debug("before body write, Customize return result handle...");

        return ResponseResult.success(body);
    }
}
```
- 配置返回拦截器（必须配置，否则`ResResultInterceptor`不会生效）
```java
@Configuration
public class InterfaceWebConfigurer extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResResultInterceptor()).addPathPatterns("/**");
    }
}
```
## 八、图片处理（虚化背景与压缩）
- 图片压缩指定宽高
```java
/**
 * 压缩图片
 * @param image 原始图片
 * @param newWidth 压缩宽度
 * @param newHeight 压缩高度
 */
public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
    Image imageSrc = image.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
    BufferedImage resizeImage = new BufferedImage(newWidth, newHeight, image.getType());
    Graphics2D g2d = resizeImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawImage(imageSrc, 0, 0,newWidth, newHeight, null);
    g2d.dispose();
    return resizeImage;
}
```
- 图片切换背景色
```java
/**
 * 切换图片背景
 * @param image 原始图片
 * @param filterColor 过滤的颜色（如：文字黑色不替换，则filterColor为BLACK）
 * @param color 新的背景颜色
 */
public static BufferedImage transImageBackgroundColor(BufferedImage image,  Color filterColor, Color color) {
    for (int x = image.getMinX(); x < image.getWidth(); x++) {
        for (int y = image.getMinY(); y < image.getHeight(); y++) {
            int rgb = image.getRGB(x, y);
            Color src = new Color(rgb);
            if(filterColor.equals(src)){
                image.setRGB(x, y, src.getRGB());
                continue;
            }
            image.setRGB(x, y, color.getRGB());
        }
    }
    return image;
}
```
- 去除图片背景（虚化）
```java
/**
 * 去除图片背景色，图片透明化
 * @param image 原始图片
 * @param isCompress 是否压缩
 * @param imageWidth 压缩后的图片宽度: isCompress为true时不能为null
 * @param imageHeight 他所后的图片高度： isCompress为true时不能为null
 */
public static BufferedImage clearImageBackground(BufferedImage image, boolean isCompress, Integer imageWidth, Integer imageHeight) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    try {
        image = transImageBackgroundColor(image, Color.BLACK, Color.WHITE);
        if(isCompress){
            image = resizeImage(image, imageWidth, imageHeight);
        }
        bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgba = image.getRGB(x, y);
                if (isWhite(rgba)) {
                    bufferedImage.setRGB(x, y, 0x00ffff);
                } else {
                    bufferedImage.setRGB(x, y, rgba);
                }
            }
        }

    }catch (Exception e){
        e.printStackTrace();
    }
    return bufferedImage;
}

public static boolean isWhite(int rgba) {
    int r = (rgba >> 16) & 0xFF;
    int g = (rgba >> 8) & 0xFF;
    int b = rgba & 0xFF;
    return r > 250 && g > 250 && b > 250;
}
```