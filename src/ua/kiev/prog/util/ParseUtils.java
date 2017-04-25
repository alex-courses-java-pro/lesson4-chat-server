package ua.kiev.prog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by arahis on 4/20/17.
 */
public class ParseUtils {
    private static Gson gson = new GsonBuilder().create();

    public static <T> T fromJson(String json, Class<T> t) {
        return gson.fromJson(json, t);
    }

    public static <T> T fromJson(Reader reader, Class<T> t) {
        return gson.fromJson(reader, t);
    }

    public static String toJson(Object obj, Type t) {
        return gson.toJson(obj, t);
    }
/*

    public static Message messageFromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    public static String messageToJson(Message message) {
        return gson.toJson(message);
    }

    public static String msgListToJson(JsonMessages jsonMessages) {
        return gson.toJson(jsonMessages);
    }

    public static String chatRoomToJson(ChatRoom room) {
        return gson.toJson(room);
    }

    public static CreateChatRoomRequest createChatRoomRequestFromJson(Reader reader) {
        return gson.fromJson(reader, crea)
    }
*/
}
