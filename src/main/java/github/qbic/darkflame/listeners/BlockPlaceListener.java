package github.qbic.darkflame.listeners;

import github.qbic.darkflame.entity.SmilerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber
public class BlockPlaceListener {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        BlockState state = event.getPlacedBlock();
        if (state.getLightEmission() > 0) {
            Level level = (Level) event.getLevel();
            BlockPos pos = event.getPos();

            List<SmilerEntity> nearby = level.getEntitiesOfClass(
                    SmilerEntity.class,
                    new AABB(pos).inflate(25)
            );

            if (!nearby.isEmpty()) {
                event.setCanceled(true);
            }
        }
    }
}
