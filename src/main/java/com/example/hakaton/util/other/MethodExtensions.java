package com.example.hakaton.util.other;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MethodExtensions {
    private static String fontPath;
    private Environment env;

    public MethodExtensions(Environment env) {
        this.env = env;
        this.fontPath = env.getProperty("hakaton.export.fonts");
        ;
    }

    public static String toDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null)
            return dateFormat.format(date);
        else
            return "";
    }

    public static void writeText(PDPageContentStream stream, String text, float x, float y) throws IOException {
        stream.beginText();
        stream.newLineAtOffset(x, y);
        stream.showText(text);
        stream.endText();
    }

    public static void writeXCenter(PDPageContentStream stream, String text, float fontSize, PDFont font, float width, float x, float y)
            throws IOException {
        float titleWidth = font.getStringWidth(text) / 1000 * fontSize;

        stream.beginText();
        stream.newLineAtOffset((width - titleWidth) / 2 + x, y);
        stream.showText(text);
        stream.endText();
    }

    public static void writeYCenter(PDPageContentStream stream, String text, float fontSize, PDFont font, float height, float x, float y)
            throws IOException {
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        stream.beginText();
        stream.newLineAtOffset(x, (height - titleHeight) / 2 - y);
        stream.showText(text);
        stream.endText();
    }

    public static void writeXYCenter(PDPageContentStream stream, String text, float fontSize, PDFont font, float width, float height, float x, float y)
            throws IOException {
        float titleWidth = font.getStringWidth(text) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        stream.beginText();
        stream.newLineAtOffset((width - titleWidth) / 2 + x, (height - titleHeight) / 2 + y);
        stream.showText(text);
        stream.endText();
    }

    public static void writeXCenterB(PDPageContentStream stream, String text, float fontSize, PDFont font, float width, float x, float y, PDDocument document)
            throws IOException {
        float titleWidth = font.getStringWidth(text) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        PDType0Font fontTNRB = PDType0Font.load(document, new File(fontPath + "timesbd.ttf"));
        stream.setFont(fontTNRB, fontSize);

        stream.beginText();
        stream.newLineAtOffset((width - titleWidth) / 2 + x, y);
        stream.showText(text);
        stream.endText();

        PDType0Font fontTNR = PDType0Font.load(document, new File(fontPath + "times.ttf"));
        stream.setFont(fontTNR, fontSize);
    }

    public static void writeSW(PDPageContentStream stream, String[] texts, float fontSize, PDFont font, float width, float Xpadding, float x, float y)
            throws IOException {
        float[] titleWidth = new float[texts.length];
        int sum = 0;
        for (int i = 0; i < texts.length; i++) {
            titleWidth[i] = font.getStringWidth(texts[i]) / 1000 * fontSize;
            sum += titleWidth[i];
        }

        float indent = (width - sum - Xpadding * 2) / (texts.length - 1);
        float step = x + Xpadding;

        for (int i = 0; i < texts.length; i++) {
            stream.beginText();
            stream.newLineAtOffset(step, y);
            stream.showText(texts[i]);
            stream.endText();

            step += titleWidth[i] + indent;
        }
    }

    public static void writeSW(PDPageContentStream stream, String text, float fontSize, PDFont font, float width, float height, float Xpadding, float x, float y)
            throws IOException {
        text = text.replace("\n", " ");
        text = text.replaceAll(" +", " ");
        String[] words = text.split(" ");
        float[] titleWidth = new float[words.length];
        List<Integer> rowsFirstWordIndex = new ArrayList<Integer>(0);
        List<Float> rowsWidth = new ArrayList<Float>();

        float Xindent = font.getStringWidth(" ") / 1000 * fontSize;
        float Yindent = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize - 2;

        float myWidth = width - 2 * Xpadding;

        float step = 0;
        for (int i = 0; i < words.length; i++) {
            titleWidth[i] = font.getStringWidth(words[i]) / 1000 * fontSize;

            if (titleWidth[i] == 0) continue;
            if (titleWidth[i] > myWidth) {
                rowsFirstWordIndex.add(i);
                rowsWidth.add(step);
                step = 0;
                continue;
            }

            step += titleWidth[i] + Xindent;
            if (step >= myWidth) {
                rowsFirstWordIndex.add(i);
                rowsWidth.add(step - titleWidth[i] - Xindent);
                step = titleWidth[i];
            }
        }

        rowsFirstWordIndex.add(words.length - 1);
        rowsWidth.add(step);

        Integer[] rowsFirstWordIndexArray = new Integer[rowsFirstWordIndex.size()];
        rowsFirstWordIndexArray = rowsFirstWordIndex.toArray(rowsFirstWordIndexArray);

        Float[] rowsWidthA = new Float[rowsWidth.size()];
        rowsWidthA = rowsWidth.toArray(rowsWidthA);

        for (int i = 0; i < rowsFirstWordIndexArray.length; i++) {
            int firstWord = (i == 0) ? 0 : rowsFirstWordIndexArray[i - 1];
            int lastWord = (i == rowsFirstWordIndexArray.length - 1) ? words.length : rowsFirstWordIndexArray[i];

            float indent = (myWidth - rowsWidthA[i]) / (lastWord - firstWord - 1) + Xindent;
            float topX = x + Xpadding;

            for (int j = firstWord; j < lastWord; j++) {
                stream.beginText();
                stream.newLineAtOffset(topX, y);
                stream.showText(words[j]);
                stream.endText();

                topX += titleWidth[j] + indent;
            }
            y -= Yindent;
        }
    }

    public static void line(PDPageContentStream stream, float x1, float y1, float x2, float y2) throws IOException {
        stream.moveTo(x1, y1);
        stream.lineTo(x2, y2);
        stream.stroke();
    }

    public static void line(PDPageContentStream stream, float[] x, float y[]) throws IOException {
        for (int i = 1; i < x.length; i++) {
            stream.moveTo(x[i - 1], y[i - 1]);
            stream.lineTo(x[i], y[i]);
        }
        stream.stroke();
    }
}
