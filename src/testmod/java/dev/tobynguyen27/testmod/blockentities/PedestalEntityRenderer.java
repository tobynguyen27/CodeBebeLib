package dev.tobynguyen27.testmod.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tobynguyen27.codebebelib.math.MathHelper;
import dev.tobynguyen27.codebebelib.render.RenderUtils;
import dev.tobynguyen27.codebebelib.utils.ClientUtils;
import dev.tobynguyen27.codebebelib.utils.RotationUtils;
import dev.tobynguyen27.codebebelib.vec.Matrix4;
import dev.tobynguyen27.codebebelib.vec.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalEntityRenderer implements BlockEntityRenderer<PedestalEntity> {
    public PedestalEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PedestalEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        int worldLight = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().above());

        ItemStack goldIngot = Items.GOLD_INGOT.getDefaultInstance();

        // This is optional but using it will make each animation from each block has a little bit different.
        double offsetTime = RenderUtils.getTimeOffset(blockEntity.getBlockPos());

        poseStack.pushPose();

        Matrix4 mat = new Matrix4(poseStack);

        // Make item bobbing
        float bobHeight = RenderUtils.getPearlBob(ClientUtils.getRenderTime() + offsetTime);
        mat.translate(0.5, 1.5 + bobHeight, 0.5);

        // Add spinning animation

        // Angel = {[(time + offset) % cycleTicks ] / cycleTicks} * 2pi
        // cycleTicks = secondsPerRotation * 20
        double cycleTicks = 5 * 20; // 5s for 1 spin

        double angel = (((ClientUtils.getRenderTime() + offsetTime) % cycleTicks) / cycleTicks) * 2 * Math.PI;
        mat.rotate(angel, Vector3.Y_POS);

        mat.scale(0.75f);

        poseStack.last().pose().load(mat.toMatrix4f());

        Minecraft.getInstance().getItemRenderer().renderStatic(goldIngot,
                ItemTransforms.TransformType.FIXED, worldLight, packedOverlay, poseStack, bufferSource, (int) blockEntity.getBlockPos().asLong());

        poseStack.popPose();
    }
}
