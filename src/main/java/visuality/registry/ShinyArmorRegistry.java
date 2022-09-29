package visuality.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class ShinyArmorRegistry {
	public static final ArrayList<Item> ENTRIES = new ArrayList<>();

	public static void reload() {
		ENTRIES.clear();
		ArrayList<Item> entries = new ArrayList<>();
		VisualityMod.config.shinyArmorEntries.forEach(entry -> getItemFromString(entry).ifPresent(entries::add));
		ENTRIES.addAll(entries);
	}

	private static Optional<Item> getItemFromString(String id) {
		return Registry.ITEM.getOrEmpty(new Identifier(id));
	}
}
