package dev.tobynguyen27.codebebelib;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeBebeLib implements ModInitializer {
    public static String MOD_ID = "codebebelib";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Bebe!");
    }
}
