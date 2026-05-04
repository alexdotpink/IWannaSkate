package com.github.alexthe668.iwannaskate.client.particle;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class IWSParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> DEF_REG = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, IWannaSkateMod.MODID);
    public static final Supplier<SimpleParticleType> HALLOWEEN = DEF_REG.register("halloween", ()-> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> BEE = DEF_REG.register("bee", ()-> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> HOVER = DEF_REG.register("hover", ()-> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> SPARKLE = DEF_REG.register("sparkle", ()-> new SimpleParticleType(false));

}
