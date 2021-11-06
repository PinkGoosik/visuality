package ru.pinkgoosik.visuality.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.HitParticleRegistry;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.FunkyUtils;
import ru.pinkgoosik.visuality.util.ParticleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    LivingEntity self = LivingEntity.class.cast(this);

    @Shadow public abstract boolean isAlive();

    int ticksDelay = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci){
        if(world.isClient && ticksDelay != 0) ticksDelay--;
        if(this.world.isClient && this.isAlive() && MinecraftClient.getInstance().player != null && VisualityMod.CONFIG.getBoolean("sparkle")){
            int shinyLevel = FunkyUtils.getShinyArmor(self);
            if(MinecraftClient.getInstance().player.getUuid().equals(this.getUuid())){
                if(MinecraftClient.getInstance().options.getPerspective().isFrontView()){
                    spawnSparkles(shinyLevel);
                }
            }else {
                spawnSparkles(shinyLevel);
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(world.isClient && source.getAttacker() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive() && VisualityMod.CONFIG.getBoolean("hit_particles")){
            HitParticleRegistry.ENTRIES.forEach(entry -> {
                if(this.getType().equals(entry.entity())){
                    ticksDelay = 10;
                    Item item = attacker.getStackInHand(Hand.MAIN_HAND).getItem();
                    int count = this.random.nextInt(2);
                    if(item instanceof SwordItem swordItem) count = (int)swordItem.getAttackDamage() / 2;
                    else if(item instanceof MiningToolItem miningToolItem) count = (int)miningToolItem.getAttackDamage() / 2;
                    spawnHitParticles(entry.particle(), count);
                }
            });
        }
    }

    @Unique
    private void spawnHitParticles(ParticleEffect particle, int count){
        float height = this.getHeight();
        if(height * 100 < 100) height = 1.0F;
        else height = height + 0.5F;
        for(int i = 0; i <= count; i++){
            double randomHeight = (double)this.random.nextInt((int)height * 10) / 10;
            ParticleUtils.add(world, particle, this.getX(), this.getY() + 0.2D + randomHeight, this.getZ());
        }
    }

    @Unique
    private void spawnSparkles(int shinyLevel){
        if(shinyLevel > 0){
            if(this.random.nextInt(20 - shinyLevel) == 0){
                double x = random.nextFloat() * 2 - 1;
                double y = random.nextFloat();
                double z = random.nextFloat() * 2 - 1;
                ParticleUtils.add(world, VisualityParticles.SPARKLE, this.getX() + x, this.getY() + y + 1, this.getZ() + z);
            }
        }
    }
}
