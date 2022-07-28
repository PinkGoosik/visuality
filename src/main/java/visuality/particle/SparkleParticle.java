package visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SparkleParticle extends TextureSheetParticle {
	private final SpriteSet sprites;

	private SparkleParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
		super(level, x, y, z, 0, 0, 0);
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
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@Override
	public int getLightColor(float tint) {
		return 15728880;
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
			return new SparkleParticle(world, x, y, z, sprites);
		}
	}
}
