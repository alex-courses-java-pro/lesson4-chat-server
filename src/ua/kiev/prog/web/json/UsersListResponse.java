package ua.kiev.prog.web.json;

import java.util.List;

/**
 * Created by arahis on 4/24/17.
 */
public class UsersListResponse {
    private List<String> names;

    public UsersListResponse(List<String> names) {
        this.names = names;
    }
}
