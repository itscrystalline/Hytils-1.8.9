package com.iwant2tryhard.hytils.core.misc;

import java.awt.*;

public class BetterColor {
    public final static BetterColor WHITE = new BetterColor(255, 255, 255);
    public final static BetterColor LIGHT_GRAY = new BetterColor(192, 192, 192);
    public final static BetterColor GRAY = new BetterColor(128, 128, 128);
    public final static BetterColor DARK_GRAY = new BetterColor(64, 64, 64);
    public final static BetterColor BLACK = new BetterColor(0, 0, 0);
    public final static BetterColor RED = new BetterColor(255, 0, 0);
    public final static BetterColor PINK = new BetterColor(255, 175, 175);
    public final static BetterColor ORANGE = new BetterColor(255, 200, 0);
    public final static BetterColor YELLOW = new BetterColor(255, 255, 0);
    public final static BetterColor GREEN = new BetterColor(0, 255, 0);
    public final static BetterColor MAGENTA = new BetterColor(255, 0, 255);
    public final static BetterColor CYAN = new BetterColor(0, 255, 255);
    public final static BetterColor BLUE = new BetterColor(0, 0, 255);
    private short r, g, b, a;

    public BetterColor(short r, short g, short b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public BetterColor(short r, short g, short b, short a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public BetterColor(int r, int g, int b) {
        this.r = (short) r;
        this.g = (short) g;
        this.b = (short) b;
    }

    public BetterColor(int r, int g, int b, int a) {
        this.r = (short) r;
        this.g = (short) g;
        this.b = (short) b;
        this.a = (short) a;
    }

    public BetterColor(Color color) {
        this.r = (short) color.getRed();
        this.g = (short) color.getGreen();
        this.b = (short) color.getBlue();
        this.a = (short) color.getAlpha();
    }

    public BetterColor(Color color, short alpha) {
        this.r = (short) color.getRed();
        this.g = (short) color.getGreen();
        this.b = (short) color.getBlue();
        this.a = alpha;
    }

    public short getRed() {
        return r;
    }

    public BetterColor setRed(short r) {
        this.r = r;
        return this;
    }

    public short getGreen() {
        return g;
    }

    public BetterColor setGreen(short g) {
        this.g = g;
        return this;
    }

    public short getBlue() {
        return b;
    }

    public BetterColor setBlue(short b) {
        this.b = b;
        return this;
    }

    public short getAlpha() {
        return a;
    }

    public BetterColor setAlpha(short a) {
        this.a = a;
        return this;
    }

    public BetterColor halfBrightness() {
        setAlpha((short) (getAlpha() / 2));
        return this;
    }

    public Color toColor() {
        return new Color(r, g, b, a);
    }
}
