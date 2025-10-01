package visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class SoulParticle extends BillboardParticle {
	private final SpriteProvider sprites;

	SoulParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites) {
		super(world, x, y, z, velX, velY, velZ, sprites.getFirst());

		this.velocityX = (random.nextDouble() * 2 - 1) / 10;
		this.velocityY = 0.1D + random.nextDouble() / 10;
		this.velocityZ = (random.nextDouble() * 2 - 1) / 10;

		this.maxAge = 16 + random.nextInt(5);
		this.sprites = sprites;
		this.scale(3F + random.nextFloat());
		this.updateSprite(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		this.updateSprite(this.sprites);
	}

	@Override
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
	}

	@Environment(EnvType.CLIENT)
	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new SoulParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
		}
	}
}
