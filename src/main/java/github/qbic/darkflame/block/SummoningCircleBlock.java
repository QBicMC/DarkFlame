package github.qbic.darkflame.block;

import github.qbic.darkflame.init.ModParticleTypes;
import github.qbic.darkflame.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class SummoningCircleBlock extends Block {
    public static final Random RANDOM = new Random(123456);

    public SummoningCircleBlock(BlockBehaviour.Properties properties) {
        super(properties.strength(-1, 3600000).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).randomTicks().isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public void activate(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 25; i++) {
            double speed = RANDOM.nextDouble() * 0.05;
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double radius = 1.5;

            double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;

            level.sendParticles(ModParticleTypes.SUMMONING_ENERGY.get(), x, y, z, 1, 0, 0, 0, speed);
        }
    }
}