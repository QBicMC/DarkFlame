package github.qbic.darkflame.block;

import github.qbic.darkflame.init.ModFluids;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TarBlock extends LiquidBlock {
    public TarBlock(BlockBehaviour.Properties properties) {
        super(ModFluids.TAR.get(), properties.mapColor(MapColor.WATER).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 15;
    }
}