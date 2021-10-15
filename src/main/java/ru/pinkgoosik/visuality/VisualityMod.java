package ru.pinkgoosik.visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import ru.pinkgoosik.visuality.api.HitMobParticleRegistry;
import ru.pinkgoosik.visuality.event.VisualityEvents;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

import java.util.ArrayList;
import java.util.List;

public class VisualityMod implements ClientModInitializer{

	public static final String MOD_ID = "visuality";

	public static final ArrayList<Item> SHINY_ARMOR = new ArrayList<>(List.of(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE,
			Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS,
			Items.GOLDEN_BOOTS));

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
