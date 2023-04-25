package com.visual.face.search.server.bootstrap.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration("visualServerConfig")
@EnableTransactionManagement
public class ServerConfig {

    @Configuration
    @MapperScan("com.visual.face.search.server.mapper")
    public static class MapperConfig {}

    @Configuration
    @ComponentScan("com.visual.face.search.server.utils")
    public static class SearchUtils {}

    @Configuration
    @ComponentScan("com.visual.face.search.server.config")
    public static class SearchConfig {}

    @Configuration
    @ComponentScan({"com.visual.face.search.server.service"})
    public static class ServiceConfig {}

    @Configuration
    @ComponentScan({"com.visual.face.search.server.controller"})
    public static class ControllerConfig {}

    @Configuration
    @ComponentScan({"com.visual.face.search.server.scheduler"})
    public static class SchedulerConfig {}
}
