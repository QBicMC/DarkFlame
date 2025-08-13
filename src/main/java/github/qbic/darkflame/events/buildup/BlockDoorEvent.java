package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDoorEvent extends ModEvent {
    public static final int RADIUS = 25;

    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        boolean placed = false;
        ServerPlayer sPlayer = (ServerPlayer) target();
        BlockPos center = target().blockPosition();
        ServerLevel world = sPlayer.serverLevel();
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);

                    if (state.getBlock() instanceof DoorBlock) {
                        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
                        BlockPos inFront = pos.offset(facing.getUnitVec3i());
                        BlockState stateInFront = world.getBlockState(inFront);

                        if (stateInFront.isAir() || stateInFront.canBeReplaced()) {
                            world.setBlock(inFront, Blocks.LAPIS_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                            if (!placed) {
                                placed = true;
                                Util.spawnBeacon(inFront, world, () -> {
                                    Util.spawnEntityBehindPlayer(sPlayer, ModEntities.INTRUSION_JUMPSCARE.get(), 3, 2);
                                }, 0.93);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String name() {
        return "block_door";
    }

    @Override
    public boolean canUse() {
        ServerPlayer sPlayer = (ServerPlayer) target();
        BlockPos center = target().blockPosition();
        ServerLevel world = sPlayer.serverLevel();
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);

                    if (state.getBlock() instanceof DoorBlock) return true;
                }
            }
        }

        return false;
    }
}
