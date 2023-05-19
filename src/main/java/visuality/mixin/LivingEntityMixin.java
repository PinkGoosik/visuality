package visuality.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import visuality.VisualityMod;
import visuality.registry.HitParticleRegistry;
import visuality.registry.VisualityParticles;
import visuality.util.ShinyArmorUtils;
import visuality.util.ParticleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	LivingEntity self = LivingEntity.class.cast(this);

	@Shadow
	public abstract boolean isAlive();

	int ticksDelay = 0;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		var client = MinecraftClient.getInstance();
		if(getWorld().isClient() && ticksDelay != 0) ticksDelay--;
		if(getWorld().isClient() && this.isAlive() && client.player != null && VisualityMod.config.shinyArmorEnabled) {
			int shinyLevel = ShinyArmorUtils.getShinyLevel(self);
			if(client.player.getUuid().equals(this.getUuid())) {
				if(!client.options.getPerspective().isFirstPerson()) {
					spawnSparkles(shinyLevel);
				}
			}
			else {
				spawnSparkles(shinyLevel);
			}
		}
	}

	@Inject(method = "damage", at = @At("HEAD"))
	void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if(getWorld().isClient() && source.getAttacker() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive() && VisualityMod.config.hitParticlesEnabled) {
			HitParticleRegistry.ENTRIES.forEach(entry -> {
				if(this.getType().equals(entry.entity())) {
					ticksDelay = 10;
					Item item = attacker.getMainHandStack().getItem();
					int count = this.random.nextInt(2);
					if(item instanceof SwordItem sword) count = (int)sword.getAttackDamage() / 2;
					else if(item instanceof MiningToolItem tool)
						count = (int)tool.getAttackDamage() / 2;
					spawnHitParticles(entry.particle(), count);
				}
			});
		}
	}

	@Unique
	private void spawnHitParticles(ParticleEffect particle, int count) {
		float height = this.getHeight();
		if(height * 100 < 100) height = 1.0F;
		else height = height + 0.5F;
		for(int i = 0; i <= count; i++) {
			double randomHeight = (double) this.random.nextInt((int) height * 10) / 10;
			ParticleUtils.add(getWorld(), particle, this.getX(), this.getY() + 0.2D + randomHeight, this.getZ());
		}
	}

	@Unique
	private void spawnSparkles(int shinyLevel) {
		if(shinyLevel > 0) {
			if(this.random.nextInt(20 - shinyLevel) == 0) {
				double x = random.nextFloat() * 2 - 1;
				double y = random.nextFloat();
				double z = random.nextFloat() * 2 - 1;
				ParticleUtils.add(getWorld(), VisualityParticles.SPARKLE, this.getX() + x, this.getY() + y + 1, this.getZ() + z);
			}
		}
	}

}
