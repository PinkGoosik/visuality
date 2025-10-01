package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class ChargeParticle extends BillboardParticle {
	private final SpriteProvider sprites;

	protected ChargeParticle(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
		super(world, x, y, z, sprites.getFirst());
		this.sprites = sprites;
		this.maxAge = 8 + this.random.nextInt(4);
		this.setVelocity(0D, 0D, 0D);
		this.scale(1.25F);
		updateSprite(sprites);
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
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new ChargeParticle(world, x, y, z, sprites);
		}
	}
}
