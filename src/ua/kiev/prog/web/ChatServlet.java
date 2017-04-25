package ua.kiev.prog.web;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kiev.prog.model.ChatRoom;
import ua.kiev.prog.model.Message;
import ua.kiev.prog.model.OnlineStatus;
import ua.kiev.prog.model.User;
import ua.kiev.prog.repositories.ChatRoomsRepository;
import ua.kiev.prog.repositories.ChatRoomsRepositoryImpl;
import ua.kiev.prog.repositories.PrivateMessagesRepository;
import ua.kiev.prog.repositories.PrivateMessagesRepositoryImpl;
import ua.kiev.prog.repositories.UsersRepository;
import ua.kiev.prog.repositories.UsersRepositoryImpl;
import ua.kiev.prog.util.Constants;
import ua.kiev.prog.util.HttpUtils;
import ua.kiev.prog.util.ParseUtils;
import ua.kiev.prog.web.json.CreateChatRoomRequest;

/**
 * Created by arahis on 4/24/17.
 */
public class ChatServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChatServlet.class.getSimpleName());

    private ChatRoomsRepository chatRoomsRepository;
    private PrivateMessagesRepository privateMessagesRepository;
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        //super.init();
        logger.info("init() called ");
        chatRoomsRepository = ChatRoomsRepositoryImpl.getInstance();
        privateMessagesRepository = PrivateMessagesRepositoryImpl.getInstance();
        usersRepository = UsersRepositoryImpl.getInstance();
    }

    //getting messages
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = HttpUtils.authUser(req, resp);
        if (user == null) return;
        if (resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        int fromIndx = Integer.parseInt(req.getParameter(Constants.FROM_INDEX_PARAMETER));
        //logger.info(String.format("from index is %d", fromIndx));

        ChatRoom chatRoomForResponse = getChatRoomForUser(user, fromIndx);
        String responseJson = ParseUtils.toJson(chatRoomForResponse, ChatRoom.class);

        //logger.info("json for response: " + responseJson);
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().write(responseJson);
    }

    //creating chat room if no exist
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = HttpUtils.authUser(req, resp);
        if (user == null) return;
        if (resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        CreateChatRoomRequest createRoomReq =
                ParseUtils.fromJson(req.getReader(), CreateChatRoomRequest.class);

        String roomName = createRoomReq.getName();
        logger.info(String.format("checking if room \"%s\" already exist", roomName));
        if (chatRoomsRepository.getByName(roomName) == null) {
            logger.info("creating room with name: " + roomName);
            chatRoomsRepository.add(new ChatRoom(createRoomReq.getName(), new ArrayList<>()));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        user.setCurrentChatRoom(roomName);
    }

    //posting message in chat room
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = HttpUtils.authUser(req, resp);
        if (user == null) return;
        if (resp.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) return;

        Message message = ParseUtils.fromJson(req.getReader(), Message.class);
        logger.info("message from json: " + message);

        if (message.getTo().equals(Constants.TO_ALL)) {
            logger.info("sending message to chat room: " + user.getCurrentChatRoom());
            chatRoomsRepository.getByName(user.getCurrentChatRoom()).addMessage(message);
        } else {
            logger.info("sending message to user");
            resp.setStatus(sendPrivateMessage(message));
        }
    }

    private int sendPrivateMessage(Message message) {
        int responseStatus = HttpServletResponse.SC_OK;
        String toUsername = message.getTo();
        User toUser = usersRepository.getByName(toUsername);
        if ((toUser) != null && (toUser.getStatus() != OnlineStatus.OFFLINE)) {
            privateMessagesRepository.addMessage(message);
        } else {
            responseStatus = HttpServletResponse.SC_NOT_FOUND;
        }
        return responseStatus;
    }

    private ChatRoom getChatRoomForUser(User user, int fromIndx) {
        String roomName = user.getCurrentChatRoom();
        //logger.info(String.format("getting all messages in \"%s\" room from %d index",
        //        roomName, fromIndx));
        ChatRoom room = chatRoomsRepository.getByName(roomName);
        List<Message> msgs = room.getMessages();
        List<Message> msgsForResponse = new ArrayList<>();

        while (fromIndx < msgs.size()) {
            msgsForResponse.add(msgs.get(fromIndx));
            fromIndx++;
        }

        String username = user.getName();
        //logger.info("getting all private messages for user: " + username);
        List<Message> privateMsgs = privateMessagesRepository.getMessagesForUser(username);
        msgsForResponse.addAll(privateMsgs);

        return new ChatRoom(roomName, msgsForResponse);
    }
}
