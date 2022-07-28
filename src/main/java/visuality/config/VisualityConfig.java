package visuality.config;

import java.util.ArrayList;
import java.util.List;

public class VisualityConfig {
	public static final ArrayList<String> DEFAULT_HIT_PARTICLES = new ArrayList<>(List.of(
			"minecraft:skeleton/visuality:bone", "minecraft:skeleton_horse/visuality:bone",
			"minecraft:stray/visuality:bone", "minecraft:wither_skeleton/visuality:wither_bone",
			"minecraft:chicken/visuality:feather", "minecraft:villager/visuality:emerald"));

	public static final ArrayList<String> DEFAULT_SHINY_ARMOR = new ArrayList<>(List.of(
			"minecraft:golden_helmet", "minecraft:golden_chestplate",
			"minecraft:golden_leggings", "minecraft:golden_boots",
			"minecraft:diamond_helmet", "minecraft:diamond_chestplate",
			"minecraft:diamond_leggings", "minecraft:diamond_boots"));

	public static final ArrayList<String> DEFAULT_SHINY_BLOCKS = new ArrayList<>(List.of(
			"minecraft:gold_ore", "minecraft:deepslate_gold_ore",
			"minecraft:nether_gold_ore", "minecraft:diamond_ore",
			"minecraft:deepslate_diamond_ore", "minecraft:emerald_ore",
			"minecraft:deepslate_emerald_ore"));

	public boolean slimeEnabled = true;
	public boolean chargeEnabled = true;
	public boolean sparkleEnabled = true;
	public boolean soulEnabled = true;

	public VisualityClothConfig.WaterCirclesConfigOption waterCircles = new VisualityClothConfig.WaterCirclesConfigOption();

	public boolean hitParticlesEnabled = true;
	public ArrayList<String> hitParticleEntries = DEFAULT_HIT_PARTICLES;

	public boolean shinyArmorEnabled = true;
	public ArrayList<String> shinyArmorEntries = DEFAULT_SHINY_ARMOR;

	public boolean shinyBlocksEnabled = true;
	public ArrayList<String> shinyBlockEntries = DEFAULT_SHINY_BLOCKS;

	public static class WaterCirclesConfigOption {
		public int density = 10;
		public int radius = 16;
		public boolean enabled = true;
		public boolean colored = true;
	}
}
