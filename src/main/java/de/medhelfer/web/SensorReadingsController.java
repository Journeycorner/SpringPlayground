package de.medhelfer.web;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_CLIENT") // TODO workaround for Spring not able to use enums in this annotation
    @CrossOrigin
    @RequestMapping("/sensorReadings")
    public Collection<SensorDataDto> findAllSensorReadings() {
        return sensorDataService.findAllSensorData();
    }


    @Secured("ROLE_CLIENT") // TODO workaround for Spring not able to use enums in this annotation
    @CrossOrigin
    @RequestMapping("/findSensorDataBetweenDates")
    public Collection<SensorDataDto> findSensorDataBetweenDates(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return sensorDataService.findSensorDataBetweenDates(
                from != null ? from : LocalDateTime.MIN,
                to != null ? to : LocalDateTime.MAX);
    }

    @Secured("ROLE_SENSOR_READER") // TODO workaround for Spring not able to use enums in this annotation
    @CrossOrigin
    @RequestMapping("/saveSensorReadings")
    public void saveSensorReadings(@RequestBody() Collection<SensorDataDto> sensorReadings) {
        sensorDataService.saveSensorReadings(sensorReadings);
    }
}
