package com.nostress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel on 2014-12-26.
 */
@Service
public class BeanService {

    protected final Logger Log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BeanConfiguration beanConfiguration;

    public boolean sendMessage(String msg, String rec) {
        Log.info("Email Sent to {} with Message={} with foo={}", rec, msg, beanConfiguration.getFoo());
        return true;
    }
}
