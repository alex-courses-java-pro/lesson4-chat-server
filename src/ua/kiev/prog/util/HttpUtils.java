package ua.kiev.prog.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kiev.prog.model.OnlineStatus;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repositories.UsersRepository;
import ua.kiev.prog.repositories.UsersRepositoryImpl;

/**
 * Created by arahis on 4/20/17.
 */
public class HttpUtils {

    public static User authUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException{
        UsersRepository users = UsersRepositoryImpl.getInstance();
        User user = null;
        final String authorization = req.getHeader(Constants.AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String userpass = values[1];

            user = users.getByName(username);
            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }else if (!user.getPassword().equals(userpass)){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return user;
    }
}