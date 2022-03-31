package ru.pinkgoosik.visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.registry.VisualityParticles;
import ru.pinkgoosik.visuality.util.NoteColors;
import ru.pinkgoosik.visuality.util.ParticleUtils;

import java.util.Random;

@Mixin(JukeboxBlock.class)
public abstract class JukeboxBlockMixin extends BaseEntityBlock {
    @Shadow @Final public static BooleanProperty HAS_RECORD;
    private static final int BROKEN_NOTE_COLOR = 5329233;

    protected JukeboxBlockMixin(Properties props) {
        super(props);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        super.animateTick(state, level, pos, random);
        if(state.getValue(HAS_RECORD)) {
            JukeboxBlockEntity blockEntity = (JukeboxBlockEntity) level.getBlockEntity(pos);
            ItemStack recordItem = blockEntity.getRecord();

            if(VisualityMod.CONFIG.getBoolean("note")) {
                if(state.getBlock() instanceof JukeboxBlock && random.nextFloat() > 0.5F) {
                    double x = pos.getX() + random.nextDouble();
                    double y = pos.getY() + 1.1D;
                    double z = pos.getZ()  + random.nextDouble();

                    if(recordItem.getItem().equals(Items.MUSIC_DISC_11)) {
                        ParticleUtils.add(level, VisualityParticles.BROKEN_NOTE, x, y, z, BROKEN_NOTE_COLOR);
                        return;
                    }

                    ParticleUtils.add(level, VisualityParticles.NOTE, x, y, z, NoteColors.getRandomColor());
                }
            }
        }
    }
}
