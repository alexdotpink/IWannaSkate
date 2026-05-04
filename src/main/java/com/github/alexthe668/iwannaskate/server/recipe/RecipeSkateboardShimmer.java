package com.github.alexthe668.iwannaskate.server.recipe;

import com.github.alexthe666.citadel.recipe.SpecialRecipeInGuideBook;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardData;
import com.github.alexthe668.iwannaskate.server.item.SkateboardWheels;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RecipeSkateboardShimmer extends CustomRecipe implements SpecialRecipeInGuideBook {


    public RecipeSkateboardShimmer(ResourceLocation name, CraftingBookCategory category) {
        super(category);
    }

    public RecipeSkateboardShimmer(CraftingBookCategory category) {
        this(ResourceLocation.fromNamespaceAndPath("iwannaskate", "skateboard_shimmer"), category);
    }

    public boolean matches(CraftingInput craftingContainer, Level level) {
        ItemStack skateboard = ItemStack.EMPTY;
        ItemStack waxStack = ItemStack.EMPTY;

        for(int i = 0; i < craftingContainer.size(); ++i) {
            ItemStack itemstack2 = craftingContainer.getItem(i);
            if (!itemstack2.isEmpty()) {
                if (itemstack2.is(IWSItemRegistry.SHIMMERING_WAX.get())) {
                    if (!waxStack.isEmpty()) {
                        return false;
                    }
                    waxStack = itemstack2;
                } else {
                    if (!itemstack2.is(IWSItemRegistry.SKATEBOARD.get())) {
                        return false;
                    }
                    if (!skateboard.isEmpty()) {
                        return false;
                    }
                    skateboard = itemstack2;
                }
            }
        }

        return !skateboard.isEmpty() && !waxStack.isEmpty();
    }

    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        ItemStack skateboard = ItemStack.EMPTY;

        for(int i = 0; i < container.size(); ++i) {
            ItemStack itemstack2 = container.getItem(i);
            if (!itemstack2.isEmpty()) {
                 if (itemstack2.is(IWSItemRegistry.SKATEBOARD.get())) {
                    skateboard = itemstack2.copy();
                }
            }
        }

        if (skateboard.isEmpty()) {
            return skateboard;
        } else {
            CompoundTag tag = SkateboardData.getCustomTag(skateboard);
            boolean prevRemoveShimmer = tag.getBoolean("RemovedShimmer");
            tag.putBoolean("RemovedShimmer", !prevRemoveShimmer);
            SkateboardData.setCustomTag(skateboard, tag);
            return skateboard;
        }
    }

    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return IWSRecipeRegistry.SKATEBOARD_SHIMMER.get();
    }

    @Override
    public NonNullList<Ingredient> getDisplayIngredients() {
        ItemStack   skateboard = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
        SkateboardData data = SkateboardData.fromStack(skateboard);
        data.removeGripTape();
        data.removeBanner();
        data.setWheelType(SkateboardWheels.DEFAULT);
        SkateboardData.setStackData(skateboard, data);
        return NonNullList.of(Ingredient.EMPTY, Ingredient.of(skateboard), Ingredient.of(IWSItemRegistry.SHIMMERING_WAX.get()));
    }

    @Override
    public ItemStack getDisplayResultFor(NonNullList<ItemStack> nonNullList) {
        return new ItemStack(IWSItemRegistry.SKATEBOARD.get());
    }
}
