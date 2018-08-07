package com.bf.portugo.util;

import android.graphics.Color;

/*
 * Model defining the elements needed to colour the app with.
 *
 * Constructors for this class only take one type and create the other type
 * of value from the information given.
 */

public class ColourPalette
{
    private String backgroundHex, mainHex;
    private int background, main;

    public ColourPalette(String mainHex, String backgroundHex)
    {
        if (!mainHex.contains("#"))
        {
            mainHex = "#" + mainHex;
        }

        if (!backgroundHex.contains("#"))
        {
            backgroundHex = "#" + backgroundHex;
        }

        this.mainHex = mainHex;
        this.backgroundHex = backgroundHex;
        this.background = Color.parseColor(backgroundHex);
        this.main = Color.parseColor(mainHex);
    }

    public ColourPalette(int mainCol, int backgroundCol)
    {
        this.main = mainCol;
        this.background = backgroundCol;
        this.backgroundHex = String.format("#%06X", (0xFFFFFF & backgroundCol));
        this.mainHex = String.format("#%06X", (0xFFFFFF & mainCol));
    }

    public String getBackgroundHex()
    {
        return backgroundHex;
    }

    public String getMainHex()
    {
        return mainHex;
    }

    public int getBackgroundColour()
    {
        return background;
    }

    public int getMainColour()
    {
        return main;
    }
}
