package ua.kiev.prog.repositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.kiev.prog.model.ChatRoom;
import ua.kiev.prog.model.Message;

/**
 * Created by arahis on 4/24/17.
 */
public class PrivateMessagesRepositoryImpl implements PrivateMessagesRepository {
    private static PrivateMessagesRepository instance;

    private List<Message> messages;

    private PrivateMessagesRepositoryImpl() {
        messages = new ArrayList<>();
    }

    public static synchronized PrivateMessagesRepository getInstance() {
        if (instance == null) {
            instance = new PrivateMessagesRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Message> getMessagesForUser(String name) {
        synchronized (this) {
            List<Message> msgForUser = new ArrayList<>();
            Iterator<Message> iter = messages.iterator();
            while (iter.hasNext()) {
                Message message = iter.next();
                if (message.getTo().equals(name)) {
                    msgForUser.add(message);
                    iter.remove();
                }
            }
            return msgForUser;
        }
    }

    @Override
    public void addMessage(Message message) {
        synchronized (this) {
            messages.add(message);
        }
    }
}
