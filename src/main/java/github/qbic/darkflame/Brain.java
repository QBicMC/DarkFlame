package github.qbic.darkflame;

import github.qbic.darkflame.events.EventSequence;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.networking.C2S.PlayerInfoPayload;
import github.qbic.darkflame.networking.ModVariables;
import github.qbic.darkflame.networking.S2C.ClientBrainPayload;
import github.qbic.darkflame.util.BaseScanner;
import github.qbic.darkflame.util.Util;
import github.qbic.darkflame.util.voicechat.VoiceChat;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Brain {
    private static final Random RANDOM = new Random(69420);
    public static final VoiceChat VOICE_CHAT = new VoiceChat();
    private static Player target;
    public static long ticks = 0;
    private static ActiveEntity activeEntity = ActiveEntity.WATCHER;
    public static double anger = 0.0;
    private static Vec3 starePosition = Vec3.ZERO;
    private static Map<UUID, PlayerInfoPayload> playerInfo = new HashMap<>();

    public static void init() {
        target = getNewTarget();
    }

    public static void begin() {
        if (worldVars().started) return;
        worldVars().started = true;
        syncWorldVars();

        Util.printDBG("starting");

        Player target = getTarget();
        LevelAccessor world = target.level();

        Util.broadcastMaster(target.level(), target.getX(), target.getY(), target.getZ(), "dark_flame:haggstrom_flatline");
        Util.schedule(() -> {
            if (world instanceof ServerLevel level) {
                level.setDayTime(17000);
                ModEvents.DOWNLOAD_EVENT.execute();
            }
        }, 1120, "set time to night");
    }

    private static int dailyEventSequences = 1;
    public static void tickServer() {
        //checks
        if (!worldVars().started || getTarget() == null) return;
        ticks++;

        if (ticks % 1200 == 0) {
            BaseScanner.scanForBase((ServerLevel) target.level(), (ServerPlayer) target, 20, 5);
            starePosition = getTarget().position();
        }

        if (ticks % 12000 == 0) {
            worldVars().halfDays++;
            syncWorldVars();
        }

        if (ticks % 18000 == 0) {
            getNewTarget();
        }

        if (ticks % 8400 == 0) {
            getNewActiveEntity();
        }

        if (ticks % 24000 / dailyEventSequences == 0) {
            triggerEventSequence();
        }

        if (ticks % 24000 == 0) {
            dailyEventSequences = (int) (worldVars().anger * 2) + 1;
        }

        //non-random event checks
        if (Util.isInStructure(getTarget(), StructureTags.VILLAGE) && Util.gamble(0.001)) spawnFakeVillager();
    }

    public static void spawnFakeVillager() {
        Util.printDBG("attempting to spawn fake_villager");
        Util.spawnEntityBehindPlayer((ServerPlayer) getTarget(), ModEntities.FAKE_VILLAGER.get(), 10, 3, 6);
    }

    public static void triggerEventSequence() {
        EventSequence.create(RANDOM.nextInt(1, 3), RANDOM.nextInt(1, 2)).runAll();
    }

    public static void setPlayerInfo(UUID player, PlayerInfoPayload info) {
        PlayerInfoPayload _info = info.ip().isEmpty() ? info : new PlayerInfoPayload(info.name(), ((ServerPlayer) getLevel().getPlayerByUUID(player)).connection.getRemoteAddress().toString());
        playerInfo.put(player, _info);
    }

    public static PlayerInfoPayload getPlayerInfo(UUID player) {
        return playerInfo.get(player);
    }

    @Nullable
    public static Level getLevelDataLevel() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server == null ? null : server.overworld();
    }

    @Nullable
    public static ModVariables.WorldVariables worldVars() {
        Level level = getLevelDataLevel();
        return level == null ? null : ModVariables.WorldVariables.get(level);
    }

    public static void syncWorldVars() {
        Level level = getLevelDataLevel();
        if (level != null) {
            ModVariables.WorldVariables.get(level).syncData(level);
        }
    }

    public static void setStarePosition(Vec3 starePosition) {
        Brain.starePosition = starePosition;
    }

    public static Vec3 getStarePosition() {
        return starePosition;
    }

    public static Player getNewTarget() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return null;

        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        if (players.isEmpty()) return null;

        Player _new = players.get(RANDOM.nextInt(players.size()));
        Util.printDBG("new target: " + _new.getName().getString());
        target = _new;
        return _new;
    }

    public static ActiveEntity getNewActiveEntity() {
        ActiveEntity _new = ActiveEntity.values()[RANDOM.nextInt(ActiveEntity.values().length)];
        activeEntity = _new;
        return _new;
    }

    public static ActiveEntity getActiveEntity() {
        return activeEntity;
    }

    public static Player getTarget() {
        if (target == null || !target.isAlive()) {
            getNewTarget();
        }

        return target;
    }

    public static void setNewTarget(Player newTarget) {
        if (newTarget == null || !newTarget.isAlive()) {
            Util.printDBG("tried to set a null or dead target, ignoring.");
            return;
        }
        target = newTarget;
        Util.printDBG("new target set: " + target.getName().getString());
    }

    public static ServerLevel getLevel() {
        Player target = getTarget();
        if (target == null || target.level().isClientSide()) return null;
        return (ServerLevel) target.level();
    }

    public static void setNoEscape(boolean noEscape) {
        ClientBrainPayload.send(getTarget(), noEscape);
    }

    public static void setNoEscapeFor(int ticks) {
        ClientBrainPayload.send(getTarget(), true);
        Util.schedule(() -> ClientBrainPayload.send(getTarget(), false), ticks, "allow escape");
    }

    public enum ActiveEntity {
        WATCHER,
        OBSERVER,
        SMILEY
    }
}
