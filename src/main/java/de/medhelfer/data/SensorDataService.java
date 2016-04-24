package de.medhelfer.data;

import java.util.Collection;

public interface SensorDataService {
    Collection<SensorDataDto> findAllSensorData();
}
