package com.anukul.mongoSpring.service;

import com.anukul.mongoSpring.exceptions.TodoExceptions;
import com.anukul.mongoSpring.model.TodoDTO;
import com.anukul.mongoSpring.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoDTO> fetchAllTodos() {
        List<TodoDTO> list= todoRepository.findAll();
        if(list.size()>0){
            return list;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public TodoDTO findTodoById(String id) throws TodoExceptions {
        Optional<TodoDTO> optionalTodoDTO= todoRepository.findById(id);
        if(optionalTodoDTO.isPresent()){
            return optionalTodoDTO.get();
        }else{
            throw new TodoExceptions(TodoExceptions.todoNotFoundException(id));
        }
    }

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoExceptions {
        Optional<TodoDTO> todoOptional= todoRepository.findByTodo(todo.getTodo());
        if(todoOptional.isPresent()){
            throw new TodoExceptions(TodoExceptions.duplicateTodo());
        }else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }

    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoExceptions {
        Optional<TodoDTO> todoOptional= todoRepository.findById(id);
        if(todoOptional.isEmpty()){
            throw new TodoExceptions((TodoExceptions.todoNotFoundException(id)));
        }else if(todoRepository.findByTodo(todo.getTodo()).isPresent()){
            throw new TodoExceptions(TodoExceptions.duplicateTodo());
        }else{
            TodoDTO todoToUpdate= todoOptional.get();
            todoToUpdate.setTodo(todo.getTodo()!=null ? todo.getTodo() : todoToUpdate.getTodo());
            todoToUpdate.setDescription(todo.getDescription()!=null ? todo.getDescription() : todoToUpdate.getDescription());
            todoToUpdate.setIsCompleted(todo.getIsCompleted()!=null ? todo.getIsCompleted() : todoToUpdate.getIsCompleted());
            todoToUpdate.setLastUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoToUpdate);
        }
    }

    @Override
    public void deleteTodoById(String id) throws TodoExceptions {
        Optional<TodoDTO> todoToDelete= todoRepository.findById(id);
        if(todoToDelete.isPresent()){
            todoRepository.deleteById(id);
        }else{
            throw new TodoExceptions(TodoExceptions.todoNotFoundException(id));
        }
    }

    @Override
    public List<TodoDTO> findPendingTodos() {
        return todoRepository.findPending();
    }
}
