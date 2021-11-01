package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EmeraldParticle extends BoneParticle {

    public EmeraldParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(world, x, y, z, velX, velY, velZ);
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            EmeraldParticle particle = new EmeraldParticle(world, x, y, z, velX, velY, velZ);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}
