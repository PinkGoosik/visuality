package visuality.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SolidFallingParticle extends AbstractSlowingParticle {

	public SolidFallingParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
		this.scale(1.1F + (float) world.random.nextInt(6) / 10);
		this.angle = prevAngle = random.nextFloat() * (float) (2 * Math.PI);
		this.velocityY = -0.25D;
		this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 12;
	}

	@Override
	public void tick() {
		if(this.age > this.maxAge / 2) {
			this.setAlpha(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
		}
		super.tick();
		if(age == 1) {
			this.velocityX = velocityX + (Math.random() * 2.0D - 1.0D) * 0.2D;
			this.velocityY = 0.3D + (double) random.nextInt(11) / 100;
			this.velocityZ = velocityZ + (Math.random() * 2.0D - 1.0D) * 0.2D;
		}
		else if(age <= 10) {
			this.velocityY = velocityY - (0.05D + (double) age / 200);
		}
		if(this.onGround) {
			this.setVelocity(0D, 0D, 0D);
			this.setPos(prevPosX, prevPosY + 0.1D, prevPosZ);
		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
			SolidFallingParticle particle = new SolidFallingParticle(world, x, y, z, velX, velY, velZ);
			particle.setSpriteForAge(sprites);
			return particle;
		}
	}
}
