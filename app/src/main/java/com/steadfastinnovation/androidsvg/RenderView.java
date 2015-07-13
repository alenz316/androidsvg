package com.steadfastinnovation.androidsvg;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.util.ArrayList;

public class RenderView extends View {

    private ArrayList<PatternedBackground> mSwatches = new ArrayList<>();
    private SvgBackground mSvg;

    //    PortableBackground pb;
    private float zoom = 1f;
    private float offSetX = 0f;
    private float offSetY = 0f;

    private static final String LINES_HOR_SVG = "<?xml version=\"1.0\" standalone=\"no\"?>\n" +
            "<svg width=\"100%\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
            " <defs>\n" +
            "   <pattern id=\"Pattern\" x=\"0\" y=\"0\" width=\"100%\" height=\"0.74cm\" patternUnits=\"userSpaceOnUse\">\n" +
            "     <rect x=\"0\" y=\"0\" width=\"100%\" height=\"0.03cm\" fill=\"skyblue\"/>\n" +
            "   </pattern>\n" +
            " </defs>\n" +
            "\n" +
            " <rect fill=\"url(#Pattern)\" x=\"0\" y=\"0\" width=\"100%\" height=\"0.74cm\"/>\n" +
            "</svg>";

    private static final String LINES_VERT_SVG = "<?xml version=\"1.0\" standalone=\"no\"?>\n" +
            "<svg width=\"100%\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
            " <defs>\n" +
            "   <pattern id=\"Pattern\" x=\"0\" y=\"0\" width=\"100%\" height=\"0.74cm\" patternUnits=\"userSpaceOnUse\">\n" +
            "     <rect x=\"0\" y=\"0\" width=\"0.03cm\" height=\"100%\" fill=\"skyblue\"/>\n" +
            "   </pattern>\n" +
            " </defs>\n" +
            "\n" +
            " <rect fill=\"url(#Pattern)\" x=\"0\" y=\"0\" width=\"100%\" height=\"0.74cm\"/>\n" +
            "</svg>";

    private static final String ISO_SVG = "<?xml version=\"1.0\" standalone=\"no\"?>\n" +
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
            " <rect fill=\"url(#Pattern)\" x=\"0\" y=\"0\" width=\"1cm\" height=\"2cm\"/>\n" +
            "</svg>";


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
            mSwatches.add(new PatternedBackground.Builder(c)
//                    .setSvg(ISO_SVG, 1f, 2f)
                    .setSvg(LINES_HOR_SVG, true, false)
                    .build());
            mSwatches.add(new PatternedBackground.Builder(c)
//                    .setSvg(ISO_SVG, 1f, 2f)
                    .setSvg(LINES_VERT_SVG, false, true)
                    .build());
            mSvg = new SvgBackground(c, "drawing-1.svg");
        } catch (SVGParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long time = System.currentTimeMillis();

//        for (PatternedBackground pb : mSwatches) {
//            pb.draw(canvas, zoom, offSetX, offSetY);
//        }
        mSvg.draw(canvas, zoom, offSetX, offSetY);

        Log.e("SVGers", "Zoom: " + zoom + " Time(s): " + ((System.currentTimeMillis() - time) / 1000f));
        super.onDraw(canvas);
    }

    public void scale(float zoom) {
        this.zoom *= zoom;
        invalidate();
    }

    public void translateX(float trans) {
        this.offSetX += trans;
        invalidate();
    }

    public void translateY(float trans) {
        this.offSetY += trans;
        invalidate();
    }
}
