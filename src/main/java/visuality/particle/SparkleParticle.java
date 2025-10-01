package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class SparkleParticle extends BillboardParticle {
	private final SpriteProvider sprites;

	private SparkleParticle(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
		super(world, x, y, z, 0, 0, 0, sprites.getFirst());
		this.maxAge = 5 + this.random.nextInt(4);
		this.setVelocity(0D, 0D, 0D);
		this.scale(1.1F);
		this.sprites = sprites;
		this.updateSprite(sprites);
	}

	@Override
	public void tick() {
		if(this.age++ >= this.maxAge) {
			this.markDead();
		}
		else {
			this.updateSprite(sprites);
		}
	}

	@Override
	public int getBrightness(float tint) {
		return 15728880;
	}

	@Override
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new SparkleParticle(world, x, y, z, sprites);
		}
	}
}
