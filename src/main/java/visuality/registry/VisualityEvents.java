package visuality.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import visuality.event.CirclesOnWaterEvent;

public class VisualityEvents {

	public static void init() {
		ClientTickEvents.START_WORLD_TICK.register(CirclesOnWaterEvent::onTick);
	}
}
