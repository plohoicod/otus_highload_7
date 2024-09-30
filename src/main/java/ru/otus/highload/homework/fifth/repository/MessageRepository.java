package ru.otus.highload.homework.fifth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.highload.homework.fifth.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByChatId(Long chatId);
}
