package ua.kiev.prog.repositories;

import java.util.List;

import ua.kiev.prog.model.Message;

/**
 * Created by arahis on 4/24/17.
 */
public interface PrivateMessagesRepository {
    List<Message> getMessagesForUser(String name);
    void addMessage(Message message);
}
