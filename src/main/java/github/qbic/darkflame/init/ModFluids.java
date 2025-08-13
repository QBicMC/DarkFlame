package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.fluid.TarFluid;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, DarkFlame.MOD_ID);
    public static final DeferredHolder<Fluid, FlowingFluid> TAR = REGISTRY.register("tar", () -> new TarFluid.Source());
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_TAR = REGISTRY.register("flowing_tar", () -> new TarFluid.Flowing());

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class FluidsClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(TAR.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_TAR.get(), RenderType.translucent());
        }
    }
}