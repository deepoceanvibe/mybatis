package com.spring.blog.exception;

public class NotFoundBlogIdException extends RuntimeException{
    public NotFoundBlogIdException(String message) {
        super(message);
    }

}
