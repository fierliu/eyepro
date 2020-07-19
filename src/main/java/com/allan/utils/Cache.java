package com.allan.utils;

import com.allan.domain.Config;

/**
 * 内存工具
 */
public class Cache {

    private static Cache cache;

    public static Cache getCache() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    private Config configCache;

    public Config getConfigCache() {
        return configCache;
    }

    public void setConfigCache(Config configCache) {
        this.configCache = configCache;
    }
}
