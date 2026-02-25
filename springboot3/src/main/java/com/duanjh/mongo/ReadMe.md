### 多数据源情况下，默认的单数据源对象需要放到`@Primary`注解的包下
- 单数据源情况
  - 1、将`singlesource`下的类移动到mongo目录下
  - 2、删除`multisource`目录以及目录下的文件
  - 3、删除`MultiMongoProp`、`PrimaryMongoConfig`、`SecondaryMongoConfig`、`MultiMongoDSConfig`多数据源相关配置
- 多数据源情况
  - 1、数据源属性配置
    ```
    multi.mongodb.primary.uri=mongodb://root:root@127.0.0.1:27017
    multi.mongodb.primary.database=mongo
    multi.mongodb.secondary.uri=mongodb://root:root@127.0.0.1:27017
    multi.mongodb.secondary.database=secondary
    ```
  - 2、读取多数据源属性
  ```
  @Data
  @Component
  @ConfigurationProperties(prefix = "multi.mongodb")
  public class MultiMongoProp {
  
      MongoProperties primary = new MongoProperties();
  
      MongoProperties secondary = new MongoProperties();
  
  }
  ```
  - 3、多数据源模板配置
  ```
  @Configuration
  @EnableMongoRepositories(basePackages = "com.duanjh.mongo.multisource.primary", mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
  public class PrimaryMongoConfig {
  
      public static final String MONGO_TEMPLATE = "primaryMongoTemplate";
  
  }
  
  @Configuration
  @EnableMongoRepositories(basePackages = "com.duanjh.mongo.multisource.secondary", mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
  public class SecondaryMongoConfig {
  
      public static final String MONGO_TEMPLATE = "secondaryMongoTemplate";
  
  }
  ```
  - 4、配置多数据源
  ```
  @Configuration
  public class MultiMongoDSConfig {
  
      @Autowired
      MultiMongoProp dsProps;
  
      @Primary    // 必须指定主库
      @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)    // name与PrimaryMongoConfig中定义的MONGO_TEMPLATE名保持一致
      public MongoTemplate primaryTemplate(){
          return new MongoTemplate(primaryMongoDbFactory(dsProps.getPrimary()));
      }
  
      @Primary
      @Bean
      public MongoDatabaseFactory primaryMongoDbFactory(MongoProperties props){
          MongoClient client = MongoClients.create(props.getUri());
          return new SimpleMongoClientDatabaseFactory(client, props.getDatabase());
      }
  
  
      @Bean(name = SecondaryMongoConfig.MONGO_TEMPLATE)
      public MongoTemplate secondaryTemplate(){
          return new MongoTemplate(secondaryMongoDbFactory(dsProps.getSecondary()));
      }
  
      @Bean
      public MongoDatabaseFactory secondaryMongoDbFactory(MongoProperties props){
          MongoClient client = MongoClients.create(props.getUri());
          return new SimpleMongoClientDatabaseFactory(client, props.getDatabase());
      }
  }
  ```