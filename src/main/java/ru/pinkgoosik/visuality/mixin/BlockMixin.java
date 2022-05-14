package ru.pinkgoosik.visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
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

    @Inject(method = "fallOn", at = @At("TAIL"))
    void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f, CallbackInfo ci) {
        if(VisualityMod.config.soulEnabled) {
            if(state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
                for(int i = 0; i <= level.random.nextInt(5) + 1; i++) {
                    double x = entity.getX();
                    double y = entity.getY() + 0.1;
                    double z = entity.getZ();
                    ParticleUtils.add(level, VisualityParticles.SOUL, x, y, z);
                }
            }
        }
    }

    @Inject(method = "animateTick", at = @At("TAIL"))
    void animateTick(BlockState state, Level level, BlockPos pos, Random random, CallbackInfo ci) {
        if(VisualityMod.config.soulEnabled) {
            if(state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
                if(level.getBlockState(pos.above()).isAir()) {
                    if(random.nextFloat() > 0.995F) {
                        double x = pos.getX() + random.nextDouble();
                        double y = pos.getY() + 1.1D;
                        double z = pos.getZ() + random.nextDouble();
                        ParticleUtils.add(level, VisualityParticles.SOUL, x, y, z);
                    }
                }
            }
        }
        if (VisualityMod.config.shinyBlocksEnabled) {
            if(ShinyBlockRegistry.ENTRIES.contains(this)) {
                for(Direction direction : Direction.values()) {
                    BlockPos blockPos = pos.relative(direction);
                    if (!level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                        if (random.nextFloat() > 0.8) {
                            Direction.Axis axis = direction.getAxis();
                            double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getStepX() : (double)random.nextFloat();
                            double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getStepY() : (double)random.nextFloat();
                            double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getStepZ() : (double)random.nextFloat();
                            ParticleUtils.add(level, VisualityParticles.SPARKLE, (double)pos.getX() + x, (double)pos.getY() + y, (double)pos.getZ() + z);
                        }
                    }
                }
            }
        }
    }

}
