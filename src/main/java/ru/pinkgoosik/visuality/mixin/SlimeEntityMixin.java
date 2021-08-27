package ru.pinkgoosik.visuality.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {

    protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getParticles", at = @At("HEAD"), cancellable = true)
    void getParticles(CallbackInfoReturnable<ParticleEffect> cir){
        if(getEntityWorld().isClient && this.getType().equals(EntityType.SLIME)){
            cir.setReturnValue(VisualityParticles.SLIME);
        }
    }
}
