package ru.pinkgoosik.visuality.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.ParticleUtils;

import java.util.Random;

public class CirclesOnWaterEvent implements ClientTickEvents.StartWorldTick {
    static Random random = new Random();

    @Override
    public void onStartTick(ClientWorld world) {
        if (!VisualityMod.CONFIG.getBoolean("water_circle")) return;
        if (MinecraftClient.getInstance().options.particles == ParticlesMode.MINIMAL) return;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;
        if(player.isSubmergedInWater() || !world.getRegistryKey().equals(World.OVERWORLD)) return;
        if(!world.isRaining()) return;
        Biome biome = world.getBiome(player.getBlockPos());
        if (!(biome.getPrecipitation().equals(Biome.Precipitation.RAIN)) || !(biome.getTemperature(player.getBlockPos()) >= 0.15F)) return;

        for (int i = 0; i<= random.nextInt(10) + 5; i++){
            int x = random.nextInt(15) - 7;
            int z = random.nextInt(15) - 7;
            BlockPos playerPos = new BlockPos((int)player.getX() + x, (int)player.getY(), (int)player.getZ() + z);
            BlockPos pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, playerPos);

            if (world.getBlockState(pos.down()).isOf(Blocks.WATER) && world.getBlockState(pos).isAir()){
                if(world.getFluidState(pos.down()).getLevel() == 8){
                    ParticleUtils.add(world, VisualityParticles.WATER_CIRCLE, pos.getX() + random.nextDouble(), pos.getY() + 0.05D, pos.getZ()  + random.nextDouble(), biome.getWaterColor());
                }
            }
        }
    }
}
