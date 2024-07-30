package com.univr.pump.insulinpump.service;

import com.univr.pump.insulinpump.model.InsulinMachine;
import com.univr.pump.insulinpump.repository.InsulinMachineRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class InsulinMachineServiceTest {

    @Mock
    private InsulinMachineRepository insulinMachineRepository;

    @InjectMocks
    private InsulinMachineService insulinMachineService;

    private InsulinMachine insulinMachine;

    @Before
    public void setUp() {
        insulinMachine = new InsulinMachine();
    }

    @Test
    public void getBatteryLevelWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        InsulinMachine newMachine = new InsulinMachine();
        newMachine.setCurrentCapacity(100);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(newMachine);
        int batteryLevel = insulinMachineService.getBatteryLevel();
        assertEquals(100, batteryLevel);
        verify(insulinMachineRepository, times(1)).save(any(InsulinMachine.class));
    }

    @Test
    public void getBatteryLevelWhenRepositoryIsNotEmpty() {
        when(insulinMachineRepository.count()).thenReturn(1L);
        InsulinMachine existingMachine = new InsulinMachine();
        existingMachine.setCurrentCapacity(75);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(existingMachine);
        int batteryLevel = insulinMachineService.getBatteryLevel();
        assertEquals(75, batteryLevel);
        verify(insulinMachineRepository, times(0)).save(any(InsulinMachine.class));
    }

    @Test
    public void getBatteryLevel_WhenRepositoryIsNotEmpty() {
        InsulinMachine existingMachine = new InsulinMachine();
        existingMachine.setCurrentCapacity(75);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(existingMachine);
        int batteryLevel = insulinMachineService.getBatteryLevel();
        assertEquals(75, batteryLevel);
        verify(insulinMachineRepository, times(0)).save(any(InsulinMachine.class));
    }

    @Test
    public void chargeBatteryWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        insulinMachineService.chargeBattery();
        verify(insulinMachineRepository, times(1)).save(any(InsulinMachine.class));
    }

    @Test
    public void chargeBatteryWhenBatteryIsNotFull() {
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        insulinMachine.setCurrentCapacity(50);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.chargeBattery();
        assertEquals(100, insulinMachine.getCurrentCapacity());
        verify(insulinMachineRepository, times(1)).save(insulinMachine);
    }

    @Test
    public void chargeBatteryWhenBatteryIsAlreadyFull() {
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        insulinMachine.setCurrentCapacity(100);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.chargeBattery();
        assertEquals(100, insulinMachine.getCurrentCapacity());
        verify(insulinMachineRepository, times(0)).save(insulinMachine);
    }

    @Test
    public void decrBatteryWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        insulinMachineService.decrBattery();
        verify(insulinMachineRepository, times(1)).save(any(InsulinMachine.class));
    }

    @Test
    public void decrBatteryWhenBatteryIsNotEmpty() {
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        insulinMachine.setCurrentCapacity(50);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.decrBattery();
        assertEquals(49, insulinMachine.getCurrentCapacity());
        verify(insulinMachineRepository, times(1)).save(insulinMachine);
    }

    @Test
    public void decrBattery_WhenBatteryIsEmpty() {
        insulinMachine.setCurrentCapacity(0);
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.decrBattery();
        assertEquals(0, insulinMachine.getCurrentCapacity());
        verify(insulinMachineRepository, times(0)).save(insulinMachine);
    }

    @Test
    public void refillInsulinPumpWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        insulinMachineService.refillInsulinPump();
        verify(insulinMachineRepository, times(1)).save(any(InsulinMachine.class));
    }

    @Test
    public void refillInsulinPumpWhenInsulinPumpIsNotFull() {
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        insulinMachine.setCurrentTankLevel(50);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.refillInsulinPump();
        assertEquals(100, insulinMachine.getCurrentTankLevel());
        verify(insulinMachineRepository, times(1)).save(insulinMachine);
    }

    @Test
    public void refillInsulinPumpWhenInsulinPumpIsAlreadyFull() {
        insulinMachine.setCurrentTankLevel(100);
        Long machineId = 1L;
        insulinMachine.setId(machineId);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(insulinMachine);
        when(insulinMachineRepository.findById(machineId)).thenReturn(Optional.of(insulinMachine));
        insulinMachineService.refillInsulinPump();
        assertEquals(100, insulinMachine.getCurrentTankLevel());
        verify(insulinMachineRepository, times(0)).save(insulinMachine);
    }

    @Test
    public void getInsulinLevelWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        InsulinMachine newMachine = new InsulinMachine();
        newMachine.setCurrentTankLevel(50);
        when(insulinMachineRepository.save(any(InsulinMachine.class))).thenReturn(newMachine);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(newMachine);
        int insulinLevel = insulinMachineService.getInsulinLevel();
        assertEquals(50, insulinLevel);
        verify(insulinMachineRepository, times(1)).save(any(InsulinMachine.class));
    }

    @Test
    public void getInsulinLevelWhenRepositoryIsNotEmpty() {
        InsulinMachine existingMachine = new InsulinMachine();
        existingMachine.setCurrentTankLevel(75);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(existingMachine);
        int insulinLevel = insulinMachineService.getInsulinLevel();
        assertEquals(75, insulinLevel);
        verify(insulinMachineRepository, times(0)).save(any(InsulinMachine.class));
    }

    @Test
    public void testInjectInsulinWithSufficientInsulin() {
        InsulinMachine existingMachine = new InsulinMachine();
        existingMachine.setCurrentTankLevel(50);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(existingMachine);

        boolean result = insulinMachineService.injectInsulin(10);

        assertTrue(result);
        verify(insulinMachineRepository, times(1)).save(existingMachine);
    }

    @Test
    public void testInjectInsulinWhenRepositoryIsEmpty() {
        when(insulinMachineRepository.count()).thenReturn(0L);
        InsulinMachine newInsulinMachine = new InsulinMachine();
        newInsulinMachine.setCurrentTankLevel(100);

        when(insulinMachineRepository.save(any(InsulinMachine.class))).thenReturn(newInsulinMachine);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(newInsulinMachine);

        boolean result = insulinMachineService.injectInsulin(10);

        assertTrue(result);
        verify(insulinMachineRepository, times(2)).save(any(InsulinMachine.class));
        verify(insulinMachineRepository, times(1)).findFirstByOrderByIdDesc();
    }

    @Test
    public void testInjectInsulinWithInsufficientInsulin() {
        InsulinMachine existingMachine = new InsulinMachine();
        existingMachine.setCurrentTankLevel(5);
        when(insulinMachineRepository.count()).thenReturn(1L);
        when(insulinMachineRepository.findFirstByOrderByIdDesc()).thenReturn(existingMachine);

        boolean result = insulinMachineService.injectInsulin(10);

        assertFalse(result);
        verify(insulinMachineRepository, times(0)).save(existingMachine);
    }
}