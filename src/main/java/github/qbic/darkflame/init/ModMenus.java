package github.qbic.darkflame.init;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.inv.ConsentMenu;
import github.qbic.darkflame.networking.MenuStateUpdatePayload;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.Map;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, DarkFlame.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<ConsentMenu>> CONSENT_GUI = REGISTRY.register("consent_gui", () -> IMenuTypeExtension.create(ConsentMenu::new));

    public interface MenuAccessor {
        Map<String, Object> getMenuState();

        Map<Integer, Slot> getSlots();

        default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
            getMenuState().put(elementType + ":" + name, elementState);
            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdatePayload(elementType, name, elementState));
            } else if (player.level().isClientSide) {
                if (Minecraft.getInstance().screen instanceof ModScreens.ScreenAccessor accessor && needClientUpdate)
                    accessor.updateMenuState(elementType, name, elementState);
                PacketDistributor.sendToServer(new MenuStateUpdatePayload(elementType, name, elementState));
            }
        }

        default <T> T getMenuState(int elementType, String name, T defaultValue) {
            try {
                return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
            } catch (ClassCastException e) {
                return defaultValue;
            }
        }
    }
}