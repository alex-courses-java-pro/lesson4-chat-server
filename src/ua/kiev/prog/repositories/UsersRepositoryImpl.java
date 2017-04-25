package ua.kiev.prog.repositories;

import java.util.ArrayList;
import java.util.List;

import ua.kiev.prog.model.User;

/**
 * Created by arahis on 4/20/17.
 */
public class UsersRepositoryImpl implements UsersRepository {

    private static UsersRepositoryImpl instance;

    private List<User> users;

    public static synchronized UsersRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UsersRepositoryImpl();
        }
        return instance;
    }

    private UsersRepositoryImpl() {
        synchronized (this) {
            users = new ArrayList<>();
            users.add(new User("Erich", "pass"));
            users.add(new User("Richard", "pass"));
            users.add(new User("Ralph", "pass"));
            users.add(new User("John", "pass"));
        }
    }

    @Override
    public List<User> getAll() {
        synchronized (this) {
            return users;
        }
    }

    @Override
    public User getByName(String name) {
        synchronized (this) {
            User user = null;

            for (User u : users)
                if (u.getName().equals(name))
                    user = u;

            return user;
        }
    }
}
