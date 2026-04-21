package com.duanjh;

import com.duanjh.modulith.product.ProductService;
import com.duanjh.modulith.product.internal.Product;
import jakarta.annotation.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 15:05
 * @Version: v1.0
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ModulithAppTests {

    ApplicationModules modules;

    @Resource
    ProductService publishService;

    @Before
    public void init(){
        /**
         * ApplicationModules类提供创建模块模型
         */
        modules = ApplicationModules.of(Springboot3Application.class);
    }

    /**
     * 基于代码结构分析生成应用模块模型
     */
    @Test
    public void createApplicationModuleModelTest() {
        /**
         * 会识别到com.duanjh下的所有包，不会识别到internal下的包
         */
        modules.forEach(System.out::println);
    }

    /**
     * 验证模块结构
     */
    @Test
    @Ignore
    public void verifyModularStructureTest() {
        /**
         * 使用 verify() 方法检查代码是否符合约束
         */
        modules.verify();
    }

    /**
     * 生成项目模块关系图：支持基于 PlantUML 生成 UML 或 C4 风格图表
     * 生成的图表在target/spring-modulith-docs下
     */
    @Test
    public void createModuleDocumentationTest(){
        new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
    }

    @Test
    public void notifyPublishTest(){
        publishService.publish(new Product("Duanjh", "Publish Message", 1));
    }
}
