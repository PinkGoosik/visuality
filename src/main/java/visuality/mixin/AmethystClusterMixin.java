package visuality.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterMixin extends AmethystBlock implements Waterloggable {
	private int visuality$height;

	public AmethystClusterMixin(Settings settings) {
		super(settings);
	}


	@Inject(method = "<init>", at = @At("RETURN"))
	public void onInit(int height, int xzOffset, Settings settings, CallbackInfo ci) {
		this.visuality$height = height;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if(VisualityMod.config.sparkleEnabled) {
			if(state.getBlock() instanceof AmethystClusterBlock && visuality$height > 5 && random.nextFloat() > 0.5) {
				double x = pos.getX() + random.nextDouble();
				double y = pos.getY() + random.nextDouble();
				double z = pos.getZ() + random.nextDouble();
				ParticleUtils.add(world, VisualityParticles.SPARKLE, x, y, z);
			}
		}
	}

}
