package github.qbic.darkflame.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated(forRemoval = true)
public class BaseScanner {
    public interface BlockMatcher {
        boolean matches(BlockState state);
    }

    public static class BlockEntry implements BlockMatcher {
        private final Block block;

        public BlockEntry(Block block) {
            this.block = block;
        }

        @Override
        public boolean matches(BlockState state) {
            return state.is(block);
        }
    }

    public static class TagEntry implements BlockMatcher {
        private final TagKey<Block> tag;

        public TagEntry(TagKey<Block> tag) {
            this.tag = tag;
        }

        @Override
        public boolean matches(BlockState state) {
            return state.is(tag);
        }
    }

    private static final Set<BlockMatcher> baseMatchers = new HashSet<>(List.of(
            new BlockEntry(Blocks.COBBLESTONE),
            new BlockEntry(Blocks.CRAFTING_TABLE),
            new BlockEntry(Blocks.FURNACE),
            new BlockEntry(Blocks.CHEST),
            new BlockEntry(Blocks.TORCH),
            new TagEntry(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("planks")))
    ));

    private static BlockPos basePos = null;

    public static void scanForBase(ServerLevel level, ServerPlayer player, int radius, int threshold) {
        BlockPos origin = player.blockPosition();
        int count = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    pos.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    BlockState state = level.getBlockState(pos);

                    for (BlockMatcher matcher : baseMatchers) {
                        if (matcher.matches(state)) {
                            count++;
                            break;
                        }
                    }

                    if (count >= threshold) {
                        basePos = origin;
                        return;
                    }
                }
            }
        }
    }

    public static @Nullable BlockPos getBasePos() {
        return basePos;
    }

    public static void addBaseBlock(Block block) {
        baseMatchers.add(new BlockEntry(block));
    }

    public static void addBaseTag(ResourceLocation tagId) {
        TagKey<Block> tag = TagKey.create(Registries.BLOCK, tagId);
        baseMatchers.add(new TagEntry(tag));
    }

    public static void removeBaseBlock(Block block) {
        baseMatchers.removeIf(m -> m instanceof BlockEntry b && b.block.equals(block));
    }

    public static void removeBaseTag(ResourceLocation tagId) {
        baseMatchers.removeIf(m -> m instanceof TagEntry t && t.tag.location().equals(tagId));
    }
}
