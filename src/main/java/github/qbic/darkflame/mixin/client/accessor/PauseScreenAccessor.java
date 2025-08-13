package github.qbic.darkflame.mixin.client.accessor;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PauseScreen.class)
public interface PauseScreenAccessor {
    @Accessor("disconnectButton")
    Button getDisconnectButton();

    @Accessor("disconnectButton")
    void setDisconnectButton(Button button);
}
