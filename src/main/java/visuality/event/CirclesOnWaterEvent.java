package visuality.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import visuality.VisualityMod;
import visuality.config.VisualityConfig;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

import java.util.Random;

public class CirclesOnWaterEvent {
	static final Random random = new Random();
	static final VisualityConfig config = VisualityMod.config;
	static AbstractClientPlayer player;
	static Biome biome;

	public static void onTick(ClientLevel world) {
		if(!shown(world)) return;

		int density = config.waterCircles.density;
		int radius = config.waterCircles.radius;
		if(density <= 0 || radius <= 0) return;
		int randomDensity = random.nextInt(density) + (density / 2);

		for(int i = 0; i <= randomDensity; i++) {
			int x = random.nextInt(radius) - (radius / 2);
			int z = random.nextInt(radius) - (radius / 2);
			BlockPos playerPos = new BlockPos((int) player.getX() + x, (int) player.getY(), (int) player.getZ() + z);
			BlockPos pos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, playerPos);

			if(world.getBlockState(pos.below()).is(Blocks.WATER) && world.getBlockState(pos).isAir()) {
				if(world.getFluidState(pos.below()).getAmount() == 8) {
					int color = config.waterCircles.colored ? biome.getWaterColor() : 0;
					ParticleUtils.add(world, VisualityParticles.WATER_CIRCLE, pos.getX() + random.nextDouble(), pos.getY() + 0.05D, pos.getZ() + random.nextDouble(), color);
				}
			}
		}
	}

	private static boolean shown(ClientLevel world) {
		var client = Minecraft.getInstance();
		if(!config.waterCircles.enabled) return false;
		if(client.isPaused()) return false;
		if(client.options.particles().get() == ParticleStatus.MINIMAL) return false;
		CirclesOnWaterEvent.player = client.player;
		if(player == null) return false;
		if(player.isUnderWater() || !Level.OVERWORLD.equals(world.dimension())) return false;
		if(!world.isRaining()) return false;
		CirclesOnWaterEvent.biome = world.getBiome(player.getOnPos()).value();
		if(!(biome.getPrecipitation().equals(Biome.Precipitation.RAIN)) || !(biome.warmEnoughToRain(player.getOnPos())))
			return false;
		return true;
	}
}
