package visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SoulParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;

	SoulParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites) {
		super(world, x, y, z, velX, velY, velZ);

		this.velocityX = (random.nextDouble() * 2 - 1) / 10;
		this.velocityY = 0.1D + random.nextDouble() / 10;
		this.velocityZ = (random.nextDouble() * 2 - 1) / 10;

		this.maxAge = 16 + random.nextInt(5);
		this.sprites = sprites;
		this.scale(3F + random.nextFloat());
		this.setSpriteForAge(sprites);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteForAge(this.sprites);
	}

	@Environment(EnvType.CLIENT)
	public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new SoulParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
		}
	}
}
