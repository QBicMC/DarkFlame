package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.init.ModItems;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class GivePlayerItemEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        Util.playCaveNoise((ServerPlayer) target());
        target().getInventory().add(new ItemStack(ModItems.ERR_NULL.asItem()));
    }

    @Override
    public String name() {
        return "give_item";
    }
}
