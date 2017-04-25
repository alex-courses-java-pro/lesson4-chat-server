package ua.kiev.prog.model;

import ua.kiev.prog.util.Constants;

/**
 * Created by arahis on 4/20/17.
 */
public class User {
    private final String name;
    private final String password;
    private OnlineStatus status;
    private String currentChatRoom;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        status = OnlineStatus.OFFLINE;
        currentChatRoom = Constants.DEFAULT_CHAT_ROOM_NAME;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public String getCurrentChatRoom() {
        return currentChatRoom;
    }

    public void setCurrentChatRoom(String currentChatRoom) {
        this.currentChatRoom = currentChatRoom;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", currentChatRoom='" + currentChatRoom + '\'' +
                '}';
    }
}
