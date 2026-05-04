package com.github.alexthe668.iwannaskate.server.item;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;

public class SkaterHelmetItem extends ArmorItem {

    public SkaterHelmetItem(Holder<ArmorMaterial> material, Properties props) {
        super(material, ArmorItem.Type.HELMET, props);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) IWannaSkateMod.PROXY.getArmorRenderProperties());
    }

    @Nullable
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        if (this == IWSItemRegistry.SPIKED_SKATER_HELMET.get()) {
            return ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "textures/entity/armor/spiked_skater_helmet.png");
        }
        return null;
    }


    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        if (this == IWSItemRegistry.SPIKED_SKATER_HELMET.get()) {
            tooltip.add(Component.translatable("item.iwannaskate.spiked_skater_helmet.desc").withStyle(ChatFormatting.GRAY));
        }
    }
}
