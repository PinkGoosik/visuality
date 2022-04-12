package ru.pinkgoosik.visuality.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.pinkgoosik.visuality.event.CirclesOnWaterEvent;

public class VisualityEvents {

    public static void register() {
        ClientTickEvents.START_WORLD_TICK.register(CirclesOnWaterEvent::onTick);
    }
}
