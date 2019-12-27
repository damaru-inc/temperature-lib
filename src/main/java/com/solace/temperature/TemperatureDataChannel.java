
package com.solace.temperature;
import javax.annotation.PostConstruct;

import com.solacesystems.jcsmp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TemperatureDataChannel {

	// Channel name: temperature/data
	private static final String SUBSCRIBE_TOPIC = "temperature/data";

	@Autowired
	private SolaceSession solaceSession;
	private JCSMPSession jcsmpSession;
	private Serializer<Temperature> serializer;
	private XMLMessageConsumer consumer;

	@PostConstruct
	public void init() throws Exception {
		jcsmpSession = solaceSession.getSession();
		serializer = SerializerFactory.getSerializer("application/json", Temperature.class);
	}

	public void subscribe(TemperatureMessage.SubscribeListener listener) throws Exception {
		System.out.println("got a listener: " + listener);
		MessageListener messageListener = new MessageListener(listener);
		consumer = jcsmpSession.getMessageConsumer(messageListener);
		Topic topic = JCSMPFactory.onlyInstance().createTopic(SUBSCRIBE_TOPIC);
		jcsmpSession.addSubscription(topic);
		consumer.start();
	}


	public void close() {

		if (consumer != null) {
			consumer.close();		
		}

		solaceSession.close();
	}


	class MessageListener implements XMLMessageListener {

		TemperatureMessage.SubscribeListener listener;
		
		public MessageListener(TemperatureMessage.SubscribeListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void onException(JCSMPException exception) {
			listener.handleException(exception);
		}

		@Override
		public void onReceive(BytesXMLMessage bytesXMLMessage) {


			String messageText = null;

			if (bytesXMLMessage instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) bytesXMLMessage;
				messageText = textMessage.getText();
			} else if (bytesXMLMessage instanceof BytesMessage) {
				BytesMessage bytesMessage = (BytesMessage) bytesXMLMessage;
				messageText = new String(bytesMessage.getData());
			}

			//System.out.println("Got a message: " + bytesXMLMessage.getClass() + " " + messageText);
			Temperature payload;
			try {
				payload = serializer.deserialize(messageText);
				TemperatureMessage  temperatureMessage = new TemperatureMessage();
				temperatureMessage.setMessageId(bytesXMLMessage.getMessageId());
				temperatureMessage.setPayload(payload);
				temperatureMessage.setTopic(bytesXMLMessage.getDestination().getName());
				listener.onReceive(temperatureMessage);
			} catch (Exception exception) {
				listener.handleException(exception);
			}			
		}
	}

}

