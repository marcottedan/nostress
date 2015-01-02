package com.nostress;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.redisson.Config;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Daniel on 2014-12-25.
 */
@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
public class Bootstrap implements CommandLineRunner {

    protected final Logger Log = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private BeanService beanService;

    @Autowired
    private BeanConfiguration beanConfiguration;

    @Override
    public void run(String... args) {
        Log.info("Config :: {}", beanConfiguration.getFoo());
        Log.info("Service :: {}", beanService.beanConfiguration.getFoo());

        // Get BeanService and try it out
        beanService.sendMessage("Hi Daniel2", "tamere2@gmail.com");

        // Instantiate Redisson configuration
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress("127.0.0.1:6379");

        // Instantiate Redisson connection
        Redisson redisson = Redisson.create(redissonConfig);

        // Instantiate RedissonClientStoreFactory
        RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory();

        // Instantiate SocketIO Configuration
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8080);
        config.setStoreFactory(redisStoreFactory);

        // Instantiate SocketIO Server
        SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject chatMessage, AckRequest ackRequest) {
                Log.info("'{}' said '{}'", chatMessage.getUserName(), chatMessage.getMessage());
                server.getBroadcastOperations().sendEvent("chatevent", chatMessage);
            }
        });

        // Start the SocketIO Server
        server.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
