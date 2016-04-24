package de.medhelfer.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

@Controller
public class SensorDataServiceImpl implements SensorDataService {

    private final Collection<SensorDataDto> sensorReadings;

    // TODO get data from data base
    public SensorDataServiceImpl() throws IOException {
        String sensorReadingsJson = new String(
                Files.readAllBytes(Paths.get("sensor-readings.json"))
        );
        ObjectMapper mapper = new ObjectMapper();
        sensorReadings = mapper.readValue(sensorReadingsJson, Collection.class);
    }

    @Override
    public Collection<SensorDataDto> findAllSensorData() {
        return sensorReadings;
    }
}
