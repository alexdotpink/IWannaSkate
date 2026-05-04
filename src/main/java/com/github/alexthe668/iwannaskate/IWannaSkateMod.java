package com.github.alexthe668.iwannaskate;

import com.github.alexthe668.iwannaskate.client.ClientProxy;
import com.github.alexthe668.iwannaskate.client.IWSClientConfig;
import com.github.alexthe668.iwannaskate.client.model.IWSModelLayers;
import com.github.alexthe668.iwannaskate.client.particle.IWSParticleRegistry;
import com.github.alexthe668.iwannaskate.server.CommonEvents;
import com.github.alexthe668.iwannaskate.server.CommonProxy;
import com.github.alexthe668.iwannaskate.server.IWSServerConfig;
import com.github.alexthe668.iwannaskate.server.block.IWSBlockRegistry;
import com.github.alexthe668.iwannaskate.server.blockentity.IWSBlockEntityRegistry;
import com.github.alexthe668.iwannaskate.server.entity.IWSEntityRegistry;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.misc.IWSAdvancements;
import com.github.alexthe668.iwannaskate.server.misc.IWSCreativeTabRegistry;
import com.github.alexthe668.iwannaskate.server.misc.IWSSoundRegistry;
import com.github.alexthe668.iwannaskate.server.network.SkateboardJumpMessage;
import com.github.alexthe668.iwannaskate.server.network.SkateboardKeyMessage;
import com.github.alexthe668.iwannaskate.server.network.SkateboardPartMessage;
import com.github.alexthe668.iwannaskate.server.network.SkateboardRackMessage;
import com.github.alexthe668.iwannaskate.server.potion.IWSEffectRegistry;
import com.github.alexthe668.iwannaskate.server.recipe.IWSRecipeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import static com.github.alexthe668.iwannaskate.server.misc.PlayerCapes.registerCapes;

@Mod(IWannaSkateMod.MODID)
public class IWannaSkateMod {
    public static final String MODID = "iwannaskate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CommonProxy PROXY = FMLEnvironment.dist.isClient() ? new ClientProxy() : new CommonProxy();
    private static final String PROTOCOL_VERSION = "1";
    public static final IWSServerConfig COMMON_CONFIG;
    private static final ModConfigSpec COMMON_CONFIG_SPEC;
    public static final IWSClientConfig CLIENT_CONFIG;
    private static final ModConfigSpec CLIENT_CONFIG_SPEC;

    static {
        final Pair<IWSServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(IWSServerConfig::new);
        COMMON_CONFIG = serverPair.getLeft();
        COMMON_CONFIG_SPEC = serverPair.getRight();
        final Pair<IWSClientConfig, ModConfigSpec> clientPair = new ModConfigSpec.Builder().configure(IWSClientConfig::new);
        CLIENT_CONFIG = clientPair.getLeft();
        CLIENT_CONFIG_SPEC = clientPair.getRight();
    }

    public IWannaSkateMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG_SPEC, "iwannaskate-common.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG_SPEC, "iwannaskate-client.toml");
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::setupEntityModelLayers);
        modEventBus.addListener(this::onConfigReloaded);
        IWSItemRegistry.DEF_REG.register(modEventBus);
        IWSBlockRegistry.DEF_REG.register(modEventBus);
        IWSCreativeTabRegistry.DEF_REG.register(modEventBus);
        IWSRecipeRegistry.DEF_REG.register(modEventBus);
        IWSEntityRegistry.DEF_REG.register(modEventBus);
        IWSSoundRegistry.DEF_REG.register(modEventBus);
        IWSParticleRegistry.DEF_REG.register(modEventBus);
        IWSEffectRegistry.DEF_REG.register(modEventBus);
        IWSBlockEntityRegistry.DEF_REG.register(modEventBus);
        IWSAdvancements.DEF_REG.register(modEventBus);
        NeoForge.EVENT_BUS.register(new CommonEvents());
        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.register(PROXY);
        }
        PROXY.init(modEventBus);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        PROXY.clientInit();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        registerCapes();
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned(PROTOCOL_VERSION);
        registrar.playBidirectional(SkateboardPartMessage.TYPE, SkateboardPartMessage.STREAM_CODEC, SkateboardPartMessage.Handler::handle);
        registrar.playBidirectional(SkateboardKeyMessage.TYPE, SkateboardKeyMessage.STREAM_CODEC, SkateboardKeyMessage.Handler::handle);
        registrar.playBidirectional(SkateboardRackMessage.TYPE, SkateboardRackMessage.STREAM_CODEC, SkateboardRackMessage.Handler::handle);
        registrar.playBidirectional(SkateboardJumpMessage.TYPE, SkateboardJumpMessage.STREAM_CODEC, SkateboardJumpMessage.Handler::handle);
    }

    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            IWSBlockEntityRegistry.SKATEBOARD_RACK.get(),
            (blockEntity, side) -> blockEntity.getItemHandler()
        );
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static void sendNonLocal(CustomPacketPayload msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, msg);
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        IWSModelLayers.register(event);
    }

    private void onConfigReloaded(final ModConfigEvent.Reloading configEvent) {
        PROXY.reloadConfig();
    }

    public static void sendMSGToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }
}
