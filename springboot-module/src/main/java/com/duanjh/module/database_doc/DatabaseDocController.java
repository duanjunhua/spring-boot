package com.duanjh.module.database_doc;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-05-29 周三 10:11
 * @Version: v1.0
 * @Description: 数据库设计文档自动生成
 */

@RestController
@RequestMapping("/database_doc")
public class DatabaseDocController {

    @Value("${spring.datasource.jdbc-url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String datatbaseAccount;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @GetMapping("/generate")
    public String generate(){
        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir("C:\\Users\\Administrator\\Desktop")
                // 打开目录
                .openOutputDir(false)
                // 文件类型 HTML/WORD/MD 三种格式
                .fileType(EngineFileType.WORD)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();

        HikariDataSource hikariDataSource = new HikariDataSource();
        //设置数据库连接
        hikariDataSource.setJdbcUrl(databaseUrl);
        hikariDataSource.setUsername(datatbaseAccount);
        hikariDataSource.setPassword(databasePassword);
        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version("v1.0")
                .description("数据库设计文档")
                .dataSource(hikariDataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();

        // 执行生成
        new DocumentationExecute(config).execute();
        return "Success";
    }

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig(){
        // 忽略表名
        List<String> ignoreTableName = Arrays.asList();
        // 忽略表前缀
        List<String> ignorePrefix = Arrays.asList();
        // 忽略表后缀
        List<String> ignoreSuffix = Arrays.asList();
        return ProcessConfig.builder()
                //根据名称指定表生成 我需要生成所有表 这里暂时不设置
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成 我需要生成所有表 这里暂时不设置
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成 我需要生成所有表 这里暂时不设置
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }
}
