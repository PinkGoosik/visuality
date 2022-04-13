package ru.pinkgoosik.visuality.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.TranslatableComponent;
import ru.pinkgoosik.visuality.config.VisualityClothConfig;
import ru.pinkgoosik.visuality.registry.HitParticleRegistry;
import ru.pinkgoosik.visuality.registry.ShinyArmorRegistry;
import ru.pinkgoosik.visuality.registry.ShinyBlockRegistry;

@Environment(EnvType.CLIENT)
public class VisualityModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(text("title"))
                    .setSavingRunnable(() -> {
                        HitParticleRegistry.reload();
                        ShinyArmorRegistry.reload();
                        ShinyBlockRegistry.reload();
                    });

            ConfigCategory general = builder.getOrCreateCategory(text("general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            VisualityClothConfig.setupEntries(general, entryBuilder);

            return builder.build();
        };

//        return parent -> AutoConfig.getConfigScreen(VisualityClothConfig.class, parent).get();
    }

    private static TranslatableComponent text(String key) {
        return new TranslatableComponent("config.visuality." + key);
    }
}
