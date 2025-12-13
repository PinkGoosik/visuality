package visuality.particle;

import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import visuality.VisualityMod;
import org.joml.Quaternionf;

public class WaterCircleParticle extends SingleQuadParticle {
	private final SpriteSet sprites;
	private static final Quaternionf ROTATION = Axis.XP.rotationDegrees(-90);

	private WaterCircleParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites) {
		super(world, x, y, z, 0, 0, 0, sprites.first());
		this.lifetime = 5 + this.random.nextInt(3);
		this.setParticleSpeed(0D, 0D, 0D);
		if (VisualityMod.config.waterCircles.colored) this.setColor();
		this.scale(2F + (float) this.random.nextInt(11) / 10);
		this.sprites = sprites;
		this.setSpriteFromAge(sprites);
	}

	public void setColor() {
		var waterColor = BiomeColors.getAverageWaterColor(this.level, BlockPos.containing(x, y, z));
		var red = ARGB.red(waterColor) / 255.0f;
		var green = ARGB.green(waterColor) / 255.0f;
		var blue = ARGB.blue(waterColor) / 255.0f;
		this.setColor(red, green, blue);
	}

	@Override
	public void tick() {
		if(this.age > this.lifetime / 2) {
			this.setAlpha(1.0F - ((float) this.age - (float) (this.lifetime / 2)) / (float) this.lifetime);
		}
		if(this.age++ >= this.lifetime) {
			this.remove();
		}
		else {
			this.setSpriteFromAge(sprites);
		}
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	protected void extractRotatedQuad(QuadParticleRenderState submittable, Camera camera, Quaternionf rotation, float ticks) {
		super.extractRotatedQuad(submittable, camera, ROTATION, ticks);
	}

	public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			return new WaterCircleParticle(world, x, y, z, sprites);
		}
	}
}
