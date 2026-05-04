package com.github.alexthe668.iwannaskate.server;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;

public class CommonProxy {
    public void init(IEventBus modEventBus) {
    }

    public void clientInit() {
    }

    public Object getISTERProperties() {
        return null;
    }

    public Object getArmorRenderProperties() {
        return null;
    }

    public void setHoverItem(ItemStack stack){}

    public Player getClientSidePlayer() {
        return null;
    }

    public boolean isKeyDown(int keyType) {
        return false;
    }

    public void onEntityStatus(Entity entity, byte updateKind) {
    }

    public void reloadConfig() {
    }

    public void openBookGUI(ItemStack itemStackIn) {
    }
}
