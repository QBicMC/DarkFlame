package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.fluid.types.TarFluidType;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, DarkFlame.MOD_ID);
    public static final DeferredHolder<FluidType, FluidType> TAR_TYPE = REGISTRY.register("tar", () -> new TarFluidType());
}