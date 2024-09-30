package ru.otus.highload.homework.fifth.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.sql.Timestamp;

@RedisHash(value = "message")
@Data
public class Message {

    @Id
    private Integer id;

    @Indexed
    private Long chatId;
    private Long userFromId;
    private Long userToId;
    private String message;
    private Timestamp createDatetime;
}
