package ru.pinkgoosik.visuality.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.TranslatableComponent;
import ru.pinkgoosik.visuality.VisualityMod;

import java.util.ArrayList;

@Config(name = "visuality")
public class VisualityClothConfig extends AbstractVisualityConfig implements ConfigData {

    public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
        var config = VisualityMod.config;
        category.addEntry(builder.startBooleanToggle(text("option.slime"), config.slimeEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.slimeEnabled = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.charge"), config.chargeEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.chargeEnabled = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.sparkle"), config.sparkleEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.sparkleEnabled = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.soul"), config.soulEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.soulEnabled = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.waterCircle"), config.waterCircles.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.waterCircles.enabled = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.waterCircle.colored"), config.waterCircles.colored)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.waterCircles.colored = newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.hitParticles"), config.hitParticlesEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.hitParticlesEnabled = newValue)
                .build());

        category.addEntry(builder.startStrList(text("option.hitParticles.entries"), config.hitParticleEntries)
                .setDefaultValue(VisualityConfig.DEFAULT_HIT_PARTICLES)
                .setSaveConsumer(newValue -> config.hitParticleEntries = (ArrayList<String>) newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.shinyArmor"), config.shinyArmorEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shinyArmorEnabled = newValue)
                .build());

        category.addEntry(builder.startStrList(text("option.shinyArmor.entries"), config.shinyArmorEntries)
                .setDefaultValue(VisualityConfig.DEFAULT_SHINY_ARMOR)
                .setSaveConsumer(newValue -> config.shinyArmorEntries = (ArrayList<String>) newValue)
                .build());

        category.addEntry(builder.startBooleanToggle(text("option.shinyBlocks"), config.shinyBlocksEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shinyBlocksEnabled = newValue)
                .build());

        category.addEntry(builder.startStrList(text("option.shinyBlocks.entries"), config.shinyBlockEntries)
                .setDefaultValue(VisualityConfig.DEFAULT_SHINY_BLOCKS)
                .setSaveConsumer(newValue -> config.shinyBlockEntries = (ArrayList<String>) newValue)
                .build());

    }

    private static TranslatableComponent text(String key) {
        return new TranslatableComponent("config.visuality." + key);
    }

    public static void init() {
        AutoConfig.register(VisualityClothConfig.class, GsonConfigSerializer::new);
    }

    public static VisualityClothConfig getConfig() {
        return AutoConfig.getConfigHolder(VisualityClothConfig.class).getConfig();
    }

}
