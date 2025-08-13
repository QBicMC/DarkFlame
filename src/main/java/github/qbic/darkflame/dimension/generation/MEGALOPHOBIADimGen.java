package github.qbic.darkflame.dimension.generation;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.qbic.darkflame.dimension.ModDimGenerator;
import github.qbic.darkflame.init.ModBlocks;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class MEGALOPHOBIADimGen extends ModDimGenerator {
    public static final MapCodec<MEGALOPHOBIADimGen> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource)
            ).apply(instance, MEGALOPHOBIADimGen::new)
    );

    public MEGALOPHOBIADimGen(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected BlockState generateAtXYZ(int chunkX, int chunkZ, int worldX, int y, int worldZ, double chunkRnd) {
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState filler = air;
        BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState dirt = Blocks.DIRT.defaultBlockState();

        if (chunkRnd < 0.1) {
            filler = Blocks.BEDROCK.defaultBlockState();
        } else if (chunkRnd < 0.3) {
            filler = ModBlocks.UNBREAKABLE_STONE.get().defaultBlockState();
        }

        if (y == 0) return grass;
        if (y < 0) return dirt;
        if (chunkX == 0 || chunkX == 15 || chunkZ == 0 || chunkZ == 15) return air;
        return filler;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}
