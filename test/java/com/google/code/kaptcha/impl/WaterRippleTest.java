package com.google.code.kaptcha.impl;

import java.awt.image.BufferedImage;
import java.util.Properties;

import junit.framework.TestCase;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.TestUtil;

/**
 * @author cliffano
 */
public class WaterRippleTest extends TestCase
{
	private Properties properties;

	private WaterRipple gimpy;

	public void setUp()
	{
		properties = new Properties();
		gimpy = new WaterRipple();
	}

	public void testGetDistortedImageAppliesShadowToFontAndAddsTwoNoises()
			throws Exception
	{
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "BLUE");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "WHITE");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "50");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Helvetica");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "RED");
		properties.put(Constants.KAPTCHA_NOISE_COLOR, "CYAN");

		Config configManager = new Config(properties);

		DefaultWordRenderer renderer = new DefaultWordRenderer();
		DefaultBackground background = new DefaultBackground();

		renderer.setConfig(configManager);
		background.setConfig(configManager);
		gimpy.setConfig(configManager);

		BufferedImage imageWithWord = renderer.renderWord("Jerry ESPENSON",
				300, 80);
		BufferedImage imageWithShadow = gimpy.getDistortedImage(imageWithWord);
		BufferedImage imageWithBackground = background
				.addBackground(imageWithShadow);
		assertNotNull(imageWithBackground);
		TestUtil.writePngImageFile("WaterRipple_rippleAndTwoNoises",
				imageWithBackground);
	}
}
