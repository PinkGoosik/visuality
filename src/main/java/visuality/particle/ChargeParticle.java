package visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class ChargeParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	protected ChargeParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites) {
		super(world, x, y, z, sprites.first());
		this.sprites = sprites;
		this.lifetime = 8 + this.random.nextInt(4);
		this.setParticleSpeed(0D, 0D, 0D);
		this.scale(1.25F);
		setSpriteFromAge(sprites);
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
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			return new ChargeParticle(world, x, y, z, sprites);
		}
	}
}
