package com.github.alexthe668.iwannaskate.server.item;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;

public class DyeableHatItem extends ArmorItem {

    private String type;
    private int defaultColor;

    public DyeableHatItem(Holder<ArmorMaterial> material, String type, int defaultColor, Properties props) {
        super(material, Type.HELMET, props);
        this.type = type;
        this.defaultColor = defaultColor;
    }

    public int getColor(ItemStack itemStack) {
        return DyedItemColor.getOrDefault(itemStack, defaultColor);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) IWannaSkateMod.PROXY.getArmorRenderProperties());
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if(type != null && type.equals("overlay")){
            if(this == IWSItemRegistry.SKATER_CAP.get()){
                return IWannaSkateMod.MODID + ":textures/entity/armor/skater_cap_rim.png";
            }
            return IWannaSkateMod.MODID + ":textures/entity/armor/color_layer.png";
        }
        return IWannaSkateMod.MODID + ":textures/entity/armor/" + this.type + ".png";
    }
}
