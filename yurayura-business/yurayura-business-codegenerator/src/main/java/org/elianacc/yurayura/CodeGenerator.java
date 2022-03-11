package org.elianacc.yurayura;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Mybatis-Plus代码生成器(新)
 *
 * @author ELiaNaCc
 * @since 2019-10-21
 */
public class CodeGenerator {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/yurayura_service?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8";

    public static void main(String[] args) {

        AtomicReference<String> generatorTable = new AtomicReference<>();

        FastAutoGenerator.create(CodeGenerator.DB_URL, "root", "123456")
                // 全局配置(GlobalConfig)
                .globalConfig(builder -> builder
                        .author("ELiaNaCc")
                        .outputDir("D://mp_generator")
                        .fileOverride()
                        .enableSwagger()
                )
                // 包配置(PackageConfig)
                .packageConfig((scanner, builder) -> {
                    generatorTable.set(scanner.apply("请输入需要生成的表名"));
                    if (!generatorTable.get().matches("^[a-zA-Z]([a-zA-Z0-9_]+)?$")) {
                        System.out.println("表名输入异常！");
                        System.exit(0);
                    }
                    String modulePackagePath;
                    if (generatorTable.get().contains("_sys_")) {
                        modulePackagePath = "sys." + generatorTable.get().substring(generatorTable.get().indexOf("_") + 5);
                    } else {
                        modulePackagePath = generatorTable.get().substring(generatorTable.get().indexOf("_") + 1);
                    }
                    modulePackagePath = modulePackagePath.contains("_") ? modulePackagePath.substring(0, modulePackagePath.indexOf("_")) : modulePackagePath;
                    builder.parent("org.elianacc")
                            .moduleName("yurayura")
                            .entity("entity." + modulePackagePath)
                            .xml("dao." + modulePackagePath + ".mapper")
                            .mapper("dao." + modulePackagePath)
                            .serviceImpl("service." + modulePackagePath + ".impl")
                            .service("service." + modulePackagePath)
                            .controller("controller." + modulePackagePath);
                })
                // 模板配置(TemplateConfig)
                .templateConfig(builder -> builder
                        .entity("templates/entity.java.vm")
                        .mapperXml("templates/mapper.xml.vm")
                        .mapper("templates/mapper.java.vm")
                        .serviceImpl("templates/serviceImpl.java.vm")
                        .service("templates/service.java.vm")
                        .controller("templates/controller.java.vm")
                )
                // 注入配置(InjectionConfig)
                .injectionConfig(builder -> {
                    Map<String, Object> parmMap = new HashMap<>();
                    parmMap.put("sysModulePath", generatorTable.get().contains("_sys_") ? "/sys/" : "/");
                    parmMap.put("packageParentPath", "org.elianacc.yurayura");
                    builder.customMap(parmMap);
                })
                // 策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    String tablePrefix = generatorTable.get().contains("_sys_") ? "yurayura_sys_" : "yurayura_";
                    String filePrefix = generatorTable.get().contains("_sys_") ? "Sys" : "";
                    builder.addInclude(generatorTable.get())
                            .addTablePrefix(tablePrefix)
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .formatFileName(filePrefix + "%s")
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatXmlFileName(filePrefix + "%sMapper")
                            .formatMapperFileName(filePrefix + "%sMapper")
                            .serviceBuilder()
                            .formatServiceImplFileName(filePrefix + "%sServiceImpl")
                            .formatServiceFileName("I" + filePrefix + "%sService")
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .formatFileName(filePrefix + "%sController");
                })
                .execute();
    }

}
