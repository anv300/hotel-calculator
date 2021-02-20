package me.anvaru.hotelcalculator;

import net.fabricmc.api.ModInitializer;

public class HotelCalculator implements ModInitializer {
    public static final String MODID = "hotel-calculator";

    @Override
    public void onInitialize() {
        BlockRegister.reg();
    }
}
