package com.nostress;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Daniel on 2014-12-27.
 */
@Profile("unit-test")
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class TestConfig {
}
