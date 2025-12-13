package visuality.mixin;

import com.google.common.collect.Lists;
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
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Unique
	LivingEntity self = LivingEntity.class.cast(this);

	@Shadow
	public abstract boolean isAlive();

	@Unique
	int ticksDelay = 0;

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		var client = Minecraft.getInstance();
		if(level().isClientSide() && ticksDelay != 0) ticksDelay--;
		if(level().isClientSide() && this.isAlive() && client.player != null && VisualityMod.config.shinyArmorEnabled) {

			if(client.player.getUUID().equals(this.getUUID())) {
				if(!client.options.getCameraType().isFirstPerson()) {
					spawnSparkles();
				}
			}
			else {
				spawnSparkles();
			}
		}
	}

	@Override
	public boolean hurtClient(DamageSource source) {
		if(level().isClientSide() && source.getEntity() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive() && VisualityMod.config.hitParticlesEnabled) {
			HitParticleRegistry.ENTRIES.forEach(entry -> {
				if(this.getType().equals(entry.entity())) {
					ticksDelay = 10;
					ItemStack stack = attacker.getMainHandItem();
					int count = this.random.nextInt(3);

					if(stack.getComponents().has(DataComponents.ATTRIBUTE_MODIFIERS)) {
						var data = stack.getComponents().get(DataComponents.ATTRIBUTE_MODIFIERS);
						for(var att : data.modifiers()) {
							if(att.attribute().equals(Attributes.ATTACK_DAMAGE)) {
								count =  (int)((float)att.modifier().amount() * 0.5F);
							}
						}
					}

					if((Object)this instanceof Chicken chicken && entry.particle().equals(VisualityParticles.FEATHER)) {
						var variant = chicken.getVariant();
						ParticleOptions particle = VisualityParticles.FEATHER;
						if(variant.unwrapKey().isPresent()) {
							if(variant.unwrapKey().get().equals(ChickenVariants.COLD)) particle = VisualityParticles.COLD_FEATHER;
							if(variant.unwrapKey().get().equals(ChickenVariants.WARM)) particle = VisualityParticles.WARM_FEATHER;
						}
						spawnHitParticles(particle, count);

					}
					else spawnHitParticles(entry.particle(), count);
				}
			});
		}
		return super.hurtClient(source);
	}

	@Unique
	private void spawnHitParticles(ParticleOptions particle, int count) {
		float height = this.getBbHeight();
		if(height * 100 < 100) height = 1.0F;
		else height = height + 0.5F;
		for(int i = 0; i <= Math.min(count, 10); i++) {
			double randomHeight = (double) this.random.nextInt((int) height * 10) / 10;
			ParticleUtils.add(level(), particle, this.getX(), this.getY() + 0.2D + randomHeight, this.getZ());
		}
	}

	@Unique
	private void spawnSparkles() {
		if(self instanceof Zombie zomb && zomb.isBaby()) return;
		if(!hasShinyArmor()) return;

		ArrayList<Float> heights = Lists.newArrayList();

		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.FEET))) heights.add(0.25F);
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.LEGS))) heights.add(0.65F);
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.CHEST))) heights.add(1.1F);
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.HEAD)) && self.isShiftKeyDown()) heights.add(1.65F);
		else if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.HEAD))) heights.add(1.85F);

		if(!heights.isEmpty()) {
			float height = heights.get(random.nextInt(heights.size()));

			if(this.random.nextInt(20 - (heights.size() * 2)) == 0) {
				double randX = random.nextFloat() - 0.5;
				double randY = (random.nextFloat() * 2 - 1) / 5D;
				double randZ = random.nextFloat() - 0.5;

				ParticleUtils.add(level(), VisualityParticles.SPARKLE, this.getX() + randX, this.getY() + randY + height, this.getZ() + randZ);
			}
		}

	}

	private boolean hasShinyArmor() {
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.FEET))) return true;
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.LEGS))) return true;
		if(ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.CHEST))) return true;
		return ShinyArmorRegistry.isShiny(self.getItemBySlot(EquipmentSlot.HEAD));
	}

}
