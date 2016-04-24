package de.medhelfer.data;

import java.time.LocalDateTime;

public class SensorDataDto {

    private final LocalDateTime timestamp;
    private final float temperature;
    private final float humidity;

    public SensorDataDto(LocalDateTime timestamp, float temperature, float humidity) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }
}
