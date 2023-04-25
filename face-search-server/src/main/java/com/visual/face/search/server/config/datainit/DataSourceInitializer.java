package com.visual.face.search.server.config.datainit;

import java.io.Reader;
import javax.sql.DataSource;
import java.sql.Connection;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInitializer implements InitializingBean {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("start init schema info.");
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        long startCurrentTime = System.currentTimeMillis();
        boolean initFlag = false;
        Exception exception = null;
        while ((System.currentTimeMillis() - startCurrentTime) <= 2 * 60 * 1000){
            try (
                    Connection connection= dataSource.getConnection();
                    Reader resourceAsReader = Resources.getResourceAsReader("sqls/schema-init.sql")
            ){
                ScriptRunner runner=new ScriptRunner(connection);
                runner.setLogWriter(null);
                runner.setErrorLogWriter(null);
                runner.runScript(resourceAsReader);
                initFlag = true;
                break;
            } catch (Exception e) {
                exception = e;
            }
        }
        if(initFlag){
            logger.info("success init schema info.");
        }else{
            if(null == exception){
                logger.error("run schema-init error");
            }else{
                logger.error("run schema-init error", exception);
            }
        }
    }

}
