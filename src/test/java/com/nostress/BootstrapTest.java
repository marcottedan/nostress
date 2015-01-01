package com.nostress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Daniel on 2014-12-25.
 */
@ActiveProfiles("unit-test")
@SpringApplicationConfiguration(classes = {TestConfig.class})
public class BootstrapTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BeanService beanService;

    @Autowired
    BeanConfiguration beanConfiguration;

    @Test
    public void BeanService_sendMessage_ok() throws Exception {
        Assert.assertEquals(beanConfiguration.getFoo(), "babar");
    }
}