package ru.pinkgoosik.visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class FeatherParticle extends RisingParticle {

    private FeatherParticle(ClientLevel level, double x, double y, double z, double velX, double velY, double velZ) {
        super(level, x, y, z, velX, velY, velZ);
        this.scale(0.7F + (float)level.random.nextInt(6) / 10);
        this.roll = oRoll = random.nextFloat() * (float)(2 * Math.PI);
        this.yd = -0.25D;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 12;
    }

    @Override
    public void tick() {
        if (this.age > this.lifetime / 2) {
            this.setAlpha(1.0F - ((float)this.age - (float)(this.lifetime / 2)) / (float)this.lifetime);
        }
        super.tick();
        if(age == 1){
            this.xd = xd + (Math.random() * 2.0D - 1.0D) * 0.2D;
            this.yd = 0.3D + (double)random.nextInt(11) / 100;
            this.zd = zd + (Math.random() * 2.0D - 1.0D) * 0.2D;
        }else if(age <= 10){
            this.yd = yd - 0.05D;
        }
        if(this.onGround){
            this.setParticleSpeed(0D, 0D, 0D);
            this.setPos(xo, yo + 0.1D, zo);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
            FeatherParticle particle = new FeatherParticle(world, x, y, z, velX, velY, velZ);
            particle.setSpriteFromAge(sprites);
            return particle;
        }
    }
}
