package visuality.event;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import visuality.VisualityMod;
import visuality.config.VisualityConfig;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

import java.util.Random;

public class CirclesOnWaterEvent {
	static final Random random = new Random();
	static final VisualityConfig config = VisualityMod.config;
	static AbstractClientPlayerEntity player;
	static Biome biome;

	public static void onTick(ClientWorld world) {
		if(!shown(world)) return;

		int density = config.waterCircles.density;
		int radius = config.waterCircles.radius;
		if(density <= 0 || radius <= 0) return;
		int randomDensity = random.nextInt(density) + (density / 2);

		for(int i = 0; i <= randomDensity; i++) {
			int x = random.nextInt(radius) - (radius / 2);
			int z = random.nextInt(radius) - (radius / 2);
			BlockPos playerPos = new BlockPos((int) player.getX() + x, (int) player.getY(), (int) player.getZ() + z);
			BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, playerPos);

			if(world.getBlockState(topPos.down()).isOf(Blocks.WATER) && world.getBlockState(topPos).isAir()) {
				if(world.getFluidState(topPos.down()).getLevel() == 8) {
					int color = config.waterCircles.colored ? biome.getWaterColor() : 0;
					ParticleUtils.add(world, VisualityParticles.WATER_CIRCLE, topPos.getX() + random.nextDouble(), topPos.getY() + 0.05D, topPos.getZ() + random.nextDouble(), color);
				}
			}
		}
	}

	private static boolean shown(ClientWorld world) {
		var client = MinecraftClient.getInstance();
		if(!config.waterCircles.enabled) return false;
		if(client.isPaused()) return false;
		if(client.options.getParticles().getValue() == ParticlesMode.MINIMAL) return false;
		CirclesOnWaterEvent.player = client.player;
		if(player == null) return false;
		if(player.isSubmergedInWater() || !World.OVERWORLD.equals(world.getRegistryKey())) return false;
		if(!world.isRaining()) return false;
		CirclesOnWaterEvent.biome = world.getBiome(player.getSteppingPos()).value();
		if(!(biome.getPrecipitation().equals(Biome.Precipitation.RAIN)) || !(biome.doesNotSnow(player.getSteppingPos())))
			return false;
		return true;
	}
}
