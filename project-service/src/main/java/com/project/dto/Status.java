package com.project.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
	
	NEW,
	DEFINED,
	TO_DO,
	IN_PROGRESS,
	DONE,
	ACCEPECTED,
	DEPLOYED;

	/*NEW("NEW"),
	DEFINED("DEFINED"),
	TO_DO("TO_DO"),
	IN_PROGRESS("IN_PROGRESS"),
	DONE("DONE"),
	ACCEPECTED("ACCEPECTED"),
	DEPLOYED("DEPLOYED");
	
	private String statusCode;
 
    private Status(String statusCode) {
 
        this.statusCode = statusCode;
    }
 
    public String getStatusCode() {
 
        return this.statusCode;
    }
	
    @JsonCreator
    public static Status getStatusFromCode(String value) {
 
        for (Status s : Status.values()) {
 
            if (s.getStatusCode().equals(value)) {
 
                return s;
            }
        }
 
        return null;
    }*/
}
