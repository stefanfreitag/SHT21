package de.freitag.stefan.sht21.model;

import lombok.Getter;

/**
 * The end of battery alert is activated when the battery
 * power falls below 2.25 V.
 * <p>Value of the status bit:
 * <ul>
 * <li>0: VDD greater than 2.25 Volt</li>
 * <li>1: VDD less than 2.25 Volt</li>
 * </ul>
 * </p>
 */
@Getter
public enum EndOfBatteryAlert {
    /**
     * End of battery alert is set.
     */
    EOB_ALERT_ON((byte) 0x40),
    /**
     * End of battery alert is not set.
     */
    EOB_ALERT_OFF((byte) 0x00);
    /**
     * Mask for accessing the end of battery alert-bit (bit 6) in user register.
     */
    private static final byte EOB_ALERT_MASK = 0x40;
    private final byte eobByte;

    /**
     * Create a new {@code EndOfBatteryAlert}.
     *
     * @param eobByte The byte-encoded {@code EndOfBatteryAlert}.
     */
    EndOfBatteryAlert(final byte eobByte) {
        this.eobByte = eobByte;
    }

    /**
     * Returns the end Of battery alert status (alert on/off) for the given
     * {@code eobByte}.
     *
     * @param eobByte Byte to evaluate.
     * @return EndOfBattery status (alert on/off) for the given
     * {@code eobByte}.
     */
    public static EndOfBatteryAlert getEOBAlert(final byte eobByte) {
        if (EOB_ALERT_OFF.getEobByte() == (EOB_ALERT_MASK & eobByte)) {
            return EOB_ALERT_OFF;
        }
        return EOB_ALERT_ON;
    }
    
    @Override
    public String toString() {
        return this.name();
    }
}
