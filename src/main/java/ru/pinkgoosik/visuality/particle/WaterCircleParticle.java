package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class WaterCircleParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private static final Quaternion QUATERNION = new Quaternion(0F, -0.7F, 0.7F, 0F);

    private WaterCircleParticle(ClientWorld world, double x, double y, double z, double color, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0, 0, 0);
        this.maxAge = 5 + this.random.nextInt(3);
        this.setVelocity(0D, 0D, 0D);
        this.setColor((int)color);
        this.scale(2F + (float)this.random.nextInt(11) / 10);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    public void setColor(int rgbHex) {
        float red = (float)((rgbHex & 16711680) >> 16) / 255.0F;
        float green = (float)((rgbHex & '\uff00') >> 8) / 255.0F;
        float blue = (float)((rgbHex & 255)) / 255.0F;
        this.setColor(red, green, blue);
    }

    @Override
    public void tick() {
        if (this.age > this.maxAge / 2) {
            this.setColorAlpha(1.0F - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
        }
        if (this.age++ >= this.maxAge) {
            this.markDead();
        }else {
            this.setSpriteForAge(spriteProvider);
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float x = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float y = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float z = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());

        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float size = this.getSize(tickDelta);

        for (Vec3f vec3f : vec3fs){
            vec3f.rotate(QUATERNION);
            vec3f.scale(size);
            vec3f.add(x, y, z);
        }

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();
        int light = this.getBrightness(tickDelta);
        vertexConsumer.vertex(vec3fs[0].getX(), vec3fs[0].getY(), vec3fs[0].getZ()).texture(maxU, maxV).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(light).next();
        vertexConsumer.vertex(vec3fs[1].getX(), vec3fs[1].getY(), vec3fs[1].getZ()).texture(maxU, minV).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(light).next();
        vertexConsumer.vertex(vec3fs[2].getX(), vec3fs[2].getY(), vec3fs[2].getZ()).texture(minU, minV).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(light).next();
        vertexConsumer.vertex(vec3fs[3].getX(), vec3fs[3].getY(), vec3fs[3].getZ()).texture(minU, maxV).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(light).next();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            return new WaterCircleParticle(world, x, y, z, velX, spriteProvider);
        }
    }
}
