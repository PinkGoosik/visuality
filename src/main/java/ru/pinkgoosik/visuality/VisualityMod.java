package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pinkgoosik.goosikconfig.api.Config;
import ru.pinkgoosik.visuality.config.VisualityConfig;
import ru.pinkgoosik.visuality.registry.HitParticleRegistry;
import ru.pinkgoosik.visuality.registry.VisualityEvents;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

public class VisualityMod implements ClientModInitializer{
	public static final String MOD_ID = "visuality";
    public static final Logger LOGGER = LogManager.getLogger("Visuality");
	public static final Config CONFIG = new VisualityConfig("visuality");

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.register();
		HitParticleRegistry.reload();
	}

	public static ResourceLocation newId(String path){
		return new ResourceLocation(MOD_ID, path);
	}
}
