package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> roomMessages;

    public Chatroom(Long id, String name, User owner,
                    List<Message> roomMessages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.roomMessages = roomMessages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public User getOwner() {
        return owner;
    }

    @SuppressWarnings("unused")
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @SuppressWarnings("unused")
    public List<Message> getRoomMessages() {
        return roomMessages;
    }

    @SuppressWarnings("unused")
    public void setRoomMessages(List<Message> roomMessages) {
        this.roomMessages = roomMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return Objects.equals(id, chatroom.id) && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner) && Objects.equals(roomMessages, chatroom.roomMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, roomMessages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", roomMessages=" + roomMessages +
                '}';
    }
}
