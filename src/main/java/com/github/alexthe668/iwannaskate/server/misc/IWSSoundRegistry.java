package com.github.alexthe668.iwannaskate.server.misc;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class IWSSoundRegistry {
    public static final DeferredRegister<SoundEvent> DEF_REG = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IWannaSkateMod.MODID);

    public static final Supplier<SoundEvent> SKATEBOARD_PEDAL = DEF_REG.register("skateboard_pedal", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_pedal")));
    public static final Supplier<SoundEvent> SKATEBOARD_ROUGH_ROLLING_LOOP = DEF_REG.register("skateboard_rough_rolling_loop", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_rough_rolling_loop")));
    public static final Supplier<SoundEvent> SKATEBOARD_SMOOTH_ROLLING_LOOP = DEF_REG.register("skateboard_smooth_rolling_loop", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_smooth_rolling_loop")));
    public static final Supplier<SoundEvent> SKATEBOARD_GRIND_LOOP = DEF_REG.register("skateboard_grind_loop", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_grind_loop")));
    public static final Supplier<SoundEvent> SKATEBOARD_CHANGE_POSE = DEF_REG.register("skateboard_change_pose", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_change_pose")));
    public static final Supplier<SoundEvent> SKATEBOARD_JUMP_START = DEF_REG.register("skateboard_jump_start", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_jump_start")));
    public static final Supplier<SoundEvent> SKATEBOARD_JUMP_LAND = DEF_REG.register("skateboard_jump_land", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_jump_land")));
    public static final Supplier<SoundEvent> SKATER_SKELETON_IDLE = DEF_REG.register("skater_skeleton_idle", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skater_skeleton_idle")));
    public static final Supplier<SoundEvent> SKATER_SKELETON_WALK = DEF_REG.register("skater_skeleton_walk", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skater_skeleton_walk")));
    public static final Supplier<SoundEvent> SKATER_SKELETON_HURT = DEF_REG.register("skater_skeleton_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skater_skeleton_hurt")));
    public static final Supplier<SoundEvent> SKATER_SKELETON_DIE = DEF_REG.register("skater_skeleton_die", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skater_skeleton_die")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_IDLE = DEF_REG.register("wandering_skater_idle", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_idle")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_HURT = DEF_REG.register("wandering_skater_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_hurt")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_DIE = DEF_REG.register("wandering_skater_die", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_die")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_YES = DEF_REG.register("wandering_skater_yes", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_yes")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_MAYBE = DEF_REG.register("wandering_skater_maybe", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_maybe")));
    public static final Supplier<SoundEvent> WANDERING_SKATER_NO = DEF_REG.register("wandering_skater_no", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "wandering_skater_no")));

}
