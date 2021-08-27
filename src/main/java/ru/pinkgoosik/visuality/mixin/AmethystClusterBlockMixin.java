package ru.pinkgoosik.visuality.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

import java.util.Random;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock implements Waterloggable {

    public AmethystClusterBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if(this.equals(Blocks.LARGE_AMETHYST_BUD) || this.equals(Blocks.AMETHYST_CLUSTER)){
            if(random.nextFloat() > 0.5){
                world.addParticle(VisualityParticles.SPARKLE, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0, 0);
            }
        }
    }
}
