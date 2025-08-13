/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

import github.qbic.darkflame.block.WritingBlock;
import github.qbic.darkflame.DarkFlame;

public class DarkFlameModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(DarkFlame.MOD_ID);
	public static final DeferredBlock<Block> WRITING = register("writing", WritingBlock::new);

	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.of());
	}
}