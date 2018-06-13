package org.yang.zhang.config;

import de.felixroske.jfxsupport.SplashScreen;

public class CustomSplash extends SplashScreen {
    /**
     * Use your own splash image instead of the default one
     *
     * @return "/splash/javafx.png"
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Customize if the splash screen should be visible at all
     *
     * @return true by default
     */
    @Override
    public boolean visible() {
        return false;
    }
}
