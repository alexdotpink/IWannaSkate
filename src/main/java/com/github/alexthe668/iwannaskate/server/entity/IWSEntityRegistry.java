package com.github.alexthe668.iwannaskate.server.entity;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

@EventBusSubscriber(modid = IWannaSkateMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IWSEntityRegistry {
    public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, IWannaSkateMod.MODID);
    public static final Supplier<EntityType<SkateboardEntity>> SKATEBOARD = DEF_REG.register("skateboard", () -> (EntityType)EntityType.Builder.of(SkateboardEntity::new, MobCategory.MISC).sized(0.6F, 0.3125F).setUpdateInterval(1).clientTrackingRange(10).build("skateboard"));
    public static final Supplier<EntityType<SkaterSkeletonEntity>> SKATER_SKELETON = DEF_REG.register("skater_skeleton", () -> (EntityType)EntityType.Builder.of(SkaterSkeletonEntity::new, MobCategory.MONSTER).sized(0.65F, 1.9F).build("skater_skeleton"));
    public static final Supplier<EntityType<WanderingSkaterEntity>> WANDERING_SKATER = DEF_REG.register("wandering_skater", () -> (EntityType)EntityType.Builder.of(WanderingSkaterEntity::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wandering_skater"));

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        event.put(SKATER_SKELETON.get(), SkaterSkeletonEntity.createAttributes().build());
        event.put(WANDERING_SKATER.get(), WanderingSkaterEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(SKATER_SKELETON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkaterSkeletonEntity::checkSkaterSkeletonSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(WANDERING_SKATER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WanderingSkaterEntity::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
