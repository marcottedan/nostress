package com.nostress;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.redisson.Config;
import org.redisson.Redisson;

/**
 * Created by Daniel on 2014-12-25.
 */
public class Bootstrap {

    public static void main (String[] args){
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

        // Start the SocketIO Server
        server.start();
    }
}
