package com.adarsh.notes.features.explorer;


import java.util.UUID;

/**
 * Created by adars on 10/20/2017.
 */

public interface Screen {
    void cachePresenter(Presenter presenter);
    void restorePresenter(UUID uuid);
}
