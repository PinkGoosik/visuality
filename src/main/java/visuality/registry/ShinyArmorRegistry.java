package visuality.registry;

import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
				tags.add(TagKey.create(Registries.ITEM, Identifier.parse(entry.replace("#", ""))));
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
			if(item.is(tag)) return true;
		}
		return false;
	}

	private static Optional<Item> getItemFromString(String id) {
		return BuiltInRegistries.ITEM.getOptional(Identifier.parse(id));
	}
}
