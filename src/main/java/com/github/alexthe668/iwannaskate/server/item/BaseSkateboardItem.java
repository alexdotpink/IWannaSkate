package com.github.alexthe668.iwannaskate.server.item;

import com.github.alexthe666.citadel.item.ItemWithHoverAnimation;
import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class BaseSkateboardItem extends Item implements ItemWithHoverAnimation, CustomTabBehavior {

    public BaseSkateboardItem(Properties properties) {
        super(properties);
    }

    public void fillItemCategory(CreativeModeTab.Output contents) {
        SkateboardMaterials.getSkateboardMaterials().forEach(item -> addBoardToTab(contents, BaseSkateboardItem.this, item));
    }

    public void addBoardToTab(CreativeModeTab.Output contents, Item board, Item material) {
        ItemStack stack = new ItemStack(board);
        SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(material));
        SkateboardData.setStackData(stack, data);
        contents.accept(stack);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) IWannaSkateMod.PROXY.getISTERProperties());
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        SkateboardData.fromStack(stack).appendHoverText(tooltip, stack);
    }

    public boolean canFlipInInventory(ItemStack stack){
        return false;
    }

    public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
        return stack2.is(IWSItemRegistry.SKATEBOARD_TRUCK.get());
    }

    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) && !SkateboardData.getCustomTag(stack).getBoolean("RemovedShimmer");
    }

    @Override
    public float getMaxHoverOverTime(ItemStack itemStack) {
        return 5F;
    }

    @Override
    public boolean canHoverOver(ItemStack itemStack) {
        return false;
    }


}
