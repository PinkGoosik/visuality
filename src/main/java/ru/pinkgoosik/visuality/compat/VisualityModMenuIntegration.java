package ru.pinkgoosik.visuality.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import ru.pinkgoosik.visuality.config.VisualityClothConfig;

@Environment(EnvType.CLIENT)
public class VisualityModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
		if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return VisualityClothConfig.getModConfigScreenFactory();
        }
        return null;
    }

}
