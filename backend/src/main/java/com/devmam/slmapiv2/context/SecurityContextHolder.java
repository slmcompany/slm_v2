package com.devmam.slmapiv2.context;

public class SecurityContextHolder {
    private static final ThreadLocal<String> pathContext = new ThreadLocal<>();

    public static void setPath(String path) {
        pathContext.set(path);
    }
    public static String getPath() {
        return pathContext.get();
    }
    public static void clear() {
        pathContext.remove();
    }
}
