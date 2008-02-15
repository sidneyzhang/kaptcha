package com.google.code.kaptcha.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.image.WaterFilter;

/**
 * {@link WaterRipple} adds water ripple effect to an image.
 */
public class WaterRipple extends Configurable implements GimpyEngine
{
	/**
	 * Applies distortion by adding water ripple effect.
	 * 
	 * @param baseImage the base image
	 * @return the distorted image
	 */
	public BufferedImage getDistortedImage(BufferedImage baseImage)
	{
		NoiseProducer noiseProducer = getConfig().getNoiseImpl();
		BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
				baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = (Graphics2D) distortedImage.getGraphics();

		RippleFilter rippleFilter = new RippleFilter();
		rippleFilter.setWaveType(RippleFilter.SINGLEFRAME);
		rippleFilter.setXAmplitude(2.6f);
		rippleFilter.setYAmplitude(1.7f);
		rippleFilter.setXWavelength(15);
		rippleFilter.setYWavelength(5);
		rippleFilter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		WaterFilter waterFilter = new WaterFilter();
		waterFilter.setAmplitude(4);
		waterFilter.setAntialias(true);
		waterFilter.setPhase(15);
		waterFilter.setWavelength(70);

		FilteredImageSource filteredImageSource = new FilteredImageSource(
				baseImage.getSource(), waterFilter);
		Image effectImage = Toolkit.getDefaultToolkit().createImage(
				filteredImageSource);

		filteredImageSource = new FilteredImageSource(effectImage.getSource(),
				rippleFilter);
		effectImage = Toolkit.getDefaultToolkit().createImage(
				filteredImageSource);
		graphics.drawImage(effectImage, 0, 0, null, null);

		graphics.dispose();

		noiseProducer.makeNoise(distortedImage, .1f, .1f, .25f, .25f);
		noiseProducer.makeNoise(distortedImage, .1f, .25f, .5f, .9f);
		return distortedImage;
	}
}
