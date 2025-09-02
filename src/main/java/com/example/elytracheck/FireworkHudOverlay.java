package com.example.elytracheck;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class FireworkHudOverlay implements HudRenderCallback {
    private static final Identifier FIREWORK_ICON = new Identifier("elytracheck", "textures/item/firework_rocket.png");
    private static final Identifier TORCH_ICON = new Identifier("elytracheck", "textures/item/torch.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int x = client.getWindow().getScaledWidth() / 2 - 91; // left of hotbar
        int y = client.getWindow().getScaledHeight() - 39;    // above hotbar

        if (ElytraCheckMod.fireworksDisabled) {
            RenderSystem.enableBlend();
            context.drawTexture(FIREWORK_ICON, x, y - 20, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
        }
        if (ElytraCheckMod.torchesDisabled) {
            RenderSystem.enableBlend();
            context.drawTexture(TORCH_ICON, x + 20, y - 20, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
        }
    }
}
