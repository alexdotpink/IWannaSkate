package com.github.alexthe668.iwannaskate.server.network;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.blockentity.SkateboardRackBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SkateboardRackMessage(long blockPos, int slot, ItemStack heldStack) implements CustomPacketPayload {
    public static final Type<SkateboardRackMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_rack"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkateboardRackMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, SkateboardRackMessage::blockPos,
            ByteBufCodecs.INT, SkateboardRackMessage::slot,
            ItemStack.OPTIONAL_STREAM_CODEC, SkateboardRackMessage::heldStack,
            SkateboardRackMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handle(SkateboardRackMessage message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.flow().isClientbound() ? IWannaSkateMod.PROXY.getClientSidePlayer() : context.player();
                if (player == null || player.level() == null) {
                    return;
                }
                BlockPos pos = BlockPos.of(message.blockPos());
                if (player.level().getBlockEntity(pos) instanceof SkateboardRackBlockEntity blockEntity) {
                    blockEntity.setItem(message.slot(), message.heldStack());
                }
            });
        }
    }
}
