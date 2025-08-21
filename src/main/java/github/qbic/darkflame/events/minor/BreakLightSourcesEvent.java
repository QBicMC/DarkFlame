package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BreakLightSourcesEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        ServerLevel level = (ServerLevel) level();
        ServerPlayer player = (ServerPlayer) target();
        List<BlockPos> poses = Util.getBlocksOfType(level, player.blockPosition(), Blocks.TORCH, 25);

        for (int i = 0; i < poses.size(); i++) {
            int _i = i;

            Util.schedule(() -> {
                BlockPos pos = poses.get(_i);
                BlockState state = level.getBlockState(pos);

                Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
                level.removeBlock(pos, false);
                level.playSound(null, pos, state.getSoundType(level, pos, player).getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }, i);
        }
    }

    @Override
    public String name() {
        return "lights_out";
    }
}
