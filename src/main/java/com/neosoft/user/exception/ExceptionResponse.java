package com.neosoft.user.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse{
	
	private Date timestamp;
    private String message;
    private String details;

}
