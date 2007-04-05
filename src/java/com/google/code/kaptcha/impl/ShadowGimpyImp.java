/*
 * Created on Sep 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.google.code.kaptcha.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.util.Properties;
import java.util.Random;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Helper;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;
import com.jhlabs.image.TransformFilter;

/**
 * 
 */
public class ShadowGimpyImp implements GimpyEngine
{

	private Properties props = null;

	public BufferedImage getDistortedImage(BufferedImage image)
	{

		BufferedImage imageDistorted =
			new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graph = (Graphics2D)imageDistorted.getGraphics();

		//create filter ripple
		//SphereFilter filter = new SphereFilter();
		//double d = 1.2;
		//filter.setRefractionIndex(d);

		ShadowFilter filter = new ShadowFilter();
		filter.setRadius(10);

		Random rand = new Random();

		RippleFilter wfilter = new RippleFilter();
		wfilter.setWaveType(RippleFilter.SINGLEFRAME);
		wfilter.setXAmplitude(7.6f);
		wfilter.setYAmplitude(rand.nextFloat() + 1.0f);
		wfilter.setXWavelength(rand.nextInt(7) + 8);
		wfilter.setYWavelength(rand.nextInt(3) + 2);
		wfilter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		wfilter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		//apply filter water              

		FilteredImageSource wfiltered = new FilteredImageSource(image.getSource(), wfilter);

		Image img = Toolkit.getDefaultToolkit().createImage(wfiltered);
		img = Toolkit.getDefaultToolkit().createImage(wfiltered);

		FilteredImageSource filtered = new FilteredImageSource(img.getSource(), filter);
		img = Toolkit.getDefaultToolkit().createImage(filtered);

		graph.drawImage(img, 0, 0, null, null);

		graph.dispose();

		//draw line over the iamge and/or text
		NoiseProducer noise = (NoiseProducer)Helper.ThingFactory.loadImpl(Helper.ThingFactory.NOICEIMP, props);
		noise.makeNoise(imageDistorted, .1f, .1f, .25f, .25f);
		noise.makeNoise(imageDistorted, .1f, .25f, .5f, .9f);
		return imageDistorted;
	}

	/* (non-Javadoc)
	 * @see nl.captcha.obscurity.GimpyEngine#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props)
	{
		this.props = props;

	}

}
