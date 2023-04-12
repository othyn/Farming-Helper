package com.farminghelper.speaax;


import java.awt.*;

public class TeleportOption {

    public class Teleport {

        public boolean require_house_teleport = false;

        String[] category = {"GAME_OBJECT", "ITEMS", "DECORATIVE_OBJECT"};
        String[] color = {"brightBlue", "brightGreen"};

        int id = 0;
        int interfaceGroupId = 0;
        int interfaceChildId = 0;
        String description = "";
    }
}