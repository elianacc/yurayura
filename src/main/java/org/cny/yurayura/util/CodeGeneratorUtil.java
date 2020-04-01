package org.cny.yurayura.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Mybatis-Plus代码生成 util
 *
 * @author CNY
 * @since 2019-10-21
 */
public class CodeGeneratorUtil {
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
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("输入异常！");
    }


    public static void main(String[] args) {
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
        gc.setSwagger2(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        gc.setAuthor("CNY");
        // 生成完后是否自动打开生成文件夹
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/yurayura_new?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.cny");
        pc.setModuleName(scanner("请输入模块名"));
        String isSysModule = scanner("是否是后端系统子模块？（如果是输入Y/y,不是请随意输入）");
        String submoduleName = scanner("请输入子模块名");
        submoduleName = isSysModule.equalsIgnoreCase("y") ? "sys." + submoduleName : submoduleName;
        // 自定义包名
        pc.setEntity("entity." + submoduleName);
        pc.setMapper("dao." + submoduleName);
        pc.setXml("dao." + submoduleName + ".mapper");
        pc.setService("service." + submoduleName);
        pc.setServiceImpl("service." + submoduleName + ".impl");
        pc.setController("controller." + submoduleName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("isSysModule", isSysModule.equalsIgnoreCase("y"));
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
        templateConfig.setEntity("templates/entity.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("请输入表名，多个英文逗号分割（生成多个表时最好是一个子模块内容，否则无法对应）").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(isSysModule.equalsIgnoreCase("y") ? "yurayura_sys_" : "yurayura_");
        mpg.setStrategy(strategy);

        mpg.execute();

    }

}
