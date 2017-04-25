package ua.kiev.prog.repositories;

import java.util.List;

import ua.kiev.prog.model.ChatRoom;

/**
 * Created by arahis on 4/20/17.
 */
public interface ChatRoomsRepository {

    List<ChatRoom> getAll();

    ChatRoom getByName(String name);

    void add(ChatRoom chatRoom);
}
