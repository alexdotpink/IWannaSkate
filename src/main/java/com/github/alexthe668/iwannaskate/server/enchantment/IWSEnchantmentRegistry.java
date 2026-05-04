package com.github.alexthe668.iwannaskate.server.enchantment;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class IWSEnchantmentRegistry {
    public static final SkateEnchantment SIDEWINDER = create("sidewinder", 2);
    public static final SkateEnchantment CLAMBERING = create("clambering", 1);
    public static final SkateEnchantment INERTIAL = create("inertial", 3);
    public static final SkateEnchantment PEDALLING = create("pedalling", 2);
    public static final SkateEnchantment AERIAL = create("aerial", 4);
    public static final SkateEnchantment SECURED = create("secured", 1);
    public static final SkateEnchantment EARTHCROSSER = create("earthcrosser", 1);
    public static final SkateEnchantment SURFING = create("surfing", 1);
    public static final SkateEnchantment HARDWOOD = create("hardwood", 1);
    public static final SkateEnchantment BASHING = create("bashing", 4);
    public static final SkateEnchantment ONBOARDING = create("onboarding", 1);
    public static final SkateEnchantment BENTHIC = create("benthic", 1);
    public static final SkateEnchantment INSTANT_RETURN = create("instant_return", 1);

    public static final List<SkateEnchantment> ALL = List.of(
            SIDEWINDER, CLAMBERING, INERTIAL, PEDALLING, AERIAL, SECURED, EARTHCROSSER,
            SURFING, HARDWOOD, BASHING, ONBOARDING, BENTHIC, INSTANT_RETURN);

    private static SkateEnchantment create(String name, int maxLevel) {
        return new SkateEnchantment(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, name)), maxLevel);
    }

    public static Holder<Enchantment> holder(RegistryAccess registryAccess, SkateEnchantment enchantment) {
        return registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment.key());
    }

    public static boolean isSkateboard(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(key -> ALL.stream().anyMatch(skateEnchantment -> skateEnchantment.key().equals(key))).orElse(false);
    }

    public record SkateEnchantment(ResourceKey<Enchantment> key, int maxLevel) {
        public ResourceKey<Enchantment> get() {
            return key;
        }
    }
}
