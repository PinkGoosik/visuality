package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class SlimeParticle extends BillboardParticle {

	private SlimeParticle(ClientWorld world, double x, double y, double z, double color, double size, Sprite sprite) {
		super(world, x, y, z, 0, 0, 0, sprite);
		this.setColor((int) color);
		this.setAlpha(0.8F);
		this.velocityX *= 0.10000000149011612D;
		this.velocityY *= 0.10000000149011612D;
		this.velocityZ *= 0.10000000149011612D;
		this.gravityStrength = 1.0F;
		this.scale((float) size + (float) random.nextInt(6) / 10);
		this.maxAge = 10 + random.nextInt(7);
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
		super.tick();
		if(this.onGround) {
			this.gravityStrength = 0F;
			this.setVelocity(0D, 0D, 0D);
			this.setPos(lastX, lastY + 0.1D, lastZ);
		}
	}

	@Override
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			SlimeParticle particle = new SlimeParticle(world, x, y, z, velocityX, velocityY, sprites.getSprite(world.random));
//			particle.setSprite(sprites.getSprite(world.random));
			return particle;
		}
	}
}
