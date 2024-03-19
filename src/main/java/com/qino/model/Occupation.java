package com.qino.model;

import lombok.Getter;

@Getter
public enum Occupation {
	
	DIRECTOR("Directors"),
	PRODUCER("Producers"),
	WRITER("Writers"),
	COMPOSER("Composers"),
	ACTOR("Casts");
	
	private final String pluralName;
	
	Occupation(String pluralName) {
		this.pluralName = pluralName;
	}
	
	;
}
