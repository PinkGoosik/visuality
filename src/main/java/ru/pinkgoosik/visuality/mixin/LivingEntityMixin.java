package ru.pinkgoosik.visuality.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
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

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci){
        if(level.isClientSide && ticksDelay != 0) ticksDelay--;
        if(level.isClientSide && this.isAlive() && Minecraft.getInstance().player != null && VisualityMod.CONFIG.getBoolean("sparkle")){
            int shinyLevel = FunkyUtils.getShinyArmor(self);
            if(Minecraft.getInstance().player.getUUID().equals(this.getUUID())){
                if(!Minecraft.getInstance().options.getCameraType().isFirstPerson()){
                    spawnSparkles(shinyLevel);
                }
            }else {
                spawnSparkles(shinyLevel);
            }
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(level.isClientSide && source.getEntity() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive() && VisualityMod.CONFIG.getBoolean("hit_particles")){
            HitParticleRegistry.ENTRIES.forEach(entry -> {
                if(this.getType().equals(entry.entity())){
                    ticksDelay = 10;
                    Item item = attacker.getMainHandItem().getItem();
                    int count = this.random.nextInt(2);
                    if(item instanceof SwordItem swordItem) count = (int)swordItem.getDamage() / 2;
                    else if(item instanceof DiggerItem miningToolItem) count = (int)miningToolItem.getAttackDamage() / 2;
                    spawnHitParticles(entry.particle(), count);
                }
            });
        }
    }

    @Unique
    private void spawnHitParticles(ParticleOptions particle, int count){
        float height = this.getBbHeight();
        if(height * 100 < 100) height = 1.0F;
        else height = height + 0.5F;
        for(int i = 0; i <= count; i++){
            double randomHeight = (double)this.random.nextInt((int)height * 10) / 10;
            ParticleUtils.add(level, particle, this.getX(), this.getY() + 0.2D + randomHeight, this.getZ());
        }
    }

    @Unique
    private void spawnSparkles(int shinyLevel){
        if(shinyLevel > 0){
            if(this.random.nextInt(20 - shinyLevel) == 0){
                double x = random.nextFloat() * 2 - 1;
                double y = random.nextFloat();
                double z = random.nextFloat() * 2 - 1;
                ParticleUtils.add(level, VisualityParticles.SPARKLE, this.getX() + x, this.getY() + y + 1, this.getZ() + z);
            }
        }
    }
}
