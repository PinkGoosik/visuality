package visuality.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class ShinyBlockRegistry {
	private static final ArrayList<AbstractBlock> BLOCKS = new ArrayList<>();
	private static final ArrayList<TagKey<Block>> TAGS = new ArrayList<>();

	public static void reload() {
		BLOCKS.clear();
		TAGS.clear();
		ArrayList<Block> blocks = new ArrayList<>();
		ArrayList<TagKey<Block>> tags = new ArrayList<>();

		VisualityMod.config.shinyBlockEntries.forEach(entry -> {
			if(entry.startsWith("#")) {
				tags.add(TagKey.of(RegistryKeys.BLOCK, Identifier.of(entry.replace("#", ""))));
				return;
			}
			getBlockFromString(entry).ifPresent(blocks::add);
		});

		BLOCKS.addAll(blocks);
		TAGS.addAll(tags);
	}

	public static boolean isShiny(BlockState block) {
		if(BLOCKS.contains(block.getBlock())) return true;
		for(var tag : TAGS) {
			if(block.isIn(tag)) return true;
		}
		return false;
	}

	private static Optional<Block> getBlockFromString(String id) {
		return Registries.BLOCK.getOrEmpty(Identifier.of(id));
	}
}
