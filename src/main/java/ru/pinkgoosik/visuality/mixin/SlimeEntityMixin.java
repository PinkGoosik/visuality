package ru.pinkgoosik.visuality.mixin;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.ParticleUtils;
import ru.pinkgoosik.visuality.util.SlimeColors;

@Mixin(Slime.class)
public abstract class SlimeEntityMixin extends Mob implements Enemy {
    @Shadow @Final private static EntityDataAccessor<Integer> ID_SIZE;

    protected SlimeEntityMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    void addParticle(Level world, ParticleOptions particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if(level.isClientSide && this.getType().equals(EntityType.SLIME) && VisualityMod.config.slimeEnabled) {
            spawnSlimeParticle(x, y, z);
        }else {
            this.level.addParticle(particle, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Unique
    private void spawnSlimeParticle(double x, double y, double z) {
        if(getEntityData().get(ID_SIZE) == 1) {
            ParticleUtils.add(level, VisualityParticles.SMALL_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 1.0D);
        }
        else if(getEntityData().get(ID_SIZE) == 2) {
            ParticleUtils.add(level, VisualityParticles.MEDIUM_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 1.0D);
        }
        else {
            ParticleUtils.add(level, VisualityParticles.BIG_SLIME_BLOB, x, y, z, SlimeColors.VANILLA, 2.0D);
        }
    }

}
