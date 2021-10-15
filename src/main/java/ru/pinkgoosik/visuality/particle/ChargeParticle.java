package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ChargeParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    protected ChargeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.maxAge = 8 + this.random.nextInt(4);
        this.setVelocity(0D, 0D, 0D);
        this.scale(1.25F);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.maxAge) {
            this.markDead();
        }else {
            this.setSpriteForAge(spriteProvider);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            return new ChargeParticle(world, x, y, z, spriteProvider);
        }
    }
}
