package com.alexm.chatapp.entity;


public class MessageBody {
    private Long senderId;
    private Long receiverId;
    private String body;
    private Long date;
    public MessageBody(String body, Long senderId, Long receiverId, Long date) {
        this.body = body;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
