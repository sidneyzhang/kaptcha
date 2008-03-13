package com.google.code.kaptcha.impl;

import java.awt.image.BufferedImage;
import java.util.Properties;

import junit.framework.TestCase;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.TestUtil;

/**
 * @author cliffano
 */
public class DefaultBackgroundTest extends TestCase
{
	private DefaultBackground background;

	private Properties properties;

	public void setUp()
	{
		properties = new Properties();
		background = new DefaultBackground();
		background.setConfig(new Config(properties));
	}

	public void testAddBackgroundWithDifferentColorFromAndToGivesDiagonalGradientBackgroundWithSpecifiedColors()
			throws Exception
	{
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "GREEN");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "MAGENTA");
		BufferedImage baseImage = TestUtil.createBaseImage();
		BufferedImage imageWithBackground = background.addBackground(baseImage);
		TestUtil.writePngImageFile(
				"DefaultBackground_gradientBackgroundGreenMagenta",
				imageWithBackground);
	}

	public void testAddBackgroundWithSameColorFromAndToGivesFlatBackgroundColor()
			throws Exception
	{
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "YELLOW");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "YELLOW");
		BufferedImage baseImage = TestUtil.createBaseImage();
		BufferedImage imageWithBackground = background.addBackground(baseImage);
		TestUtil.writePngImageFile("DefaultBackground_flatBackgroundYellow",
				imageWithBackground);
	}

	public void testAddBackgroundWithNullColorFromAndToGivesDiagonalBackgroundColorWithDefaultLightGrayAndWhite()
			throws Exception
	{
		BufferedImage baseImage = TestUtil.createBaseImage();
		BufferedImage imageWithBackground = background.addBackground(baseImage);
		TestUtil.writePngImageFile(
				"DefaultBackground_gradientBackgroundLightGrayWhite",
				imageWithBackground);
	}
}
