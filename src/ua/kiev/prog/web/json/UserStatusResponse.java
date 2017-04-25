package ua.kiev.prog.web.json;

import ua.kiev.prog.model.OnlineStatus;

/**
 * Created by arahis on 4/24/17.
 */
public class UserStatusResponse {
    private String username;
    private OnlineStatus status;

    public UserStatusResponse(String username, OnlineStatus status) {
        this.username = username;
        this.status = status;
    }
}
