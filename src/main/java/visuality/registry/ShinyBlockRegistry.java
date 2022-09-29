package visuality.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class ShinyBlockRegistry {
	public static final ArrayList<AbstractBlock> ENTRIES = new ArrayList<>();

	public static void reload() {
		ENTRIES.clear();
		ArrayList<Block> entries = new ArrayList<>();
		VisualityMod.config.shinyBlockEntries.forEach(entry -> getBlockFromString(entry).ifPresent(entries::add));
		ENTRIES.addAll(entries);
	}

	private static Optional<Block> getBlockFromString(String id) {
		return Registry.BLOCK.getOrEmpty(new Identifier(id));
	}
}
