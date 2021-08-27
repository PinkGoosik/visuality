package ru.pinkgoosik.visuality.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.visuality.api.HitMobParticleRegistry;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){

        if(source.getAttacker() != null && amount >= 2 && this.getEntityWorld() instanceof ServerWorld world){
            HitMobParticleRegistry.getEntries().forEach((entry) -> {
                if(this.getType().equals(entry.entityType())){
                    for(int i = 1; i <= (int)amount / 2; i++){
                        world.spawnParticles(entry.particleEffect(), this.getX(), this.getY() + 0.5 + (double)this.random.nextInt(entry.height()) / 10, this.getZ(), 0, 0, 0, 0, 0);
                    }
                }
            });
        }
    }
}
