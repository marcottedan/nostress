package com.nostress.demo;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.VoidAckCallback;
import com.corundumstudio.socketio.listener.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AckChatLauncher {

    protected static final Logger Log = LoggerFactory.getLogger(AckChatLauncher.class);

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8080);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(final SocketIOClient client, ChatObject chatObject, final AckRequest ackRequest) {

                final String userName = chatObject.getUserName();
                final String chatMessage = chatObject.getMessage();

                // check is ack requested by client, but it's not required check
                if (ackRequest.isAckRequested()) {
                    // send ack response with chatObject to client
                    ChatObject syncChatObject = new ChatObject(userName, "(Sync) Server saw " + chatMessage);
                    ackRequest.sendAckData(syncChatObject);
                }

                // send message back to client with ack callback WITH chatObject
                ChatObject ackChatObject = new ChatObject(userName, "(Ack) Server saw " + chatMessage);
                client.sendEvent("ackevent2", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        Log.info("Ack Callback from client: '{}' with userName '{}' and message '{}' with result '{}'", client.getSessionId(), userName, chatMessage, result);
                    }
                }, ackChatObject);

                ChatObject voidAckChatObject = new ChatObject(chatObject.getUserName(), "(VoidAck) Server saw " + chatObject.getMessage());
                client.sendEvent("ackevent3", new VoidAckCallback() {
                    protected void onSuccess() {
                        Log.info("VoidAck Callback from client: '{}' with userName '{}' and message '{}'", client.getSessionId(), userName, chatMessage);
                    }

                }, voidAckChatObject);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}