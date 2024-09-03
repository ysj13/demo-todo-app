package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        // TodoEntity 저장
        repository.save(entity);

        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // validation
        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return repository.findByUserIdQuery(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("entity is null");
            throw new RuntimeException("entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserIdQuery(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        // 1. 저장할 엔티티가 유효한지 확인한다.
        validate(entity);

        // 2. 넘겨받은 엔티티 id를 이용해 TodoEntity 를 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        // 3. 반환된 TodoEntity 가 존재하면 값을 새 entity 값으로 덮어 씌운다.
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 4. 데이터베이스에 새 값을 저장한다
            repository.save(todo);
        });

        // Retrieve Todo 에서 만든 메서드를 이용해 유저의 모든 Todo 리스트를 리턴한다.
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);

            throw new RuntimeException("error deleting entity" + entity.getId());
        }

        return retrieve(entity.getUserId());
    }
}
