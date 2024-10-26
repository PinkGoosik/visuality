package visuality.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class ShinyArmorRegistry {
	private static final ArrayList<Item> ITEMS = new ArrayList<>();
	private static final ArrayList<TagKey<Item>> TAGS = new ArrayList<>();

	public static void reload() {
		ITEMS.clear();
		TAGS.clear();
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<TagKey<Item>> tags = new ArrayList<>();

		VisualityMod.config.shinyArmorEntries.forEach(entry -> {
			if(entry.startsWith("#")) {
				tags.add(TagKey.of(RegistryKeys.ITEM, Identifier.of(entry.replace("#", ""))));
				return;
			}
			getItemFromString(entry).ifPresent(items::add);
		});

		ITEMS.addAll(items);
		TAGS.addAll(tags);
	}

	public static boolean isShiny(ItemStack item) {
		if(ITEMS.contains(item.getItem())) return true;
		for(var tag : TAGS) {
			if(item.isIn(tag)) return true;
		}
		return false;
	}

	private static Optional<Item> getItemFromString(String id) {
		return Registries.ITEM.getOptionalValue(Identifier.of(id));
	}
}
