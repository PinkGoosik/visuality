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
import ru.pinkgoosik.visuality.registry.VisualityParticles;

import java.util.Random;

public class CirclesOnWaterEvent implements ClientTickEvents.StartWorldTick {
    static Random random = new Random();

    @Override
    public void onStartTick(ClientWorld world) {
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
            BlockPos blockPos = new BlockPos((int)player.getX() + x, (int)player.getY(), (int)player.getZ() + z);
            BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos);

            if (world.getBlockState(topPos.down()).isOf(Blocks.WATER) && world.getBlockState(topPos).isAir()){
                if(world.getFluidState(topPos.down()).getLevel() == 8){
                    world.addParticle(VisualityParticles.WATER_CIRCLE, topPos.getX() + random.nextDouble(), topPos.getY() + 0.05D, topPos.getZ()  + random.nextDouble(), biome.getWaterColor(), 0, 0);
                }
            }
        }
    }
}
