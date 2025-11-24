package dev.tobynguyen27.testmod;

import dev.tobynguyen27.testmod.blockentities.TestModBlockEntities;
import dev.tobynguyen27.testmod.blocks.TestModBlocks;
import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer {
    public static String MOD_ID = "testmod";

    @Override
    public void onInitialize() {
        TestModBlocks.init();
        TestModBlockEntities.init();
    }
}