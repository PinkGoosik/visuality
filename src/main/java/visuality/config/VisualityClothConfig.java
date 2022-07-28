package visuality.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import visuality.VisualityMod;
import visuality.registry.HitParticleRegistry;
import visuality.registry.ShinyArmorRegistry;
import visuality.registry.ShinyBlockRegistry;

import java.util.ArrayList;

@Config(name = "visuality")
public class VisualityClothConfig extends VisualityConfig implements ConfigData {

	public static Screen buildScreen(Screen parent) {
		ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("title"));
		configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(VisualityClothConfig.class).save());
		ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
		ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		VisualityClothConfig.setupEntries(general, entryBuilder);
		return configBuilder.build();
	}

	private static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = VisualityMod.config;
		category.addEntry(entryBuilder.startBooleanToggle(text("option.slime"), config.slimeEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.slimeEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.charge"), config.chargeEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.chargeEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.sparkle"), config.sparkleEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.sparkleEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.soul"), config.soulEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.soulEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.waterCircle"), config.waterCircles.enabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.waterCircles.enabled = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.waterCircle.colored"), config.waterCircles.colored)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.waterCircles.colored = newValue)
				.build());

		category.addEntry(entryBuilder.startIntField(text("option.waterCircle.density"), config.waterCircles.density)
				.setDefaultValue(10)
				.setSaveConsumer(newValue -> config.waterCircles.density = newValue)
				.build());

		category.addEntry(entryBuilder.startIntField(text("option.waterCircle.radius"), config.waterCircles.radius)
				.setDefaultValue(16)
				.setSaveConsumer(newValue -> config.waterCircles.radius = newValue)
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.hitParticles"), config.hitParticlesEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.hitParticlesEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startStrList(text("option.hitParticles.entries"), config.hitParticleEntries)
				.setDefaultValue(VisualityConfig.DEFAULT_HIT_PARTICLES)
				.setSaveConsumer(newValue -> {
					config.hitParticleEntries = (ArrayList<String>) newValue;
					HitParticleRegistry.reload();
				})
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.shinyArmor"), config.shinyArmorEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.shinyArmorEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startStrList(text("option.shinyArmor.entries"), config.shinyArmorEntries)
				.setDefaultValue(VisualityConfig.DEFAULT_SHINY_ARMOR)
				.setSaveConsumer(newValue -> {
					config.shinyArmorEntries = (ArrayList<String>) newValue;
					ShinyArmorRegistry.reload();
				})
				.build());

		category.addEntry(entryBuilder.startBooleanToggle(text("option.shinyBlocks"), config.shinyBlocksEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.shinyBlocksEnabled = newValue)
				.build());

		category.addEntry(entryBuilder.startStrList(text("option.shinyBlocks.entries"), config.shinyBlockEntries)
				.setDefaultValue(VisualityConfig.DEFAULT_SHINY_BLOCKS)
				.setSaveConsumer(newValue -> {
					config.shinyBlockEntries = (ArrayList<String>) newValue;
					ShinyBlockRegistry.reload();
				})
				.build());
	}

	private static Component text(String key) {
		return Component.translatable("config.visuality." + key);
	}

	public static VisualityClothConfig init() {
		AutoConfig.register(VisualityClothConfig.class, GsonConfigSerializer::new);
		return AutoConfig.getConfigHolder(VisualityClothConfig.class).getConfig();
	}
}
