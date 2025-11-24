package dev.tobynguyen27.codebebelib.utils;

import com.mojang.authlib.GameProfile;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * Created by covers1624 on 22/10/2016.
 */
public class ServerUtils {

    @Getter
    @Setter
    private static MinecraftServer server;

    @Deprecated
    public static ServerPlayer getPlayer(String playername) {
        return getServer().getPlayerList().getPlayerByName(playername);
    }

    public static List<ServerPlayer> getPlayers() {
        return getServer().getPlayerList().getPlayers();
    }

    public static boolean isPlayerLoadingChunk(ServerPlayer player, ChunkPos chunk) {
        return player.getLevel().getChunkSource().chunkMap.getPlayers(chunk, false).stream()
                .anyMatch(e -> e.getId() == player.getId());
    }

    public static Path getSaveDirectory() {
        return getSaveDirectory(Level.OVERWORLD);
    }

    public static Path getSaveDirectory(ResourceKey<Level> dimension) {
        return getServer().storageSource.getDimensionPath(dimension);
    }

    public static GameProfile getGameProfile(String username) {
        Player player = getPlayer(username);
        if (player != null) {
            return player.getGameProfile();
        }

        // try and access it in the cache without forcing a save
        username = username.toLowerCase(Locale.ROOT);
        GameProfileCache.GameProfileInfo cachedEntry =
                getServer().getProfileCache().profilesByName.get(username);
        if (cachedEntry != null) {
            return cachedEntry.getProfile();
        }

        // load it from the cache
        return getServer().getProfileCache().get(username).orElse(null);
    }

    public static boolean isPlayerOP(UUID uuid) {
        GameProfile profile = getServer().getProfileCache().get(uuid).orElse(null);
        return profile != null && getServer().getPlayerList().isOp(profile);
    }

    public static boolean isPlayerOP(String username) {
        GameProfile prof = getGameProfile(username);
        return prof != null && getServer().getPlayerList().isOp(prof);
    }
}
