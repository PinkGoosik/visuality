package visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SparkleParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	private SparkleParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites) {
		super(world, x, y, z, 0, 0, 0, sprites.first());
		this.lifetime = 5 + this.random.nextInt(4);
		this.setParticleSpeed(0D, 0D, 0D);
		this.scale(1.1F);
		this.sprites = sprites;
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		if(this.age++ >= this.lifetime) {
			this.remove();
		}
		else {
			this.setSpriteFromAge(sprites);
		}
	}

	@Override
	public int getLightColor(float tint) {
		return 15728880;
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			return new SparkleParticle(world, x, y, z, sprites);
		}
	}
}
