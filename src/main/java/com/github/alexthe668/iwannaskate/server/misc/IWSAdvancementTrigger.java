package com.github.alexthe668.iwannaskate.server.misc;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class IWSAdvancementTrigger extends SimpleCriterionTrigger<IWSAdvancementTrigger.Instance> {
    public final ResourceLocation resourceLocation;
    private final Codec<Instance> codec;

    public IWSAdvancementTrigger(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.codec = Codec.unit(new IWSAdvancementTrigger.Instance(resourceLocation));
    }

    public void trigger(ServerPlayer p_192180_1_) {
        this.trigger(p_192180_1_, instance -> true);
    }

    @Override
    public Codec<Instance> codec() {
        return codec;
    }

    public record Instance(ResourceLocation resourceLocation) implements SimpleCriterionTrigger.SimpleInstance {
        @Override
        public java.util.Optional<ContextAwarePredicate> player() {
            return java.util.Optional.empty();
        }
    }
}
