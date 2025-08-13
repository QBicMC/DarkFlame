package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.BeaconEntity;
import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.SignUtil;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SignWithNameEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        BlockPos pos = Util.randomAirPosBehindPlayer(level(), target(), 2, 0);
        ServerLevel level = (ServerLevel) level();
        if (pos == null) return;

        BlockState signState = SignUtil.getSign(Blocks.BIRCH_SIGN);
        level.setBlock(pos, signState, Block.UPDATE_ALL);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SignBlockEntity signEntity) {
            SignText text = SignUtil.getSignText(List.of(Brain.getPlayerInfo(target().getUUID()).name()));

            signEntity.setText(text, true);
            signEntity.setText(text, false);
            signEntity.setWaxed(true);

            signEntity.setChanged();
            level.sendBlockUpdated(pos, signState, signState, Block.UPDATE_NONE);

        }

        ModEvents.FOOT_STEPS_EVENT.execute();
        Util.spawnBeacon(pos, level, () -> {
            Util.playCaveNoise((ServerPlayer) target());
            Util.schedule(() -> {
                if (be instanceof SignBlockEntity signEntity) {
                    SignText newText = SignUtil.getSignText(List.of(
                            "DEAD DEAD DEAD",
                            "DEAD DEAD DEAD",
                            "DEAD DEAD DEAD",
                            "DEAD DEAD DEAD"
                    ));

                    signEntity.setText(newText, true);
                    signEntity.setText(newText, false);

                    signEntity.setChanged();
                    level.sendBlockUpdated(pos, signState, signState, Block.UPDATE_NONE);
                }
            }, 60);
        }, 0.95);
    }

    @Override
    public String name() {
        return "name_sign";
    }
}
