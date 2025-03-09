package com.example.websocket_chat.chat;

import lombok.*;

@Getter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
