package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WaterCircleParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;
	private static final Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);

	private WaterCircleParticle(ClientWorld world, double x, double y, double z, double color, SpriteProvider sprites) {
		super(world, x, y, z, 0, 0, 0);
		this.maxAge = 5 + this.random.nextInt(3);
		this.setVelocity(0D, 0D, 0D);
		if(color != 0) this.setColor((int) color);
		this.scale(2F + (float) this.random.nextInt(11) / 10);
		this.sprites = sprites;
		this.setSpriteForAge(sprites);
	}

	public void setColor(int rgbHex) {
		float red = (float) ((rgbHex & 16711680) >> 16) / 255.0F;
		float green = (float) ((rgbHex & '\uff00') >> 8) / 255.0F;
		float blue = (float) ((rgbHex & 255)) / 255.0F;
		this.setColor(red, green, blue);
	}

	@Override
	public void tick() {
		if(this.age > this.maxAge / 2) {
			this.setAlpha(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
		}
		if(this.age++ >= this.maxAge) {
			this.markDead();
		}
		else {
			this.setSpriteForAge(sprites);
		}
	}

	@Override
	public void buildGeometry(VertexConsumer buffer, Camera camera, float ticks) {
		Vec3d vec3 = camera.getPos();
		float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
		float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
		float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
		float f4 = this.getSize(ticks);

		for(int i = 0; i < 4; ++i) {
			Vector3f vector3f = vector3fs[i];
			vector3f.rotate(QUATERNION);
			vector3f.mul(f4);
			vector3f.add(x, y, z);
		}

		float f7 = this.getMinU();
		float f8 = this.getMaxU();
		float f5 = this.getMinV();
		float f6 = this.getMaxV();
		int light = this.getBrightness(ticks);
		buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
		buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
		buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
		buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
			return new WaterCircleParticle(world, x, y, z, velX, sprites);
		}
	}
}
