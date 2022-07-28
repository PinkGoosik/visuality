package visuality.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import visuality.registry.ShinyArmorRegistry;

public class ShinyArmorUtils {

	public static int getShinyLevel(LivingEntity entity) {
		int shinyArmor = 0;
		for(ItemStack stack : entity.getArmorSlots()) {
			if(ShinyArmorRegistry.ENTRIES.contains(stack.getItem())) {
				shinyArmor++;
			}
		}
		return shinyArmor;
	}
}
