package github.qbic.darkflame.dimension.generation;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

import java.util.Collections;

public class Dims {

    public static void teleportToLabyrinth(ServerPlayer player) {
        teleportToDim(player, "labyrinth", new BlockPos(0, 64, 0));
    }

    public static void teleportTargetToLabyrinth() {
        teleportToLabyrinth((ServerPlayer) Brain.getTarget());
    }

    public static void teleportToMineshaft(ServerPlayer player) {
        teleportToDim(player, "mineshaft", new BlockPos(0, 60, 0));
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 1));
    }

    public static void teleportToTorch(ServerPlayer player) {
        teleportToDim(player, "cursed_hallways", new BlockPos(0, 64, 0));
    }

    public static void teleportToDim(ServerPlayer player, ResourceKey<Level> targetDim, BlockPos pos) {
        MinecraftServer server = player.server;
        ServerLevel targetLevel = server.getLevel(targetDim);

        if (targetLevel != null) {
            player.teleportTo(
                    targetLevel,
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    Collections.emptySet(),
                    player.getYRot(),
                    player.getXRot(),
                    false
            );
        }
    }

    public static void teleportToDim(ServerPlayer player, String targetDim, BlockPos pos) {
        ResourceKey<Level> dimKey = Util.createResourceKey(targetDim);
        teleportToDim(player, dimKey, pos);
    }
}
