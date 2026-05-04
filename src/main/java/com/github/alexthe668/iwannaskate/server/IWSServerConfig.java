package com.github.alexthe668.iwannaskate.server;

import net.neoforged.neoforge.common.ModConfigSpec;

public class IWSServerConfig {

    public final ModConfigSpec.BooleanValue spawnSkaterSkeletons;
    public final ModConfigSpec.BooleanValue skaterSkeletonsUseSkateboards;
    public final ModConfigSpec.DoubleValue skaterSkeletonsHolidayWheelsDropChance;
    public final ModConfigSpec.BooleanValue spawnWanderingSkaters;
    public final ModConfigSpec.IntValue wanderingSkaterSpawnDelay;
    public final ModConfigSpec.DoubleValue wanderingSkaterSpawnChance;
    public final ModConfigSpec.BooleanValue convertVillagersToNitwits;
    public final ModConfigSpec.BooleanValue convertNitwitsToSkaters;

    public final ModConfigSpec.BooleanValue enableSlowMotion;
    public final ModConfigSpec.BooleanValue playersSlowMotion;
    public final ModConfigSpec.IntValue slowMotionDistance;

    public final ModConfigSpec.DoubleValue skateboardExpAnvilRateModifier;

    public IWSServerConfig(final ModConfigSpec.Builder builder) {
        builder.push("mobs");
        spawnSkaterSkeletons = builder.comment("when enabled, skater skeletons will spawn where applicable.").translation("spawn_skater_skeletons").define("spawn_skater_skeletons", true);
        skaterSkeletonsUseSkateboards = builder.comment("when enabled, skater skeletons will skate using the skateboards they spawn with or any they pick up.").translation("skater_skeletons_use_skateboards").define("skater_skeletons_use_skateboards", true);
        skaterSkeletonsHolidayWheelsDropChance = builder.comment("the chance that a skater skeleton will drop a spooky or snowy wheel when in the month of its respective holiday. 1.0 = 100% chance, 0.0 = disabled.").translation("skater_skeleton_holiday_wheel_drop_chance").defineInRange("skater_skeleton_holiday_wheel_drop_chance", 0.25D, 0D, 1D);
        spawnWanderingSkaters = builder.comment("when enabled, wandering skaters will spawn where applicable.").translation("spawn_wandering_skaters").define("spawn_wandering_skaters", true);
        wanderingSkaterSpawnDelay = builder.comment("the time in game ticks between each attempt to spawn a wandering skater.").translation("wandering_skater_spawn_delay").defineInRange("wandering_skater_spawn_delay", 24000, 1200, Integer.MAX_VALUE);
        wanderingSkaterSpawnChance = builder.comment("the chance that each time the spawn delay passes, a wandering skater will spawn. If it does not, the next time the chance is increased by this number again.").translation("wandering_skater_spawn_chance").defineInRange("wandering_skater_spawn_chance", 0.05D, 0F, 1.0F);
        convertVillagersToNitwits = builder.comment("whether players can give baby villagers energy drinks to turn them into nitwit villagers.").translation("convert_villagers_to_nitwits").define("convert_villagers_to_nitwits", true);
        convertNitwitsToSkaters = builder.comment("whether players can give nitwit villagers a skateboard to turn them into wandering skaters.").translation("convert_nitwits_to_skaters").define("convert_nitwits_to_skaters", true);
        builder.pop();
        builder.push("slow-motion");
        enableSlowMotion = builder.comment("when enabled, certain skateboards can cause a slow-motion effect to the entities around them.").translation("slow_motion").define("slow_motion", true);
        playersSlowMotion = builder.comment("when enabled, nearby player entities can be slowed down if someone else is using the slow motion feature.").translation("players_slow_motion").define("players_slow_motion", true);
        slowMotionDistance = builder.comment("determines how far in blocks entities are effected by slow motion.").translation("slow_motion_distance").defineInRange("slow_motion_distance", 30, 1, 2000);
        builder.pop();
        builder.push("board-customization");
        skateboardExpAnvilRateModifier = builder.comment("Adding enchantments to the skateboard via the anvil is made cheaper by multiplying the total amount of exp cost by this number. 1.0 = disabled, 100% usual exp, 0.0 = 1 exp").translation("skateboard_exp_anvil_rate_modifier").defineInRange("skateboard_exp_anvil_rate_modifier", 0.75D, 0D, 1D);
        builder.pop();

    }
}
