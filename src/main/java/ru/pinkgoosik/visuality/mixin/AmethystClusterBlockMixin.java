package ru.pinkgoosik.visuality.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

import java.util.Random;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock implements Waterloggable {
    private int hgHeight;

    protected AmethystClusterBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at=@At("RETURN"))
    public void onInit(int i, int j, Settings settings, CallbackInfo ci) {
        this.hgHeight = i;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if(VisualityMod.config.particles.sparkle){
            if(state.getBlock() instanceof AmethystClusterBlock && hgHeight > 5 && random.nextFloat() > 0.5) {
                world.addParticle(VisualityParticles.SPARKLE, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0, 0);
            }
        }
    }
}
