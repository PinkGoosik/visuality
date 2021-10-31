package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.visuality.registry.VisualityEvents;
import ru.pinkgoosik.visuality.registry.ModHitParticles;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

public class VisualityMod implements ClientModInitializer{
	public static final String MOD_ID = "visuality";

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.registerClient();
		ModHitParticles.register();
	}

	public static Identifier newId(String path){
		return new Identifier(MOD_ID, path);
	}
}
