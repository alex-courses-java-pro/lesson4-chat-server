package ua.kiev.prog.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kiev.prog.model.User;
import ua.kiev.prog.repositories.UsersRepository;
import ua.kiev.prog.repositories.UsersRepositoryImpl;
import ua.kiev.prog.util.Constants;
import ua.kiev.prog.util.HttpUtils;
import ua.kiev.prog.util.ParseUtils;
import ua.kiev.prog.web.json.UsersListResponse;

/**
 * Created by arahis on 4/21/17.
 */
public class UsersServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsersServlet.class.getSimpleName());

    private UsersRepository users;

    @Override
    public void init() throws ServletException {
        super.init();
        users = UsersRepositoryImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = HttpUtils.authUser(req, resp);
        if(user == null) return;
        if(resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        List<String> usernames = new ArrayList<>();
        for (User u : users.getAll()) {
            usernames.add(u.getName());
        }

        String responseJson = ParseUtils.toJson(
                new UsersListResponse(usernames), UsersListResponse.class);

        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().write(responseJson);
    }
}
