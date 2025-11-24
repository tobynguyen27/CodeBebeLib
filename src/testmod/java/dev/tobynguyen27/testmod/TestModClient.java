package dev.tobynguyen27.testmod;

import dev.tobynguyen27.codebebelib.BebeClient;
import net.fabricmc.api.ClientModInitializer;

public class TestModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BebeClient.initialize();
    }
}