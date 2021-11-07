package ru.pinkgoosik.visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class EmeraldParticle extends BoneParticle {

    public EmeraldParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
        super(world, x, y, z, velX, velY, velZ);
    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
            EmeraldParticle particle = new EmeraldParticle(world, x, y, z, velX, velY, velZ);
            particle.setSpriteFromAge(spriteProvider);
            return particle;
        }
    }
}
