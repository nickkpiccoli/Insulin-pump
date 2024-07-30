package com.univr.pump.insulinpump.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InsulinMachineTest {

    @Test
    public void testCreateVitalParameters() {
        InsulinMachine insulinMachine = new InsulinMachine();

        assertNotNull(insulinMachine);
    }

    @Test
    public void testSettersAndGetters() {
        InsulinMachine insulinMachine = new InsulinMachine();

        insulinMachine.setId(1L);
        assertEquals(1L, insulinMachine.getId().longValue());

        insulinMachine.setMaxCapacity(100);
        assertEquals(100, insulinMachine.getMaxCapacity());

        insulinMachine.setCurrentCapacity(100);
        assertEquals(100, insulinMachine.getCurrentCapacity());

        insulinMachine.setTankCapacity(100);
        assertEquals(100, insulinMachine.getTankCapacity());

        insulinMachine.setCurrentTankLevel(100);
        assertEquals(100, insulinMachine.getCurrentTankLevel());
    }

    @Test
    public void testCharge() {
        InsulinMachine insulinMachine = new InsulinMachine();

        insulinMachine.charge();
        assertEquals(insulinMachine.getMaxCapacity(), insulinMachine.getCurrentCapacity());

        insulinMachine.setCurrentCapacity(insulinMachine.getMaxCapacity() - 1);
        insulinMachine.charge();
        assertEquals(insulinMachine.getMaxCapacity(), insulinMachine.getCurrentCapacity());
    }

    @Test
    public void testRefill() {
        InsulinMachine insulinMachine = new InsulinMachine();

        insulinMachine.refill();
        assertEquals(insulinMachine.getTankCapacity(), insulinMachine.getCurrentTankLevel());

        insulinMachine.setCurrentTankLevel(insulinMachine.getTankCapacity() - 1);
        insulinMachine.refill();
        assertEquals(insulinMachine.getTankCapacity(), insulinMachine.getCurrentTankLevel());
    }

    @Test
    public void testDecrBattery() {
        InsulinMachine insulinMachine = new InsulinMachine();

        int currentCapacity = insulinMachine.getCurrentCapacity();
        insulinMachine.decrBattery();
        assertEquals(currentCapacity - 1, insulinMachine.getCurrentCapacity());

        insulinMachine.setCurrentCapacity(0);
        insulinMachine.decrBattery();
        assertEquals(0, insulinMachine.getCurrentCapacity());
    }

    @Test
    public void testInjectInsulin() {
        InsulinMachine insulinMachine = new InsulinMachine();
        insulinMachine.setCurrentTankLevel(10);

        int dose = 3;
        insulinMachine.injectInsulin(dose);
        assertEquals(7, insulinMachine.getCurrentTankLevel());

        insulinMachine.setCurrentTankLevel(2);
        insulinMachine.injectInsulin(dose);
        assertEquals(2, insulinMachine.getCurrentTankLevel());

        insulinMachine.setCurrentTankLevel(5);
        insulinMachine.injectInsulin(0);
        assertEquals(5, insulinMachine.getCurrentTankLevel());
    }
}
