package com.anukul.mongoSpring.service;

import com.anukul.mongoSpring.exceptions.TodoExceptions;
import com.anukul.mongoSpring.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface TodoService {

    public List<TodoDTO> fetchAllTodos();

    public TodoDTO findTodoById(String id) throws TodoExceptions;

    public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoExceptions;

    public void updateTodo(String id, TodoDTO todo) throws TodoExceptions;

    public void deleteTodoById(String id) throws TodoExceptions;

    public List<TodoDTO> findPendingTodos();
}
