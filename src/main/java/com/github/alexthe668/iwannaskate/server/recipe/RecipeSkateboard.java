package com.github.alexthe668.iwannaskate.server.recipe;

import com.github.alexthe666.citadel.recipe.SpecialRecipeInGuideBook;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardData;
import com.github.alexthe668.iwannaskate.server.item.SkateboardWheels;
import com.github.alexthe668.iwannaskate.server.misc.IWSTags;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import java.util.Optional;

public class RecipeSkateboard extends ShapedRecipe implements SpecialRecipeInGuideBook {
    public RecipeSkateboard(ResourceLocation name, CraftingBookCategory category) {
        super("", category, new ShapedRecipePattern(3, 2, NonNullList.of(Ingredient.EMPTY, Ingredient.of(IWSItemRegistry.SKATEBOARD_TRUCK.get()), Ingredient.of(IWSItemRegistry.SKATEBOARD_DECK.get()), Ingredient.of(IWSItemRegistry.SKATEBOARD_TRUCK.get()), Ingredient.of(IWSTags.SKATEBOARD_WHEELS), Ingredient.EMPTY, Ingredient.of(IWSTags.SKATEBOARD_WHEELS)), Optional.empty()), new ItemStack(IWSItemRegistry.SKATEBOARD.get()));
    }

    public RecipeSkateboard(CraftingBookCategory category) {
        this(ResourceLocation.fromNamespaceAndPath("iwannaskate", "skateboard"), category);
    }

    public boolean matches(CraftingInput container, Level level) {
        if (super.matches(container, level)) {
            ItemStack wheels1 = ItemStack.EMPTY;
            ItemStack wheels2 = ItemStack.EMPTY;
            for (int i = 0; i < container.size(); ++i) {
                if (!container.getItem(i).isEmpty() && container.getItem(i).is(IWSTags.SKATEBOARD_WHEELS)) {
                    if (wheels1.isEmpty()) {
                        wheels1 = container.getItem(i);
                    } else if (wheels2.isEmpty()) {
                        wheels2 = container.getItem(i);
                    }
                }
            }
            return ItemStack.isSameItem(wheels1, wheels2);
        }
        return false;
    }

    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        ItemStack deck = ItemStack.EMPTY;
        ItemStack wheels = ItemStack.EMPTY;
        for (int i = 0; i < container.size(); i++) {
            if (container.getItem(i).is(IWSItemRegistry.SKATEBOARD_DECK.get())) {
                deck = container.getItem(i);
            }
            if (container.getItem(i).is(IWSTags.SKATEBOARD_WHEELS)) {
                wheels = container.getItem(i);
            }
        }
        ItemStack board = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
        CompoundTag customTag = SkateboardData.getCustomTag(deck);
        CompoundTag skateDataTag = customTag.contains("Skateboard") ? customTag.getCompound("Skateboard") : new CompoundTag();
        SkateboardData data = SkateboardData.fromTag(skateDataTag);
        data.setWheelType(SkateboardWheels.fromItem(wheels.getItem()));
        SkateboardData.setStackData(board, data);
        return board;
    }

    public RecipeSerializer<?> getSerializer() {
        return IWSRecipeRegistry.SKATEBOARD.get();
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getDisplayIngredients() {
        return getIngredients();
    }

    @Override
    public ItemStack getDisplayResultFor(NonNullList<ItemStack> nonNullList) {
        ItemStack deck = ItemStack.EMPTY;
        ItemStack wheels = ItemStack.EMPTY;
        for (int i = 0; i < nonNullList.size(); i++) {
            if (nonNullList.get(i).is(IWSItemRegistry.SKATEBOARD_DECK.get())) {
                deck = nonNullList.get(i);
            }
            if (nonNullList.get(i).is(IWSTags.SKATEBOARD_WHEELS)) {
                wheels = nonNullList.get(i);
            }
        }
        ItemStack board = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
        CompoundTag customTag = SkateboardData.getCustomTag(deck);
        CompoundTag skateDataTag = customTag.contains("Skateboard") ? customTag.getCompound("Skateboard") : new CompoundTag();
        SkateboardData data = SkateboardData.fromTag(skateDataTag);
        data.setWheelType(SkateboardWheels.fromItem(wheels.getItem()));
        data.removeBanner();
        data.removeGripTape();
        SkateboardData.setStackData(board, data);
        return board;
    }
}
