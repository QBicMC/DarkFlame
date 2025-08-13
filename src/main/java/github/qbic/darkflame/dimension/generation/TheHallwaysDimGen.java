package github.qbic.darkflame.dimension.generation;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.qbic.darkflame.dimension.ModDimGenerator;
import github.qbic.darkflame.init.ModBlocks;
import github.qbic.darkflame.util.MathUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;

public class TheHallwaysDimGen extends ModDimGenerator {
    public TheHallwaysDimGen(BiomeSource biomeSource) {
        super(biomeSource);
    }

    public static final MapCodec<TheHallwaysDimGen> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource)
            ).apply(instance, TheHallwaysDimGen::new)
    );

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    protected BlockState generateAtXYZ(int chunkX, int chunkZ, int worldX, int y, int worldZ, double chunkRnd) {
        int modWorldX = MathUtil.betterModulo(worldX, 7);
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState stone = ModBlocks.UNBREAKABLE_STONE.get().defaultBlockState();

        if (chunkRnd < 0.05) {
            if (((modWorldX == 2 || modWorldX == 3 || modWorldX == 4) && MathUtil.betterModulo(y, 14) == 0) || chunkX == 0) return stone;
            return air;
        }

        if (modWorldX == 0 || modWorldX == 1 || modWorldX == 5 || modWorldX == 6) return stone;
        switch (MathUtil.betterModulo(y, 7)) {
            case 1, 3: return air;
            case 2: {
                if (modWorldX == 2) return redstoneTorchFacing("east");
                if (modWorldX == 4) return redstoneTorchFacing("west");
                return air;
            }
            default: return stone;
        }
    }

    public BlockState redstoneTorchFacing(String dir) {
        BlockState torch = Blocks.REDSTONE_WALL_TORCH.defaultBlockState();
        return switch (dir) {
            case "north" -> torch.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
            case "south" -> torch.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
            case "east" -> torch.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST);
            case "west" -> torch.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST);
            default -> torch.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
        };
    }
}
