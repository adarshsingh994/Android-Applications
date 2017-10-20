package com.adarsh.notes.features.init;

import java.util.UUID;

/**
 * Created by adars on 10/18/2017.
 */

public interface Screen {
    void cachePresenter(Presenter presenter);
    void restorePresenter(UUID uuid);
}
