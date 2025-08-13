/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import github.qbic.darkflame.DarkFlame;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DarkFlameModTabs {

	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkFlame.MOD_ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARK_FLAME_TAB = REGISTRY.register("dark_flame_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.dark_flame.dark_flame_tab")).icon(() -> new ItemStack(DarkFlameModItems.ERR_NULL.get())).displayItems((parameters, tabData) -> {
				tabData.accept(DarkFlameModItems.SMILEY_SPAWN_EGG.get());
				tabData.accept(DarkFlameModBlocks.WRITING.get().asItem());
				tabData.accept(DarkFlameModItems.ERR_NULL.get());
			}).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(DarkFlameModItems.SMILEY_SPAWN_EGG.get());
			tabData.accept(DarkFlameModItems.WATCHER_SPAWN_EGG.get());
			tabData.accept(DarkFlameModItems.OBSERVER_SPAWN_EGG.get());
		}
	}
}