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

    @CrossOrigin
    @RequestMapping("/sensorReadings")
    public Collection<SensorDataDto> findAllSensorReadings() {
        return sensorDataService.findAllSensorData();
    }

    // TODO replace String by LocalDateTime and fix mapping
    @CrossOrigin
    @RequestMapping("/findSensorDataBetweenDates")
    public Collection<SensorDataDto> findSensorDataBetweenDates(
            @RequestParam(value = "from", required = false) String f,
            @RequestParam(value = "to", required = false) String t) {
        LocalDateTime from = LocalDateTime.parse(f);
        LocalDateTime to = LocalDateTime.parse(t);
        return sensorDataService.findSensorDataBetweenDates(
                from != null ? from : LocalDateTime.MIN,
                to != null ? to : LocalDateTime.MAX
        );
    }

    @CrossOrigin
    @RequestMapping("/saveSensorReadings")
    public void saveSensorReadings(
            @RequestBody() Collection<SensorDataDto> sensorReadings) {
        sensorDataService.saveSensorReadings(sensorReadings);
    }
}
