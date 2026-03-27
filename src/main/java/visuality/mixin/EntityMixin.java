package visuality.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(Entity.class)
abstract class EntityMixin {

	@Shadow
	public abstract Level level();

	@Inject(method = "spawnSprintParticle", at = @At("TAIL"))
	void spawnSprintingParticles(CallbackInfo ci, @Local BlockState state) {
		Entity entity = Entity.class.cast(this);

		if(level().isClientSide() && VisualityMod.config.soulEnabled && state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)) {
			if(level().getRandom().nextInt(5) == 0) {
				double x = entity.getX();
				double y = entity.getY() + 0.1;
				double z = entity.getZ();
				ParticleUtils.add(level(), VisualityParticles.SOUL, x, y, z);
			}
		}
	}
}
