package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pinkgoosik.visuality.config.Config;
import ru.pinkgoosik.visuality.registry.VisualityEvents;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

public class VisualityMod implements ClientModInitializer{
	public static final String MOD_ID = "visuality";
    public static final Logger LOGGER = LogManager.getLogger("Visuality");
	public static Config config = new Config();

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.register();
	}

	public static Identifier newId(String path){
		return new Identifier(MOD_ID, path);
	}
}
