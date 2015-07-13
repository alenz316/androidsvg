package com.steadfastinnovation.androidsvg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.util.Log;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

public class PatternedBackground {

    private Picture mSwatch;
    private boolean mStretchX;
    private boolean mStretchY;
    private float mWidth;
    private float mHeight;
    private float mTopMargin;
    private float mLeftMargin;

    private PatternedBackground() {
    }

    public PatternedBackground(Picture swatch, float width, float height, boolean stretchX,
                               boolean stretchY, float topMargin, float leftMargin) {
        this.mSwatch = swatch;
        this.mWidth = width;
        this.mHeight = height;
        this.mStretchX = stretchX;
        this.mStretchY = stretchY;
        this.mTopMargin = topMargin;
        this.mLeftMargin = leftMargin;
    }

    public void draw(Canvas canvas, float zoom, float offsetX, float offsetY) {
        canvas.save();

        float swatchWidth = mWidth;//mSwatch.getWidth();
        float swatchHeight = mHeight;//mSwatch.getHeight();
        float swatchScaleX = zoom;
        float swatchScaleY = zoom;

        if (mStretchX) {
            if (zoom < 1f) {
                swatchScaleX = (canvas.getWidth() / (float) mSwatch.getWidth()) / zoom;
            } else {
                swatchScaleX = (canvas.getWidth() / (float) mSwatch.getWidth()) * zoom;
            }

            // Stretching means swatch width is the canvas width
            swatchWidth = canvas.getWidth();
        }

        if (mStretchY) {
            if (zoom < 1f) {
                swatchScaleY = (canvas.getHeight() / (float) mSwatch.getHeight()) / zoom;
            } else {
                swatchScaleY = (canvas.getHeight() / (float) mSwatch.getHeight()) * zoom;
            }

            // Stretching means swatch height is the canvas height
            swatchHeight = canvas.getHeight();
        }

        final float tileScaledWidth = mStretchX ? swatchWidth : zoom * swatchWidth;
        final int xIterations = (int) Math.ceil(canvas.getWidth() / tileScaledWidth);

        final float tileScaledHeight = mStretchY ? swatchHeight : zoom * swatchHeight;
        final int yIterations = (int) Math.ceil(canvas.getHeight() / tileScaledHeight);


        final float swatchXOffset = mStretchX ? 0f : (offsetX % tileScaledWidth);
        final float swatchYOffset = mStretchY ? 0f : (offsetY % tileScaledHeight);

        Log.e("SVGers", "Size: " + canvas.getWidth() + " , " + canvas.getHeight());
        Log.e("SVGers", "width: " + tileScaledWidth + " iterations: " + xIterations);
        Log.e("SVGers", "height: " + tileScaledHeight + " iterations: " + yIterations);

        canvas.translate(-swatchXOffset, -swatchYOffset);
        canvas.scale(swatchScaleX, swatchScaleY);
        for (int x = 0; x <= xIterations; x++) {
            canvas.save();
            for (int y = 0; y <= yIterations; y++) {
                mSwatch.draw(canvas);
                canvas.translate(0, swatchHeight);
            }
            canvas.restore();
            canvas.translate(swatchWidth, 0);
        }

        canvas.restore();
    }

    public static class Builder {
        private Picture mSwatch;
        private boolean mStretchX;
        private boolean mStretchY;
        private float mWidth;
        private float mHeight;
        private float mTopMargin;
        private float mLeftMargin;

        final float DENSITY;
        final float PIXELS_PER_CENTIMETER;

        public Builder(Context c) {
            DENSITY = c.getResources().getDisplayMetrics().xdpi;
            PIXELS_PER_CENTIMETER = DENSITY / 2.54f;
        }

        public Builder setTopMargin(float lopMargin) {
            this.mTopMargin = lopMargin;
            return this;
        }

        public Builder setLeftMargin(float leftMargin) {
            this.mLeftMargin = leftMargin;
            return this;
        }


        public Builder setSvg(String svgString) throws SVGParseException {
            return setSvg(svgString, -1, -1);
        }

        public Builder setSvg(String svgString, boolean stretchX,
                              boolean stretchY) throws SVGParseException {
            return setSvg(svgString, stretchX ? -1 : 0, stretchY ? -1 : 0);
        }

        public Builder setSvg(String svgString, float widthCm,
                              float heightCm) throws SVGParseException {
            SVG svg = SVG.getFromString(svgString);
            svg.setRenderDPI(DENSITY);

            mStretchX = widthCm < 0;
            mStretchY = heightCm < 0;
            if (mStretchX || mStretchY) {
                // Ignore if one dimension is set
                mSwatch = svg.renderToPicture();
                mWidth = mSwatch.getWidth();
                mHeight = mSwatch.getHeight();
            } else {
                mWidth = widthCm * PIXELS_PER_CENTIMETER;
                mHeight = heightCm * PIXELS_PER_CENTIMETER;
                mSwatch = svg.renderToPicture((int) Math.ceil(mWidth), (int) Math.ceil(mHeight));
            }

            return this;
        }

        public PatternedBackground build() {
            return new PatternedBackground(mSwatch, mWidth, mHeight, mStretchX, mStretchY, mTopMargin, mLeftMargin);
        }
    }
}