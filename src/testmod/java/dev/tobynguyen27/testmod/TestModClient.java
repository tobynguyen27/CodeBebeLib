package dev.tobynguyen27.testmod;

import dev.tobynguyen27.codebebelib.BebeClient;
import dev.tobynguyen27.testmod.blockentities.PedestalEntityRenderer;
import dev.tobynguyen27.testmod.blockentities.TestModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class TestModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BebeClient.initialize();

        BlockEntityRendererRegistry.register(TestModBlockEntities.PEDESTAL_ENTITY, PedestalEntityRenderer::new);
    }
}