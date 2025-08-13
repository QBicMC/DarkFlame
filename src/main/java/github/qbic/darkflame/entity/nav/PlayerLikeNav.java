package github.qbic.darkflame.entity.nav;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.*;

// i hope this was worth it
public class PlayerLikeNav extends GroundPathNavigation {
    private static final Vec3 JUMP_VECTOR = new Vec3(0.0, 0.5, 0.0);
    private final Mob mob;
    private int jumpCooldown = 0;
    private BlockPos targetPos;
    private FakePlayer fakePlayer;
    private final Queue<BlockPos> blocksToMine = new LinkedList<>();
    private BlockPos currentlyMining;
    private float miningProgress = 0.0f;
    private Path lastPath;

    public PlayerLikeNav(Mob mob, Level level) {
        super(mob, level);
        this.mob = mob;
        if (level instanceof ServerLevel serverLevel) {
            this.fakePlayer = FakePlayerFactory.getMinecraft(serverLevel);
        }
    }

    @Override
    public void tick() {
        super.tick();

        Path currentPath = getPath();
        if (currentPath != lastPath) {
            updateMiningQueue(currentPath);
            lastPath = currentPath;
        }

        if (currentlyMining != null) {
            if (continueMining()) {
                return;
            }
        }

        if (!blocksToMine.isEmpty() && currentlyMining == null) {
            startMiningNextBlock();
        }

        if (checkJump()) {
            jump();
        }

        if (this.mob.isSprinting()) {
            this.setSpeedModifier(1.3f);
        }

        if (isDone()) {
            mob.setSprinting(false);
            blocksToMine.clear();
            currentlyMining = null;
            lastPath = null;
        }
    }

    private void updateMiningQueue(Path path) {
        blocksToMine.clear();
        currentlyMining = null;
        miningProgress = 0.0f;

        if (path == null) return;

        for (int i = 0; i < path.getNodeCount(); i++) {
            Node node = path.getNode(i);
            BlockPos pos = new BlockPos(node.x, node.y, node.z);
            BlockState state = level.getBlockState(pos);

            if (!state.isAir() && !state.canBeReplaced() && needsToMine(pos)) {
                blocksToMine.offer(pos);
            }
        }
    }

    private boolean needsToMine(BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (state.isAir() || state.canBeReplaced()) {
            return false;
        }

        if (isUnbreakable(state)) {
            return false;
        }

        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);

        boolean currentSolid = state.isSolid();
        boolean aboveSolid = aboveState.isSolid() && !aboveState.isAir();

