package org.elianacc.yurayura;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Mybatis-Plus代码生成器
 *
 * @author ELiaNaCc
 * @since 2019-10-21
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(tip);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("输入异常！");
    }


    public static void main(String[] args) {

        String generatorTable = scanner("请输入需要生成的表名");
        if (!generatorTable.matches("^[a-zA-Z]([a-zA-Z0-9_]+)?$")) {
            throw new MybatisPlusException("表名输入异常！");
        }
        String modulePackagePath;
        if (generatorTable.contains("_sys_")) {
            modulePackagePath = "sys." + generatorTable.substring(generatorTable.indexOf("_") + 5);
        } else {
            modulePackagePath = generatorTable.substring(generatorTable.indexOf("_") + 1);
        }
        modulePackagePath = modulePackagePath.contains("_") ? modulePackagePath.substring(0, modulePackagePath.indexOf("_")) : modulePackagePath;

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成路径
        gc.setOutputDir("D://mp_generator");
        //是否覆盖生成
        gc.setFileOverride(true);
        // 是否生成XML ResultMap
        gc.setBaseResultMap(true);
        // 是否生成XML columList
        gc.setBaseColumnList(true);
        // 是否支持 AR, 实体类只需继承 Model 类即可进行强大的 CRUD 操作
        gc.setActiveRecord(false);
        // 是否启用swagger2
        gc.setSwagger2(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        gc.setAuthor("ELiaNaCc");
        // 生成完后是否自动打开生成文件夹
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/yurayura_service?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.elianacc");
        pc.setModuleName("yurayura");
        // 自定义包名
        pc.setEntity("entity." + modulePackagePath);
        pc.setXml("dao." + modulePackagePath + ".mapper");
        pc.setMapper("dao." + modulePackagePath);
        pc.setServiceImpl("service." + modulePackagePath + ".impl");
        pc.setService("service." + modulePackagePath);
        pc.setController("controller." + modulePackagePath);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("isSysModule", generatorTable.contains("_sys_"));
                map.put("packageParentPath", "org.elianacc.yurayura");
                this.setMap(map);
            }
        };
        mpg.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        templateConfig.setController("templates/controller.java.vm");
        templateConfig.setServiceImpl("templates/serviceImpl.java.vm");
        templateConfig.setService("templates/service.java.vm");
        templateConfig.setMapper("templates/mapper.java.vm");
        templateConfig.setXml("templates/mapper.xml.vm");
        templateConfig.setEntity("templates/entity.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(generatorTable);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(generatorTable.contains("_sys_") ? "yurayura_sys_" : "yurayura_");
        mpg.setStrategy(strategy);

        mpg.execute();

    }

}
