1、SpringData：Spring的一个子项目,用于简化数据库访问,支持NoSQL和关系数据存储.其主要目标是使数据库的访问变得方便快捷
2、SpringData项目所支持NoSQL存储：
    (1)、MongoDB - 文档数据库
    (2)、Neo4j - 图形数据库
    (3)、Redis - 键/值(key-value)存储
    (4)、Hbase - 列族数据库
3、SpringData项目所支持的关系数据存储技术：
    (1)、JDBC
    (2)、JPA
4、JPA Spring Data：致力于减少数据访问层(DAO)的开发量,开发者唯一要做的,就是声明持久层的接口,其他都交给Spring Data JPA来完成
5、Spring Data JPA之HelloWolrd:
    (1)、配置Spring整合JPA
    (2)、在Spring配置文件中配置Spring Data.让Spring为声明的接口创建代理对象,配置了<jpa:repositories>后,Spring初始化容器时
        将会扫描base-package制定的包目录及其子目录,为继承Repository或其子接口的接口创建代理对象,并将代理对象注册为Spring Bean,业
        务层便可以通过Spring自动封装的特性来直接使用该对象。
    (3)、声明持久层的接口,该接口继承Repository, Repository是一个标记型接口,它不包含任何方法,如必要,Spring Data可实现Repository其他
        子接口, 其中定义了一些常用的增删改查,以及分页相关的方法
    (4)、在接口中声明需要的方法。Spring Data将根据给定的策略来为其生成实现代码。
6、Repository子接口：
	(1)、Repository：仅仅是一个标识,表明任何继承它的均为仓库接口类
	(2)、CrudRepository：继承Repository,实现了一组CRUD相关的方法
	(3)、PagingAndSortingRepository：继承CrudRepository,实现了一组分页排序相关的方法
	(4)、jpaRepository：继承PagingAndSortingRepository,实现了一组JPA规范相关的方法
	(5)、自定义的XxxxRepository需要继承JpaRepository,这样的XxxRepository接口就具备了通用的数据访问控制层的能力
	(6)JpaSpecificationExecutor：不属于Repository体系,实现一组JPA Criteria查询相关的方法
7、SpringData Repository查询方法定义规范：
	(1)、查询方法以find、read或get开头
	(2)、涉及查询条件时,条件的属性用条件关键字连接,注意：条件属性以首字母大写,如：
		@Query("select u from User u")
		List<User> findAllUser();
		@Query("select u from User u where u.id=:id")
		User findUserById(@Param("id") int id);
	(3)、支持的关键字(不使用@Query注解的情况下)：
		1)、And、Or、Between,如: findUserByIdAndUserName
		2)、LessThan、GreaterThan,如:findUserByAgeLessThan
		3)、After、Before,如：findUserByBirthdayAfter
		4）、IsNull、IsNotNull,NotNull：findUserByAddressIsNull
		5)、Like、NotLike、StartingWith、EndingWith、Containing、OrderBy、Not
		6)、In、NotIn,如：findUserByAddressIn(Collection<String> addressList)
		7）True、False,如：findUserByStatusTrue
8、@Query注解：
	(1)、参数的传递方式：
		1)、占位符(传入的参数顺序必须与占位符一致)：
			@Query("select u from User u where u.id=?1")
			User findUserById(int id);			
		2)、命名参数：
			@Query("select u from User u where u.id=:id")
			User findUserById(@Param("id") int id);
	(2)、原生的SQL查询：
		//user为表名,user_name为表列名
		@Query(value="select * from user where user_name=:userName",nativeQuery=true)
		User findUserByUserName(@Param("userName") String userName);
	(3)、查询不存在的Bean时:
		@Query(value="select sub_id as id, name as user_name, pass as password, '' as adress, '' as email, '' as description from subUser where sub_id=:subId",nativeQuery=true)
		User findUserBySubId(@Param("subId") int subId);
	(4)、@Query不支持insert,需要保存数据时,使用继承CrudRepository接口的默认方法(save(Collection<User> users))
9、@Modifying注解及其事物：
	(1)、加上该注解可以执行update与delete操作,往往与@Transactional一起使用
		@Modifying
		@Transactional
		@Query(value="update user set user_name=:userName where id=:id",nativeQuery=true)
		void updateUserById(@Param("id") int id, @Param("userName") String userName);