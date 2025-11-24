package dev.tobynguyen27.testmod.blockentities;

import dev.tobynguyen27.testmod.blocks.TestModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static dev.tobynguyen27.testmod.TestMod.MOD_ID;

public class TestModBlockEntities {
    public static BlockEntityType<AssemblerBlockEntity> ASSEMBLER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation(MOD_ID, "assembler_entity"), FabricBlockEntityTypeBuilder.create(AssemblerBlockEntity::new, TestModBlocks.ASSEMBLER).build());

    public static void init() {}
}
