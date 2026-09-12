package at.pavlov.cannons.multiversion;

import lombok.Getter;
import org.bukkit.Bukkit;

public final class VersionHandler {
    @Getter
    private static int[] version;

    static {
        initVersion();
    }

    private VersionHandler() {}

    private static void initVersion() {
        // Bukkit.getBukkitVersion() changed format on modern Paper versions
        // (e.g. 26.2.build.123-stable). Use the Minecraft version directly;
        // Paper documents Bukkit.getMinecraftVersion() as the API for this.
        var temp = Bukkit.getServer().getMinecraftVersion().split("\\.");
        version = new int[] {0, 0, 0};

        for (int i = 0; i < Math.min(temp.length, version.length); i++) {
            try {
                version[i] = Integer.parseInt(temp[i]);
            } catch (NumberFormatException ignored) {
                // Keep the default value for an unexpected version component.
            }
        }
    }

    public static boolean isGreaterThan1_20_5() {
        // Minecraft changed its versioning scheme to 26.x in 2026.
        // Any major version above 1 is newer than 1.20.5.
        if (version[0] > 1) {
            return true;
        }

        if (version[0] < 1) {
            return false;
        }

        if (version[1] >= 21) {
            return true;
        }

        return version[1] == 20 && version[2] >= 5;
    }
}
