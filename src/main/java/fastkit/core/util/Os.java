package fastkit.core.util;

import java.util.Locale;

public class Os {
    public enum listOs {
        gnulinux,
        macos,
        windows;
    }
    private boolean is_gnulinux = false;
    private boolean is_macos = false;
    private boolean is_windows = false;

    public Os() {
        String osinfo = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if (osinfo.contains("nux")) {
            this.is_gnulinux = true;
        }

        if (osinfo.contains("mac") || osinfo.contains("darwin")) {
            this.is_macos = true;
        }

        if (osinfo.contains("win")) {
            this.is_windows = true;
        }
    }

    public listOs getOs() {
        if (is_gnulinux) {
            return listOs.gnulinux;
        }

        if (is_macos) {
            return listOs.macos;
        }

        if (is_windows) {
            return listOs.windows;
        }

        return null;
    }
}

