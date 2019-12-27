
package com.solace.temperature;
import java.util.HashMap;

public class TemperatureMessage { 


	// topic and messageId: These fields allow the client to see the topic
	// and messageId of a received messages. It is not necessary to set these 
	// when publishing.

	private String topic;

	public String getTopic() {
		return topic;
	}

	public TemperatureMessage setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	private String messageId;

	public String getMessageId() {
		return messageId;
	}

	public TemperatureMessage setMessageId(String messageId) {
		this.messageId = messageId;
		return this;
	}

	// Headers with their getters and setters.
	private HashMap<String, Object> headers = new HashMap<>();

	// Payload


	private Temperature temperature;

	public Temperature getPayload() {
		return temperature;
	}

	public TemperatureMessage setPayload(Temperature temperature) {
		this.temperature = temperature;
		return this;
	}

	// Listers

	public interface SubscribeListener {
		public void onReceive(TemperatureMessage temperatureMessage);
		public void handleException(Exception exception);
	}
}
