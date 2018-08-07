package com.bf.portugo.factory;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;

import com.bf.portugo.R;
import com.bf.portugo.util.ColourPalette;

public abstract class ColourPaletteFactory {
    /**
     * Automatically retrieve colours from the correct place with fallbacks.
     * This should be the method used to retrieve colours for the app.
     *
     * @param resources To get fallback colour from colours.xml
     * @return Colour palette used to theme the UI
     */
    public static ColourPalette createPalette(Resources resources)
    {
/*
        if (SiteManager.getInstance().getSiteActive() != null) {
            if (!TextUtils.isEmpty(SiteManager.getInstance().getSiteActive().getPrimaryColour())) {
                return createPalette(SiteManager.getInstance().getSiteActive().getPrimaryColour());
            }
        }
*/
        return createPalette(resources.getColor(R.color.colorPrimary));
    }

    public static ColourPalette createPalette(String colourHex)
    {
        return createPalette(Color.parseColor(colourHex));
    }

    /**
     * Create the colours necessary to style the app and create a more bespoke look for
     * a particular client based on a single colour.
     *
     * @param ofColour The colour to extract from
     * @return Palette of colours to use on the UI
     */
    public static ColourPalette createPalette(int ofColour)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(ofColour, hsv);
        //int mainCol = Color.HSVToColor(new float[]{hsv[0], hsv[1], 0.35f});
        int mainCol = Color.HSVToColor(new float[]{hsv[0], hsv[1], 0.9f});
        int backgroundCol = Color.HSVToColor(new float[]{hsv[0], hsv[1], 0.15f});
        return new ColourPalette(mainCol, backgroundCol);
    }
}
