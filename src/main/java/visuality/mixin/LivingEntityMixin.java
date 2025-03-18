package visuality.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import visuality.VisualityMod;
import visuality.registry.HitParticleRegistry;
import visuality.registry.ShinyArmorRegistry;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Unique
	LivingEntity self = LivingEntity.class.cast(this);

	@Shadow
	public abstract boolean isAlive();

	@Unique
	int ticksDelay = 0;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		var client = MinecraftClient.getInstance();
		if(getWorld().isClient() && ticksDelay != 0) ticksDelay--;
		if(getWorld().isClient() && this.isAlive() && client.player != null && VisualityMod.config.shinyArmorEnabled) {

			if(client.player.getUuid().equals(this.getUuid())) {
				if(!client.options.getPerspective().isFirstPerson()) {
					spawnSparkles();
				}
			}
			else {
				spawnSparkles();
			}
		}
	}

	@Override
	public boolean clientDamage(DamageSource source) {
		if(getWorld().isClient() && source.getAttacker() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive() && VisualityMod.config.hitParticlesEnabled) {
			HitParticleRegistry.ENTRIES.forEach(entry -> {
				if(this.getType().equals(entry.entity())) {
					ticksDelay = 10;
					ItemStack stack = attacker.getMainHandStack();
					int count = this.random.nextInt(3);

					if(stack.getComponents().contains(DataComponentTypes.ATTRIBUTE_MODIFIERS)) {
						var data = stack.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
						for(var att : data.modifiers()) {
							if(att.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
								count =  (int)((float)att.modifier().value() * 0.5F);
							}
						}
					}

					spawnHitParticles(entry.particle(), count);
				}
			});
		}
		return super.clientDamage(source);
	}

	@Unique
	private void spawnHitParticles(ParticleEffect particle, int count) {
		float height = this.getHeight();
		if(height * 100 < 100) height = 1.0F;
		else height = height + 0.5F;
		for(int i = 0; i <= Math.min(count, 10); i++) {
			double randomHeight = (double) this.random.nextInt((int) height * 10) / 10;
			ParticleUtils.add(getWorld(), particle, this.getX(), this.getY() + 0.2D + randomHeight, this.getZ());
		}
	}

	@Unique
	private void spawnSparkles() {
		if(self instanceof ZombieEntity zomb && zomb.isBaby()) return;
		if(!hasShinyArmor()) return;

		ArrayList<Float> heights = Lists.newArrayList();

		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.FEET))) heights.add(0.25F);
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.LEGS))) heights.add(0.65F);
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.CHEST))) heights.add(1.1F);
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.HEAD)) && self.isSneaking()) heights.add(1.65F);
		else if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.HEAD))) heights.add(1.85F);

		if(!heights.isEmpty()) {
			float height = heights.get(random.nextInt(heights.size()));

			if(this.random.nextInt(20 - (heights.size() * 2)) == 0) {
				double randX = random.nextFloat() - 0.5;
				double randY = (random.nextFloat() * 2 - 1) / 5D;
				double randZ = random.nextFloat() - 0.5;

				ParticleUtils.add(getWorld(), VisualityParticles.SPARKLE, this.getX() + randX, this.getY() + randY + height, this.getZ() + randZ);
			}
		}

	}

	private boolean hasShinyArmor() {
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.FEET))) return true;
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.LEGS))) return true;
		if(ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.CHEST))) return true;
		return ShinyArmorRegistry.isShiny(self.getEquippedStack(EquipmentSlot.HEAD));
	}

}
