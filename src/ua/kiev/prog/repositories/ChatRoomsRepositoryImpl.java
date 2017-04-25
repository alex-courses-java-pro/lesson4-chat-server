package ua.kiev.prog.repositories;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.List;

import ua.kiev.prog.model.Message;
import ua.kiev.prog.util.Constants;
import ua.kiev.prog.model.ChatRoom;

/**
 * Created by arahis on 4/20/17.
 */
public class ChatRoomsRepositoryImpl implements ChatRoomsRepository {

    private static ChatRoomsRepositoryImpl instance;

    private List<ChatRoom> chatRooms;


    public static synchronized ChatRoomsRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ChatRoomsRepositoryImpl();
        }
        return instance;
    }

    private ChatRoomsRepositoryImpl() {
        synchronized (this) {
            chatRooms = new ArrayList<>();
            ChatRoom def = new ChatRoom(Constants.DEFAULT_CHAT_ROOM_NAME, new ArrayList<>());
            chatRooms.add(def);
        }
    }

    @Override
    public List<ChatRoom> getAll() {
        synchronized (this) {
            return chatRooms;
        }
    }

    @Override
    public ChatRoom getByName(String name) {
        synchronized (this) {
            ChatRoom chatRoom = null;

            for (ChatRoom cr : chatRooms)
                if (cr.getRoomName().equals(name))
                    chatRoom = cr;

            return chatRoom;
        }
    }

    @Override
    public void add(ChatRoom chatRoom) {
        synchronized (this) {
            chatRooms.add(chatRoom);
        }
    }
}
