package ru.pinkgoosik.visuality.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.visuality.api.HitMobParticleRegistry;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean isAlive();

    int ticksDelay = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci){
        if(world.isClient && ticksDelay != 0) ticksDelay--;
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(world.isClient && source.getAttacker() instanceof LivingEntity attacker && ticksDelay == 0 && this.isAlive()){
            HitMobParticleRegistry.getEntries().forEach(entry -> {
                if(this.getType().equals(entry.entityType())){
                    ticksDelay = 10;
                    Item itemInHand = attacker.getStackInHand(Hand.MAIN_HAND).getItem();
                    if(itemInHand instanceof SwordItem swordItem) spawnHitParticles(entry, (int)swordItem.getAttackDamage() / 2);
                    else if(itemInHand instanceof MiningToolItem miningToolItem) spawnHitParticles(entry, (int)miningToolItem.getAttackDamage() / 2);
                    else spawnHitParticles(entry, this.random.nextInt(2));
                }
            });
        }
    }

    private void spawnHitParticles(HitMobParticleRegistry.Entry entry, int count){
        for(int i = 0; i <= count; i++){
            world.addParticle(entry.particleEffect(), this.getX(), this.getY() + 0.5 + (double)this.random.nextInt(entry.height()) / 10, this.getZ(), 0, 0, 0);
        }
    }
}
