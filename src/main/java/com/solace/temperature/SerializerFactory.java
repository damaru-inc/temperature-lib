
package com.solace.temperature;
public class SerializerFactory {

	private static String JSON = "application/json";
	private static String XML = "application/xml";
	private static String defaultContentType = "application/xml";
	
	public static <T> Serializer<T> getSerializer(String contentType, Class<T> objectClass) throws Exception {
		Serializer<T> ret = null;
		
		if (contentType == null || contentType.length() == 0) {
			contentType = defaultContentType;
		}
		
		if (contentType.equals(XML)) {
			ret = new XmlSerializer<T>(objectClass);
		} else if (contentType.equals(JSON)) {
			ret = new JsonSerializer<T>(objectClass);
		} else {
			throw new Exception("Unknown content type: " + contentType);
		}
		
		return ret;
	}
}

