package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        // TodoEntity 저장
        todoRepository.save(entity);

        // TodoEntity 검색
        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }
}
