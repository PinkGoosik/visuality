package visuality.event;

import visuality.VisualityMod;
import visuality.config.VisualityConfig;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

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
			BlockPos topPos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, playerPos);

			if(!(biome.getPrecipitationAt(topPos, world.getSeaLevel()).equals(Biome.Precipitation.RAIN)) || !(biome.warmEnoughToRain(player.getOnPos(), world.getSeaLevel()))) continue;
			if(world.getBlockState(topPos.below()).is(Blocks.WATER) && world.getBlockState(topPos).isAir()) {
				if(world.getFluidState(topPos.below()).getAmount() == 8) {
					ParticleUtils.add(world, VisualityParticles.WATER_CIRCLE, topPos.getX() + random.nextDouble(), topPos.getY() + 0.05D, topPos.getZ() + random.nextDouble());
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
		return true;
	}
}
