package github.qbic.darkflame.util;

import github.qbic.darkflame.DarkFlame;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureUtil {
    public static boolean inStructure(ServerPlayer player, ResourceKey<Structure> structureKey) {
        ServerLevel level = player.serverLevel();
        BlockPos pos = player.blockPosition();

        StructureManager structureManager = level.structureManager();
        Structure structure = level.registryAccess()
                .lookupOrThrow(Registries.STRUCTURE)
                .getValue(structureKey);

        if (structure == null) return false;

        StructureStart start = structureManager.getStructureAt(pos, structure);

        return start != null && start.isValid() && start.getBoundingBox().isInside(pos);
    }

    public static final class Structures {
        public static final ResourceKey<Structure> SUMMONING_CIRCLE = ResourceKey.create(
                Registries.STRUCTURE,
                ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "summoning_circle")
        );
    }
}
