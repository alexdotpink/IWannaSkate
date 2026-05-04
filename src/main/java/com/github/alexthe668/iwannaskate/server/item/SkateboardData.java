package com.github.alexthe668.iwannaskate.server.item;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class SkateboardData {
    private ResourceLocation woodBlock;
    private int gripTapeOrdinal = -1;
    private int wheelTypeOrdinal = -1;
    private boolean hasBanner = false;
    @Nullable
    private CompoundTag bannerTag = null;

    public static final SkateboardData DEFAULT = new SkateboardData(ResourceLocation.parse("minecraft:oak_slab"));

    public SkateboardData(ResourceLocation woodBlock) {
        this.woodBlock = woodBlock;
    }

    public static SkateboardData fromTag(CompoundTag tag) {
        ResourceLocation location;
        if (tag.contains("MadeOf")) {
            location = ResourceLocation.parse(tag.getString("MadeOf"));
        } else {
            location = ResourceLocation.parse("minecraft:oak_slab");
        }
        SkateboardData data = new SkateboardData(location);
        if (tag.contains("Banner")) {
            data.hasBanner = true;
            data.bannerTag = tag.getCompound("Banner");
        }
        data.gripTapeOrdinal = tag.getInt("GripTape");
        data.wheelTypeOrdinal = tag.getInt("WheelType");
        return data;
    }

    public ResourceLocation getWoodBlock() {
        return woodBlock;
    }

    public void setWoodBlock(ResourceLocation woodBlock) {
        this.woodBlock = woodBlock;
    }

    public void removeBanner() {
        this.hasBanner = false;
    }

    public void setBanner(CompoundTag bannerTag) {
        this.hasBanner = true;
        this.bannerTag = bannerTag;
    }

    public boolean hasBanner() {
        return hasBanner && this.bannerTag != null;
    }

    public CompoundTag getBannerTag() {
        return bannerTag;
    }

    public boolean hasGripTape(){
        return gripTapeOrdinal >= 0;
    }

    public void removeGripTape(){
        gripTapeOrdinal = -1;
    }

    public void setGripTape(DyeColor dyeColor){
        gripTapeOrdinal = dyeColor.ordinal();
    }

    public DyeColor getGripTapeColor(){
        if(hasGripTape()){
            return DyeColor.values()[Mth.clamp(gripTapeOrdinal, 0, DyeColor.values().length - 1)];
        }
        return DyeColor.BLACK;
    }

    public void setWheelType(SkateboardWheels wheelType){
        wheelTypeOrdinal = wheelType.ordinal();
    }

    public SkateboardWheels getWheelType(){
        return SkateboardWheels.values()[Mth.clamp(wheelTypeOrdinal, 0, SkateboardWheels.values().length - 1)];
    }

    public CompoundTag toTag() {
        CompoundTag data = new CompoundTag();
        data.putString("MadeOf", this.woodBlock.toString());
        if (this.hasBanner && bannerTag != null) {
            data.put("Banner", bannerTag);
        }
        data.putInt("GripTape", gripTapeOrdinal);
        data.putInt("WheelType", wheelTypeOrdinal);
        return data;
    }

    public void appendHoverText(List<Component> tooltip, ItemStack stack) {
        ChatFormatting chatColor = ChatFormatting.GRAY;
        Item material = BuiltInRegistries.ITEM.get(this.getWoodBlock());
        MutableComponent madeOfName = Component.translatable(material.getDescriptionId());
        tooltip.add(Component.translatable("item.iwannaskate.skateboard.made_of").withStyle(chatColor).append(" ").append(madeOfName.withStyle(chatColor)));
        if(this.hasGripTape()){
            tooltip.add(Component.translatable("item.iwannaskate.skateboard.grip_tape_" + getGripTapeColor().getName()).withStyle(chatColor));
        }
        if(this.wheelTypeOrdinal > 0){
            String wheelStr = "item.iwannaskate.skateboard.wheels_" + getWheelType().name().toLowerCase();
            tooltip.add(Component.translatable(wheelStr).withStyle(chatColor));
        }
        if(this.hasBanner()){
            tooltip.add(Component.translatable("item.iwannaskate.skateboard.banner").withStyle(chatColor));
            CompoundTag compoundtag = getBannerTag();
            DyeColor base = DyeColor.byId(compoundtag.getInt("Base"));
            MutableComponent baseText = Component.translatable("item.iwannaskate.skateboard.banner_base_" + base.getName()).withStyle(chatColor);
            tooltip.add(Component.literal("  -").withStyle(chatColor).append(baseText));
        }
        if(stack.isEnchanted()){
            tooltip.add(Component.literal(""));
            tooltip.add(Component.translatable("item.iwannaskate.skateboard.enchanted").withStyle(chatColor, ChatFormatting.UNDERLINE));
            var enchantmentList = stack.getEnchantments().entrySet();
            int maxPreview = 4;
            int i = 0;
            for(var entry : enchantmentList) {
                if(i < maxPreview || IWannaSkateMod.PROXY.isKeyDown(1)){
                    tooltip.add(Component.literal("  -").withStyle(chatColor).append(entry.getKey().value().description().copy().append(" " + entry.getIntValue())));
                }
                i++;
            }
            if(enchantmentList.size() >= maxPreview && !IWannaSkateMod.PROXY.isKeyDown(1)){
                tooltip.add(Component.translatable("item.iwannaskate.skateboard.show_more").withStyle(chatColor));
            }
        }
   }

    @NotNull
    public static SkateboardData fromStack(ItemStack stack){
        CompoundTag compoundtag = getCustomTag(stack);
        if(compoundtag != null && compoundtag.contains("Skateboard")){
            return SkateboardData.fromTag(compoundtag.getCompound("Skateboard"));
        }else {
            return SkateboardData.DEFAULT;
        }
    }

    public static void setStackData(ItemStack stack, SkateboardData data){
        CompoundTag tag = getCustomTag(stack);
        tag.put("Skateboard", data.toTag());
        setCustomTag(stack, tag);
    }

    public static CompoundTag getCustomTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    public static void setCustomTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

}