        return currentSolid || aboveSolid;
    }

    private boolean isUnbreakable(BlockState blockState) {
        return blockState.is(Blocks.BEDROCK) ||
                blockState.is(Blocks.BARRIER) ||
                blockState.is(Blocks.COMMAND_BLOCK) ||
                blockState.is(Blocks.STRUCTURE_BLOCK) ||
                blockState.is(Blocks.END_PORTAL) ||
                blockState.is(Blocks.END_PORTAL_FRAME) ||
                blockState.is(Blocks.NETHER_PORTAL) ||
                blockState.getDestroySpeed(mob.level(), new BlockPos(0, 0, 0)) < 0;
    }

    private void startMiningNextBlock() {
        BlockPos pos = blocksToMine.poll();
        if (pos != null && !level.getBlockState(pos).isAir()) {
            currentlyMining = pos;
            miningProgress = 0.0f;

            if (fakePlayer != null) {
                switchToAppropriateToolFor(pos);
            }
        }
    }

    private boolean continueMining() {
        if (currentlyMining == null) return false;

        BlockState blockState = level.getBlockState(currentlyMining);
        if (blockState.isAir()) {
            currentlyMining = null;
            miningProgress = 0.0f;
            return false;
        }

        float miningSpeed = calculateMiningSpeed(blockState, currentlyMining);
        miningProgress += miningSpeed;

        float requiredProgress = blockState.getDestroySpeed(level, currentlyMining) * 20.0f;
        if (miningProgress >= requiredProgress) {
            level.destroyBlock(currentlyMining, true, mob);
            currentlyMining = null;
            miningProgress = 0.0f;
            return false;
        }

        return true;
    }

    private void switchToAppropriateToolFor(BlockPos pos) {
        if (fakePlayer == null) return;

        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        ItemStack bestTool = getBestToolForBlock(block);
        if (!bestTool.isEmpty()) {
            fakePlayer.setItemSlot(EquipmentSlot.MAINHAND, bestTool);
            if (mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() ||
                    !isToolEffectiveFor(mob.getItemBySlot(EquipmentSlot.MAINHAND), blockState)) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, bestTool.copy());
            }
        }
    }

    private float calculateMiningSpeed(BlockState blockState, BlockPos pos) {
        ItemStack tool = mob.getItemBySlot(EquipmentSlot.MAINHAND);

        float baseSpeed = 1.0f;
        if (tool.getItem() instanceof DiggerItem diggerItem) {
            if (diggerItem.isCorrectToolForDrops(tool, blockState)) {
                baseSpeed = diggerItem.getDestroySpeed(tool, blockState);
            }
        }

//        int efficiency = tool.getEnchantmentLevel(Enchantments.EFFICIENCY);
//        if (efficiency > 0) {
//            baseSpeed += efficiency * efficiency + 1;
//        }

        return baseSpeed / 20.0f;
    }

    private boolean isToolEffectiveFor(ItemStack tool, BlockState blockState) {
        if (tool.getItem() instanceof DiggerItem diggerItem) {
            return diggerItem.isCorrectToolForDrops(tool, blockState);
        }
        return false;
    }

    private void jump() {
        if (this.mob.onGround()) {
            this.mob.addDeltaMovement(JUMP_VECTOR);
            this.mob.hasImpulse = true;
        }
    }

    private boolean checkJump() {
        if (!(mob.onGround()
                && !mob.onClimbable()
                && mob.isSprinting()
                && !mob.isInWater()
                && !mob.isInLava()
                && !mob.isPassenger()
                && !isDone()
                && !isStuck()
                && getPath() != null
                && mob.getY() == getPath().getNextNode().y)
        ) {
            return false;
        }

        if (jumpCooldown <= 0) {
            jumpCooldown = 5;
        } else {
            jumpCooldown--;
            return false;
        }

        Vec3 rawLook = mob.getLookAngle();
        Vec3 flatLook = new Vec3(rawLook.x, 0, rawLook.z).normalize();

        Vec3 basePos = mob.position();
        Vec3 feetPos = basePos.add(0, 0.3, 0);
        Vec3 headPos = basePos.add(0, mob.getBbHeight(), 0);

        Level level = mob.level();

        HitResult feetHit = level.clip(new ClipContext(
                feetPos,
                feetPos.add(flatLook.scale(3)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                mob
        ));

        HitResult headHit = level.clip(new ClipContext(
                headPos,
                headPos.add(flatLook.scale(3)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                mob
        ));

        double feetDist = feetHit.getLocation().distanceTo(feetPos);
        double headDist = headHit.getLocation().distanceTo(headPos);

        boolean feetCheck = feetDist > 2 || feetDist < 1.5;
        boolean headCheck = headDist > 1.5;

        return feetCheck && headCheck;
    }

    @Override
    protected PathFinder createPathFinder(int maxNodes) {
        this.nodeEvaluator = new MiningAwareNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, maxNodes);
    }

    public enum Behavior {
        IDLE,
        FOLLOW,
        FOLLOW_FAST,
        CHASE
    }

    public class MiningAwareNodeEvaluator extends WalkNodeEvaluator {

        private static final Map<Block, ItemStack> TOOL_CACHE = new HashMap<>();
        private static final float BASE_MINING_COST = 10.0f;
        private static final float MOVEMENT_COST = 1.0f;
        private static final float MAX_MINING_TIME = 200.0f;

        static {
            initializeToolCache();
        }

        private static void initializeToolCache() {
            TOOL_CACHE.put(Blocks.STONE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.COBBLESTONE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.DEEPSLATE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.IRON_ORE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.COAL_ORE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.COPPER_ORE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.GOLD_ORE, createTool(Items.IRON_PICKAXE));
            TOOL_CACHE.put(Blocks.DIAMOND_ORE, createTool(Items.IRON_PICKAXE));

            TOOL_CACHE.put(Blocks.OAK_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.BIRCH_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.SPRUCE_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.JUNGLE_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.ACACIA_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.DARK_OAK_LOG, createTool(Items.IRON_AXE));
            TOOL_CACHE.put(Blocks.OAK_PLANKS, createTool(Items.IRON_AXE));

            TOOL_CACHE.put(Blocks.DIRT, createTool(Items.IRON_SHOVEL));
            TOOL_CACHE.put(Blocks.GRASS_BLOCK, createTool(Items.IRON_SHOVEL));
            TOOL_CACHE.put(Blocks.SAND, createTool(Items.IRON_SHOVEL));
            TOOL_CACHE.put(Blocks.GRAVEL, createTool(Items.IRON_SHOVEL));
            TOOL_CACHE.put(Blocks.CLAY, createTool(Items.IRON_SHOVEL));
            TOOL_CACHE.put(Blocks.SOUL_SAND, createTool(Items.IRON_SHOVEL));
        }

        private static ItemStack createTool(Item item) {
            ItemStack tool = new ItemStack(item);
//            tool.enchant(efficiency, 3);
            return tool;
        }

        @Override
        public Node getStart() {
            return super.getStart();
        }

        @Override
        public int getNeighbors(Node[] neighbors, Node currentNode) {
            int neighborCount = 0;

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = new BlockPos(
                        currentNode.x + direction.getStepX(),
                        currentNode.y + direction.getStepY(),
                        currentNode.z + direction.getStepZ()
                );

                Node neighborNode = getNodeAt(neighborPos.getX(), neighborPos.getY(), neighborPos.getZ());
                if (neighborNode != null && neighborCount < neighbors.length) {
                    neighbors[neighborCount++] = neighborNode;
                }
            }

            return neighborCount;
        }

        private Node getNodeAt(int x, int y, int z) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState blockState = mob.level().getBlockState(pos);

            if (y < mob.level().getMinY() || y >= mob.level().getMaxY()) {
                return null;
            }

            Node node = new Node(x, y, z);
            PathType pathType = getBlockPathType(blockState, pos);

            float cost = calculateMovementCost(blockState, pos, pathType);
            if (cost < 0) {
                return null;
            }

            node.costMalus = cost;
            node.type = pathType;

            return node;
        }

        private PathType getBlockPathType(BlockState blockState, BlockPos pos) {
            Block block = blockState.getBlock();

            if (blockState.isAir() || blockState.canBeReplaced()) {
                return PathType.OPEN;
            }

            if (blockState.is(Blocks.WATER)) {
                return PathType.WATER;
            }

            if (blockState.is(Blocks.LAVA)) {
                return PathType.LAVA;
            }

            if (isUnbreakable(blockState)) {
                return PathType.BLOCKED;
            }

            if (canMineBlock(blockState)) {
                return PathType.WALKABLE;
            }

            return PathType.BLOCKED;
        }

        private boolean canMineBlock(BlockState blockState) {
            if (isUnbreakable(blockState)) {
                return false;
            }

            float hardness = blockState.getDestroySpeed(mob.level(), new BlockPos(0, 0, 0));
            return hardness >= 0 && hardness <= 50.0f; // no obsidian
        }

        private float calculateMovementCost(BlockState blockState, BlockPos pos, PathType pathType) {
            switch (pathType) {
                case OPEN:
                    return MOVEMENT_COST;
                case WATER:
                    return MOVEMENT_COST * 2.0f; // swimming slow
                case LAVA:
                    return MOVEMENT_COST * 20.0f; // dangerous and slow
                case BLOCKED:
                    return -1.0f;
                case WALKABLE:
                    // calculate mining cost
                    return calculateMiningCost(blockState, pos);
                default:
                    return MOVEMENT_COST;
            }
        }

        private float calculateMiningCost(BlockState blockState, BlockPos pos) {
            Block block = blockState.getBlock();

            ItemStack tool = getBestToolForBlock(block);

            float hardness = blockState.getDestroySpeed(mob.level(), pos);
            if (hardness < 0) {
                return -1.0f; // unbreakable
            }

            float miningSpeed = calculateToolMiningSpeed(tool, blockState);
            float miningTime = Math.max(1.0f, hardness * 20.0f / miningSpeed);

            miningTime = Math.min(miningTime, MAX_MINING_TIME);

            // addBlockToMine(pos);

            return MOVEMENT_COST + (miningTime * BASE_MINING_COST / 20.0f);
        }

        private float calculateToolMiningSpeed(ItemStack tool, BlockState blockState) {
            if (tool.isEmpty()) {
                return 1.0f;
            }

            float baseSpeed = 1.0f;
            if (tool.getItem() instanceof DiggerItem diggerItem) {
                if (diggerItem.isCorrectToolForDrops(tool, blockState)) {
                    baseSpeed = diggerItem.getDestroySpeed(tool, blockState);
                }
            }

//            int efficiency = tool.getEnchantmentLevel(Enchantments.EFFICIENCY);
//            if (efficiency > 0) {
//                baseSpeed += efficiency * efficiency + 1;
//            }

            return baseSpeed;
        }

        @Override
        public PathType getPathType(PathfindingContext context, int x, int y, int z) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState blockState = level.getBlockState(pos);
            return getBlockPathType(blockState, pos);
        }
    }

    private static ItemStack getBestToolForBlock(Block block) {
        ItemStack cachedTool = MiningAwareNodeEvaluator.TOOL_CACHE.get(block);
        if (cachedTool != null) {
            return cachedTool.copy();
        }

        BlockState state = block.defaultBlockState();
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return createEnchantedTool(Items.IRON_PICKAXE);
        } else if (state.is(BlockTags.MINEABLE_WITH_AXE)) {
            return createEnchantedTool(Items.IRON_AXE);
        } else if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return createEnchantedTool(Items.IRON_SHOVEL);
        } else if (state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return createEnchantedTool(Items.IRON_HOE);
        }

        return createEnchantedTool(Items.IRON_PICKAXE);
    }

    private static ItemStack createEnchantedTool(Item item) {
        ItemStack tool = new ItemStack(item);
//        tool.enchant(Enchantments.EFFICIENCY, 3);
        return tool;
    }
}