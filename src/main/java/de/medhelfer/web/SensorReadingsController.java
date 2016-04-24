package de.medhelfer.web;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;

@RestController
public class SensorReadingsController {

    private final SensorDataService sensorDataService;

    @Inject
    public SensorReadingsController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @RequestMapping("/sensorReadings")
    public Collection<SensorDataDto> helloWorld() {
        return sensorDataService.findAllSensorData();
    }
}
