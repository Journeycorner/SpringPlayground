package de.medhelfer.web;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
public class SensorReadingsController {

    private final SensorDataService sensorDataService;

    @Inject
    public SensorReadingsController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @CrossOrigin()
    @RequestMapping("/sensorReadings")
    public Collection<SensorDataDto> findAllSensorReadings() {
        return sensorDataService.findAllSensorData();
    }

    @CrossOrigin()
    @RequestMapping("/findSensorDataBetweenDates")
    public Collection<SensorDataDto> findSensorDataBetweenDates(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to) {
        return sensorDataService.findSensorDataBetweenDates(
                from != null ? from : LocalDateTime.MIN,
                to != null ? to : LocalDateTime.MAX
        );
    }

    @CrossOrigin()
    @RequestMapping("/saveSensorReadings")
    public Collection<SensorDataDto> saveSensorReadings(
            @RequestBody() Collection<SensorDataDto> sensorReadings) {
        return sensorDataService.saveSensorReadings(sensorReadings);
    }
}
