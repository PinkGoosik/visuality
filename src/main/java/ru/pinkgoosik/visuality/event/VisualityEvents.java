package ru.pinkgoosik.visuality.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class VisualityEvents {

    public static void registerClient(){
//        ClientTickEvents.START_WORLD_TICK.register(new FallingRocksEvent());
        ClientTickEvents.START_WORLD_TICK.register(new UnderwaterBubblesEvent());
    }
}
