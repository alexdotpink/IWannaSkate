package com.github.alexthe668.iwannaskate.server.item;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.block.IWSBlockRegistry;
import com.github.alexthe668.iwannaskate.server.entity.IWSEntityRegistry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class IWSItemRegistry {

    public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(BuiltInRegistries.ITEM, IWannaSkateMod.MODID);

    public static final Supplier<Item> SKATING_MANUAL = DEF_REG.register("skating_manual", () -> new SkatingManualItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SKATER_SKELETON_SPAWN_EGG = DEF_REG.register("spawn_egg_skater_skeleton", () -> new DeferredSpawnEggItem(IWSEntityRegistry.SKATER_SKELETON, 0X9D9C9F,0XAD352B, new Item.Properties()));
    public static final Supplier<Item> WANDERING_SKATER_SPAWN_EGG = DEF_REG.register("spawn_egg_wandering_skater", () -> new DeferredSpawnEggItem(IWSEntityRegistry.WANDERING_SKATER, 0X707C4C,0X7B4430, new Item.Properties()));
    public static final Supplier<Item> SKATEBOARD = DEF_REG.register("skateboard", () -> new SkateboardItem(new Item.Properties().durability(1000)));
    public static final Supplier<Item> SKATEBOARD_DECK = DEF_REG.register("skateboard_deck", () -> new BaseSkateboardItem(new Item.Properties()));
    public static final Supplier<Item> SKATEBOARD_TRUCK = DEF_REG.register("skateboard_truck", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SKATEBOARD_RACK = DEF_REG.register("skateboard_rack", () -> new IWSBlockItem(IWSBlockRegistry.SKATEBOARD_RACK, new Item.Properties()));
    public static final Supplier<Item> SHIMMERING_WAX = DEF_REG.register("shimmering_wax", () -> new Item(new Item.Properties()) {
        @Override
        public boolean isFoil(ItemStack stack) {
            return true;
        }
    });
    public static final Supplier<Item> SPIKED_SKATER_HELMET = DEF_REG.register("spiked_skater_helmet", () -> new SkaterHelmetItem(ArmorMaterials.IRON, new Item.Properties()));
    public static final Supplier<Item> BEANIE = DEF_REG.register("beanie", () -> new DyeableHatItem(ArmorMaterials.LEATHER, "beanie", 0XCBA780, new Item.Properties()));
    public static final Supplier<Item> SKATER_CAP = DEF_REG.register("skater_cap", () -> new DyeableHatItem(ArmorMaterials.LEATHER, "skater_cap", 0XA65538, new Item.Properties()));
    public static final Supplier<Item> PIZZA_SLICE = DEF_REG.register("pizza_slice", () -> new PizzaItem(IWSBlockRegistry.PIZZA, new Item.Properties().food((new FoodProperties.Builder()).nutrition(4).saturationModifier(0.2F).build())));
    public static final Supplier<Item> ENERGY_DRINK = DEF_REG.register("energy_drink", () -> new EnergyDrinkItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0.35F).build())));
    static {
        SkateboardWheels.init();
    }

}
