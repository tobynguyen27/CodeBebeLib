package dev.tobynguyen27.testmod.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalEntity extends BlockEntity {
    public PedestalEntity(BlockPos pos, BlockState blockState) {
        super(TestModBlockEntities.PEDESTAL_ENTITY, pos, blockState);
    }
}
