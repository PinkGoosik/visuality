package visuality.particle;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import visuality.VisualityMod;
import org.joml.Quaternionf;

public class WaterCircleParticle extends BillboardParticle {
	private final SpriteProvider sprites;
	private static final Quaternionf ROTATION = RotationAxis.POSITIVE_X.rotationDegrees(-90);

	private WaterCircleParticle(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
		super(world, x, y, z, 0, 0, 0, sprites.getFirst());
		this.maxAge = 5 + this.random.nextInt(3);
		this.setVelocity(0D, 0D, 0D);
		if (VisualityMod.config.waterCircles.colored) this.setColor();
		this.scale(2F + (float) this.random.nextInt(11) / 10);
		this.sprites = sprites;
		this.updateSprite(sprites);
	}

	public void setColor() {
		var waterColor = BiomeColors.getWaterColor(this.world, BlockPos.ofFloored(x, y, z));
		var red = ColorHelper.getRed(waterColor) / 255.0f;
		var green = ColorHelper.getGreen(waterColor) / 255.0f;
		var blue = ColorHelper.getBlue(waterColor) / 255.0f;
		this.setColor(red, green, blue);
	}

	@Override
	public void tick() {
		if(this.age > this.maxAge / 2) {
			this.setAlpha(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
		}
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

	protected void render(BillboardParticleSubmittable submittable, Camera camera, Quaternionf rotation, float ticks) {
		super.render(submittable, camera, ROTATION, ticks);
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new WaterCircleParticle(world, x, y, z, sprites);
		}
	}
}
