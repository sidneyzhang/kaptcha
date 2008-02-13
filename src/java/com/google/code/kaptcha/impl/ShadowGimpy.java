package com.google.code.kaptcha.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.util.Random;

import com.google.code.kaptcha.Configurable;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.ConfigManager;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;
import com.jhlabs.image.TransformFilter;

/**
 * {@link ShadowGimpy} adds shadow to the text on the image and two noises.
 */
public class ShadowGimpy implements GimpyEngine, Configurable
{
	private ConfigManager configManager;

	/**
	 * Applies distortion by adding shadow to the text and also two noises.
	 * 
	 * @param baseImagethe
	 *            base image
	 * @return the distorted image
	 */
	public BufferedImage getDistortedImage(BufferedImage baseImage)
	{
		NoiseProducer noiseProducer = configManager.getNoiseImpl();
		BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
				baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graph = (Graphics2D) distortedImage.getGraphics();

		ShadowFilter shadowFilter = new ShadowFilter();
		shadowFilter.setRadius(10);

		Random rand = new Random();

		RippleFilter rippleFilter = new RippleFilter();
		rippleFilter.setWaveType(RippleFilter.SINGLEFRAME);
		rippleFilter.setXAmplitude(7.6f);
		rippleFilter.setYAmplitude(rand.nextFloat() + 1.0f);
		rippleFilter.setXWavelength(rand.nextInt(7) + 8);
		rippleFilter.setYWavelength(rand.nextInt(3) + 2);
		rippleFilter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		rippleFilter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		FilteredImageSource rippleFilteredImageSource = new FilteredImageSource(
				baseImage.getSource(), rippleFilter);
		Image effectImage = Toolkit.getDefaultToolkit().createImage(
				rippleFilteredImageSource);
		FilteredImageSource shadowFilteredImageSource = new FilteredImageSource(
				effectImage.getSource(), shadowFilter);
		effectImage = Toolkit.getDefaultToolkit().createImage(
				shadowFilteredImageSource);

		graph.drawImage(effectImage, 0, 0, null, null);
		graph.dispose();

		// draw lines over the image and/or text
		noiseProducer.makeNoise(distortedImage, .1f, .1f, .25f, .25f);
		noiseProducer.makeNoise(distortedImage, .1f, .25f, .5f, .9f);

		return distortedImage;
	}

	public void setConfigManager(ConfigManager configManager)
	{
		this.configManager = configManager;
	}
}
