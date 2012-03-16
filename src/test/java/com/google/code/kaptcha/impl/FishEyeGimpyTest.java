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
public class FishEyeGimpyTest extends TestCase
{
	private Properties properties;

	private FishEyeGimpy gimpy;

	public void setUp()
	{
		properties = new Properties();
		gimpy = new FishEyeGimpy();
	}

	public void testGetDistortedImageAppliesShadowToFontAndAddsTwoNoises()
			throws Exception
	{
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "CYAN");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "BLACK");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "50");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Helvetica");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "WHITE");

		Config config = new Config(properties);

		DefaultWordRenderer renderer = new DefaultWordRenderer();
		DefaultBackground background = new DefaultBackground();

		renderer.setConfig(config);
		background.setConfig(config);

		BufferedImage imageWithWord = renderer
				.renderWord("Alan SHORE", 300, 80);
		BufferedImage imageWithShadow = gimpy.getDistortedImage(imageWithWord);
		BufferedImage imageWithBackground = background
				.addBackground(imageWithShadow);
		assertNotNull(imageWithBackground);
		TestUtil.writePngImageFile("FishEyeGimpy_fishEyeAndLines",
				imageWithBackground);
	}
}
