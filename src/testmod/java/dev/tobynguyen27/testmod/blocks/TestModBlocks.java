package dev.tobynguyen27.testmod.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static dev.tobynguyen27.testmod.TestMod.MOD_ID;

public class TestModBlocks {
    public static Block CASING = Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "casing"), new CasingBlock());
    public static Item CASING_BLOCK_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "casing"), new BlockItem(CASING, new FabricItemSettings()));

    public static Block ASSEMBLER = Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "assembler"), new AssemblerBlock());
    public static Item ASSEMBLER_BLOCK_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "assembler"), new BlockItem(ASSEMBLER, new FabricItemSettings()));

    public static void init() {}
}
