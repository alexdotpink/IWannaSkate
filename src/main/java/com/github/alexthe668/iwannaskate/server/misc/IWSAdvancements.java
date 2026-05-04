package com.github.alexthe668.iwannaskate.server.misc;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IWSAdvancements {

    public static final DeferredRegister<CriterionTrigger<?>> DEF_REG = DeferredRegister.create(Registries.TRIGGER_TYPE, IWannaSkateMod.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> TAKE_SKATE_DAMAGE = create("take_skate_damage");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> TRICK_OLLIE = create("trick_ollie");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> TRICK_KICKFLIP = create("trick_kickflip");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> TRICK_GRIND = create("trick_grind");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> SLOW_MOTION = create("slow_motion");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> SKATE_10K = create("skate_10k");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> SKATE_SURFING = create("skate_surfing");
    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> SKATE_BASHING = create("skate_bashing");

    public static final DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> GIVE_VILLAGER_DRINK = create("give_villager_drink");

    private static DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> create(String name) {
        return DEF_REG.register(name, () -> new IWSAdvancementTrigger(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, name)));
    }

    public static void trigger(Entity entity, DeferredHolder<CriterionTrigger<?>, IWSAdvancementTrigger> trigger){
        if(entity instanceof ServerPlayer serverPlayer){
            trigger.get().trigger(serverPlayer);
        }
    }

}
