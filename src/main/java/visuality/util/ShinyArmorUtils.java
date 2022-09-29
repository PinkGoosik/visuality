package visuality.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import visuality.registry.ShinyArmorRegistry;

public class ShinyArmorUtils {

	public static int getShinyLevel(LivingEntity entity) {
		int shinyArmor = 0;
		for(ItemStack stack : entity.getArmorItems()) {
			if(ShinyArmorRegistry.ENTRIES.contains(stack.getItem())) {
				shinyArmor++;
			}
		}
		return shinyArmor;
	}
}
