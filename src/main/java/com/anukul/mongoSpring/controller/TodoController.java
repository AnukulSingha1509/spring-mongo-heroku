package com.anukul.mongoSpring.controller;

import com.anukul.mongoSpring.exceptions.TodoExceptions;
import com.anukul.mongoSpring.model.TodoDTO;
import com.anukul.mongoSpring.repository.TodoRepository;
import com.anukul.mongoSpring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todos= todoService.fetchAllTodos();
        return new ResponseEntity<>(todos, todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id) throws TodoExceptions {
        try{
            TodoDTO todo= todoService.findTodoById(id);
            return new ResponseEntity<>(todo,HttpStatus.OK);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingTodos(){
        List<TodoDTO> pendingTodos= todoService.findPendingTodos();

        if(pendingTodos.size()>0){
            return new ResponseEntity<List<TodoDTO>>(pendingTodos, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No pending todos available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTodo(@RequestBody TodoDTO todo){
        try{
            todoService.createTodo(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todo, @PathVariable String id){
        try{
            todoService.updateTodo(id,todo);
            return new ResponseEntity<>(todo,HttpStatus.OK);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable String id){
        try{
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Deleted todo with id: "+id,HttpStatus.OK);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
