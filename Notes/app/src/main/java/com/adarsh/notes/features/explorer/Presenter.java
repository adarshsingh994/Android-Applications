package com.adarsh.notes.features.explorer;

import java.util.UUID;

/**
 * Created by adars on 10/20/2017.
 */

public interface Presenter {

    /*
     The method is used to restore the view
     reference in the presenter, after an
     orientation change
     */
    void setScreen(Screen screen);

    /*
     The UUID is used to save and restore
     the presenter instance during an
     orientation change
     */
    UUID getUuid();
}
