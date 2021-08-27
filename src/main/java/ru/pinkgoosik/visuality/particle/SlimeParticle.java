package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SlimeParticle extends SpriteBillboardParticle {

    private SlimeParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(world, x, y, z, velX, velY, velZ);
        this.velocityX *= 0.10000000149011612D;
        this.velocityY *= 0.10000000149011612D;
        this.velocityZ *= 0.10000000149011612D;
        this.velocityX += velX;
        this.velocityY += velY;
        this.velocityZ += velZ;
        this.gravityStrength = 1.0F;
        this.scale(1F + (float)world.random.nextInt(6) / 10);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.onGround){
            this.gravityStrength = 0F;
            this.setVelocity(0D, 0D, 0D);
            this.setPos(prevPosX, prevPosY + 0.1D, prevPosZ);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            SlimeParticle particle = new SlimeParticle(world, x, y, z, velX, velY, velZ);
            particle.setSprite(spriteProvider.getSprite(world.random));
            return particle;
        }
    }
}
