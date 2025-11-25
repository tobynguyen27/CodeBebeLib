package dev.tobynguyen27.testmod.blocks;

import com.google.common.collect.ImmutableSet;
import dev.tobynguyen27.codebebelib.raytracer.VoxelShapeCache;
import dev.tobynguyen27.codebebelib.vec.Cuboid6;
import dev.tobynguyen27.testmod.blockentities.PedestalEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Pedestal extends BaseEntityBlock {

    public static ImmutableSet<VoxelShape> SHAPE = ImmutableSet.of(
            new Cuboid6(2 / 16d, 0 / 16d, 2 / 16d, 14 / 16d, 2 / 16d, 14 / 16d).shape(),
            new Cuboid6(4 / 16d, 2 / 16d, 4 / 16d, 12 / 16d, 14 / 16d, 12 / 16d).shape(),
            new Cuboid6(2 / 16d, 14 / 16d, 2 / 16d, 14 / 16d, 16 / 16d, 14 / 16d).shape()
    );

    protected Pedestal() {
        super(FabricBlockSettings.of(Material.STONE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VoxelShapeCache.merge(SHAPE);
    }
}
