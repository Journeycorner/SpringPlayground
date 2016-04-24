package de.medhelfer;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BackendApplication.class)
@WebIntegrationTest
public class SensorReadingsControllerIntegrationTests {

    @Inject
    SensorDataService sensorDataService;

    RestTemplate restTemplate = new RestTemplate();

    //Collection<SensorDataDto> saveSensorReadings(Collection<SensorDataDto> sensorReadings);

    @Test
    public void findAllSensorData() {
        Collection<SensorDataDto> result = restTemplate.getForObject("http://localhost:8080/sensorReadings", Collection.class);
        assertThat(result).hasSize(3861);
    }

    @Test
    public void findSensorDataBetweenDates() {
        Collection<SensorDataDto> result = restTemplate.getForObject(
                "http://localhost:8080/findSensorDataBetweenDates?from={from}&to={to}",
                Collection.class,
                LocalDateTime.of(2016,1,1,1,0,0),
                LocalDateTime.of(2016,2,1,1,0,0));

        assertThat(result).hasSize(308);
    }

}
