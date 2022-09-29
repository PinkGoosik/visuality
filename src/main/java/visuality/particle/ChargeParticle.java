package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ChargeParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;

	protected ChargeParticle(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
		super(world, x, y, z);
		this.maxAge = 8 + this.random.nextInt(4);
		this.setVelocity(0D, 0D, 0D);
		this.scale(1.25F);
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

	public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType simpleParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
			return new ChargeParticle(world, x, y, z, sprites);
		}
	}
}
