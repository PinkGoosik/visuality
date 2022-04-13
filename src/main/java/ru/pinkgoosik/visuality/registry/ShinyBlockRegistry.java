package ru.pinkgoosik.visuality.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import ru.pinkgoosik.visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class ShinyBlockRegistry {
    public static final ArrayList<BlockBehaviour> ENTRIES = new ArrayList<>();

    public static void reload() {
        ENTRIES.clear();
        ArrayList<Block> entries = new ArrayList<>();
        VisualityMod.config.shinyBlocks.forEach(entry -> getBlockFromString(entry).ifPresent(entries::add));
        ENTRIES.addAll(entries);
    }

    private static Optional<Block> getBlockFromString(String id) {
        return Registry.BLOCK.getOptional(new ResourceLocation(id));
    }
}
