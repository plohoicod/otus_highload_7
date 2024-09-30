package ru.otus.highload.homework.fifth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.highload.homework.fifth.dto.MessageInDto;
import ru.otus.highload.homework.fifth.dto.MessageOutDto;
import ru.otus.highload.homework.fifth.entity.Message;
import ru.otus.highload.homework.fifth.repository.MessageRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Value("${db.shard}")
    private Long shard1;

    @Value("${db.shard.second}")
    private Long shard2;



    public List<MessageOutDto> getMessages(Long chatId) {
        if (chatId % shard2 < shard1) {
            return getMessage(chatId);
        } else {
            return getMessage(chatId);
        }
    }

    private List<MessageOutDto> getMessage(Long chatId) {
        List<MessageOutDto> messageOutDtos = new ArrayList<>();

        try {
            List<Message> messages = messageRepository.findAllByChatId(chatId);

            messages.forEach(e -> messageOutDtos.add(new MessageOutDto(e.getUserFromId(),
                    e.getUserToId(),
                    e.getMessage(),
                    e.getCreateDatetime())));



        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return messageOutDtos;
    }

    public void addMessage(MessageInDto dto) {
        if (dto.chatId() % shard2 < shard1) {
            createMessage(dto);
        } else {
            createMessage(dto);
        }
    }

    private void createMessage(MessageInDto dto) {
        try {
            Message message = new Message();
            message.setMessage(dto.message());
            message.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
            message.setChatId(dto.chatId());
            message.setUserFromId(dto.userFromId());
            message.setUserToId(dto.userToId());
            messageRepository.save(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
