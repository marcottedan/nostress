package com.nostress;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Daniel on 2014-12-26.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "nostress")
public class BeanConfiguration {

    protected String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
