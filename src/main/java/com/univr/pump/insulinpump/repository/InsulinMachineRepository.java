package com.univr.pump.insulinpump.repository;

import com.univr.pump.insulinpump.model.InsulinMachine;
import org.springframework.data.repository.CrudRepository;

public interface InsulinMachineRepository extends CrudRepository<InsulinMachine, Long> {

    /**
     * Find last insulin machine of a patient
     * @return last insulin machine of a patient
     */
    InsulinMachine findFirstByOrderByIdDesc();
}
