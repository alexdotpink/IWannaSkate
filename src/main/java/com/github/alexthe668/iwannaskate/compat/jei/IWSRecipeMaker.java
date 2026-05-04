package com.github.alexthe668.iwannaskate.compat.jei;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardData;
import com.github.alexthe668.iwannaskate.server.item.SkateboardMaterials;
import com.github.alexthe668.iwannaskate.server.item.SkateboardWheels;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IWSRecipeMaker {

    public static List<RecipeHolder<CraftingRecipe>> createDeckRecipes() {
        return SkateboardMaterials.getSkateboardMaterials().stream().map(woodItem -> createDeckRecipe(woodItem)).toList();
    }

    public static List<RecipeHolder<CraftingRecipe>> createSkateboardRecipes() {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        SkateboardMaterials.getSkateboardMaterials().stream().forEach(woodItem -> recipes.addAll(createSkateboardRecipesForDeck(woodItem)));
        return recipes;
    }

    public static List<RecipeHolder<CraftingRecipe>> createSkateboardBannerRecipes() {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        SkateboardMaterials.getSkateboardMaterials().stream().forEach(woodItem -> recipes.addAll(createSkateboardBannerRecipes(woodItem)));
        return recipes;
    }

    public static List<RecipeHolder<CraftingRecipe>> createSkateboardGripRecipes() {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        SkateboardMaterials.getSkateboardMaterials().stream().forEach(woodItem -> recipes.addAll(createSkateboardGripRecipes(woodItem)));
        return recipes;
    }

    private static List<RecipeHolder<CraftingRecipe>> createSkateboardBannerRecipes(Item woodItem) {
        String group = "jei.skateboard_deck";
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        ItemStack input = createSkateboardForWood(woodItem);
        for(Item banner : SkateboardMaterials.getBanners()){
            if(banner instanceof BannerItem){
                ItemStack output = input.copy();
                SkateboardData data = SkateboardData.fromStack(input);
                CompoundTag bannerTag = new CompoundTag();
                bannerTag.putInt("Base", ((BannerItem)banner).getColor().getId());
                data.setBanner(bannerTag);
                SkateboardData.setStackData(output, data);
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "jei.skateboard_banner_" + BuiltInRegistries.ITEM.getKey(woodItem).getPath() + banner.getDescriptionId());
                NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, Ingredient.of(input), Ingredient.of(banner));
                recipes.add(holder(id, new ShapelessRecipe(group, CraftingBookCategory.MISC, output, inputs)));
            }
        }
        return recipes;
    }

    private static List<RecipeHolder<CraftingRecipe>> createSkateboardGripRecipes(Item woodItem) {
        String group = "jei.skateboard_deck";
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        ItemStack input = createSkateboardForWood(woodItem);
        for(Item carpet : SkateboardMaterials.getGrips()){
            if(Block.byItem(carpet) instanceof WoolCarpetBlock carpetBlock){
                ItemStack output = input.copy();
                SkateboardData data = SkateboardData.fromStack(input);
                data.setGripTape(carpetBlock.getColor());
                SkateboardData.setStackData(output, data);
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "jei.skateboard_grip_" + BuiltInRegistries.ITEM.getKey(woodItem).getPath() + carpet.getDescriptionId());
                NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, Ingredient.of(input), Ingredient.of(carpet));
                recipes.add(holder(id, new ShapelessRecipe(group, CraftingBookCategory.MISC, output, inputs)));
            }
        }
        return recipes;
    }

    private static RecipeHolder<CraftingRecipe> createDeckRecipe(Item deckMaterial) {
        String group = "jei.skateboard_deck";
        ItemStack input = new ItemStack(deckMaterial);
        ItemStack output = createDeckForWood(deckMaterial);
        Ingredient woodIngredient = Ingredient.of(input);
        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                Ingredient.EMPTY, Ingredient.EMPTY, woodIngredient,
                Ingredient.EMPTY, woodIngredient, Ingredient.EMPTY,
                woodIngredient, Ingredient.EMPTY, Ingredient.EMPTY
        );
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "jei.skateboard_deck_" + BuiltInRegistries.ITEM.getKey(deckMaterial).getPath());
        ShapedRecipePattern pattern = new ShapedRecipePattern(3, 3, inputs, Optional.empty());
        return holder(id, new ShapedRecipe(group, CraftingBookCategory.MISC, pattern, output));
    }

    private static List<RecipeHolder<CraftingRecipe>> createSkateboardRecipesForDeck(Item woodItem) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        String group = "jei.skateboard";
        for(Item wheel : SkateboardMaterials.getSkateboardWheels()){
            ItemStack output = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
            SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(woodItem));
            data.setWheelType(SkateboardWheels.fromItem(wheel));
            SkateboardData.setStackData(output, data);
            Ingredient deckIngredient = Ingredient.of(createDeckForWood(woodItem));
            Ingredient truckIngredient = Ingredient.of(IWSItemRegistry.SKATEBOARD_TRUCK.get());
            Ingredient wheelIngredient = Ingredient.of(wheel);
            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                    Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY,
                    truckIngredient, deckIngredient, truckIngredient,
                    wheelIngredient, Ingredient.EMPTY, wheelIngredient
            );
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "jei.skateboard_" + BuiltInRegistries.ITEM.getKey(woodItem).getPath() + "_" + BuiltInRegistries.ITEM.getKey(wheel).getPath());
            ShapedRecipePattern pattern = new ShapedRecipePattern(3, 3, inputs, Optional.empty());
            recipes.add(holder(id, new ShapedRecipe(group, CraftingBookCategory.MISC, pattern, output)));
        }
        return recipes;
    }

    private static ItemStack createDeckForWood(Item deck) {
        ItemStack stack = new ItemStack(IWSItemRegistry.SKATEBOARD_DECK.get());
        SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(deck));
        SkateboardData.setStackData(stack, data);
        return stack;
    }

    private static ItemStack createSkateboardForWood(Item deck) {
        ItemStack stack = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
        SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(deck));
        SkateboardData.setStackData(stack, data);
        return stack;
    }

    private static RecipeHolder<CraftingRecipe> holder(ResourceLocation id, CraftingRecipe recipe) {
        return new RecipeHolder<>(id, recipe);
    }

}
