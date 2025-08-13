package github.qbic.darkflame.client.gui;

import github.qbic.darkflame.init.ModScreens;
import github.qbic.darkflame.inv.ConsentMenu;
import github.qbic.darkflame.networking.C2S.ConsentButtonPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

public class ConsentScreen extends AbstractContainerScreen<ConsentMenu> implements ModScreens.ScreenAccessor {
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private boolean menuStateUpdateActive = false;
    Button button_accept;
    Button button_cancel;

    public ConsentScreen(ConsentMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
    }

    @Override
    public void updateMenuState(int elementType, String name, Object elementState) {
        menuStateUpdateActive = false;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
    // no
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) return true;
        return super.keyPressed(key, b, c);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int currentY = -14;
        currentY = drawMultiline(guiGraphics, "gui.dark_flame.consent.title", 7, currentY);
        currentY += 6;
        currentY = drawMultiline(guiGraphics, "gui.dark_flame.consent.ip", 7, currentY);
        currentY += 6;
        currentY = drawMultiline(guiGraphics, "gui.dark_flame.consent.system_vars", 7, currentY);
        currentY += 6;
        currentY = drawMultiline(guiGraphics, "gui.dark_flame.consent.outside_interaction", 7, currentY);
        currentY += 6;
        drawMultiline(guiGraphics, "gui.dark_flame.consent.note", 7, currentY);
    }

    private int drawMultiline(GuiGraphics guiGraphics, String key, int x, int yStart) {
        String[] lines = Component.translatable(key).getString().split("\n");
        for (String line : lines) {
            guiGraphics.drawString(this.font, line, x, yStart, 0xFFFFFFFF, false);
            yStart += this.font.lineHeight + 1;
        }
        return yStart;
    }

    @Override
    public void init() {
        super.init();
        button_accept = Button.builder(Component.translatable("gui.dark_flame.consent.button_accept"), e -> {
            PacketDistributor.sendToServer(new ConsentButtonPayload(0, x, y, z));
            ConsentButtonPayload.handleButtonAction(entity, 0, x, y, z);
        }).bounds(this.leftPos + 11, this.topPos + 165, 56, 20).build();
        this.addRenderableWidget(button_accept);

        button_cancel = Button.builder(Component.translatable("gui.dark_flame.consent.button_cancel"), e -> {
            PacketDistributor.sendToServer(new ConsentButtonPayload(1, x, y, z));
            ConsentButtonPayload.handleButtonAction(entity, 1, x, y, z);
        }).bounds(this.leftPos + 110, this.topPos + 165, 56, 20).build();
        this.addRenderableWidget(button_cancel);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
