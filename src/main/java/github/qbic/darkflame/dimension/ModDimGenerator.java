package github.qbic.darkflame.dimension;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ModDimGenerator extends ChunkGenerator {
    public ModDimGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        double chunkRnd = Math.random();
        BlockPos chunkPos = chunk.getPos().getWorldPosition();
        int baseX = chunkPos.getX();
        int baseZ = chunkPos.getZ();
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(0, 0, 0);

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                for (int y = chunk.getMinY(); y < chunk.getMaxY(); y++) {
                    int worldX = baseX + dx;
                    int worldZ = baseZ + dz;
                    mut.set(dx, y, dz);
                    chunk.setBlockState(mut, generateAtXYZ(dx, dz, worldX, y, worldZ, chunkRnd), false);
                }
            }
        }
    }

    protected BlockState generateAtXYZ(int chunkX, int chunkZ, int worldX, int y, int worldZ, double chunkRnd) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk) { }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) { }

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return -64;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return 64;
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {
        info.add("§cerror.dim_not_found");
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        BlockState[] states = new BlockState[getGenDepth()];
        for (int i = getMinY(); i < getMinY() + getGenDepth(); i++) {
            states[i] = (generateAtXYZ(0, 0, 0, i, 0, 1));
        }
        return new NoiseColumn(getMinY(), states);
    }

    public enum ChunkType {NORMAL}
}
