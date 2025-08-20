package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MineNextToEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        ServerPlayer player = (ServerPlayer) target();
        ServerLevel level = player.serverLevel();

        BlockPos startPos = getNextTo(player);
        BlockPos bottomPos = startPos.below(5);

        for (int i = 0; i < 6; i++) {
            BlockPos pos = startPos.below(i);
            BlockState state = level.getBlockState(pos);

            if (!state.isAir()) {
                Util.schedule(() -> {
                    BlockEntity be = level.getBlockEntity(pos);
                    Block.dropResources(state, level, pos, be, player, player.getMainHandItem());
                    level.removeBlock(pos, false);
                    level.playSound(null, pos, state.getSoundType(level, pos, player).getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }, 13 * i, "break " + (i + 1));
            }
        }

        Util.schedule(() -> {
            level.setBlock(bottomPos, Blocks.REDSTONE_TORCH.defaultBlockState(), 3);
        }, 78, "torch");
    }

    public static BlockPos getNextTo(Player player) {
        Direction facing = player.getDirection();
        Direction left = facing.getCounterClockWise();

        return player.blockPosition().relative(left, 5);
    }

    @Override
    public String name() {
        return "mine_beside";
    }

    @Override
    public boolean canUse() {
        BlockState state = level().getBlockState(getNextTo(target()));
        return (state.equals(Blocks.STONE.defaultBlockState()) || state.equals(Blocks.DEEPSLATE.defaultBlockState())) && ((ServerPlayer) target()).serverLevel().canSeeSky(target().blockPosition());
    }
}
