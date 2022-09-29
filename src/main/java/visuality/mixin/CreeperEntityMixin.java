package visuality.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

	@Shadow
	@Final
	private static TrackedData<Boolean> CHARGED;

	protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	void tick(CallbackInfo ci) {
		if(this.world.isClient && this.isAlive() && getDataTracker().get(CHARGED)) {
			if(VisualityMod.config.chargeEnabled) {
				if(this.random.nextInt(20) == 0) {
					double x = random.nextFloat() * 2 - 1;
					double y = random.nextFloat();
					double z = random.nextFloat() * 2 - 1;
					ParticleUtils.add(world, VisualityParticles.CHARGE, this.getX() + x, this.getY() + y + 1, this.getZ() + z);
				}
			}
		}
	}

}
