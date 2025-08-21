package github.qbic.darkflame.listeners;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.Config;
import github.qbic.darkflame.util.MathUtil;
import github.qbic.darkflame.util.SignUtil;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@EventBusSubscriber
public class ChunkLoadedListener {
    public static int movedPerChunk = 0;

    @SubscribeEvent
    public static void onChunkLoaded(ChunkEvent.Load event) {
        LevelChunk chunk = event.getChunk();
        if (event.getLevel().isClientSide()) return;

        if (!Brain.worldVars().started) return;

        ServerLevel level = (ServerLevel) chunk.getLevel();
        Random rand = new Random();
        ServerPlayer player = (ServerPlayer) Brain.getTarget();
        if (player == null) return;

        BlockPos respawn = player.getRespawnPosition();
        if (respawn == null) {
            respawn = player.level().getSharedSpawnPos();
        }

        if (!((ServerLevel) event.getLevel()).dimension().equals(Level.OVERWORLD)) return;

        ChunkPos respawnChunk = new ChunkPos(respawn);
        ChunkPos thisChunk = chunk.getPos();
        boolean nearRespawn = Math.abs(thisChunk.x - respawnChunk.x) < 1 && Math.abs(thisChunk.z - respawnChunk.z) < 1;
        if (nearRespawn) return;
        if ((int) (Brain.ticks / Config.corruptionSuppression) != movedPerChunk) {
            movedPerChunk = (int) (Brain.ticks / Config.corruptionSuppression);
            Util.printDBG("moving %d blocks in chunk", movedPerChunk);
        }

        if (event.isNewChunk() && Util.gamble(0.007)) {
            chunkError(chunk);
            return;
        }

        movedPerChunk = Math.min(movedPerChunk, 256);

        int pX = rand.nextInt(16);
        int pZ = rand.nextInt(16);

        Set<Long> used = new HashSet<>();

        int tries = 0;
        int maxTries = movedPerChunk * 4;

        for (int i = 0; i < movedPerChunk && tries < maxTries; tries++) {
            int relX = (int) MathUtil.randomBiased(0, 16, pX, 0.75f);
            int relZ = (int) MathUtil.randomBiased(0, 16, pZ, 0.75f);
            long key = (relX << 8) | relZ;

            if (!used.add(key)) continue;

            int blockX = thisChunk.getMinBlockX() + relX;
            int blockZ = thisChunk.getMinBlockZ() + relZ;
            pX = relX;
            pZ = relZ;

            int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
            int minY = surfaceY - Math.abs(Math.min(surfaceY, 40)) - 1;
            int blockY = (int) MathUtil.randomBiased(minY, surfaceY, surfaceY, 0.9f);

            BlockPos pos = new BlockPos(blockX, blockY, blockZ);
            BlockState state = chunk.getBlockState(pos);

            if (!state.isAir()) {
                if (rand.nextBoolean()) {
                    for (int attempt = 0; attempt < 10; attempt++) {
                        int dy = (int) MathUtil.randomBiased(1, 8, 8, 0.9f);
                        BlockPos newPos = pos.above(dy);
                        if (newPos.getY() >= level.getMaxY()) break;

                        if (chunk.getBlockState(newPos).isAir()) {
                            if (!chunk.getBlockState(pos).getFluidState().isEmpty() && Util.gamble(0.999)) break;
                            chunk.setBlockState(newPos, state, false);
                            chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), false);
                            break;
                        }
                    }
                } else {
                    chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), false);
                }
            }
            i++;
        }
    }

    public static void chunkError(ChunkAccess chunk) {
        Block block;
        BlockState state = null;
        int roll = (int) (Math.random() * 10) + 1;
        if (Util.gamble(0.7)) {
            block = Blocks.AIR;
        } else {
            block = switch (roll) {
                case 1, 2, 3 -> Blocks.STONE;
                case 4, 5 -> Blocks.NETHERRACK;
                case 6, 7 -> Blocks.BEDROCK;
                case 8 -> Blocks.COMMAND_BLOCK;
                case 9, 10 -> Blocks.BLACK_CONCRETE;
                default -> Blocks.AIR;
            };
        }

        state = block.defaultBlockState();

        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();

        for (int x = 0; x < 16; x++) {
            for (int y = chunk.getMinY(); y < chunk.getMaxY(); y++) {
                for (int z = 0; z < 16; z++) {
                    if (y == chunk.getMinY() && block != Blocks.AIR) {
                        chunk.setBlockState(mut, Blocks.BEDROCK.defaultBlockState(), false);
                        continue;
                    }

                    mut.set(startX + x, y, startZ + z);
                    chunk.setBlockState(mut, state, false);

                    BlockEntity blockEntity = chunk.getBlockEntity(mut);
                    String[] lines = {"", "", "", ""};
                    if (blockEntity instanceof SignBlockEntity sign) {
                        lines[0] = (y / 3) + "§kdeez§r" + (z + 1) + "/ >" + (x / 2);
                        sign.setText(SignUtil.getSignText(lines), true);
                        sign.setChanged();
                    }
                }
            }
        }
    }
}
