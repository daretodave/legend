package com.legend.graphics;

public class Color {
	
	public static final int BLACK       = 0xFF000000;
    public static final int DKGRAY      = 0xFF444444;
    public static final int GRAY        = 0xFF888888;
    public static final int LTGRAY      = 0xFFCCCCCC;
    public static final int WHITE       = 0xFFFFFFFF;
    public static final int RED         = 0xFFFF0000;
    public static final int GREEN       = 0xFF00FF00;
    public static final int BLUE        = 0xFF0000FF;
    public static final int YELLOW      = 0xFFFFFF00;
    public static final int CYAN        = 0xFF00FFFF;
    public static final int MAGENTA     = 0xFFFF00FF;
    public static final int TRANSPARENT = 0;
    
    private static final float DEFAULT_FACTOR = 0.7F;

	public static final int darker(int color, float factor) {
		int r = Math.max((int) (red(color) * factor), 0);
		int g = Math.max((int) (green(color) * factor), 0);
		int b = Math.max((int) (blue(color) * factor), 0);
		int a = alpha(color);
		return Color.argb(a, r, g, b);
	}
	public static final int darker(int color) {
		return darker(color, DEFAULT_FACTOR);
	}
	public static final int brighter(int color) {
		return brighter(color, DEFAULT_FACTOR);
	}
	public static final int brighter(int color, float factor) {
		int r = red(color);
		int g = green(color);
		int b = blue(color);
		int a = alpha(color);
		int i = (int) (1.0 / (1.0 - factor));
		if (r == 0 && g == 0 && b == 0) {
			return argb(a, r, g, b);
		}
		if (r > 0 && r < i)
			r = i;
		if (g > 0 && g < i)
			g = i;
		if (b > 0 && b < i)
			b = i;
		r = Math.min((int) (r / factor), 255);
		g = Math.min((int) (g / factor), 255);
		b = Math.min((int) (b / factor), 255);
		return Color.argb(a, r, g, b);
	}
	public static int decode(String colorString) {
        if (colorString.charAt(0) == '#') {
            long color = Long.parseLong(colorString.substring(1), 16);
            if (colorString.length() == 7) {
                color |= 0x00000000ff000000;
            } else if (colorString.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int)color;
        }
        throw new IllegalArgumentException("Unknown color");
    }
	public static int HSBtoColor(float h, float s, float b) {
        h = constrain(h, 0.0f, 1.0f);
        s = constrain(s, 0.0f, 1.0f);
        b = constrain(b, 0.0f, 1.0f);
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;
        final float hf = (h - (int) h) * 6.0f;
        final int ihf = (int) hf;
        final float f = hf - ihf;
        final float pv = b * (1.0f - s);
        final float qv = b * (1.0f - s * f);
        final float tv = b * (1.0f - s * (1.0f - f));
        switch (ihf) {
            case 0:         // Red is the dominant color
                red = b;
                green = tv;
                blue = pv;
                break;
            case 1:         // Green is the dominant color
                red = qv;
                green = b;
                blue = pv;
                break;
            case 2:
                red = pv;
                green = b;
                blue = tv;
                break;
            case 3:         // Blue is the dominant color
                red = pv;
                green = qv;
                blue = b;
                break;
            case 4:
                red = tv;
                green = pv;
                blue = b;
                break;
            case 5:         // Red is the dominant color
                red = b;
                green = pv;
                blue = qv;
                break;
        }

        return 0xFF000000 | (((int) (red * 255.0f)) << 16) |
                (((int) (green * 255.0f)) << 8) | ((int) (blue * 255.0f));
    }
    public static int alpha(int color) {
        return color >>> 24;
    }
    public static int red(int color) {
        return (color >> 16) & 0xFF;
    }
    public static int green(int color) {
        return (color >> 8) & 0xFF;
    }
    public static int blue(int color) {
        return color & 0xFF;
    }
    public static int rgb(int red, int green, int blue) {
        return (0xFF << 24) | (red << 16) | (green << 8) | blue;
    }
    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
    public static float hue(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int V = Math.max(b, Math.max(r, g));
        int temp = Math.min(b, Math.min(r, g));
        float H;
        if (V == temp) {
            H = 0;
        } else {
            final float vtemp = (float) (V - temp);
            final float cr = (V - r) / vtemp;
            final float cg = (V - g) / vtemp;
            final float cb = (V - b) / vtemp;
            if (r == V) {
                H = cb - cg;
            } else if (g == V) {
                H = 2 + cr - cb;
            } else {
                H = 4 + cg - cr;
            }

            H /= 6.f;
            if (H < 0) {
                H++;
            }
        }
        return H;
    }
    public static float saturation(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int V = Math.max(b, Math.max(r, g));
        int temp = Math.min(b, Math.min(r, g));
        float S;
        if (V == temp) {
            S = 0;
        } else {
            S = (V - temp) / (float) V;
        }
        return S;
    }
    public static float brightness(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int V = Math.max(b, Math.max(r, g));

        return (V / 255.f);
    }
    public static float constrain(float amount, float low, float high) {
    	return amount < low ? low : (amount > high ? high : amount);
    }

}
