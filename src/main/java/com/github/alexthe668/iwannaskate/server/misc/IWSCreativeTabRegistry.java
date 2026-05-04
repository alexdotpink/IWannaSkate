package com.github.alexthe668.iwannaskate.server.misc;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.item.CustomTabBehavior;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardWheels;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class IWSCreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IWannaSkateMod.MODID);

    public static final Supplier<CreativeModeTab> TAB = DEF_REG.register("iwannaskate", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.iwannaskate"))
            .icon(IWSCreativeTabRegistry::makeIcon)
            .displayItems((enabledFeatures, output) -> {
                for(Supplier<? extends Item> item : IWSItemRegistry.DEF_REG.getEntries()){
                    if(item.get() instanceof CustomTabBehavior customTabBehavior){
                        customTabBehavior.fillItemCategory(output);
                    }else{
                        output.accept(item.get());
                    }
                }
                for(SkateboardWheels wheels : SkateboardWheels.values()){
                    output.accept(wheels.getItemRegistryObject().get());
                }
            })
            .build());

    private static ItemStack makeIcon() {
        ItemStack stack = new ItemStack(IWSItemRegistry.SKATEBOARD.get());
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsCreativeTab", true);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return stack;
    }
}
