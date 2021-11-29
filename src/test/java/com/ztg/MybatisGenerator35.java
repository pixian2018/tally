package com.ztg;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @Description: 代码生成Mybtatispus3.5
 * @author: gaodm
 * @time: 2021/9/13 9:41
 */
public class MybatisGenerator35 {

    public static void main(String[] args) {
        // 代码生成器
        new AutoGenerator(new DataSourceConfig
                .Builder("jdbc:mysql://192.168.3.115:3306/wx?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8",
                "root", "root")
                .build()
        )
                .global(new GlobalConfig.Builder()
                        .outputDir(System.getProperty("user.dir") + "//src//main//java//")
                        // .outputDir("E://code//wx")
                        .author("zhoutg")
                        //.enableSwagger() //自动swagger注解
                        .dateType(DateType.ONLY_DATE)
                        .fileOverride()
                        .build()
                )
                .packageInfo(new PackageConfig.Builder()// 包配置
                        .parent("com.ztg")
                        .controller("web")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .xml("resources.mapper")
                        .build()
                )
                .strategy(new StrategyConfig.Builder()// 策略配置
                        //.enableCapitalMode()// 全局大写命名
                        //.addExclude("databasechangelog", "databasechangeloglock")// 排除生成的表 sys_user
                        .addInclude("record_detail") // 对应表 ntc_dictionary_info
                        //.addTablePrefix("ntc_") // 表前缀
                        //.addFieldPrefix("tb_") // 字段前缀
                        .enableSkipView() //跳过视图

                        .controllerBuilder()
                        .enableRestStyle() //@RestController 注解
                        //.enableHyphenStyle() //url中驼峰转连字符
                        //.superClass(IBaseController.class)
                        .formatFileName("%sController")

                        .entityBuilder()
                        .enableLombok()// lombok 模型
                        //.enableChainModel() // 链式操作
                        //.enableSerialVersionUID()
                        //.enableRemoveIsPrefix() 去掉字段前边的is
                        //.superClass(BaseEntity.class)
                        //.addSuperEntityColumns("id", "createDate", "createId", "updateDate", "updateId") // 自定义实体，公共字段
                        .naming(NamingStrategy.underline_to_camel)
                        .enableTableFieldAnnotation()

                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")

                        .mapperBuilder()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .build()
                )
                .template(new TemplateConfig.Builder()
                        // .entity("templates\\entity\\entity.java.vm")
                        // .controller("templates\\controller\\controller.java.vm")
                        // .service(
                        //         "templates\\service\\service.java.vm",
                        //         "templates\\service\\impl\\serviceImpl.java.vm"
                        // )
                        // .mapper("templates\\persistent\\mapper.java.vm")
                        // .mapperXml("templates\\persistent\\xml\\mapper.xml.vm")
                        .build()
                )
                .execute(new FreemarkerTemplateEngine());
    }

}
