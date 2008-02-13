package com.google.code.kaptcha.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Properties;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.Configurable;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;

/**
 * {@link ConfigManager} retrieves configuration values from properties file and
 * specifies default values when no value is specified.
 */
public class ConfigManager
{
	private Properties properties;

	private ConfigHelper helper;

	public ConfigManager(Properties properties)
	{
		this.properties = properties;
		helper = new ConfigHelper();
	}

	public boolean isBorderDrawn()
	{
		String paramName = Constants.KAPTCHA_BORDER;
		String paramValue = properties.getProperty(paramName);
		return helper.getBoolean(paramName, paramValue, true);
	}

	public Color getBorderColor()
	{
		String paramName = Constants.KAPTCHA_BORDER_COLOR;
		String paramValue = properties.getProperty(paramName);
		return helper.getColor(paramName, paramValue, Color.BLACK);
	}

	public int getBorderThickness()
	{
		String paramName = Constants.KAPTCHA_BORDER_THICKNESS;
		String paramValue = properties.getProperty(paramName);
		return helper.getPositiveInt(paramName, paramValue, 1);
	}

	public Producer getProducerImpl()
	{
		String paramName = Constants.KAPTCHA_PRODUCER_IMPL;
		String paramValue = properties.getProperty(paramName);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		setConfigurable(defaultKaptcha);
		Producer producer = (Producer) helper.getClassInstance(paramName,
				paramValue, defaultKaptcha);
		setConfigurable(producer);
		return producer;
	}

	public TextProducer getTextProducerImpl()
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_IMPL;
		String paramValue = properties.getProperty(paramName);
		DefaultTextCreator defaultTextCreator = new DefaultTextCreator();
		setConfigurable(defaultTextCreator);
		TextProducer textProducer = (TextProducer) helper.getClassInstance(
				paramName, paramValue, defaultTextCreator);
		setConfigurable(textProducer);
		return textProducer;
	}

	public char[] getTextProducerCharString()
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING;
		String paramValue = properties.getProperty(paramName);
		return helper.getChars(paramName, paramValue, "abcde2345678gfynmnpwx"
				.toCharArray());
	}

	public int getTextProducerCharLength()
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH;
		String paramValue = properties.getProperty(paramName);
		return helper.getPositiveInt(paramName, paramValue, 5);
	}

	public Font[] getTextProducerFonts(int fontSize)
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;
		String paramValue = properties.getProperty(paramName);
		return helper.getFonts(paramName, paramValue, fontSize, new Font[]{
				new Font("Arial", Font.BOLD, fontSize),
				new Font("Courier", Font.BOLD, fontSize)
		});
	}

	public int getTextProducerFontSize()
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE;
		String paramValue = properties.getProperty(paramName);
		return helper.getPositiveInt(paramName, paramValue, 40);
	}

	public Color getTextProducerFontColor()
	{
		String paramName = Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR;
		String paramValue = properties.getProperty(paramName);
		return helper.getColor(paramName, paramValue, Color.BLACK);
	}

	public NoiseProducer getNoiseImpl()
	{
		String paramName = Constants.KAPTCHA_NOISE_IMPL;
		String paramValue = properties.getProperty(paramName);
		DefaultNoise defaultNoise = new DefaultNoise();
		setConfigurable(defaultNoise);
		NoiseProducer noiseProducer = (NoiseProducer) helper.getClassInstance(
				paramName, paramValue, defaultNoise);
		setConfigurable(noiseProducer);
		return noiseProducer;
	}

	public Color getNoiseColor()
	{
		String paramName = Constants.KAPTCHA_NOISE_COLOR;
		String paramValue = properties.getProperty(paramName);
		return helper.getColor(paramName, paramValue, Color.BLACK);
	}

	public GimpyEngine getObscurificatorImpl()
	{
		String paramName = Constants.KAPTCHA_OBSCURIFICATOR_IMPL;
		String paramValue = properties.getProperty(paramName);
		WaterRipple defaultObscurificator = new WaterRipple();
		setConfigurable(defaultObscurificator);
		GimpyEngine gimpyEngine = (GimpyEngine) helper.getClassInstance(
				paramName, paramValue, defaultObscurificator);
		setConfigurable(gimpyEngine);
		return gimpyEngine;
	}

	public WordRenderer getWordRendererImpl()
	{
		String paramName = Constants.KAPTCHA_WORDRENDERER_IMPL;
		String paramValue = properties.getProperty(paramName);
		DefaultWordRenderer defaultWordRenderer = new DefaultWordRenderer();
		setConfigurable(defaultWordRenderer);
		WordRenderer wordRenderer = (WordRenderer) helper.getClassInstance(
				paramName, paramValue, defaultWordRenderer);
		setConfigurable(wordRenderer);
		return wordRenderer;
	}

	public BackgroundProducer getBackgroundImpl()
	{
		String paramName = Constants.KAPTCHA_BACKGROUND_IMPL;
		String paramValue = properties.getProperty(paramName);
		DefaultBackground defaultBackground = new DefaultBackground();
		setConfigurable(defaultBackground);
		BackgroundProducer backgroundProducer = (BackgroundProducer) helper
				.getClassInstance(paramName, paramValue, defaultBackground);
		setConfigurable(backgroundProducer);
		return backgroundProducer;
	}

	public Color getBackgroundColorFrom()
	{
		String paramName = Constants.KAPTCHA_BACKGROUND_CLR_FROM;
		String paramValue = properties.getProperty(paramName);
		return helper.getColor(paramName, paramValue, Color.LIGHT_GRAY);
	}

	public Color getBackgroundColorTo()
	{
		String paramName = Constants.KAPTCHA_BACKGROUND_CLR_TO;
		String paramValue = properties.getProperty(paramName);
		return helper.getColor(paramName, paramValue, Color.WHITE);
	}

	private void setConfigurable(Object object)
	{
		if (object instanceof Configurable)
		{
			((Configurable) object).setConfigManager(this);
		}
	}

	public Properties getProperties()
	{
		return properties;
	}
}
