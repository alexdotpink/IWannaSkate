package com.github.alexthe668.iwannaskate.server.block;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class IWSBlockRegistry {

    public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(BuiltInRegistries.BLOCK, IWannaSkateMod.MODID);

    public static final Supplier<Block> SKATEBOARD_RACK = DEF_REG.register("skateboard_rack", () -> new SkateboardRackBlock(BlockBehaviour.Properties.of().strength(0.5F).pushReaction(PushReaction.DESTROY).sound(SoundType.WOOD)));
    public static final Supplier<Block> PIZZA = DEF_REG.register("pizza", () -> new PizzaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.WOOL)));


}
