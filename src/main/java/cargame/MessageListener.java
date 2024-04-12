package cargame;

import socket.object.Message;

public interface MessageListener {
    void onMessageReceived(Message message);
}
