package com.shaoxing.lixing.global.config.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author caishaodong
 * @Date 2020-10-10 11:17
 * @Description
 **/
@Configuration
public class TomcatConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        // 修改内置的 tomcat 容器配置
        TomcatServletWebServerFactory tomcatServlet = new TomcatServletWebServerFactory();
        tomcatServlet.addConnectorCustomizers(
                connector -> connector.setProperty("relaxedQueryChars", "[]{}")
        );
        return tomcatServlet;
    }
}
