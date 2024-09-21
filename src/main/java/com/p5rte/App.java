package com.p5rte;

import com.p5rte.GUI.GUIManager;
import com.p5rte.Utils.Constants;

/**
 * JavaFX App
 */
public class App {

    public static void main(String[] args) {
        Constants.init();
        GUIManager.launch(GUIManager.class, args);
    }
}