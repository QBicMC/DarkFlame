package github.qbic.darkflame.networking;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.HorrorEntity;
import github.qbic.darkflame.inv.ConsentMenu;
import github.qbic.darkflame.networking.C2S.ChangePerspectivePayload;
import github.qbic.darkflame.networking.C2S.ConsentScreenOpenRequest;
import github.qbic.darkflame.networking.C2S.PlayerInfoPayload;
import github.qbic.darkflame.networking.S2C.VolumePayload;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerPayloadHandler {

    public static void handleVolumeDataOnMain(final VolumePayload data, final IPayloadContext context) {
    }

    public static void handleConsentRequest(final ConsentScreenOpenRequest data, final IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("ConsentGUI");
            }

            @Override
            public boolean shouldTriggerClientSideContainerClosingOnOpen() {
                return false;
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return new ConsentMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(player.getOnPos()));
            }
        }, player.getOnPos());
    }

    public static void handlePlayerInfo(final PlayerInfoPayload data, final IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();
        Brain.setPlayerInfo(player.getUUID(), data);
    }

    public static void handleChangePerspective(final ChangePerspectivePayload data, final IPayloadContext context) {
        if (data.perspective() == 1) return;

        ServerPlayer player = (ServerPlayer) context.player();
        AABB box = new AABB(player.getOnPos()).inflate(15);
        List<LivingEntity> mobs = player.level().getEntitiesOfClass(
                LivingEntity.class,
                box,
                e -> (e instanceof HorrorEntity)
        );

        mobs.forEach(mob -> ((HorrorEntity) mob).onSeenThirdPerson(context.player()));
    }
}