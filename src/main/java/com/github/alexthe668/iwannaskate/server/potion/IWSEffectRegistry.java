package com.github.alexthe668.iwannaskate.server.potion;


import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

public class IWSEffectRegistry {

    public static final DeferredRegister<MobEffect> DEF_REG = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, IWannaSkateMod.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> HIGH_OCTANE = DEF_REG.register("high_octane", HighOctaneEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> OVERCAFFEINATED = DEF_REG.register("overcaffeinated", OvercaffeinatedEffect::new);

}
