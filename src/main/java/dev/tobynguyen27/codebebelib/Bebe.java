package dev.tobynguyen27.codebebelib;

import dev.tobynguyen27.codebebelib.utils.ServerUtils;
import dev.tobynguyen27.codebebelib.world.TileChunkLoadHook;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class Bebe {

    /**
     * Registers necessary listeners on server-side.
     * <p>
     * This function <b>must be invoked</b> before using CodeBebeLib components.
     * For example, {@link ServerUtils}, {@link TileChunkLoadHook} are required this function get invoked before using them.
     * <p>
     * This function is designed to be invoked exactly once during the mod lifecycle.
     */
    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(ServerUtils::setServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> ServerUtils.setServer(null));
        ServerChunkEvents.CHUNK_LOAD.register((serverLevel, levelChunk) -> {
            TileChunkLoadHook.onChunkLoad(levelChunk);
        });
    }
}
