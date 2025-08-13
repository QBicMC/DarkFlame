package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.SignUtil;
import github.qbic.darkflame.util.Stringer;
import github.qbic.darkflame.util.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class SignEvent extends ModEvent {
    String[][] messages = {
            // general
            {"get out"},
            {"leave", "leave", "leave", "leave"},
            {"death"},
            {"it is", "coming"},
            {"await", "its", "arrival"},
            // intrusion
            {"friend?"},
            {"are you", "my", "friend,", "${name}"},
            // exclusion
            {"LET", "ME", "OUT"},
            {"help me"},
            {"${ip}"},
            // singularity
            {"join", "us"},
            {"one", "of", "us"}
    };

    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        BlockPos pos = Util.randomAirPosBehindPlayer(level(), target(), 4, 0);
        if (pos == null) return;

        Util.playCaveNoise((ServerPlayer) target());
        SignUtil.placeAndSet(Blocks.OAK_SIGN, pos, List.of(Stringer.replaceVars(messages[(int) (Math.random() * messages.length)], target())), (ServerLevel) level());
        target().lookAt(EntityAnchorArgument.Anchor.EYES, pos.getCenter());
    }

    @Override
    public String name() {
        return "friend_sign";
    }
}
