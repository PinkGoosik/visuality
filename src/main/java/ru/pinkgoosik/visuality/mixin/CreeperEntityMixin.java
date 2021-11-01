package ru.pinkgoosik.visuality.mixin;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements SkinOverlayOwner {

    @Shadow @Final private static TrackedData<Boolean> CHARGED;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci){
        if(this.world.isClient && this.isAlive() && getDataTracker().get(CHARGED)){
            if(VisualityMod.config.particles.charge){
                if(this.random.nextInt(20) == 0){
                    double randomX = random.nextFloat() * 2 - 1;
                    double randomY = random.nextFloat();
                    double randomZ = random.nextFloat() * 2 - 1;
                    world.addParticle(VisualityParticles.CHARGE, this.getX() + randomX, this.getY() + randomY + 1, this.getZ() + randomZ, 0, 0, 0);
                }
            }
        }
    }
}
