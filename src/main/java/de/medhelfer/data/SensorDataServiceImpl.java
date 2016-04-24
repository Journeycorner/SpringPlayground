package de.medhelfer.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Controller
public class SensorDataServiceImpl implements SensorDataService {

    private final Collection<SensorDataDto> sensorReadings;

    // TODO get data from data base
    public SensorDataServiceImpl() throws IOException {
        String sensorReadingsJson = new String(
                Files.readAllBytes(Paths.get("sensor-readings.json"))
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        sensorReadings = mapper.readValue(sensorReadingsJson, new TypeReference<Collection<SensorDataDto>>() { });
    }

    @Override
    public Collection<SensorDataDto> findAllSensorData() {
        return sensorReadings;
    }

    @Override
    public Collection<SensorDataDto> findSensorDataBetweenDates(LocalDateTime from, LocalDateTime to) {
        // TODO sort in db
        return findAllSensorData().stream()
                .sorted(comparing(SensorDataDto::getTimestamp).reversed())
                .filter(item -> item.getTimestamp().isAfter(from) && item.getTimestamp().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SensorDataDto> saveSensorReadings(Collection<SensorDataDto> sensorReadings) {
        // TODO save in db
        return null;
    }
}
