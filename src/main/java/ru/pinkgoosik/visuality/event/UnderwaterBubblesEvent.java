package ru.pinkgoosik.visuality.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.pinkgoosik.visuality.registry.VisualityParticles;

import java.util.ArrayList;

public class UnderwaterBubblesEvent implements ClientTickEvents.StartWorldTick {
    private static BlockPos bubblesPos = new BlockPos(0, 0, 0);
    private static int bubblesAge = 0;
    private static int ticksDelay = 0;

    @Override
    public void onStartTick(ClientWorld world) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;
        if(!player.isSubmergedInWater() || !world.getRegistryKey().equals(World.OVERWORLD) || player.getY() >= 64 || player.getY() <= 32) return;
        ticksDelay++;

        if(bubblesAge > 0){
            bubblesAge--;
            if(bubblesAge % 3 == 0){
                world.addParticle(VisualityParticles.UNDERWATER_BUBBLE, bubblesPos.getX() + 0.5D, bubblesPos.getY() + 0.1D, bubblesPos.getZ() + 0.5D, 0, 0, 0);
            }
        }

        if (ticksDelay == 120){
            ticksDelay = 0;
            ArrayList<BlockPos> possibleBlockPoses = new ArrayList<>();

            for(int x = -5; x <= 5; x++){
                for(int y = 3; y >= -5; y--){
                    for(int z = -5; z <= 5; z++){
                        BlockPos blockPos = new BlockPos((int)player.getX() + x, (int)player.getY() + y, (int)player.getZ() + z);

                        if(isSoil(world.getBlockState(blockPos)) && isOpenWater(world, blockPos)){
                            possibleBlockPoses.add(blockPos.up());
                        }
                    }
                }
            }

            if(possibleBlockPoses.size() > 10){
                BlockPos randomPos = possibleBlockPoses.get(world.getRandom().nextInt(possibleBlockPoses.size()));
                addBubbles(randomPos);
            }
        }

    }

    private static boolean isSoil(BlockState state) {
        return state.isOf(Blocks.DIRT) || state.isOf(Blocks.SAND) || state.isOf(Blocks.GRAVEL) || state.isOf(Blocks.CLAY);
    }

    private static boolean isOpenWater(ClientWorld world, BlockPos pos){
        int waters = 0;

        for (int i = 0; i <= 3; i++){
            if (world.getBlockState(pos.up(i)).isOf(Blocks.WATER)){
                waters++;
            }
        }
        return waters == 3;
    }

    private static void addBubbles(BlockPos blockPos){
        UnderwaterBubblesEvent.bubblesPos = blockPos;
        UnderwaterBubblesEvent.bubblesAge = 120;
    }
}
