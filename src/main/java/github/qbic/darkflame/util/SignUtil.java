package github.qbic.darkflame.util;

import github.qbic.darkflame.Brain;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;

public class SignUtil {
    public static BlockState getSign(Block type) {
        int rotation = (int) (Math.random() * 16);

        BlockState signState = type.defaultBlockState().setValue(BlockStateProperties.ROTATION_16, rotation);

        return signState;
    }

    public static SignText getSignText(Component[] lines) {
        return new SignText(
                lines,
                lines,
                DyeColor.BLACK,
                false
        );
    }

    public static SignText getSignText(String[] lines) {
        Component[] text = new Component[4];

        for (int i = 0; i < 4; i++) {
            text[i] = Component.literal(lines[i]);
        }

        return new SignText(
                text,
                text,
                DyeColor.BLACK,
                false
        );
    }

    public static SignText getSignText(List<String> lines) {
        Component[] text = new Component[4];

        for (int i = 0; i < 4; i++) {
            String currentLine = "";
            if (i < lines.size()) {
                currentLine = lines.get(i);
            }
            text[i] = Component.literal(currentLine);
        }

        return new SignText(
                text,
                text,
                DyeColor.BLACK,
                false
        );
    }

    public static SignText getSignText(ArrayList<Component> lines) {
        Component[] text = new Component[4];

        for (int i = 0; i < 4; i++) {
            Component currentLine = lines.get(i);
            if (currentLine == null) {
                currentLine = Component.empty();
            }
            text[i] = currentLine;
        }

        return new SignText(
                text,
                text,
                DyeColor.BLACK,
                false
        );
    }

    public static void placeAndSet(Block type, BlockPos pos, List<String> lines, ServerLevel level) {
        BlockState signState = getSign(type);
        level.setBlock(pos, signState, Block.UPDATE_ALL);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SignBlockEntity signEntity) {
            SignText text = SignUtil.getSignText(lines);

            signEntity.setText(text, true);
            signEntity.setText(text, false);
            signEntity.setWaxed(true);

            signEntity.setChanged();
            level.sendBlockUpdated(pos, signState, signState, Block.UPDATE_NONE);
        }
    }
}
