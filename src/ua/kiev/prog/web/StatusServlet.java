package ua.kiev.prog.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kiev.prog.model.OnlineStatus;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repositories.UsersRepository;
import ua.kiev.prog.repositories.UsersRepositoryImpl;
import ua.kiev.prog.util.Constants;
import ua.kiev.prog.util.HttpUtils;
import ua.kiev.prog.util.ParseUtils;
import ua.kiev.prog.web.json.ChangeStatusRequest;
import ua.kiev.prog.web.json.UserStatusResponse;

/**
 * Created by arahis on 4/21/17.
 */
public class StatusServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(StatusServlet.class.getSimpleName());

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
        if (user == null) return;
        if (resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        String username = req.getParameter(Constants.USERNAME_PARAMETER);
        User checkedUser = users.getByName(username);
        if (checkedUser != null) {
            String responseJson = ParseUtils.toJson(
                    new UserStatusResponse(username, checkedUser.getStatus()), UserStatusResponse.class);
            resp.setContentType(Constants.CONTENT_TYPE_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.getWriter().write(responseJson);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    //changing status of user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = HttpUtils.authUser(req, resp);
        if (user == null) return;
        if (resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        ChangeStatusRequest changeStatusReq =
                ParseUtils.fromJson(req.getReader(), ChangeStatusRequest.class);
        try {
            user.setStatus(OnlineStatus.valueOf(changeStatusReq.getStatus()));
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
