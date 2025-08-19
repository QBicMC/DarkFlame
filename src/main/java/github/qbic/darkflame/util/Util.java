package github.qbic.darkflame.util;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.client.util.Window;
import github.qbic.darkflame.entity.BeaconEntity;
import github.qbic.darkflame.entity.HorrorEntity;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.networking.ModVariables;
import github.qbic.darkflame.networking.S2C.ClientNonSystemChatPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    private static final Random RANDOM = new Random(69420);
    public static final String CONSOLE_NAME = ConsoleFormat.BRIGHT_BLACK + "Dark" + ConsoleFormat.RED + "Flame" + ConsoleFormat.RESET;

    public static List<ServerPlayer> getPlayersLookingAt(Entity target, ServerLevel level, double maxDistance, double maxDot) {
        List<ServerPlayer> result = new ArrayList<>();
        AABB hitbox = target.getBoundingBox().inflate(0.4); // just to make sure it is hidden

        List<Vec3> targetPoints = getBoundingBoxCorners(hitbox);

        boolean anyCanSee = false;
        boolean anyInFOV = false;

        for (ServerPlayer player : level.players()) {
            if (player == target) continue;

            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getLookAngle().normalize();

            boolean inFOV = false;
            boolean hasClearLOS = false;

            for (Vec3 corner : targetPoints) {
                Vec3 toCorner = corner.subtract(eyePos);
                double distSq = toCorner.lengthSqr();

                if (distSq <= maxDistance * maxDistance) {
                    Vec3 dir = toCorner.normalize();
                    double dot = lookVec.dot(dir);

                    if (dot > maxDot) {
                        inFOV = true;

                        BlockHitResult hit = level.clip(new ClipContext(eyePos, corner, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, player));
                        if (hit.getType() == HitResult.Type.MISS || hit.getLocation().distanceTo(corner) < 0.2) {
                            hasClearLOS = true;
                            break;
                        }
                    }
                }
            }

            if (inFOV) {
                anyInFOV = true;

                if (hasClearLOS) {
                    anyCanSee = true;

                    if (target instanceof HorrorEntity entity && !entity.seenByPlayers.contains(player)) {
                        entity.onSeenByPlayer(player);
                    }

                    result.add(player);
                }
            }
        }

        if (target instanceof HorrorEntity entity) {
            if (anyCanSee) {
                entity.visibilityState = HorrorEntity.VisibilityState.VISIBLE;
            } else if (anyInFOV) {
                entity.visibilityState = HorrorEntity.VisibilityState.BLOCKED;
            } else {
                entity.visibilityState = HorrorEntity.VisibilityState.BEHIND;
            }
        }

        return result;
    }

    private static List<Vec3> getBoundingBoxCorners(AABB box) {
        return List.of(
                new Vec3(box.minX, box.minY, box.minZ),
                new Vec3(box.minX, box.minY, box.maxZ),
                new Vec3(box.minX, box.maxY, box.minZ),
                new Vec3(box.minX, box.maxY, box.maxZ),
                new Vec3(box.maxX, box.minY, box.minZ),
                new Vec3(box.maxX, box.minY, box.maxZ),
                new Vec3(box.maxX, box.maxY, box.minZ),
                new Vec3(box.maxX, box.maxY, box.maxZ)
        );
    }

    public static void chatAsPlayer(ServerPlayer player, String msg) {
        PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), msg);
        player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));
    }

    public static void chatAsFakePlayer(ServerLevel level, String name, String msg) {
        for (ServerPlayer target : level.getServer().getPlayerList().getPlayers()) {
            ClientNonSystemChatPayload.send(target, "<" + name + "> " + msg);
        }
    }

    public static boolean gamble(double chance) {
        ModVariables.WorldVariables vars = Brain.worldVars();
        if (vars == null) return Math.random() < chance;
        return Math.random() < chance + (chance * 0.04 * Math.min(vars.halfDays, 100)); //x5 after 100 days
    }

    public static boolean isInStructure(Entity entity, Structure structure) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) return false;

        BlockPos pos = entity.blockPosition();
        StructureManager structureManager = serverLevel.structureManager();

        return structureManager.getStructureWithPieceAt(pos, structure).isValid();
    }

    public static boolean isInStructure(Entity entity, TagKey<Structure> structure) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) return false;

        BlockPos pos = entity.blockPosition();
        StructureManager structureManager = serverLevel.structureManager();

        return structureManager.getStructureWithPieceAt(pos, structure).isValid();
    }

    // large distance
    public static void broadcastSound(LevelAccessor world, double x, double y, double z, String sound) {
        playSound(world, x, y, z, sound, SoundSource.AMBIENT, 255);
    }

    public static void broadcastMaster(LevelAccessor world, double x, double y, double z, String sound) {
        playSound(world, x, y, z, sound, SoundSource.MASTER, 255);
    }

    // SERVER ONLY
    public static void broadcastMasterTo(ServerPlayer player, Holder<SoundEvent> sound) {
        stopAllSounds(player);
        player.connection.send(new ClientboundSoundPacket(sound, SoundSource.MASTER, player.getX(), player.getY(), player.getZ(), 255.0f, 1.0f, player.getRandom().nextLong()));
    }

    // SERVER ONLY
    public static void broadcastToNoStop(ServerPlayer player, Holder<SoundEvent> sound) {
        player.connection.send(new ClientboundSoundPacket(sound, SoundSource.MASTER, player.getX(), player.getY(), player.getZ(), 255.0f, 1.0f, player.getRandom().nextLong()));
    }

    // SERVER ONLY
    public static void broadcastMasterTo(ServerPlayer player, ResourceLocation sound) {
        stopAllSounds(player);
        Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(sound));
        broadcastMasterTo(player, holder);
    }

    // SERVER ONLY
    public static void broadcastMasterTo(ServerPlayer player, String sound) {
        ResourceLocation soundLocation;

        if (sound.contains(":")) {
            soundLocation = ResourceLocation.parse(sound);
        } else {
            if (ResourceLocation.isValidPath("minecraft:" + sound)) {
                soundLocation = ResourceLocation.withDefaultNamespace("minecraft:" + sound);
            } else {
                soundLocation = ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, sound);
            }
        }

        Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(soundLocation));
        broadcastMasterTo(player, holder);
    }

    //SERVER ONLY
    public static void broadcastMasterToFor(ServerPlayer player, String sound, int ticks) {
        broadcastMasterTo(player, sound);
        schedule(() -> stopAllSounds(player), ticks, "stop sounds");
    }

    // SERVER ONLY
    public static void broadcastToNoStop(ServerPlayer player, String sound) {
        ResourceLocation soundLocation;

        if (sound.contains(":")) {
            soundLocation = ResourceLocation.parse(sound);
        } else {
            if (ResourceLocation.isValidPath("minecraft:" + sound)) {
                soundLocation = ResourceLocation.withDefaultNamespace("minecraft:" + sound);
            } else {
                soundLocation = ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, sound);
            }
        }

        Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(soundLocation));
        broadcastToNoStop(player, holder);
    }

    public static void playCaveNoise(ServerPlayer player) {
        Util.broadcastToNoStop(player, SoundEvents.AMBIENT_CAVE);
    }

    // SERVER ONLY
    public static void broadcastMasterToAll(ServerLevel level, Holder<SoundEvent> sound) {
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            broadcastMasterTo(player, sound);
        }
    }

    // SERVER ONLY
    public static void stopAllSounds(ServerPlayer player) {
        player.connection.send(new ClientboundStopSoundPacket(null, null));
    }

    public static void playSound(LevelAccessor world, double x, double y, double z, String sound) {
        playSound(world, x, y, z, sound, SoundSource.AMBIENT, 1);
    }

    // "ambient.basalt_deltas.additions" or "dark_flame:some_sound"
    public static void playSound(LevelAccessor world, double x, double y, double z, String sound, SoundSource source, float volume) {
        if (world instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(sound)), source, volume, 1);
            } else {
                level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(sound)), source, volume, 1, false);
            }
        }
    }

    public static void schedule(Runnable runnable, int delayTicks) {
        new Scheduler(runnable, delayTicks, Dist.DEDICATED_SERVER);
    }

    public static void schedule(Runnable runnable, int delayTicks, String name) {
        new Scheduler(runnable, delayTicks, Dist.DEDICATED_SERVER, name);
    }

    public static void scheduleClient(Runnable runnable, int delayTicks) {
        new Scheduler(runnable, delayTicks, Dist.CLIENT);
    }

    public static void scheduleClient(Runnable runnable, int delayTicks, String name) {
        new Scheduler(runnable, delayTicks, Dist.CLIENT, name);
    }

    public static void printDBG(String msg) {
        if (!DarkFlame.DEBUG) return;
        System.out.println(getTime() + " [" + CONSOLE_NAME + " - " + ConsoleFormat.GREEN + "DEBUG" + ConsoleFormat.RESET + "] " + msg);
//        ((ServerPlayer) Brain.getTarget()).sendSystemMessage(Component.literal(msg));
    }

    public static void printDBG(String msg, Object... format) {
        if (!DarkFlame.DEBUG) return;
        System.out.printf(getTime() + " [" + CONSOLE_NAME + " - " + ConsoleFormat.GREEN + "DEBUG" + ConsoleFormat.RESET + "] " + msg + "\n", format);
//        ((ServerPlayer) Brain.getTarget()).sendSystemMessage(Component.literal(msg));
    }

    public static String getTime() {
        LocalTime time = LocalTime.now();
        return "[" + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]";
    }

    public static Entity spawnEntityAt(BlockPos pos, Level level, EntityType<? extends Entity> type) {
        return spawnEntityAt(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), level, type, false);
    }

    public static Entity spawnEntityAt(Vec3 pos, Level level, EntityType<? extends Entity> type, boolean noAi) {
        Entity entity = type.create(level, EntitySpawnReason.TRIGGERED);

        if (entity != null) {
            entity.setPos(pos.x, pos.y, pos.z);

            if (noAi) {
                entity.setNoGravity(true);
                if (entity instanceof Mob mob) {
                    mob.setNoAi(true);
                }
            }

            level.addFreshEntity(entity);
        }

        return entity;
    }

    public static void spawnEntityAt(int x, int y, int z, Level level, EntityType<? extends Entity> type) {
        Entity entity = type.create(level, EntitySpawnReason.TRIGGERED);

        if (entity != null) {
            entity.setPos(x + 0.5, y, z + 0.5);
            level.addFreshEntity(entity);
        }
    }

    public static BeaconEntity spawnBeacon(BlockPos pos, Level level, Runnable onTrigger) {
        BeaconEntity entity = (BeaconEntity) spawnEntityAt(pos.offset(0, -1, 0), level, ModEntities.BEACON.get());
        entity.setOnLookedAt(onTrigger);
        return entity;
    }

    public static BeaconEntity spawnBeacon(BlockPos pos, Level level, Runnable onTrigger, double lookSensitivity) {
        BeaconEntity entity = (BeaconEntity) spawnEntityAt(pos.offset(0, -1, 0), level, ModEntities.BEACON.get());
        entity.setOnLookedAt(onTrigger);
        entity.setLookSensitivity(lookSensitivity);
        return entity;
    }

    @Nullable
    public static BlockPos searchAround(Level level, BlockPos center) {
        final int maxRange = 32;

        for (int r = 1; r <= maxRange; r++) {
            List<BlockPos> found = new ArrayList<>();

            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (Math.abs(dx) != r && Math.abs(dy) != r && Math.abs(dz) != r) continue; // only outer shell
                        BlockPos pos = center.offset(dx, dy, dz);
                        if (level.getBlockState(pos).isAir()) {
                            found.add(pos);
                        }
                    }
                }
            }

            if (!found.isEmpty()) {
                return closestOrRandom(center, found);
            }
        }

        return null;
    }

    @Nullable
    public static BlockPos searchAroundWithBias(Level level, BlockPos center, BlockPos bias) {
        final int maxRange = 32;

        for (int r = 1; r <= maxRange; r++) {
            List<BlockPos> found = new ArrayList<>();

            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (Math.abs(dx) != r && Math.abs(dy) != r && Math.abs(dz) != r) continue;
                        BlockPos pos = center.offset(dx, dy, dz);
                        if (level.getBlockState(pos).isAir()) {
                            found.add(pos);
                        }
                    }
                }
            }

            if (!found.isEmpty()) {
                return closestOrRandom(bias, found);
            }
        }

        return null;
    }

    private static BlockPos closestOrRandom(BlockPos origin, List<BlockPos> positions) {
        Map<Double, List<BlockPos>> distanceMap = new HashMap<>();
        double minDist = Double.MAX_VALUE;

        for (BlockPos pos : positions) {
            double dist = pos.distSqr(origin);
            minDist = Math.min(minDist, dist);
            distanceMap.computeIfAbsent(dist, d -> new ArrayList<>()).add(pos);
        }

        List<BlockPos> closest = distanceMap.get(minDist);
        return closest.get(RANDOM.nextInt(closest.size()));
    }

    public static BlockPos randomPosAtDistance(BlockPos origin, int distance) {
        if (distance <= 0) return origin;

        while (true) {
            int dx = RANDOM.nextInt(distance * 2 + 1) - distance;
            int dy = RANDOM.nextInt(distance * 2 + 1) - distance;
            int dz = RANDOM.nextInt(distance * 2 + 1) - distance;

            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (Math.round(dist) == distance) {
                return origin.offset(dx, dy, dz);
            }
        }
    }

    public static BlockPos randomPosInRange(BlockPos origin, int minDist, int maxDist) {
        if (minDist < 0) minDist = 0;
        if (maxDist < minDist) maxDist = minDist;

        int dist = RANDOM.nextInt(maxDist - minDist + 1) + minDist;
        return randomPosAtDistance(origin, dist);
    }

    // i need better names
    public static BlockPos randomAirPosBehindPlayer(Level level, Player player, int distance, int heightOffset) {
        for (int attempts = 0; attempts < 100; attempts++) {
            float angleOffset = (RANDOM.nextFloat() - 0.5f) * 60f;
            float angle = player.getYRot() + 180f + angleOffset;

            double radians = Math.toRadians(angle);
            int dx = Mth.floor(-Math.sin(radians) * distance);
            int dz = Mth.floor(Math.cos(radians) * distance);

            BlockPos base = player.blockPosition().offset(dx, 0, dz);
            int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, base.getX(), base.getZ());

            BlockPos finalPos = new BlockPos(base.getX(), surfaceY + heightOffset, base.getZ());

            if (level.getBlockState(finalPos).isAir()) {
                return finalPos;
            }
        }

        return null;
    }

    public static Player getPlayerFromName(LevelAccessor level, String name) {
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : serverLevel.players()) {
                if (player.getName().getString().equals(name)) {
                    return player;
                }
            }
        }

        if (level instanceof Level clientLevel) {
            for (Player player : clientLevel.players()) {
                if (player.getName().getString().equals(name)) {
                    return player;
                }
            }
        }

        return null;
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    public static <T> T chooseRandom(List<T> list) {
        if (list.isEmpty()) return null;
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    @SafeVarargs
    public static <T> T chooseRandom(T... items) {
        List<T> list = Arrays.asList(items);
        return chooseRandom(list);
    }

    public static ResourceKey<Level> createDimResourceKey(String path) {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, path));
    }

    public static boolean isOnSurface(Entity entity) {
        if (entity == null) return false;

        if (entity.level() instanceof ServerLevel serverLevel) {
            BlockPos blockPosition = entity.blockPosition();
            if (serverLevel.canSeeSky(blockPosition)) return true;

            Holder<Biome> biome = serverLevel.getBiome(blockPosition);
            if (biome.is(Tags.Biomes.IS_CAVE) || biome.is(Tags.Biomes.IS_UNDERGROUND)) return false;

            int baseSkyLightLevel = serverLevel.getBrightness(LightLayer.SKY, blockPosition) - serverLevel.getSkyDarken();
            if (baseSkyLightLevel > 0) return true;
        }

        return false;
    }

    public static void spawnEntityBehindPlayer(
            ServerPlayer player,
            EntityType<?> type,
            double distance,
            double minDistance
    ) {
        spawnEntityBehindPlayer(player, type, distance, minDistance, 3, () -> {
            Util.printDBG("could not place entity of type " + type.toString());
        });
    }

    public static void spawnEntityBehindPlayer(
            ServerPlayer player,
            EntityType<?> type,
            double distance,
            double minDistance,
            int verticalSearch
    ) {
        spawnEntityBehindPlayer(player, type, distance, minDistance, verticalSearch, () -> {
            Util.printDBG("could not place entity of type " + type.toString());
        });
    }

    public static void spawnEntityBehindPlayer(
            ServerPlayer player,
            EntityType<?> type,
            double distance,
            double minDistance,
            int verticalSearch,
            Runnable onCantFind
    ) {
        Vec3 look = player.getLookAngle();
        Vec3 flatLook = new Vec3(look.x, 0, look.z).normalize();
        ServerLevel level = player.serverLevel();
        Vec3 origin = player.getEyePosition();

        for (double d = distance; d >= minDistance; d--) {
            double x = origin.x - flatLook.x * d;
            double z = origin.z - flatLook.z * d;
            BlockPos basePos = BlockPos.containing(x, origin.y, z);

            if (level.getBlockState(basePos).isAir()) {
                HitResult hit = level.clip(new ClipContext(
                        new Vec3(x, origin.y, z),
                        new Vec3(x, origin.y - verticalSearch, z),
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        player
                ));

                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockPos spawnPos = BlockPos.containing(hit.getLocation()).above();
                    spawnEntityAt(spawnPos, level, type);
                    return;
                }
            } else {
                for (int i = 1; i <= verticalSearch + 3; i++) {
                    BlockPos above = basePos.above(i);
                    if (level.getBlockState(above).isAir()) {
                        spawnEntityAt(above, level, type);
                        return;
                    }
                }
            }
        }

        onCantFind.run();
    }

    public static boolean disconnectingClient = false;
    public static void safeDisconnect(Player player, Component msg) {
        if (player instanceof ServerPlayer sPlayer && !sPlayer.getServer().isSingleplayer()) {
            Util.printDBG("disconnecting " + sPlayer.getDisplayName() + " from server");
            sPlayer.connection.disconnect(msg);
            return;
        }
        if (disconnectingClient) return;
        Util.printDBG("disconnecting on client");
        Minecraft minecraft = Minecraft.getInstance();
        boolean local = minecraft.isLocalServer();
        disconnectingClient = true;
        minecraft.execute(() -> {
//            DisconnectedScreen screen = new DisconnectedScreen(new TitleScreen(), Component.literal("Disconnected"), msg);
            minecraft.level.disconnect();
            if (local) {
                minecraft.disconnect(new GenericMessageScreen(msg));
            } else {
                minecraft.disconnect();
            }
//            minecraft.setScreen(screen);
            Window.shouldShow = false;
            minecraft.stop();
        });
    }

    public static boolean isVanillaDimension(LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel) {
            ResourceKey<Level> dimensionKey = serverLevel.dimension();
            return dimensionKey.location().getNamespace().equals("minecraft") || dimensionKey.location().getNamespace().equals("neoforge");
        }
        return false;
    }

    public static void teleportToSpawn(ServerPlayer player) {
        MinecraftServer server = player.server;
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        BlockPos spawn = overworld.getSharedSpawnPos();
        BlockPos offset = spawn.offset(15, 0, 0);

        int surfaceY = overworld.getHeight(Heightmap.Types.MOTION_BLOCKING, offset.getX(), offset.getZ());
        BlockPos surfacePos = new BlockPos(offset.getX(), surfaceY, offset.getZ());

        player.teleportTo(
                overworld,
                surfacePos.getX() + 0.5,
                surfacePos.getY(),
                surfacePos.getZ() + 0.5,
                Collections.emptySet(),
                player.getYRot(),
                player.getXRot(),
                true
        );
    }

    public static ServerPlayer getRandomPlayerInDimension(ServerLevel level) {
        List<ServerPlayer> players = level.players();
        if (players.isEmpty()) return null;
        return players.get(level.random.nextInt(players.size()));
    }

    public static Vec3 getPosInFront(ServerPlayer player, double distance) {
        Vec3 look = player.getLookAngle();
        return player.position().add(look.x * distance, 0, look.z * distance);
    }

    public static class Scheduler {
        public static List<Scheduler> schedule = new ArrayList<>();
        public static List<Scheduler> scheduleClient = new ArrayList<>();

        private final Runnable runnable;
        private int timer;
        private final String name;

        public Scheduler(Runnable runnable, int delay, Dist dist, String name) {
            this.runnable = runnable;
            this.timer = delay;
            this.name = name;
            if (dist == Dist.CLIENT) {
                scheduleClient.add(this);
            } else {
                schedule.add(this);
            }
        }

        public Scheduler(Runnable runnable, int delay, Dist dist) {
            this(runnable, delay, dist, "unnamed scheduler");
        }

        public void tick() {
            timer--;
            if (timer <= 0) {
                runnable.run();
                schedule.remove(this);
            }
        }

        public String getName() {
            return name;
        }

        public static void update() {
            List<Scheduler> toRemove = new ArrayList<>();
            for (Scheduler s : new ArrayList<>(schedule)) {
                s.timer--;
                if (s.timer <= 0) {
                    printDBG("running \"" + s.getName() + "\"");
                    try {
                        s.runnable.run();
                    } catch (Exception e) {
                        printDBG(e.getMessage());
                    }
                    toRemove.add(s);
                }
            }
            schedule.removeAll(toRemove);
        }

        public static void updateClient() {
            List<Scheduler> toRemove = new ArrayList<>();
            for (Scheduler s : scheduleClient) {
                s.timer--;
                if (s.timer <= 0) {
                    printDBG("running " + s.runnable);
                    try {
                        s.runnable.run();
                    } catch (Exception e) {
                        printDBG(e.getMessage());
                    }
                    toRemove.add(s);
                }
            }
            scheduleClient.removeAll(toRemove);
        }
    }

    public static List<BlockPos> getBlocksOfType(Level world, BlockPos center, Block targetBlock, int radius) {
        List<BlockPos> matches = new ArrayList<>();

        int rSquared = radius * radius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos currentPos = center.offset(dx, dy, dz);
                    if (center.distSqr(currentPos) <= rSquared &&
                            world.getBlockState(currentPos).is(targetBlock)) {
                        matches.add(currentPos.immutable());
                    }
                }
            }
        }

        return matches;
    }
}
