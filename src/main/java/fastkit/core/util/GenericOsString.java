package fastkit.core.util;

import fastkit.core.util.Os;

public  abstract class GenericOsString {
    public abstract String gnulinux();
    public abstract String macos();
    public abstract String windows();

    public String get() {
        Os os = new Os();

        switch (os.getOs()) {
            case gnulinux: {
                return gnulinux();
            }

            case macos: {
                return macos();
            }

            case windows: {
                return windows();
            }
        }
        return null;
    }
}
