package com.steadfastinnovation.androidsvg;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

public class RenderView extends View {

    private SVG svg;

    public RenderView(Context context) {
        super(context);
        initialize(context);
    }

    public RenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public RenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RenderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initialize(Context c) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        try {
            svg = SVG.getFromString("<?xml version=\"1.0\" standalone=\"no\"?>\n" +
                    "<svg width=\"100%\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
                    " <defs>\n" +
                    "   <pattern id=\"Pattern\" x=\"0\" y=\"0\" width=\"1cm\" height=\"2cm\" patternUnits=\"userSpaceOnUse\">\n" +
                    "      <line x1=\"0\" y1=\"0\" x2=\"1cm\" y2=\"0\" stroke=\"black\"/>\n" +
                    "      <line x1=\"0\" y1=\"2cm\" x2=\"1cm\" y2=\"2cm\" stroke=\"black\"/>\n" +
                    "      <line x1=\"0\" y1=\"0\" x2=\"1cm\" y2=\"2cm\" stroke=\"black\"/>\n" +
                    "      <line x1=\"1cm\" y1=\"0\" x2=\"0\" y2=\"2cm\" stroke=\"black\"/>\n" +
                    "      <line x1=\"0\" y1=\"1cm\" x2=\"1cm\" y2=\"1cm\" stroke=\"black\"/>\n" +
                    "   </pattern>\n" +
                    " </defs>\n" +
                    "\n" +
                    " <rect fill=\"url(#Pattern)\" x=\"0\" y=\"0\" width=\"100%\" height=\"100%\"/>\n" +
                    "</svg>");
            svg.setRenderDPI(c.getResources().getDisplayMetrics().xdpi);

        } catch (SVGParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
        svg.renderToCanvas(canvas);
        super.onDraw(canvas);
    }
}
