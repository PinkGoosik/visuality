package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SoulParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    SoulParticle(ClientLevel level, double x, double y, double z, double velX, double velY, double velZ, SpriteSet sprites) {
        super(level, x, y, z, velX, velY, velZ);

        this.xd = (random.nextDouble() * 2 - 1) / 10;
        this.yd = 0.1D + random.nextDouble() / 10;
        this.zd = (random.nextDouble() * 2 - 1) / 10;

        this.lifetime = 16 + random.nextInt(5);
        this.sprites = sprites;
        this.scale(3F + random.nextFloat());
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoulParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
