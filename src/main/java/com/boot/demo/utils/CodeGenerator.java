package com.boot.demo.utils;


import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: CodeGenerator
 * @Description:
 * @Author: Vic_Liang
 * @Date:2019/9/26 8:24 AM
 * @Version V1.0
 **/
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {


        /*全局配置*/
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        /*生成路径*/
        gc.setOutputDir("D:/demo");
        gc.setFileOverride(true);
        gc.setAuthor("system");
        gc.setOpen(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        //自定义数据类型转换
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setUrl("jdbc:mysql://172.17.33.69:3306/etc_manager?useSSL=false&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Z6x3iG1wjK!i");



        /*生成文件所在包配置*/
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.etc");
        pc.setController("controller");
        pc.setService("mpservice");
        pc.setServiceImpl("pmservice.impl");
        pc.setEntity("entity");
        pc.setMapper("dao");


        /*xml文件配置*/
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing

            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        /*xml生成路径*/
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                //return "/Users/vicliang/IdeaProjects/java/com/etc/etc_card_info/mapper/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper.xml";
                return null;
            }
        });
        cfg.setFileOutConfigList(focList);

        /*代码生成器*/
        AutoGenerator mpg = new AutoGenerator();
        /*全局配置*/
        mpg.setGlobalConfig(gc);
        /*数据源配置*/
        mpg.setDataSource(dsc);
        /*包配置*/
        mpg.setPackageInfo(pc);
        /*xml配置*/
        mpg.setCfg(cfg);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        /*策略配置*/
        StrategyConfig strategy = new StrategyConfig();
        /*去除数据库表前缀*/
        strategy.setTablePrefix(new String[]{"sys_", "or_"});
        /*表名生成策略*/
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.liusong.miaojob.common.BaseEntity");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名"));

        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


}
