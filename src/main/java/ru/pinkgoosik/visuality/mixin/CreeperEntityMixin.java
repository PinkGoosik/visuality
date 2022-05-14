package ru.pinkgoosik.visuality.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.ParticleUtils;

@Mixin(Creeper.class)
public abstract class CreeperEntityMixin extends Monster {

    @Shadow @Final private static EntityDataAccessor<Boolean> DATA_IS_POWERED;

    protected CreeperEntityMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci) {
        if(this.level.isClientSide && this.isAlive() && getEntityData().get(DATA_IS_POWERED)) {
            if(VisualityMod.config.chargeEnabled) {
                if(this.random.nextInt(20) == 0) {
                    double x = random.nextFloat() * 2 - 1;
                    double y = random.nextFloat();
                    double z = random.nextFloat() * 2 - 1;
                    ParticleUtils.add(level, VisualityParticles.CHARGE, this.getX() + x, this.getY() + y + 1, this.getZ() + z);
                }
            }
        }
    }

}
