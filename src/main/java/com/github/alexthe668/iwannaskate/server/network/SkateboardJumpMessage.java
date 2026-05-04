package com.github.alexthe668.iwannaskate.server.network;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.server.entity.SkateboardEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SkateboardJumpMessage(int skateboardId, int playerId, int jumpAmount) implements CustomPacketPayload {
    public static final Type<SkateboardJumpMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "skateboard_jump"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkateboardJumpMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkateboardJumpMessage::skateboardId,
            ByteBufCodecs.INT, SkateboardJumpMessage::playerId,
            ByteBufCodecs.INT, SkateboardJumpMessage::jumpAmount,
            SkateboardJumpMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handle(SkateboardJumpMessage message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player playerSided = context.flow().isClientbound() ? IWannaSkateMod.PROXY.getClientSidePlayer() : context.player();
                if (playerSided == null) {
                    return;
                }
                Entity parent = playerSided.level().getEntity(message.skateboardId());
                Entity jumpPlayer = playerSided.level().getEntity(message.playerId());
                if (jumpPlayer instanceof Player && parent instanceof SkateboardEntity skateboard && jumpPlayer.isPassengerOfSameVehicle(skateboard)) {
                    skateboard.handleStartJump(Mth.clamp(message.jumpAmount(), 0, 1000));
                }
            });
        }
    }
}
