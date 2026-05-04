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

public record SkateboardKeyMessage(int skateboardId, int playerId, int typeId) implements CustomPacketPayload {
    public static final Type<SkateboardKeyMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_key"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkateboardKeyMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkateboardKeyMessage::skateboardId,
            ByteBufCodecs.INT, SkateboardKeyMessage::playerId,
            ByteBufCodecs.INT, SkateboardKeyMessage::typeId,
            SkateboardKeyMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handle(SkateboardKeyMessage message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player playerSided = context.flow().isClientbound() ? IWannaSkateMod.PROXY.getClientSidePlayer() : context.player();
                if (playerSided == null) {
                    return;
                }
                Entity parent = playerSided.level().getEntity(message.skateboardId());
                Entity keyPresser = playerSided.level().getEntity(message.playerId());
                if (keyPresser instanceof Player && parent instanceof SkateboardEntity skateboard && keyPresser.isPassengerOfSameVehicle(skateboard)) {
                    skateboard.onKeyPacket(keyPresser, message.typeId());
                }
            });
        }
    }
}
