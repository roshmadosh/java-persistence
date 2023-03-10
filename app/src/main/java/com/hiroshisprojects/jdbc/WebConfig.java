package com.hiroshisprojects.jdbc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.hiroshisprojects.jdbc.employee", "com.hiroshisprojects.jdbc.data" })
public class WebConfig implements WebMvcConfigurer {

}

