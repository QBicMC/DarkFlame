package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.levelgen.Heightmap;

public class LightningStrikeCircleEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        ServerPlayer player = (ServerPlayer) target();
        ServerLevel level = player.serverLevel();
        BlockPos center = player.blockPosition();

        int radius = 8;
        int strikes = 8;

        for (int i = 0; i < strikes; i++) {
            double angle = (2 * Math.PI / strikes) * i;
            int x = center.getX() + (int) Math.round(Math.cos(angle) * radius);
            int z = center.getZ() + (int) Math.round(Math.sin(angle) * radius);
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.MOB_SUMMONED);
            if (lightning != null) {
                lightning.moveTo(x + 0.5, y, z + 0.5);
                level.addFreshEntity(lightning);
            }
        }

        level.setDayTime(17000);
    }

    @Override
    public String name() {
        return "lightning_circle";
    }
}
