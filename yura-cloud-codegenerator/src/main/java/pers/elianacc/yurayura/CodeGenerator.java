package pers.elianacc.yurayura;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Mybatis-Plus代码生成器(新)
 *
 * @author ELiaNaCc
 * @since 2019-10-21
 */
public class CodeGenerator {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(tip);
        return scanner.next();
    }

    public static void main(String[] args) {

        String generatorDb = scanner("请输入需要生成的库名");

        String generatorDbUrl = "jdbc:mysql://127.0.0.1:3306/" + generatorDb
                + "?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";

        AtomicReference<String> generatorTable = new AtomicReference<>();
        AtomicReference<String> modulePackagePath = new AtomicReference<>();

        FastAutoGenerator.create(generatorDbUrl, "root", "123456")
                // 全局配置(GlobalConfig)
                .globalConfig(builder -> builder
                        .author("ELiaNaCc")
                        .outputDir("D://mp_generator")
                        .enableSwagger()
                )
                // 包配置(PackageConfig)
                .packageConfig((scanner, builder) -> {
                    generatorTable.set(scanner.apply("请输入需要生成的表名"));
                    if (!generatorTable.get().matches("^[a-zA-Z]([a-zA-Z0-9_]+)?$")) {
                        System.out.println("表名输入异常！");
                        System.exit(0);
                    }
                    if (generatorTable.get().contains("_sys_")) {
                        modulePackagePath.set("sys." + generatorTable.get().substring(generatorTable.get().indexOf("_") + 5));
                    } else {
                        modulePackagePath.set(generatorTable.get().substring(generatorTable.get().indexOf("_") + 1));
                    }
                    modulePackagePath.set(modulePackagePath.get().contains("_")
                            ? modulePackagePath.get().substring(0, modulePackagePath.get().indexOf("_")) : modulePackagePath.get());
                    builder.parent("pers.elianacc") // 根据实际修改
                            .moduleName("yurayura") // 根据实际修改
                            .entity("entity")
                            // SpringBoot单体项目加上模块包路径
//                            .xml("dao." + modulePackagePath.get() + ".mapper")
//                            .mapper("dao." + modulePackagePath.get())
//                            .serviceImpl("service." + modulePackagePath.get() + ".impl")
//                            .service("service." + modulePackagePath.get())
//                            .controller("controller." + modulePackagePath.get());
                            .xml("dao.mapper")
                            .mapper("dao")
                            .serviceImpl("service.impl")
                            .service("service")
                            .controller("controller");
                })
                // 模板配置(TemplateConfig)
                .templateConfig(builder -> builder
                        .entity("templates/entity.java.vm")
                        .xml("templates/mapper.xml.vm")
                        .mapper("templates/mapper.java.vm")
                        .serviceImpl("templates/serviceImpl.java.vm")
                        .service("templates/service.java.vm")
                        .controller("templates/controller.java.vm")
                )
                // 注入配置(InjectionConfig)
                .injectionConfig(builder -> {
                    Map<String, Object> parmMap = new HashMap<>();
                    parmMap.put("sysModulePath", generatorTable.get().contains("_sys_") ? "/sys/" : "/");
                    parmMap.put("modulePackagePath", modulePackagePath.get());
                    parmMap.put("packageParentPath", "pers.elianacc.yurayura"); // 根据实际修改
                    builder.customMap(parmMap);
                })
                // 策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    builder.addInclude(generatorTable.get())
                            .addTablePrefix("yurayura_") // 根据实际修改
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .formatFileName("%s")
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatXmlFileName("%sMapper")
                            .formatMapperFileName("%sMapper")
                            .serviceBuilder()
                            .formatServiceImplFileName("%sServiceImpl")
                            .formatServiceFileName("I" + "%sService")
                            .controllerBuilder()
                            .enableRestStyle()
                            .formatFileName("%sController");
                })
                .execute();
    }

}
