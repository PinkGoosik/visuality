package ru.pinkgoosik.visuality.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
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
import ru.pinkgoosik.visuality.api.HitMobParticleRegistry;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

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
        if(this.world.isClient && this.isAlive() && MinecraftClient.getInstance().player != null){
            if(MinecraftClient.getInstance().player.getUuid().equals(this.getUuid())){
                if(MinecraftClient.getInstance().options.getPerspective().isFrontView()){
                    if(getShinyArmor() > 0){
                        if(this.random.nextInt(20 - getShinyArmor()) == 0){
                            double randomX = random.nextFloat() * 2 - 1;
                            double randomY = random.nextFloat();
                            double randomZ = random.nextFloat() * 2 - 1;
                            world.addParticle(VisualityParticles.SPARKLE, this.getX() + randomX, this.getY() + randomY + 1, this.getZ() + randomZ, 0, 0, 0);
                        }
                    }
                }
            }else {
                if(getShinyArmor() > 0){
                    if(this.random.nextInt(20 - getShinyArmor()) == 0){
                        double randomX = random.nextFloat() * 2 - 1;
                        double randomY = random.nextFloat();
                        double randomZ = random.nextFloat() * 2 - 1;
                        world.addParticle(VisualityParticles.SPARKLE, this.getX() + randomX, this.getY() + randomY + 1, this.getZ() + randomZ, 0, 0, 0);
                    }
                }
            }
        }
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

    @Unique
    private void spawnHitParticles(HitMobParticleRegistry.Entry entry, int count){
        for(int i = 0; i <= count; i++){
            world.addParticle(entry.particleEffect(), this.getX(), this.getY() + 0.5 + (double)this.random.nextInt(entry.height()) / 10, this.getZ(), 0, 0, 0);
        }
    }

    @Unique
    private int getShinyArmor(){
        int shinyArmor = 0;
        for(ItemStack stack : this.getItemsEquipped()){
            if(VisualityMod.SHINY_ARMOR.contains(stack.getItem())){
                shinyArmor++;
            }
        }
        return shinyArmor;
    }
}
