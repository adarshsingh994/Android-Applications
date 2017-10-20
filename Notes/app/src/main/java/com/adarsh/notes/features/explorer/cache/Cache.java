package com.adarsh.notes.features.explorer.cache;

import com.adarsh.notes.features.explorer.Presenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by adars on 10/20/2017.
 */

public class Cache implements Serializable {
    private static Cache ourInstance;
    private Map<UUID, Presenter> cache;

    public static Cache getInstance() {
        if (ourInstance == null) {
            ourInstance = new Cache();
        }
        return ourInstance;
    }

    private Cache() {
        cache = new HashMap<>();
    }

    public void cachePresenterFor(UUID uuid, Presenter presenter) {
        cache.put(uuid, presenter);
    }

    public Presenter restorePresenterFor(UUID uuid) {
        Presenter presenter = cache.get(uuid);
        cache.remove(presenter);
        return presenter;
    }
}
