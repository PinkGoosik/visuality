package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import ru.pinkgoosik.visuality.config.AbstractVisualityConfig;
import ru.pinkgoosik.visuality.config.VisualityClothConfig;
import ru.pinkgoosik.visuality.registry.*;

public class VisualityMod implements ClientModInitializer {
	public static final String MOD_ID = "visuality";
	public static AbstractVisualityConfig config = createConfig();

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.register();
		HitParticleRegistry.reload();
		ShinyArmorRegistry.reload();
		ShinyBlockRegistry.reload();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	private static AbstractVisualityConfig createConfig() {
		if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
			VisualityClothConfig.init();
			return VisualityClothConfig.getConfig();
		}
		else return new AbstractVisualityConfig();
	}

}
