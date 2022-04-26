package com.anukul.mongoSpring.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "todos")
public class TodoDTO {

    @Id
    private String id;

    @NotNull(message = "Oops! Todo cannot be null")
    @NotBlank(message = "Todo cannot be blank")
    private String todo;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Boolean isCompleted= false;

    private Date createdAt;

    private Date lastUpdatedAt;
}
