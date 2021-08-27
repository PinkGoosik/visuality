package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class FallingRockParticle extends SpriteBillboardParticle {

    private boolean hitFloor = false;
    private int ticksAfterHittingFloor = 0;

    private FallingRockParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);

        this.scale(1.3F + (float)world.random.nextInt(6) / 10);
        this.angle = this.prevAngle = random.nextFloat() * (float)(2 * Math.PI);

        this.setVelocity(0D, -0.4D, 0D);

        this.setMaxAge(120);
    }

    @Override
    public void tick() {
        super.tick();
        if(onGround && !hitFloor) {
            hitFloor = true;
            this.velocityX = velocityX + (Math.random() * 2.0D - 1.0D) * 0.3D;
            this.velocityY = 0.3D + (double)random.nextInt(11) / 100;
            this.velocityZ = velocityZ + (Math.random() * 2.0D - 1.0D) * 0.3D;
        }else if(this.onGround){
            this.setVelocity(0, 0, 0);
            this.setPos(prevPosX, prevPosY + 0.1, prevPosZ);
        }
        if (hitFloor) ticksAfterHittingFloor++;

        if (ticksAfterHittingFloor <= 10) this.velocityY = velocityY - (0.05D + (float)ticksAfterHittingFloor / 200);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            FallingRockParticle particle = new FallingRockParticle(world, x, y, z);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}
