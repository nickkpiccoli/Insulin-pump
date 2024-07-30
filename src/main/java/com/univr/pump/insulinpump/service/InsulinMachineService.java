package com.univr.pump.insulinpump.service;

import com.univr.pump.insulinpump.model.InsulinMachine;
import com.univr.pump.insulinpump.repository.InsulinMachineRepository;
import org.springframework.stereotype.Service;

@Service
public class InsulinMachineService {

    private final InsulinMachineRepository insulinMachineRepository;

    public InsulinMachineService(InsulinMachineRepository insulinMachineRepository) {
        this.insulinMachineRepository = insulinMachineRepository;
    }

    /**
     * Get current battery level
     * if repository is empty, create a new machine
     * @return battery level
     */
    public int getBatteryLevel() {
        if (insulinMachineRepository.count() == 0) {
            insulinMachineRepository.save(new InsulinMachine());
        }
        return insulinMachineRepository.findFirstByOrderByIdDesc().getCurrentCapacity();
    }

    /**
     * Charge battery to full capacity (100%)
     * if battery is already full, do nothing
     * if repository is empty, create a new machine
     */
    public void chargeBattery() {
        if (insulinMachineRepository.count() == 0) {
            InsulinMachine newInsulinMachine = new InsulinMachine();
            insulinMachineRepository.save(newInsulinMachine);
        } else {
            Long latestInsulinMachineId = insulinMachineRepository.findFirstByOrderByIdDesc().getId();
            InsulinMachine insulinMachine = insulinMachineRepository.findById(latestInsulinMachineId).orElse(null);
            if (insulinMachine != null) {
                if(insulinMachine.getCurrentCapacity() < 100)
                {
                    insulinMachine.charge();
                    insulinMachineRepository.save(insulinMachine);
                }
            }
        }
    }

    /**
     * Discharge battery by 1%
     * if battery is empty, do nothing
     * if repository is empty, create a new machine
     */
    public void decrBattery() {
        if (insulinMachineRepository.count() == 0) {
            InsulinMachine newInsulinMachine = new InsulinMachine();
            insulinMachineRepository.save(newInsulinMachine);
        } else {
            Long latestInsulinMachineId = insulinMachineRepository.findFirstByOrderByIdDesc().getId();
            InsulinMachine insulinMachine = insulinMachineRepository.findById(latestInsulinMachineId).orElse(null);
            if (insulinMachine != null) {
                if(insulinMachine.getCurrentCapacity() > 0)
                {
                    insulinMachine.decrBattery();
                    insulinMachineRepository.save(insulinMachine);
                }
            }
        }
    }

    /**
     * Refill insulin pump to full capacity
     * if insulin pump is already full, do nothing
     * if repository is empty, create a new machine
     */
    public void refillInsulinPump() {
        if (insulinMachineRepository.count() == 0) {
            InsulinMachine newInsulinMachine = new InsulinMachine();
            insulinMachineRepository.save(newInsulinMachine);
        } else {
            Long latestInsulinMachineId = insulinMachineRepository.findFirstByOrderByIdDesc().getId();
            InsulinMachine insulinMachine = insulinMachineRepository.findById(latestInsulinMachineId).orElse(null);
            if (insulinMachine != null) {
                if(insulinMachine.getCurrentTankLevel() < 100)
                {
                    insulinMachine.refill();
                    insulinMachineRepository.save(insulinMachine);
                }
            }
        }
    }

    /**
     * Get current insulin level
     * if repository is empty, create a new machine
     * @return insulin level
     */
    public int getInsulinLevel() {
        if (insulinMachineRepository.count() == 0) {
            insulinMachineRepository.save(new InsulinMachine());
        }
        return insulinMachineRepository.findFirstByOrderByIdDesc().getCurrentTankLevel();
    }

    /**
     * Inject insulin
     * if repository is empty, create a new machine
     * if insulin pump is not empty, inject insulin
     * if insulin pump is empty, do nothing
     */
    public boolean injectInsulin(int compDose) {
        if (insulinMachineRepository.count() == 0) {
            InsulinMachine newInsulinMachine = new InsulinMachine();
            insulinMachineRepository.save(newInsulinMachine);
        }
        InsulinMachine insulinMachine = insulinMachineRepository.findFirstByOrderByIdDesc();

        if (insulinMachine != null && insulinMachine.getCurrentTankLevel() >= compDose)
        {
            insulinMachine.injectInsulin(compDose);
            insulinMachineRepository.save(insulinMachine);
            return true;
        }
        return false;
    }
}
