package com.github.alexthe668.iwannaskate.server.recipe;

import com.github.alexthe666.citadel.recipe.SpecialRecipeInGuideBook;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardData;
import com.github.alexthe668.iwannaskate.server.misc.IWSTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.Optional;

public class RecipeSkateboardDeck  extends ShapedRecipe implements SpecialRecipeInGuideBook {
    public RecipeSkateboardDeck(ResourceLocation name, CraftingBookCategory category) {
        super("", category, new ShapedRecipePattern(3, 3, NonNullList.of(Ingredient.EMPTY, Ingredient.of(IWSTags.DECK_MATERIALS), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(IWSTags.DECK_MATERIALS), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(IWSTags.DECK_MATERIALS)), Optional.empty()), new ItemStack(IWSItemRegistry.SKATEBOARD_DECK.get()));
    }

    public RecipeSkateboardDeck(CraftingBookCategory category) {
        this(ResourceLocation.fromNamespaceAndPath("iwannaskate", "skateboard_deck"), category);
    }

    public boolean matches(CraftingInput container, Level level) {
        ItemStack lastTagged = ItemStack.EMPTY;
        int deckMaterials = 0;
        for(int i = 0; i < container.size(); i++){
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty()){
                if(!stack.is(IWSTags.DECK_MATERIALS)){
                    return false;
                }
                if(!lastTagged.isEmpty() && !ItemStack.isSameItem(lastTagged, stack)){
                    return false;
                }
                lastTagged = stack;
                deckMaterials++;
            }
        }
        return deckMaterials == 3;
    }


    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(IWSItemRegistry.SKATEBOARD_DECK.get());
    }

    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        ItemStack lastTagged = ItemStack.EMPTY;
        for(int i = 0; i < container.size(); i++){
            if(container.getItem(i).is(IWSTags.DECK_MATERIALS)){
                lastTagged = container.getItem(i);
            }
        }
        ItemStack deck = new ItemStack(IWSItemRegistry.SKATEBOARD_DECK.get());
        SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(lastTagged.getItem()));
        SkateboardData.setStackData(deck, data);
        return deck;
    }

    public RecipeSerializer<?> getSerializer() {
        return IWSRecipeRegistry.SKATEBOARD_DECK.get();
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
        ItemStack lastTagged = ItemStack.EMPTY;
        for(int i = 0; i < nonNullList.size(); i++){
            if(nonNullList.get(i).is(IWSTags.DECK_MATERIALS)){
                lastTagged = nonNullList.get(i);
            }
        }
        ItemStack deck = new ItemStack(IWSItemRegistry.SKATEBOARD_DECK.get());
        SkateboardData data = new SkateboardData(BuiltInRegistries.ITEM.getKey(lastTagged.getItem()));
        SkateboardData.setStackData(deck, data);
        return deck;
    }


}
