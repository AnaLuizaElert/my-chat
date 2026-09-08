package utils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MessagePackage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public MessagePackage(MessageType type, String sender, String receiver, LocalDateTime dateTime, int payloadLength, String fileName, byte[] payload) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.dateTime = dateTime;
        this.payloadLength = payloadLength;
        this.fileName = fileName;
        this.payload = payload;
    }

    public MessagePackage(MessageType type, String sender, String receiver, LocalDateTime dateTime, String fileName, byte[] payload) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.dateTime = dateTime;
        this.fileName = fileName;
        this.payload = payload;
    }


    private MessageType type;
    private String sender;
    private String receiver;
    private LocalDateTime dateTime;
    private int payloadLength;
    private String fileName;
    private byte[] payload;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}