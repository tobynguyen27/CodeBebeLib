package dev.tobynguyen27.testmod.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static dev.tobynguyen27.testmod.TestMod.MOD_ID;

public class TestModBlocks {
    public static Block PEDESTAL = Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "pedestal"), new Pedestal());
    public static Item PEDESTAL_BLOCK_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "pedestal"), new BlockItem(PEDESTAL, new FabricItemSettings()));

    public static void init() {}
}
