package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SparkleParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;

	private SparkleParticle(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
		super(world, x, y, z, 0, 0, 0);
		this.maxAge = 5 + this.random.nextInt(4);
		this.setVelocity(0D, 0D, 0D);
		this.scale(1.1F);
		this.sprites = sprites;
		this.setSpriteForAge(sprites);
	}

	@Override
	public void tick() {
		if(this.age++ >= this.maxAge) {
			this.markDead();
		}
		else {
			this.setSpriteForAge(sprites);
		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_LIT;
	}

	@Override
	public int getBrightness(float tint) {
		return 15728880;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
			return new SparkleParticle(world, x, y, z, sprites);
		}
	}
}
