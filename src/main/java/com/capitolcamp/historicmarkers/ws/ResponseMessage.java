package com.capitolcamp.historicmarkers.ws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseMessage {
	private String message;

	ResponseMessage() {
		super();
	}

	ResponseMessage(final String message) {
		super();
		this.message = message;
	}

	@XmlElement()
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}

}
