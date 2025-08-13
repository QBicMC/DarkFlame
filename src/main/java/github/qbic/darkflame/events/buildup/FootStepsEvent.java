package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArmorStandEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        Util.spawnEntityBehindPlayer((ServerPlayer) target(), EntityType.ARMOR_STAND, 4, 1);
    }

    @Override
    public String name() {
        return "armor_stand";
    }
}
