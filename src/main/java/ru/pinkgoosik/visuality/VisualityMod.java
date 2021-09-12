package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.EntityType;
import ru.pinkgoosik.visuality.api.HitMobParticleRegistry;
import ru.pinkgoosik.visuality.event.VisualityEvents;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

public class VisualityMod implements ClientModInitializer {

	public static final String MOD_ID = "visuality";

	@Override
	public void onInitializeClient() {
		VisualityParticles.register();
		VisualityEvents.registerClient();

		HitMobParticleRegistry.register(EntityType.SKELETON, VisualityParticles.BONE, 15);
		HitMobParticleRegistry.register(EntityType.SKELETON_HORSE, VisualityParticles.BONE, 15);
		HitMobParticleRegistry.register(EntityType.STRAY, VisualityParticles.BONE, 15);
		HitMobParticleRegistry.register(EntityType.WITHER_SKELETON, VisualityParticles.WITHER_BONE, 20);
		HitMobParticleRegistry.register(EntityType.CHICKEN, VisualityParticles.FEATHER, 3);
	}
}
