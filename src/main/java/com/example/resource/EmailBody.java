package com.example.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailBody {
	
	  private String email;
	  private String subject;
	  private String content;
}
