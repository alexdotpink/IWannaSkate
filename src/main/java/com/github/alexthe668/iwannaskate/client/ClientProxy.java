package com.github.alexthe668.iwannaskate.client;

import com.github.alexthe668.iwannaskate.IWannaSkateMod;
import com.github.alexthe668.iwannaskate.client.color.BoardColorSampler;
import com.github.alexthe668.iwannaskate.client.gui.SkateManualScreen;
import com.github.alexthe668.iwannaskate.client.model.ModelRootRegistry;
import com.github.alexthe668.iwannaskate.client.particle.*;
import com.github.alexthe668.iwannaskate.client.render.IWSRenderTypes;
import com.github.alexthe668.iwannaskate.client.render.blockentity.SkateboardRackRenderer;
import com.github.alexthe668.iwannaskate.client.render.entity.SkateboardRenderer;
import com.github.alexthe668.iwannaskate.client.render.entity.SkaterSkeletonRenderer;
import com.github.alexthe668.iwannaskate.client.render.entity.WanderingSkaterRenderer;
import com.github.alexthe668.iwannaskate.client.render.item.IWSItemArmorProperties;
import com.github.alexthe668.iwannaskate.client.render.item.IWSItemRenderProperties;
import com.github.alexthe668.iwannaskate.client.render.item.IWSItemstackRenderer;
import com.github.alexthe668.iwannaskate.client.sound.SkateSoundType;
import com.github.alexthe668.iwannaskate.client.sound.SkateboardSound;
import com.github.alexthe668.iwannaskate.server.CommonProxy;
import com.github.alexthe668.iwannaskate.server.blockentity.IWSBlockEntityRegistry;
import com.github.alexthe668.iwannaskate.server.entity.IWSEntityRegistry;
import com.github.alexthe668.iwannaskate.server.entity.SkateboardEntity;
import com.github.alexthe668.iwannaskate.server.item.DyeableHatItem;
import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.github.alexthe668.iwannaskate.server.potion.IWSEffectRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

    public static final Map<Integer, SkateboardSound> SKATEBOARD_SOUND_MAP = new HashMap<>();
    protected static final ResourceLocation OVERCAFFENIATED_OVERLAY = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "textures/gui/overcaffeniated_overlay.png");
    private static final ResourceLocation SKATEBOARD_INDICATOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(IWannaSkateMod.MODID, "textures/gui/skateboard_peddle_indicator.png");

    private static float prevCameraRoll = 0;
    private static float cameraRoll = 0;

    public static void onTexturesLoaded(TextureAtlasStitchedEvent event) {
        BoardColorSampler.sampleColorsOnLoad();
    }

    public static void setupItemColors(RegisterColorHandlersEvent.Item event) {
        IWannaSkateMod.LOGGER.info("loaded in item colorizer");
        event.register((stack, colorIn) -> colorIn != 0 ? -1 : ((DyeableHatItem) stack.getItem()).getColor(stack), IWSItemRegistry.BEANIE.get());
        event.register((stack, colorIn) -> colorIn != 0 ? -1 : ((DyeableHatItem) stack.getItem()).getColor(stack), IWSItemRegistry.SKATER_CAP.get());
    }

    public static void setupParticles(RegisterParticleProvidersEvent registry) {
        IWannaSkateMod.LOGGER.debug("Registered particle factories");
        registry.registerSpriteSet(IWSParticleRegistry.HALLOWEEN.get(), HalloweenParticle.Factory::new);
        registry.registerSpriteSet(IWSParticleRegistry.BEE.get(), BeeParticle.Factory::new);
        registry.registerSpecial(IWSParticleRegistry.HOVER.get(), new HoverParticle.Factory());
        registry.registerSpriteSet(IWSParticleRegistry.SPARKLE.get(), SparkleParticle.Factory::new);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event){
        IWSItemstackRenderer.tick();
    }

    @SubscribeEvent
    public void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event){
        if(Minecraft.getInstance().player.getVehicle() instanceof SkateboardEntity skateboard && IWannaSkateMod.CLIENT_CONFIG.rotateCameraOnBoard.get()){
            event.setRoll(getSkateboardCameraRot((float) event.getPartialTick()) * 0.25F);
        }
    }

    @SubscribeEvent
    public void onPreRenderGuiOverlay(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(VanillaGuiLayers.EXPERIENCE_BAR) && IWannaSkateMod.CLIENT_CONFIG.hideExperienceBar.get() && getClientSidePlayer().getVehicle() instanceof SkateboardEntity skateboard) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPostRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        if (event.getName().equals(VanillaGuiLayers.JUMP_METER) && getClientSidePlayer().getVehicle() instanceof SkateboardEntity skateboard) {
            int screenWidth = event.getGuiGraphics().guiWidth();
            int screenHeight = event.getGuiGraphics().guiHeight();
            if(IWannaSkateMod.CLIENT_CONFIG.showInertiaIndicator.get()){
                int j = screenWidth / 2 - IWannaSkateMod.CLIENT_CONFIG.inertiaIndicatorX.get();
                int k = screenHeight - IWannaSkateMod.CLIENT_CONFIG.inertiaIndicatorY.get();
                float f = skateboard.getForwards() / skateboard.getMaxForwardsTicks();
                event.getGuiGraphics().pose().pushPose();
                event.getGuiGraphics().blit(SKATEBOARD_INDICATOR_TEXTURE, j, k, 50, 0, 0, 29, 9, 64, 64);
                event.getGuiGraphics().blit(SKATEBOARD_INDICATOR_TEXTURE, j, k, 50, 0, 9, Math.round(29 * f), 9, 64, 64);
                event.getGuiGraphics().pose().popPose();
            }
        }
        if (event.getName().equals(VanillaGuiLayers.CAMERA_OVERLAYS) && Minecraft.getInstance().player.hasEffect(IWSEffectRegistry.OVERCAFFEINATED) && IWannaSkateMod.CLIENT_CONFIG.overcaffeniatedOverlay.get()) {
            int screenWidth = event.getGuiGraphics().guiWidth();
            int screenHeight = event.getGuiGraphics().guiHeight();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.setShaderTexture(0, OVERCAFFENIATED_OVERLAY);
            event.getGuiGraphics().blit(OVERCAFFENIATED_OVERLAY, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public void onComputeFOV(ComputeFovModifierEvent event) {
        if (Minecraft.getInstance().player.hasEffect(IWSEffectRegistry.OVERCAFFEINATED) && IWannaSkateMod.CLIENT_CONFIG.overcaffeniatedOverlay.get()) {
            event.setNewFovModifier(event.getFovModifier() + 1);
        }
    }

    public void onRegisterClientReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ModelRootRegistry.INSTANCE);
    }

    public void init(IEventBus modEventBus) {
        modEventBus.addListener(ClientProxy::setupParticles);
        modEventBus.addListener(ClientProxy::setupItemColors);
        modEventBus.addListener(ClientProxy::onTexturesLoaded);
        modEventBus.addListener(this::onRegisterClientReloadListener);
    }

    public void clientInit() {
        EntityRenderers.register(IWSEntityRegistry.SKATEBOARD.get(), SkateboardRenderer::new);
        EntityRenderers.register(IWSEntityRegistry.SKATER_SKELETON.get(), SkaterSkeletonRenderer::new);
        EntityRenderers.register(IWSEntityRegistry.WANDERING_SKATER.get(), WanderingSkaterRenderer::new);
        BlockEntityRenderers.register(IWSBlockEntityRegistry.SKATEBOARD_RACK.get(), SkateboardRackRenderer::new);
    }

    public Object getISTERProperties() {
        return new IWSItemRenderProperties();
    }

    public Object getArmorRenderProperties() {
        return new IWSItemArmorProperties();
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    public boolean isKeyDown(int keyType) {
        if (keyType == 0) {
            return Minecraft.getInstance().options.keySprint.isDown();
        }
        if (keyType == 1) {
            return Screen.hasShiftDown();
        }
        return false;
    }

    @Override
    public void onEntityStatus(Entity entity, byte updateKind) {
        if (entity instanceof SkateboardEntity skateboard && entity.isAlive() && updateKind == 67 && IWannaSkateMod.CLIENT_CONFIG.skateboardLoopSounds.get()) {
            SkateboardSound sound;
            if (SKATEBOARD_SOUND_MAP.get(entity.getId()) == null || SKATEBOARD_SOUND_MAP.get(entity.getId()).isDifferentBoard(entity)) {
                sound = new SkateboardSound(SkateSoundType.getForSkateboard(skateboard), 0.0F, skateboard);
                SKATEBOARD_SOUND_MAP.put(entity.getId(), sound);
            } else {
                sound = SKATEBOARD_SOUND_MAP.get(entity.getId());
            }
            if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound()) {
                Minecraft.getInstance().getSoundManager().play(sound);
            }
        }
    }

    public void reloadConfig() {
    }

    public void openBookGUI(ItemStack book) {
        Minecraft.getInstance().setScreen(new SkateManualScreen(book));

    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent.Post event) {
        float targetRot = 0;
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() instanceof SkateboardEntity skateboard && IWannaSkateMod.CLIENT_CONFIG.rotateCameraOnBoard.get()) {
            float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
            targetRot = skateboard.getZRot(partialTick);
            if (Math.abs(targetRot) <= 1.0F) {
                targetRot = 0;
            }
        }
        prevCameraRoll = cameraRoll;
        cameraRoll = targetRot;
    }


    public static float getSkateboardCameraRot(float partialTicks){
        return prevCameraRoll + (cameraRoll - prevCameraRoll) * partialTicks;
    }

}
