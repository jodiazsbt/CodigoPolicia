package com.policia.movil;

import java.util.Arrays;

/**
 * Created by 1085253556 on 13/02/2018.
 */

public class FormatoImagen {

    public enum Formato {
        Bmp,
        Jpeg,
        Jpeg2,
        Gif,
        Tiff,
        Tiff2,
        Png,
        Unknown
    }

    private static byte[] getLength(byte[] bytes, int length) {
        byte[] copy = new byte[length];
        for (int i = 0; i < length; i++) {
            copy[i] = bytes[i];
        }
        return copy;
    }

    public static Formato compararFormato(byte[] bytes) {
        // see http://www.mikekunz.com/image_file_header.html
        byte[] bmp = "BM".getBytes();                                               // BMP
        byte[] gif = "GIF".getBytes();                                              // GIF
        byte[] png = new byte[]{(byte) 137, (byte) 80, (byte) 78, (byte) 71};       // PNG
        byte[] tiff = new byte[]{(byte) 73, (byte) 73, (byte) 42};                  // TIFF
        byte[] tiff2 = new byte[]{(byte) 77, (byte) 77, (byte) 42};                 // TIFF
        byte[] jpeg = new byte[]{(byte) 255, (byte) 216, (byte) 255, (byte) 224};   // jpeg
        byte[] jpeg2 = new byte[]{(byte) 255, (byte) 216, (byte) 255, (byte) 225};  // jpeg canon

        if (Arrays.equals(bmp, getLength(bytes, bmp.length))) return Formato.Bmp;
        if (Arrays.equals(gif, getLength(bytes, gif.length))) return Formato.Gif;
        if (Arrays.equals(png, getLength(bytes, png.length))) return Formato.Png;
        if (Arrays.equals(tiff, getLength(bytes, tiff.length))) return Formato.Tiff;
        if (Arrays.equals(tiff2, getLength(bytes, tiff2.length))) return Formato.Tiff2;
        if (Arrays.equals(jpeg, getLength(bytes, jpeg.length))) return Formato.Jpeg;
        if (Arrays.equals(jpeg2, getLength(bytes, jpeg2.length))) return Formato.Jpeg2;
        return Formato.Unknown;
    }

}
