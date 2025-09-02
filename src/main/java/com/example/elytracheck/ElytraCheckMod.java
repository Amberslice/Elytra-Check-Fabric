package com.example.elytracheck;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ElytraCheckMod implements ClientModInitializer {
    public static boolean fireworksDisabled = false;
    public static boolean torchesDisabled = false;

    private static KeyBinding toggleFireworksKey;
    private static KeyBinding toggleTorchesKey;

    @Override
    public void onInitializeClient() {
        // Register keybinds (default: Shift+Z and Shift+X)
        toggleFireworksKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.elytracheck.toggle_fireworks",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category.elytracheck"
        ));
        toggleTorchesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.elytracheck.toggle_torches",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.elytracheck"
        ));

        // Tick handler
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleFireworksKey.wasPressed()) {
                fireworksDisabled = !fireworksDisabled;
                client.player.sendMessage(Text.literal("§eFireworks: " + (fireworksDisabled ? "§cDisabled" : "§aEnabled")), true);
            }
            while (toggleTorchesKey.wasPressed()) {
                torchesDisabled = !torchesDisabled;
                client.player.sendMessage(Text.literal("§eTorches: " + (torchesDisabled ? "§cDisabled" : "§aEnabled")), true);
            }
        });
    }

    public static boolean canUseFirework(ItemStack stack) {
        return !(fireworksDisabled && stack.isOf(Items.FIREWORK_ROCKET));
    }

    public static boolean canPlaceTorch(ItemStack stack) {
        return !(torchesDisabled && (stack.isOf(Items.TORCH) || stack.isOf(Items.SOUL_TORCH)));
    }

    public static boolean hasElytraEquipped(ItemStack chestItem) {
        return chestItem.isOf(Items.ELYTRA);
    }
}
