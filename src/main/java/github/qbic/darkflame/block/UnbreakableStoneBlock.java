package github.qbic.darkflame.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class UnbreakableStoneBlock extends Block {
    public UnbreakableStoneBlock(BlockBehaviour.Properties properties) {
        super(properties.strength(-1, 3600000));
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 15;
    }
}