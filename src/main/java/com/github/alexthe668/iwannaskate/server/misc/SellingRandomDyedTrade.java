package com.github.alexthe668.iwannaskate.server.misc;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.ItemCost;

public class SellingRandomDyedTrade implements VillagerTrades.ItemListing {
    private final ItemStack tradeItem;
    private final int price;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    public SellingRandomDyedTrade(ItemStack itemLike, int price, int maxUses, int xpValue) {
        this.tradeItem = itemLike;
        this.price = price;
        this.maxUses = maxUses;
        this.xpValue = xpValue;
        this.priceMultiplier = 0.05F;
    }

    public MerchantOffer getOffer(Entity tradingWith, RandomSource randomSource) {
        ItemStack selling = tradeItem.copy();
        selling.set(DataComponents.DYED_COLOR, new DyedItemColor((int) (randomSource.nextFloat() * 0xFFFFFF), true));
        return new MerchantOffer(new ItemCost(Items.EMERALD, this.price), selling, this.maxUses, this.xpValue, this.priceMultiplier);
    }
}
