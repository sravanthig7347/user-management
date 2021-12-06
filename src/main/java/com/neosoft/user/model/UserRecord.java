package com.neosoft.user.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecord {
 	

	@Id
	@GeneratedValue 
	private Long id;
	
	@Size(min =1, max = 30, message = "fName must not be more than 30 chars ")
	@NotNull(message = "Name cannot be null")
	private String fistName;
	
	@Size(min =1, max = 30, message = "lName must not be more than 30 chars ")
	@NotNull(message = "Name cannot be null")
	private String lastName;

	@Value("${user.status.default}")
	private int userStatus;
	
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public Date dateOfJoining;
	
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public Date dateOfBirth;
	
	@Email(message = "Email should be valid")
	private String email;
	
	private int pinCode;
	
}
