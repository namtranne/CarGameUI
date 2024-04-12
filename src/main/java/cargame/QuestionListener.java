package cargame;

import socket.object.Game;

public interface QuestionListener {
    void onQuestionReceived(String question);

    void receivedGameData(Game receivedObject);
}
