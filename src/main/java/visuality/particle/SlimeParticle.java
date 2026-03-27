package visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;

public class SlimeParticle extends SingleQuadParticle {

	private SlimeParticle(ClientLevel world, double x, double y, double z, double color, double size, TextureAtlasSprite sprite) {
		super(world, x, y, z, 0, 0, 0, sprite);
		this.setColor((int) color);
		this.setAlpha(0.8F);
		this.xd *= 0.1D;
		this.yd *= 0.1D;
		this.zd *= 0.1D;
		this.gravity = 1.0F;
		this.scale((float) size + (float) random.nextInt(6) / 10);
		this.lifetime = 10 + random.nextInt(7);
	}

	public void setColor(int color) {
		var red = ARGB.red(color) / 255.0f;
		var green = ARGB.green(color) / 255.0f;
		var blue = ARGB.blue(color) / 255.0f;
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
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			SlimeParticle particle = new SlimeParticle(world, x, y, z, velocityX, velocityY, sprites.get(world.getRandom()));
//			particle.setSprite(sprites.getSprite(world.random));
			return particle;
		}
	}
}
