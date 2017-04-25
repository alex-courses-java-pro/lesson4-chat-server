package ua.kiev.prog.model;

import java.util.Date;

public class Message {
    private Date date;
    private String from;
    private String to;
    private String text;

    public Message(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
        date = new Date();
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
