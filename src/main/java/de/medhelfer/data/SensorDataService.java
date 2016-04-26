package de.medhelfer.data;

import java.time.LocalDateTime;
import java.util.Collection;

public interface SensorDataService {
    Collection<SensorDataDto> findAllSensorData();

    Collection<SensorDataDto> findSensorDataBetweenDates(LocalDateTime from, LocalDateTime to);

    void saveSensorReadings(Collection<SensorDataDto> sensorReadings);
}
