package github.qbic.darkflame.listeners;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.dimension.Dims;
import github.qbic.darkflame.entity.SingularityEntity;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.init.ModParticleTypes;
import github.qbic.darkflame.util.StructureUtil;
import github.qbic.darkflame.util.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Comparator;

@EventBusSubscriber
public class PlayerTickListener {
    public static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post e) {
        tickCounter++;
        Level level = e.getEntity().level();
        Player player = e.getEntity();

        Entity closestSingularity = getClosestEntity(level, player, SingularityEntity.class, 128);
        if (closestSingularity != null) {
            double dx = closestSingularity.getX() - player.getX();
            double dy = closestSingularity.getY() - player.getY();
            double dz = closestSingularity.getZ() - player.getZ();
            double length = Math.sqrt(dx * dx + dy * dy + dz * dz); // normalize
            if (length > 0) {
                double speed = 0.1;
                double vx = dx / length * speed;
                double vy = dy / length * speed;
                double vz = dz / length * speed;
                level.addParticle(ModParticleTypes.BLACK_DOT.get(), player.getX() + (Math.random() - 0.5), player.getY() + (Math.random() * 2), player.getZ() + (Math.random() - 0.5), vx, vy, vz);
            }
        }

//        Util.printDBG("player tick on: " + (level.isClientSide() ? "client" : "server"));
        if (level.isClientSide()) return;
        if (StructureUtil.inStructure((ServerPlayer) e.getEntity(), StructureUtil.Structures.SUMMONING_CIRCLE)) {
            Brain.begin();
        }

        ServerPlayer sPlayer = ((ServerPlayer) player);
        ResourceKey<Level> dim = sPlayer.serverLevel().dimension();

        if (player.getBlockY() < level.getMinY()) {

            if (dim.equals(Level.OVERWORLD) || dim.equals(Level.NETHER)) {
                if (Util.gamble(0.5)) {
                    Dims.teleportToLabyrinth(sPlayer);
                } else {
                    Dims.teleportToTheHallways(sPlayer);
                }
            } else if (dim.equals(Level.END)) {

            } else if (dim.equals(Util.createDimResourceKey("the_hallways"))) {
                sPlayer.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
            }
        }

        if (tickCounter % 5 == 0 && dim.equals(Level.OVERWORLD)) {
            if (player.getXRot() < 60f) return;

            Vec3 start = player.getEyePosition();
            Vec3 end = start.add(player.getLookAngle().scale(800));

            ClipContext context = new ClipContext(
                    start,
                    end,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            );

            BlockHitResult hit = player.level().clip(context);

            if (hit.getType() == HitResult.Type.MISS) {
                ModEvents.JUMP_MESSAGE_EVENT.executeIfUsable();
            }
        }
    }

    public static <T extends Entity> T getClosestEntity(Level level, Player origin, Class<T> type, double range) {
        AABB box = origin.getBoundingBox().inflate(range);

        return level.getEntitiesOfClass(type, box, e -> e != origin && e.isAlive())
                .stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(origin)))
                .orElse(null);
    }
}
