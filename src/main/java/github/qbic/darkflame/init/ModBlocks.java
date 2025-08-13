/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.block.SummoningCircleBlock;
import github.qbic.darkflame.block.TarBlock;
import github.qbic.darkflame.block.UnbreakableStoneBlock;
import github.qbic.darkflame.block.WritingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(DarkFlame.MOD_ID);
	public static final DeferredBlock<Block> WRITING = register("writing", WritingBlock::new);
	public static final DeferredBlock<Block> TAR = register("tar", TarBlock::new);
	public static final DeferredBlock<Block> UNBREAKABLE_STONE = register("unbreakable_stone", UnbreakableStoneBlock::new);
	public static final DeferredBlock<Block> SUMMONING_CIRCLE = register("summoning_circle", SummoningCircleBlock::new);

	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.of());
	}
}