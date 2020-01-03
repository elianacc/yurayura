package org.cny.yurayura.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * Mybatis-Plus代码生成器
 *
 * @author CNY
 * @date 2019年10月21日 16:31
 */
public class CodeGeneratorUtil {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
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
        gc.setSwagger2(true);
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
        pc.setModuleName(scanner("模块名"));
        String isSysModule = scanner("是否是后端系统子模块？（如果是输入Y/y,不是请随意输入）");
        String submoduleName = scanner("子模块名");
        submoduleName = isSysModule.equalsIgnoreCase("y") ? "sys." + submoduleName : submoduleName;
        // 自定义包名
        pc.setEntity("entity." + submoduleName);
        pc.setMapper("dao." + submoduleName);
        pc.setXml("dao." + submoduleName + ".mapper");
        pc.setService("service." + submoduleName);
        pc.setServiceImpl("service." + submoduleName + ".impl");
        pc.setController("controller." + submoduleName);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割（生成多个表最好是一个子模块内容，否则无法对应）").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(isSysModule.equalsIgnoreCase("y") ? "yurayura_sys_" : "yurayura_");
        mpg.setStrategy(strategy);

        mpg.execute();

    }

}
