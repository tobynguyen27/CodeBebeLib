package dev.tobynguyen27.codebebelib;

import dev.tobynguyen27.codebebelib.raytracer.VoxelShapeBlockHitResult;
import dev.tobynguyen27.codebebelib.render.RenderUtils;
import dev.tobynguyen27.codebebelib.vec.Matrix4;
import lombok.Getter;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;

public class BebeClient {
    /**
     * Start calculating since client initialized game.
     * <p>
     * Under ideal conditions, this would increase by 20 per second.
     */
    @Getter
    private static int renderTime;

    /**
     * Start from 0 (when a frame is being drawn) to 1 (when drawing done).
     * <p>
     * If you only use renderTime, the animation is not smooth on >20Hz monitor.
     */
    @Getter
    private static float renderFrame;

    /**
     * Must be initialized in client-side before using CodeBebeLib
     */
    public static void initialize() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            renderTime++;
        });

        WorldRenderEvents.START.register(context -> {
            renderFrame = context.tickDelta();
        });

        WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            HitResult target = Minecraft.getInstance().hitResult;

            if (target instanceof VoxelShapeBlockHitResult voxelHit) {
                Matrix4 mat = new Matrix4(worldRenderContext.matrixStack());
                mat.translate(voxelHit.getBlockPos());

                RenderUtils.bufferShapeHitBox(
                        mat, worldRenderContext.consumers(), worldRenderContext.camera(), voxelHit.shape);

                return false;
            }

            return true;
        });
    }
}
