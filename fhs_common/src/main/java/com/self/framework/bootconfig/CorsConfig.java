package com.self.framework.bootconfig;

import com.self.framework.utils.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Properties;

/**
 * Created by Shen,Tianqi on 2019/9/30.
 */


@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        Properties properties = PropertiesUtils.getProperties("classpath:CorsConfig/CorsConfig.properties");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(properties.getProperty("AllowedOrigin")); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader(properties.getProperty("AllowedHeader")); // 2允许任何头
        corsConfiguration.addAllowedMethod(properties.getProperty("AllowedMethod")); // 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}
