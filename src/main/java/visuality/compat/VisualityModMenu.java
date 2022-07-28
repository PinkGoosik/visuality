package visuality.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import visuality.config.VisualityClothConfig;

@Environment(EnvType.CLIENT)
public class VisualityModMenu implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return VisualityClothConfig::buildScreen;
	}
}
