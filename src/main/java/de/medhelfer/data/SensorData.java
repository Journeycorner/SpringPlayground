package de.medhelfer.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = SensorData.FIND_ALL,
        query = "SELECT NEW de.medhelfer.data.SensorDataDto(s.timestamp, s.temperature, s.humidity) " +
                "FROM SensorData s")
public class SensorData {

    public final static String FIND_ALL = "SensorData.findAll";

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime timestamp;
    private float temperature;
    private float humidity;

    public SensorData() {
    }

    public SensorData(LocalDateTime timestamp, float temperature, float humidity) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
