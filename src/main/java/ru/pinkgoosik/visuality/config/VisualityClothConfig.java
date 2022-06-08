package ru.pinkgoosik.visuality.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.HitParticleRegistry;
import ru.pinkgoosik.visuality.registry.ShinyArmorRegistry;
import ru.pinkgoosik.visuality.registry.ShinyBlockRegistry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;

import java.util.ArrayList;

@Config(name = "visuality")
public class VisualityClothConfig extends AbstractVisualityConfig implements ConfigData {
	
	public static ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> {
            ConfigBuilder configBuilder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(text("title"))
                    .setSavingRunnable(() -> {
                        HitParticleRegistry.reload();
                        ShinyArmorRegistry.reload();
                        ShinyBlockRegistry.reload();
                    });

            ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
            ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
            VisualityClothConfig.setupEntries(general, entryBuilder);

            return configBuilder.build();
        };
    }

    public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
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
                .setSaveConsumer(newValue -> config.hitParticleEntries = (ArrayList<String>) newValue)
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(text("option.shinyArmor"), config.shinyArmorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shinyArmorEnabled = newValue)
                .build());

        category.addEntry(entryBuilder.startStrList(text("option.shinyArmor.entries"), config.shinyArmorEntries)
                .setDefaultValue(VisualityConfig.DEFAULT_SHINY_ARMOR)
                .setSaveConsumer(newValue -> config.shinyArmorEntries = (ArrayList<String>) newValue)
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(text("option.shinyBlocks"), config.shinyBlocksEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shinyBlocksEnabled = newValue)
                .build());

        category.addEntry(entryBuilder.startStrList(text("option.shinyBlocks.entries"), config.shinyBlockEntries)
                .setDefaultValue(VisualityConfig.DEFAULT_SHINY_BLOCKS)
                .setSaveConsumer(newValue -> config.shinyBlockEntries = (ArrayList<String>) newValue)
                .build());

    }

    private static Component text(String key) {
        return Component.translatable("config.visuality." + key);
    }

    public static void init() {
        AutoConfig.register(VisualityClothConfig.class, GsonConfigSerializer::new);
    }

    public static VisualityClothConfig getConfig() {
        return AutoConfig.getConfigHolder(VisualityClothConfig.class).getConfig();
    }

}
