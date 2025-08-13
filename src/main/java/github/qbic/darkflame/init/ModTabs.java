/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModTabs {

	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkFlame.MOD_ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARK_FLAME_TAB = REGISTRY.register("dark_flame_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.dark_flame.dark_flame_tab")).icon(() -> new ItemStack(ModItems.ERR_NULL.get())).displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.SMILEY_SPAWN_EGG.get());
				tabData.accept(ModBlocks.WRITING.get().asItem());
				tabData.accept(ModItems.ERR_NULL.get());
				tabData.accept(ModBlocks.UNBREAKABLE_STONE.get().asItem());
				tabData.accept(ModBlocks.SUMMONING_CIRCLE.get().asItem());
			}).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(ModItems.SMILEY_SPAWN_EGG.get());
			tabData.accept(ModItems.WATCHER_SPAWN_EGG.get());
			tabData.accept(ModItems.OBSERVER_SPAWN_EGG.get());
		}
	}
}