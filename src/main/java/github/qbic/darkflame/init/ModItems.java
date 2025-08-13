/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.item.ErrNullItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(DarkFlame.MOD_ID);
	public static final DeferredItem<Item> SMILEY_SPAWN_EGG = register("smiley_spawn_egg", properties -> new SpawnEggItem(ModEntities.SMILEY.get(), properties));
	public static final DeferredItem<Item> WRITING = block(ModBlocks.WRITING);
	public static final DeferredItem<Item> ERR_NULL = register("err_null", ErrNullItem::new);
	public static final DeferredItem<Item> WATCHER_SPAWN_EGG = register("watcher_spawn_egg", properties -> new SpawnEggItem(ModEntities.WATCHER.get(), properties));
	public static final DeferredItem<Item> OBSERVER_SPAWN_EGG = register("observer_spawn_egg", properties -> new SpawnEggItem(ModEntities.OBSERVER.get(), properties));
	public static final DeferredItem<Item> UNBREAKABLE_STONE = block(ModBlocks.UNBREAKABLE_STONE, new Item.Properties().fireResistant());
	public static final DeferredItem<Item> SUMMONING_CIRCLE = block(ModBlocks.SUMMONING_CIRCLE, new Item.Properties().fireResistant());

	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
	}
}