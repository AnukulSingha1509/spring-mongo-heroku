package com.anukul.mongoSpring.repository;

import com.anukul.mongoSpring.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{ 'isCompleted': false }")
    List<TodoDTO> findPending();

    @Query("{'todo':?0}")
    Optional<TodoDTO> findByTodo(String todo);
}
