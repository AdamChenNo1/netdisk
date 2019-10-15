/*
根据配置文件逆向生成mapper接口和mapper.xml
 */

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MBGUser {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //配置文件使用绝对路径
        File configFile = new File("E:\\大数据开发学习\\netdisk2.0\\项目\\src\\main\\resources\\mybatis\\mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
