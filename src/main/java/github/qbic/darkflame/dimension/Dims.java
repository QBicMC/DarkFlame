package github.qbic.darkflame.dimension;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
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

    public static void teleportToTheHallways(ServerPlayer player) {
        teleportToDim(player, "the_hallways", new BlockPos(3, 1, 0));
    }

    public static void teleportTargetToTheHallways() {
        teleportToTheHallways((ServerPlayer) Brain.getTarget());
    }

    public static void teleportToMEGALOPHOBIA(ServerPlayer player) {
        teleportToDim(player, "megalophobia", new BlockPos(0, 1, 0));
    }

    public static void teleportTargetToMEGALOPHOBIA() {
        ServerPlayer target = (ServerPlayer) Brain.getTarget();
        target.connection.send(new ClientboundSetTitleTextPacket(Component.literal("PUNISHMENT")));
        target.connection.send(new ClientboundSetTitlesAnimationPacket(0, 10, 0));
        Util.broadcastMasterTo(target, "exclusion_jumpscare");
        Util.schedule(() -> teleportToMEGALOPHOBIA(target), 10);
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
                    player.getYRot(),/// /t/
                    player.getXRot(),
                    false
            );
        }
    }

    public static void teleportToDim(ServerPlayer player, String targetDim, BlockPos pos) {
        ResourceKey<Level> dimKey = Util.createDimResourceKey(targetDim);
        teleportToDim(player, dimKey, pos);
    }
}
