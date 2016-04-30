package de.medhelfer.web;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
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


    // TODO replace String by LocalDateTime and fix mapping
    @Secured("ROLE_CLIENT") // TODO workaround for Spring not able to use enums in this annotation
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

    @Secured("ROLE_SENSOR_READER") // TODO workaround for Spring not able to use enums in this annotation
    @CrossOrigin
    @RequestMapping("/saveSensorReadings")
    public void saveSensorReadings(@RequestBody() Collection<SensorDataDto> sensorReadings) {
        sensorDataService.saveSensorReadings(sensorReadings);
    }
}
