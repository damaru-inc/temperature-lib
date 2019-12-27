
package com.solace.temperature;


public  class Temperature {

	private Integer sensorId;
	private Integer time;
	private Double temperature;

	public Integer getSensorId() {
		return sensorId;
	}

	public Temperature setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
		return this;
	}

	public Integer getTime() {
		return time;
	}

	public Temperature setTime(Integer time) {
		this.time = time;
		return this;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Temperature setTemperature(Double temperature) {
		this.temperature = temperature;
		return this;
	}
}

