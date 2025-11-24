package dev.tobynguyen27.testmod.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AssemblerBlockEntity extends BlockEntity {
    public AssemblerBlockEntity(BlockPos pos, BlockState blockState) {
        super(TestModBlockEntities.ASSEMBLER_ENTITY, pos, blockState);
    }
}
