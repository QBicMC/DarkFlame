package github.qbic.darkflame.mixin.client;

import github.qbic.darkflame.ClientBrain;
import github.qbic.darkflame.mixin.client.accessor.PauseScreenAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @Inject(method = "createPauseMenu", at = @At("TAIL"))
    private void darkFlame$createPauseMenu(CallbackInfo ci) {
        Button button = ((PauseScreenAccessor) this).getDisconnectButton();
        if (button != null && ClientBrain.isNoEscape()) {
            button.active = false;
            button.setMessage(Component.translatable("dark_flame.menu.noEscape"));
        }
    }
}
