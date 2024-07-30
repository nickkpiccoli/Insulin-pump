package com.univr.pump.insulinpump.repository;

import com.univr.pump.insulinpump.model.VitalParameters;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface VitalParametersRepository extends CrudRepository<VitalParameters, Long> {

        /**
         * Find all vital parameters in a given time interval
         * @param from
         * @param to
         * @return all vital parameters in the given time interval
         */
        Iterable<VitalParameters> findAllByTimestampBetween(LocalDateTime from, LocalDateTime to);


        /**
         * Find last vital parameters of a patient
         * @return last vital parameters of a patient
         */
        VitalParameters findFirstByOrderByTimestampDesc();
}
