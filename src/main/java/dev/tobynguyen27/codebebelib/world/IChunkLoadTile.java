package dev.tobynguyen27.codebebelib.world;

import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Provide a callback for block entity when a chunk is loaded
 */
public interface IChunkLoadTile {

    void onChunkLoad(LevelChunk chunk);
}
