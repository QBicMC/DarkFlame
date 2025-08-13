package github.qbic.darkflame.events;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.init.ModBlocks;
import github.qbic.darkflame.util.BaseScanner;
import github.qbic.darkflame.util.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class WritingEvent extends ModEvent {

    private static final RandomSource rand = new SingleThreadedRandomSource(69420);

    public static WritingData placeWriting(ServerLevel level, Vec3 pos) {
        Util.printDBG("wall detection at: " + pos);

        for (int attempt = 0; attempt < 10; attempt++) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
            Vec3 forward = Vec3.atLowerCornerOf(direction.getUnitVec3i()).normalize();

            Vec3 right = Vec3.atLowerCornerOf(direction.getClockWise().getUnitVec3i()).normalize();
            Vec3 up = new Vec3(0, 1, 0);

            Vec3[] offsets = new Vec3[] {
                    right.scale(-0.5),
                    right.scale(0.5),
                    right.scale(-0.5).add(up),
                    right.scale(0.5).add(up)
            };

            BlockHitResult[] results = new BlockHitResult[4];
            boolean allHit = true;

            for (int i = 0; i < offsets.length; i++) {
                Vec3 start = pos.add(offsets[i]);
                Vec3 end = start.add(forward.scale(10));

                BlockHitResult result = level.clip(new ClipContext(
                        start, end,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        CollisionContext.empty()
                ));

                if (result.getType() != HitResult.Type.BLOCK) {
                    allHit = false;
                    break;
                }

                results[i] = result;
            }

            if (!allHit) {
                continue;
            }

            BlockPos[] hitPositions = new BlockPos[4];
            for (int i = 0; i < 4; i++) {
                hitPositions[i] = results[i].getBlockPos();
            }

            boolean sameX = true;
            for (int i = 1; i < 4; i++) {
                if (hitPositions[i].getX() != hitPositions[0].getX()) {
                    sameX = false;
                    break;
                }
            }

            boolean sameZ = true;
            for (int i = 1; i < 4; i++) {
                if (hitPositions[i].getZ() != hitPositions[0].getZ()) {
                    sameZ = false;
                    break;
                }
            }

            if (sameX || sameZ) {
                BlockPos hitPos = results[0].getBlockPos();
                Direction hitFace = results[0].getDirection();
                BlockPos placePos = hitPos.relative(hitFace);

                if (level.getBlockState(placePos).isAir()) {
                    Util.printDBG("placing writing at " + placePos);
                    return new WritingData(placePos, hitFace.getOpposite());
                } else {
                    Util.printDBG("position occupied: " + level.getBlockState(placePos));
                }
            }
        }

        Util.printDBG("no wall found");
        return null;
    }

    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        ServerPlayer target = (ServerPlayer) Brain.getTarget();
        ServerLevel level = (ServerLevel) Brain.getTarget().level();
//        BlockPos pos = BaseScanner.getBasePos();
        BlockPos pos = target.getRespawnPosition();
        if (pos == null) return;
        WritingData data = placeWriting(level, pos.above().getCenter());// put above otherwise it doesnt work for some reason

        if (data == null) return;
        BlockState state = ModBlocks.WRITING.get()
                .defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, data.direction.getOpposite());

        level.setBlockAndUpdate(data.wallPos, state);
    }

    public record WritingData(BlockPos wallPos, Direction direction) { }
}
