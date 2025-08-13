package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ReplaceTorchEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        int range = 25;
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        BlockPos pos = target().getOnPos();
        for (int dx = -range; dx < range; dx++) {
            for (int dz = -range; dz < range; dz++) {
                for (int dy = -range; dy < range; dy++) {
                    mut.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                    BlockState blockState = level().getBlockState(mut);
                    Block block = blockState.getBlock();

                    if (block == Blocks.TORCH) {
                        level().setBlock(mut, Blocks.REDSTONE_TORCH.withPropertiesOf(blockState), Block.UPDATE_ALL);
                    } else if (block == Blocks.WALL_TORCH) {
                        level().setBlock(mut, Blocks.REDSTONE_WALL_TORCH.withPropertiesOf(blockState), Block.UPDATE_ALL);
                    }
                }
            }
        }
    }

    @Override
    public String name() {
        return "replace_torch";
    }
}
