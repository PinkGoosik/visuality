package ru.pinkgoosik.visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SparkleParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    private SparkleParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider) {
        super(world, x, y, z, 0, 0, 0);
        this.lifetime = 5 + this.random.nextInt(4);
        this.setParticleSpeed(0D, 0D, 0D);
        this.scale(1.1F);
        this.spriteProvider = spriteProvider;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }else {
            this.setSpriteFromAge(spriteProvider);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getLightColor(float tint) {
        return 15728880;
    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
            return new SparkleParticle(world, x, y, z, spriteProvider);
        }
    }
}
