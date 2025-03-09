package com.example.websocket_chat.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;

import com.example.websocket_chat.chat.ChatMessage;
import com.example.websocket_chat.chat.MessageType;

@Component
@RequiredArgsConstructor
public class ConnectionEventListener {
    
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        @SuppressWarnings("null")
        String username = (String)headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
