package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message {
    private long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime messageDateTime;

    public Message(long id, User author, Chatroom room,
                String text, LocalDateTime messageDateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.messageDateTime = messageDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(LocalDateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Message message = (Message) other;
        return id == message.id && Objects.equals(author, message.author) && Objects.equals(room, message.room)
                && Objects.equals(text, message.text) && Objects.equals(messageDateTime, message.messageDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, messageDateTime);
    }

    @Override
    public String toString() {
        return "Message{" +
                "\nid=" + id +
                ",\nauthor=" + author +
                ",\nchatroom=" + room +
                ",\ntext='" + text + '\'' +
                ",\nmessageDateTime=" + messageDateTime +
                "\n}";
    }
}
