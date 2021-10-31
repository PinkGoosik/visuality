package ru.pinkgoosik.visuality.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.SlimeColors;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {

    @Shadow @Final private static TrackedData<Integer> SLIME_SIZE;

    protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    void addParticle(World world, ParticleEffect particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ){
        if(getEntityWorld().isClient && this.getType().equals(EntityType.SLIME)){
            if(dataTracker.get(SLIME_SIZE) == 1){
                this.world.addParticle(VisualityParticles.SMALL_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 1.0D, 0.0D);
            }else if(dataTracker.get(SLIME_SIZE) == 2){
                this.world.addParticle(VisualityParticles.MEDIUM_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 1.0D, 0.0D);
            }else{
                this.world.addParticle(VisualityParticles.BIG_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 2.0D, 0.0D);
            }
        }else{
            this.world.addParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}
