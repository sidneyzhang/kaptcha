package com.google.code.kaptcha.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.ConfigManager;

/**
 * Default {@link Producer} implementation which draws a captcha image using
 * {@link WordRenderer}, {@link GimpyEngine}, {@link BackgroundProducer}.
 * Text creation uses {@link TextProducer}.
 */
public class DefaultKaptcha implements Producer
{
	private ConfigManager configManager;

	private static final int WIDTH = 200;

	private static final int HEIGHT = 50;

	/**
	 * Create an image which will have written a distorted text.
	 * 
	 * @param text
	 *            the distorted characters
	 * @return image with the text
	 */
	public BufferedImage createImage(String text)
	{
		WordRenderer wordRenderer = configManager.getWordRendererImpl();
		GimpyEngine gimpyEngine = configManager.getObscurificatorImpl();
		BackgroundProducer backgroundProducer = configManager
				.getBackgroundImpl();
		boolean isBorderDrawn = configManager.isBorderDrawn();

		BufferedImage bi = wordRenderer.renderWord(text, WIDTH, HEIGHT);
		bi = gimpyEngine.getDistortedImage(bi);
		bi = backgroundProducer.addBackground(bi);
		Graphics2D graphics = bi.createGraphics();
		if (isBorderDrawn)
		{
			drawBox(graphics);
		}
		return bi;
	}

	private void drawBox(Graphics2D graphics)
	{
		Color borderColor = configManager.getBorderColor();
		int borderThickness = configManager.getBorderThickness();

		graphics.setColor(borderColor);

		if (borderThickness != 1)
		{
			BasicStroke stroke = new BasicStroke((float) borderThickness);
			graphics.setStroke(stroke);
		}

		Line2D line1 = new Line2D.Double(0, 0, 0, WIDTH);
		graphics.draw(line1);
		Line2D line2 = new Line2D.Double(0, 0, WIDTH, 0);
		graphics.draw(line2);
		line2 = new Line2D.Double(0, HEIGHT - 1, WIDTH, HEIGHT - 1);
		graphics.draw(line2);
		line2 = new Line2D.Double(WIDTH - 1, HEIGHT - 1, WIDTH - 1, 0);
		graphics.draw(line2);
	}

	/**
	 * @return the text to be drawn
	 */
	public String createText()
	{
		return configManager.getTextProducerImpl().getText();
	}

	public void setConfigManager(ConfigManager configManager)
	{
		this.configManager = configManager;
	}
}
