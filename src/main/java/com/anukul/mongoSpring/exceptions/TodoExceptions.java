package com.anukul.mongoSpring.exceptions;

public class TodoExceptions extends Exception{

    public TodoExceptions(String message){
        super(message);
    }

    public static String todoNotFoundException(String id){
        return "Todo with id: "+id+" doesnt exist";
    }

    public static String duplicateTodo(){
        return "Todo with given name already exists";
    }

}
