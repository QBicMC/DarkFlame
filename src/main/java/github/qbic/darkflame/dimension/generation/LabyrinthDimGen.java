package github.qbic.darkflame.dimension.generation;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class CrawlDimGen extends ChunkGenerator {

    public static final Block FILLER_BLOCK = Blocks.STONE;
    public static final MapCodec<CrawlDimGen> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource)
            ).apply(instance, CrawlDimGen::new)
    );

    public CrawlDimGen(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk) { }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        ChunkPos pos = chunk.getPos();
        int worldX = pos.getRegionX();
        int worldZ = pos.getRegionZ();
        long seed = worldX * 341873128712L + worldZ * 132897987541L;
        Random rand = new Random(1337L + seed);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        int y = 64;

        boolean[][] carved = new boolean[17][17];
        int startX = rand.nextInt(8) * 2 + 1;
        int startZ = rand.nextInt(8) * 2 + 1;
        generateMaze(carved, rand, startX, startZ);

        for (int i = 0; i < 8; i++) {
            // i gave up and just did this
            if (Math.random() < 0.5) carved[0][rand.nextInt(16)] = true;
            if (Math.random() < 0.5) carved[16][rand.nextInt(16)] = true;
            if (Math.random() < 0.5) carved[rand.nextInt(16)][0] = true;
            if (Math.random() < 0.5) carved[rand.nextInt(16)][16] = true;
        }

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                BlockState fill = FILLER_BLOCK.defaultBlockState();
                BlockState air = carved[dx][dz] ? Blocks.AIR.defaultBlockState() : fill;
                if (air.isAir() && rand.nextDouble() < 0.02) {
                    air = Blocks.REDSTONE_TORCH.defaultBlockState();
                }
                mut.set(dx, y, dz);
                chunk.setBlockState(mut, air, false);
                chunk.setBlockState(mut.below(), fill, false);
                chunk.setBlockState(mut.above(), fill, false);
            }
        }

        if (pos.x == 0 && pos.z == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(dx, y, dz);
                    chunk.setBlockState(p, Blocks.AIR.defaultBlockState(), false);
                    chunk.setBlockState(p.below(), FILLER_BLOCK.defaultBlockState(), false);
                    chunk.setBlockState(p.above(), FILLER_BLOCK.defaultBlockState(), false);
                }
            }
        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) { }

    @Override
    public int getGenDepth() {
        return 0;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    private void generateMaze(boolean[][] carved, Random rand, int x, int z) {
        carved[x][z] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        shuffleArray(dirs, rand);
        for (int[] d : dirs) {
            int nx = x + d[0] * 2;
            int nz = z + d[1] * 2;
            if (nx > 0 && nx < 16 && nz > 0 && nz < 16 && !carved[nx][nz]) {
                carved[x + d[0]][z + d[1]] = true;
                generateMaze(carved, rand, nx, nz);
            }
        }
    }

    private void shuffleArray(int[][] array, Random rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return 64;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        BlockState[] states = new BlockState[3];
        states[0] = FILLER_BLOCK.defaultBlockState();
        states[1] = Blocks.AIR.defaultBlockState();
        states[2] = FILLER_BLOCK.defaultBlockState();
        return new NoiseColumn(63, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {
        info.add("Crawl Dimension");
    }
}
