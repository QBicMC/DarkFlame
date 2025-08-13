package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.SignUtil;
import github.qbic.darkflame.util.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class FriendSignEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        BlockPos pos = Util.randomAirPosBehindPlayer(level(), target(), 4, 0);
        if (pos == null) return;

        SignUtil.placeAndSet(Blocks.OAK_SIGN, pos, List.of(), (ServerLevel) level());
        target().lookAt(EntityAnchorArgument.Anchor.EYES, pos.getCenter());
    }

    @Override
    public String name() {
        return "friend_sign";
    }
}
