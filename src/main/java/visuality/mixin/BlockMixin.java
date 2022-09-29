package visuality.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.ShinyBlockRegistry;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {

	public BlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "onLandedUpon", at = @At("TAIL"))
	void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
		if(VisualityMod.config.soulEnabled) {
			if(state.isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
				for(int i = 0; i <= world.random.nextInt(5) + 1; i++) {
					double x = entity.getX();
					double y = entity.getY() + 0.1;
					double z = entity.getZ();
					ParticleUtils.add(world, VisualityParticles.SOUL, x, y, z);
				}
			}
		}
	}

	@Inject(method = "randomDisplayTick", at = @At("TAIL"))
	void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
		if(VisualityMod.config.soulEnabled && state.isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
			if(world.getBlockState(pos.up()).isAir()) {
				if(random.nextFloat() > 0.995F) {
					double x = pos.getX() + random.nextDouble();
					double y = pos.getY() + 1.1D;
					double z = pos.getZ() + random.nextDouble();
					ParticleUtils.add(world, VisualityParticles.SOUL, x, y, z);
				}
			}
		}
		if(VisualityMod.config.shinyBlocksEnabled && ShinyBlockRegistry.ENTRIES.contains(this)) {
			for(Direction direction : Direction.values()) {
				BlockPos offset = pos.offset(direction);
				if(!world.getBlockState(offset).isOpaqueFullCube(world, offset)) {
					if(random.nextFloat() > 0.8) {
						Direction.Axis axis = direction.getAxis();
						double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getOffsetX() : (double) random.nextFloat();
						double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getOffsetY() : (double) random.nextFloat();
						double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getOffsetZ() : (double) random.nextFloat();
						ParticleUtils.add(world, VisualityParticles.SPARKLE, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z);
					}
				}
			}
		}
	}

}
