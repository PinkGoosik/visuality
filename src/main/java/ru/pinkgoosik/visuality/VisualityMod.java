package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import ru.pinkgoosik.goosikconfig.api.Config;
import ru.pinkgoosik.visuality.config.VisualityConfig;
import ru.pinkgoosik.visuality.registry.*;

public class VisualityMod implements ClientModInitializer {
	public static final String MOD_ID = "visuality";
	public static final Config CONFIG = new VisualityConfig(MOD_ID);

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.register();
		HitParticleRegistry.reload();
		ShinyArmorRegistry.reload();
		ShinyBlockRegistry.reload();
	}

	public static ResourceLocation locate(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
