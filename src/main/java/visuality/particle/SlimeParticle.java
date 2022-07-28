package visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SlimeParticle extends TextureSheetParticle {

	private SlimeParticle(ClientLevel level, double x, double y, double z, double color, double size) {
		super(level, x, y, z, 0, 0, 0);
		this.setColor((int) color);
		this.setAlpha(0.8F);
		this.xd *= 0.10000000149011612D;
		this.yd *= 0.10000000149011612D;
		this.zd *= 0.10000000149011612D;
		this.gravity = 1.0F;
		this.scale((float) size + (float) random.nextInt(6) / 10);
		this.lifetime = 10 + random.nextInt(7);
	}

	public void setColor(int rgbHex) {
		float red = (float) ((rgbHex & 16711680) >> 16) / 255.0F;
		float green = (float) ((rgbHex & '\uff00') >> 8) / 255.0F;
		float blue = (float) ((rgbHex & 255)) / 255.0F;
		this.setColor(red, green, blue);
	}

	@Override
	public void tick() {
		if(this.age > this.lifetime / 2) {
			this.setAlpha(1.0F - ((float) this.age - (float) (this.lifetime / 2)) / (float) this.lifetime);
		}
		super.tick();
		if(this.onGround) {
			this.gravity = 0F;
			this.setParticleSpeed(0D, 0D, 0D);
			this.setPos(xo, yo + 0.1D, zo);
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
			SlimeParticle particle = new SlimeParticle(world, x, y, z, velX, velY);
			particle.setSprite(sprites.get(world.random));
			return particle;
		}
	}
}
