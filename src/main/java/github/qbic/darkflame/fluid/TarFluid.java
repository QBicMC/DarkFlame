package github.qbic.darkflame.fluid;

import github.qbic.darkflame.init.ModBlocks;
import github.qbic.darkflame.init.ModFluidTypes;
import github.qbic.darkflame.init.ModFluids;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class TarFluid extends BaseFlowingFluid {
    public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> ModFluidTypes.TAR_TYPE.get(), () -> ModFluids.TAR.get(), () -> ModFluids.FLOWING_TAR.get()).explosionResistance(100f)
            .tickRate(40).levelDecreasePerBlock(2).slopeFindDistance(1).block(() -> (LiquidBlock) ModBlocks.TAR.get());

    private TarFluid() {
        super(PROPERTIES);
    }

    public static class Source extends TarFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends TarFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}