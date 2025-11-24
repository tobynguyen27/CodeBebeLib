package dev.tobynguyen27.codebebelib.world;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public class TileChunkLoadHook {
    public static void onChunkLoad(LevelChunk chunk) {
        for (BlockEntity tile : chunk.getBlockEntities().values()) {
            if (tile instanceof IChunkLoadTile) {
                ((IChunkLoadTile) tile).onChunkLoad(chunk);
            }
        }
    }
}
