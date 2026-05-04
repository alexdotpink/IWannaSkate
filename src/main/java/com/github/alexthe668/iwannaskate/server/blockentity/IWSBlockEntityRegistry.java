package com.github.alexthe668.iwannaskate.server.blockentity;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.block.IWSBlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class IWSBlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IWannaSkateMod.MODID);

    public static final Supplier<BlockEntityType<SkateboardRackBlockEntity>> SKATEBOARD_RACK = DEF_REG.register("skateboard_rack", () -> BlockEntityType.Builder.of(SkateboardRackBlockEntity::new, IWSBlockRegistry.SKATEBOARD_RACK.get()).build(null));

}
