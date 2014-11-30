package com.noa.connect.hook;

import java.util.TimerTask;

/**
 *
 * @author user
 *
 */
public abstract class HookAdapter extends TimerTask implements Hookable {

    @Override
    public void beforeDo() {
    }

    @Override
    public void run() {
        beforeDo();
        start();
        afterDo();
    }

    @Override
    public void afterDo() {
    }

    public abstract void start();

}
