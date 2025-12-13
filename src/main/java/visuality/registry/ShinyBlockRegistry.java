package visuality.registry;

import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ShinyBlockRegistry {
	private static final ArrayList<BlockBehaviour> BLOCKS = new ArrayList<>();
	private static final ArrayList<TagKey<Block>> TAGS = new ArrayList<>();

	public static void reload() {
		BLOCKS.clear();
		TAGS.clear();
		ArrayList<Block> blocks = new ArrayList<>();
		ArrayList<TagKey<Block>> tags = new ArrayList<>();

		VisualityMod.config.shinyBlockEntries.forEach(entry -> {
			if(entry.startsWith("#")) {
				tags.add(TagKey.create(Registries.BLOCK, Identifier.parse(entry.replace("#", ""))));
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
			if(block.is(tag)) return true;
		}
		return false;
	}

	private static Optional<Block> getBlockFromString(String id) {
		return BuiltInRegistries.BLOCK.getOptional(Identifier.parse(id));
	}
}
