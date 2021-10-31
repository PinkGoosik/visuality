package ru.pinkgoosik.visuality.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.pinkgoosik.visuality.event.CirclesOnWaterEvent;
import ru.pinkgoosik.visuality.event.UnderwaterBubblesEvent;

public class VisualityEvents {

    public static void registerClient(){
        ClientTickEvents.START_WORLD_TICK.register(new UnderwaterBubblesEvent());
        ClientTickEvents.START_WORLD_TICK.register(new CirclesOnWaterEvent());
    }
}
