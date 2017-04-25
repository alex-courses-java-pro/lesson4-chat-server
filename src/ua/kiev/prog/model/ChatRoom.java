package ua.kiev.prog.model;

import java.util.ArrayList;
import java.util.List;

import ua.kiev.prog.util.Constants;

/**
 * Created by arahis on 4/20/17.
 */
public class ChatRoom {
    private String roomName;
    private List<Message> messages;

    public ChatRoom(String roomName, List<Message> messages) {
        this.roomName = roomName;
        this.messages = messages;
        }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public String getRoomName() {
        return roomName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "messages=" + messages +
                '}';
    }
}
