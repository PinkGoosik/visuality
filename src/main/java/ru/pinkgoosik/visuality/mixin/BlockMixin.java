package ru.pinkgoosik.visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.ShinyBlockRegistry;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.ParticleUtils;

import java.util.Random;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements ItemLike {

    public BlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "animateTick", at = @At("TAIL"))
    void animateTick(BlockState state, Level level, BlockPos pos, Random random, CallbackInfo ci) {
        if (VisualityMod.CONFIG.getBoolean("enabled", "shiny_blocks")) {
            if(ShinyBlockRegistry.ENTRIES.contains(this) && visuality$isAirAttached(level, pos)) {
                if (random.nextFloat() > 0.4) {
                    double x = random.nextFloat() * 2 - 0.5;
                    double y = random.nextFloat() + 0.5;
                    double z = random.nextFloat() * 2 - 0.5;
                    ParticleUtils.add(level, VisualityParticles.SPARKLE, pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                }
            }
        }
    }

    private boolean visuality$isAirAttached(Level level, BlockPos pos) {
        boolean bl;
        if(level.getBlockState(pos.above()).isAir()) bl = true;
        else if(level.getBlockState(pos.below()).isAir()) bl = true;
        else if(level.getBlockState(pos.north()).isAir()) bl = true;
        else if(level.getBlockState(pos.south()).isAir()) bl = true;
        else if(level.getBlockState(pos.west()).isAir()) bl = true;
        else bl = level.getBlockState(pos.east()).isAir();
        return bl;
    }
}
