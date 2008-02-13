package com.google.code.kaptcha.text.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.Configurable;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.ConfigManager;

/**
 * The default implementation of {@link WordRenderer}, creates an image with a
 * word rendered on it.
 */
public class DefaultWordRenderer implements WordRenderer, Configurable
{
    private ConfigManager configManager;

    private static final int START_POS_X = 25;

    /**
     * Renders a word to an image.
     *
     * @param word
     *            The word to be rendered.
     * @param width
     *            The width of the image to be created.
     * @param height
     *            The height of the image to be created.
     * @return The BufferedImage created from the word.
     */
    public BufferedImage renderWord(String word, int width, int height)
    {
        int fontSize = configManager.getTextProducerFontSize();
        Font[] fonts = configManager.getTextProducerFonts(fontSize);
        Color color = configManager.getTextProducerFontColor();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(color);

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g2D.setRenderingHints(hints);

        FontRenderContext frc = g2D.getFontRenderContext();
        Random random = new Random();
        int startPosX = START_POS_X;

        char[] wordChars = word.toCharArray();
        for (int i = 0; i < wordChars.length; i++)
        {
            Font chosenFont = fonts[random.nextInt(fonts.length)];
            g2D.setFont(chosenFont);

            char[] charToDraw = new char[]{
                wordChars[i]
            };
            GlyphVector gv = chosenFont.createGlyphVector(frc, charToDraw);
            double charWidth = gv.getVisualBounds().getWidth();

            g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, 35);
            startPosX = startPosX + (int) charWidth + 2;
        }

        return image;
    }

    public void setConfigManager(ConfigManager configManager)
    {
        this.configManager = configManager;
    }
}
