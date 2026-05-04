package com.github.alexthe668.iwannaskate.server;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.item.SkateboardMaterials;
import com.github.alexthe668.iwannaskate.server.potion.IWSEffectRegistry;
import com.github.alexthe668.iwannaskate.server.recipe.IWSRecipeRegistry;
import com.github.alexthe668.iwannaskate.server.world.WanderingSkaterSpawner;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashMap;
import java.util.Map;

public class CommonEvents {
    private static final Map<ServerLevel, WanderingSkaterSpawner> WANDERING_SKATER_SPAWNER_MAP = new HashMap<>();

    @SubscribeEvent
    public void onTagsLoaded(TagsUpdatedEvent event) {
        SkateboardMaterials.reload();
        IWSRecipeRegistry.registerCauldronInteractions();
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        double rate = IWannaSkateMod.COMMON_CONFIG.skateboardExpAnvilRateModifier.get();
        if (event.getLeft().is(IWSItemRegistry.SKATEBOARD.get()) && rate != 1.0D && event.getCost() > 1) {
            event.setCost(Math.max(1, (int) Math.ceil(event.getCost() * rate)));
        }
    }

    @SubscribeEvent
    public void onPlayerTrySleepInBed(CanPlayerSleepEvent event) {
        if (event.getEntity().hasEffect(IWSEffectRegistry.HIGH_OCTANE) || event.getEntity().hasEffect(IWSEffectRegistry.OVERCAFFEINATED)) {
            event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }

    @SubscribeEvent
    public void onServerTick(LevelTickEvent.Post tick) {
        if (!tick.getLevel().isClientSide && tick.getLevel() instanceof ServerLevel serverWorld) {
            WANDERING_SKATER_SPAWNER_MAP.computeIfAbsent(serverWorld, WanderingSkaterSpawner::new).tick();
        }
    }
}
