package com.steadfastinnovation.androidsvg;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;

public class SvgBackground {

    private SVG mSvg;
    final float DENSITY;
    final float PIXELS_PER_CENTIMETER;

    public SvgBackground(Context c, String assetFileName) throws SVGParseException, IOException {
        DENSITY = c.getResources().getDisplayMetrics().xdpi;
        PIXELS_PER_CENTIMETER = DENSITY / 2.54f;

        this.mSvg = SVG.getFromAsset(c.getAssets(), assetFileName);

        mSvg.setRenderDPI(DENSITY);
    }


    public void draw(Canvas canvas, float zoom, float offsetX, float offsetY) {
        canvas.save();
        canvas.translate(offsetX, offsetY);
        canvas.scale(zoom, zoom);
        mSvg.renderToCanvas(canvas);
        Log.e("TESTERS", "W: " + mSvg.getDocumentWidth() + " H: " + mSvg.getDocumentHeight());
        Log.e("TESTERS", "W: " + 5.08f*PIXELS_PER_CENTIMETER + " H: " + 2.54f*PIXELS_PER_CENTIMETER);
        canvas.restore();
    }
}
