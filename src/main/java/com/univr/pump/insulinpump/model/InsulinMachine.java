package com.univr.pump.insulinpump.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class InsulinMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int maxCapacity;
    private int currentCapacity;
    private int tankCapacity;
    private int currentTankLevel;

    private static final int MAX_TANK_CAPACITY = 100;

    private final static int DEFAULT_MAX_CAPACITY = 100;
    private final static int DEFAULT_CURRENT_CAPACITY = 100;

    public InsulinMachine() {
        this.maxCapacity = DEFAULT_MAX_CAPACITY;
        this.currentCapacity = DEFAULT_CURRENT_CAPACITY;
        this.tankCapacity = MAX_TANK_CAPACITY;
        this.currentTankLevel = MAX_TANK_CAPACITY;
    }

    /**
     * Simulates the charge of the battery.
     * The current capacity is set to the maximum.
     */
    public void charge() {
        if (this.currentCapacity < MAX_TANK_CAPACITY)
            this.currentCapacity = this.maxCapacity;
    }

    /**
     * Simulates the refill of the insulin pump.
     * The current tank level is set to the maximum.
     */
    public void refill() {
        if (this.currentTankLevel < MAX_TANK_CAPACITY)
            this.currentTankLevel = this.tankCapacity;
    }

    /**
     * Simulates the discharge of the battery.
     * The current capacity is decreased by 1.
     */
    public void decrBattery() {
        if (this.currentCapacity > 0)
            this.currentCapacity--;
    }

    /**
     * Injects insulin.
     */
    public void injectInsulin(int compDose) {
        if (this.currentTankLevel >= compDose)
            this.currentTankLevel= this.currentTankLevel - compDose;
    }

}
