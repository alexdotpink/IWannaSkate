package com.github.alexthe668.iwannaskate.compat.jei;

import com.github.alexthe668.iwannaskate.server.item.SkateboardData;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class SkateboardSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final SkateboardSubtypeInterpreter INSTANCE = new SkateboardSubtypeInterpreter();

    private SkateboardSubtypeInterpreter() {

    }

    @Override
    public Object getSubtypeData(ItemStack itemStack, UidContext context) {
        return SkateboardData.fromStack(itemStack).getWoodBlock();
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack itemStack, UidContext context) {
        return getSubtypeData(itemStack, context).toString();
    }
}
