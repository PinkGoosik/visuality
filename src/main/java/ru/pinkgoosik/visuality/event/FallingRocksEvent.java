package ru.pinkgoosik.visuality.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.pinkgoosik.visuality.particle.VisualityParticles;

import java.util.ArrayList;

public class FallingRocksEvent implements ClientTickEvents.StartWorldTick {
    private static int ticksDelay = 0;

    @Override
    public void onStartTick(ClientWorld world) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if(!world.getRegistryKey().equals(World.OVERWORLD) && player.getY() >= 40) return;
        ticksDelay++;

        if (ticksDelay == 100){
            ticksDelay = 0;
            ArrayList<BlockPos> possibleBlockPoses = new ArrayList<>();

            for(int x = -5; x <= 5; x++){
                for(int y = 0; y <= 6; y++){
                    for(int z = -5; z <= 5; z++){
                        BlockPos blockPos = new BlockPos((int)player.getX() + x, (int)player.getY() + y, (int)player.getZ() + z);

                        if(isStone(world.getBlockState(blockPos)) && isOpenAir(world, blockPos)){
                            possibleBlockPoses.add(blockPos.down());
                        }
                    }
                }
            }

            if(possibleBlockPoses.size() > 30){
                BlockPos randomPos = possibleBlockPoses.get(world.getRandom().nextInt(possibleBlockPoses.size()));
                world.addParticle(VisualityParticles.ROCK, randomPos.getX() + 0.5D, randomPos.getY() + 0.5D, randomPos.getZ() + 0.5D, 0, 0, 0);
            }
        }
    }

    private static boolean isStone(BlockState state) {
        return state.isIn(BlockTags.BASE_STONE_OVERWORLD);
    }

    private static boolean isOpenAir(ClientWorld world, BlockPos pos){
        int airs = 0;

        for (int i = 0; i <= 3; i++){
            if (world.getBlockState(pos.down(i)).isAir()){
                airs++;
            }
        }
        return airs == 3;
    }
}
