package visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SoulParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	SoulParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet sprites) {
		super(world, x, y, z, velX, velY, velZ, sprites.first());

		this.xd = (random.nextDouble() * 2 - 1) / 10;
		this.yd = 0.1D + random.nextDouble() / 10;
		this.zd = (random.nextDouble() * 2 - 1) / 10;

		this.lifetime = 16 + random.nextInt(5);
		this.sprites = sprites;
		this.scale(3F + random.nextFloat());
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			return new SoulParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
		}
	}
}
