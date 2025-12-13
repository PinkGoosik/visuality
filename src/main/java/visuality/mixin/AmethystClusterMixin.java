package visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterMixin extends AmethystBlock implements SimpleWaterloggedBlock {
	private float visuality$height;

	public AmethystClusterMixin(Properties settings) {
		super(settings);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	public void onInit(float height, float xzOffset, BlockBehaviour.Properties settings, CallbackInfo ci) {
		this.visuality$height = height;
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
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
