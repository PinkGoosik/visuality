package visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.ShinyBlockRegistry;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements ItemLike {

	public BlockMixin(Properties settings) {
		super(settings);
	}

	@Inject(method = "animateTick", at = @At("TAIL"))
	void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
		if(VisualityMod.config.soulEnabled && state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
			if(world.getBlockState(pos.above()).isAir()) {
				if(random.nextFloat() > 0.995F) {
					double x = pos.getX() + random.nextDouble();
					double y = pos.getY() + 1.1D;
					double z = pos.getZ() + random.nextDouble();
					ParticleUtils.add(world, VisualityParticles.SOUL, x, y, z);
				}
			}
		}
		if(VisualityMod.config.shinyBlocksEnabled && ShinyBlockRegistry.isShiny(state)) {
			for(Direction direction : Direction.values()) {
				BlockPos offset = pos.relative(direction);
				if(!world.getBlockState(offset).isSolidRender()) {
					if(random.nextFloat() > 0.8) {
						Direction.Axis axis = direction.getAxis();
						double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
						double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
						double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
						ParticleUtils.add(world, VisualityParticles.SPARKLE, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z);
					}
				}
			}
		}
	}

}
