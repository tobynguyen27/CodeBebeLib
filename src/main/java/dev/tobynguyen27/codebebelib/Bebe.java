package dev.tobynguyen27.codebebelib;

import dev.tobynguyen27.codebebelib.utils.ServerUtils;
import dev.tobynguyen27.codebebelib.world.TileChunkLoadHook;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class Bebe {
    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(ServerUtils::setServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> ServerUtils.setServer(null));
        ServerChunkEvents.CHUNK_LOAD.register((serverLevel, levelChunk) -> {
            TileChunkLoadHook.onChunkLoad(levelChunk);
        });
    }
}
