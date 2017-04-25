package ua.kiev.prog.repositories;

import java.util.List;

import ua.kiev.prog.model.User;

/**
 * Created by arahis on 4/20/17.
 */
public interface UsersRepository {

    List<User> getAll();

    User getByName(String name);

}
