package de.medhelfer.data;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class SensorDataServiceImpl implements SensorDataService {

    private final EntityManager em;

    @Inject
    public SensorDataServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Collection<SensorDataDto> findAllSensorData() {
        return em.createNamedQuery(SensorData.FIND_ALL, SensorDataDto.class)
                .getResultList();
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
    public void saveSensorReadings(Collection<SensorDataDto> sensorReadings) {
        if (CollectionUtils.isEmpty(sensorReadings))
            return;

        sensorReadings.stream()
                .filter(dto -> !findAllSensorData().stream()
                        .anyMatch(db -> db.getTimestamp().equals(dto.getTimestamp()))) // filter already added sensorreadings
                .forEach(dto ->
                        em.persist(new SensorData(dto.getTimestamp(), dto.getTemperature(), dto.getHumidity()))
                );
    }
}
