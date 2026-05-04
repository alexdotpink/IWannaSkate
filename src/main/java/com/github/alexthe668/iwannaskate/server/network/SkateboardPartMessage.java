package com.github.alexthe668.iwannaskate.server.network;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.entity.SkateboardEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SkateboardPartMessage(int parentId, int playerId, int typeId) implements CustomPacketPayload {
    public static final Type<SkateboardPartMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_part"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkateboardPartMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkateboardPartMessage::parentId,
            ByteBufCodecs.INT, SkateboardPartMessage::playerId,
            ByteBufCodecs.INT, SkateboardPartMessage::typeId,
            SkateboardPartMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handle(SkateboardPartMessage message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player playerSided = context.flow().isClientbound() ? IWannaSkateMod.PROXY.getClientSidePlayer() : context.player();
                if (playerSided == null) {
                    return;
                }
                Entity parent = playerSided.level().getEntity(message.parentId());
                Entity interacter = playerSided.level().getEntity(message.playerId());
                if (interacter != null && parent instanceof SkateboardEntity skateboard && interacter.distanceTo(skateboard) < 16) {
                    skateboard.onInteractPacket(interacter, message.typeId());
                }
            });
        }
    }
}
