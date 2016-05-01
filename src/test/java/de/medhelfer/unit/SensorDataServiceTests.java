package de.medhelfer.unit;

import de.medhelfer.BackendApplication;
import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BackendApplication.class)
@WebAppConfiguration
public class SensorDataServiceTests {

	@Autowired
	SensorDataService sensorDataService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void saveFindSensorReadings() {
		Collection<SensorDataDto> toSave = new ArrayList<>();
		LocalDateTime now = LocalDateTime.now();
		toSave.add(new SensorDataDto(now.minusHours(1), 10f, 50f));
		toSave.add(new SensorDataDto(now.minusHours(2), 20f, 50f));
		toSave.add(new SensorDataDto(now, 30f, 50f));
		toSave.add(new SensorDataDto(now, 20f, 30f)); // mustn't get saved
		sensorDataService.saveSensorReadings(toSave);

		assertThat(sensorDataService.findAllSensorData()).hasSize(3);
	}

}
