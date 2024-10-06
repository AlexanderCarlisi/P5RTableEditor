package com.p5rte;

import com.p5rte.GUI.GUIManager;
import com.p5rte.Utils.FileStreamUtil;

/**
 * JavaFX App
 */
public class App {

    public static void main(String[] args) {
        FileStreamUtil.startLogger();
        GUIManager.launch(GUIManager.class, args);
    }
}