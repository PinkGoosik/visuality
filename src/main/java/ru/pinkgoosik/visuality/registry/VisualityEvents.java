package ru.pinkgoosik.visuality.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.pinkgoosik.visuality.event.CirclesOnWaterEvent;
import ru.pinkgoosik.visuality.event.LoadConfigEvent;

public class VisualityEvents {

    public static void register(){
        ClientTickEvents.START_WORLD_TICK.register(new CirclesOnWaterEvent());
        ClientTickEvents.END_CLIENT_TICK.register(new LoadConfigEvent());
    }
}
