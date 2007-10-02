package com.google.code.kaptcha.util;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.StringTokenizer;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.impl.DefaultBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;

/**
 * 
 */
public class Helper
{	
	private static Font[] defaultFonts =  new Font[]{
		new Font("Arial", Font.BOLD, 40),
		new Font("Courier", Font.BOLD, 40)			
	};
	
	public static Font[] getFonts(Properties props)
	{

		if (props == null)
			return Helper.defaultFonts;

		String fontArr = props.getProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES);
		if (fontArr == null)
			return Helper.defaultFonts;

		int fontsize = Helper.getIntegerFromString(props, Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE);
		if (fontsize < 8)
			fontsize = 40;
		Font[] fonts = null;

		try
		{
			StringTokenizer tokeniz = new StringTokenizer(fontArr, ",");
			fonts = new Font[tokeniz.countTokens()];
			int cnt = 0;
			while (tokeniz.hasMoreElements())
			{
				String fontStr = tokeniz.nextToken();
				Font itf = new Font(fontStr, Font.BOLD, fontsize);
				fonts[cnt] = itf;
				cnt++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (fonts == null)
		{
			return Helper.defaultFonts;
		}
		else
		{
			return fonts;
		}
	}
	
	public static int getIntegerFromString(Properties props, String key)
	{
		int ret = 0;
		if (props == null)
			return ret;

		String val = props.getProperty(key);
		if (val == null || val.equals(""))
			return ret;

		try
		{
			ret = Integer.parseInt(val);
		}
		catch (Exception e)
		{
			// Ignore
		}
		return ret;
	}
	
	private static Color createColor(String rgbalpha)
	{

		Color c = null;
		try
		{

			StringTokenizer tok = new StringTokenizer(rgbalpha, ",");
			if (tok.countTokens() < 3)
			{
				return null;
			}
			int r = Integer.parseInt((String)tok.nextElement());
			int g = Integer.parseInt((String)tok.nextElement());
			int b = Integer.parseInt((String)tok.nextElement());

			if (tok.countTokens() == 1)
			{
				int a = Integer.parseInt((String)tok.nextElement());
				c = new Color(r, g, b, a);
			}
			else
			{
				c = new Color(r, g, b);
			}
		}
		catch (Exception e)
		{
			// Ignore
		}

		return c;
	}
	
	public static Color getColor(Properties props, String key, Color defaultc)
	{
		Color retCol = null;

		if (props == null)
			return defaultc;
		try
		{
			String color = props.getProperty(key);

			if (color != null && !color.equals(""))
			{
				if (color.indexOf(",") > 0)
				{
					retCol = Helper.createColor(color);
				}
				else
				{
					Field field = Class.forName("java.awt.Color").getField(color);
					retCol = (Color)field.get(null);
				}

			}
		}
		catch (Exception e)
		{
			// Ignore
		}

		if (retCol == null && defaultc == null)
		{
			retCol = Color.black;
		}
		else if (retCol == null)
		{
			retCol = defaultc;
		}

		return retCol;
	}
	
	public final static class ThingFactory
	{
		
		public final static int NOISE_IMPL = 1;
		public final static int OBSCURIFICATOR_IMPL = 2;
		public final static int BACKGROUND_IMPL = 3;
		public final static int WORDRENDERER_IMPL = 4;
		public final static int TEXTPRODUCER_IMPL = 5;
		public final static int PRODUCER_IMPL = 6;
		
		
		public static Object loadImpl(int type, Properties props)
		{
			switch (type)
			{
				case NOISE_IMPL:
					String nimp = props.getProperty(Constants.KAPTCHA_NOISE_IMPL);
					if (nimp == null)
						return new DefaultNoise(props);
					try
					{
						NoiseProducer nop = (NoiseProducer)Class.forName(nimp).newInstance();
						nop.setProperties(props);
						return nop;

					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						return new DefaultNoise(props);
					}

				case OBSCURIFICATOR_IMPL:
					String obs = props.getProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL);
					if (obs == null)
						return new WaterRipple(props);
					try
					{
						GimpyEngine gimp = (GimpyEngine)Class.forName(obs).newInstance();
						gimp.setProperties(props);
						return gimp;
					}
					catch (Exception e)
					{
						System.out.print(e.getMessage());
						return new WaterRipple(props);
					}
				case BACKGROUND_IMPL:
					String bg = props.getProperty(Constants.KAPTCHA_BACKGROUND_IMPL);
					if (bg == null)
						return new DefaultBackground(props);
					try
					{
						BackgroundProducer gimp = (BackgroundProducer)Class.forName(bg).newInstance();
						gimp.setProperties(props);
						return gimp;
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						return new DefaultBackground(props);
					}

				case WORDRENDERER_IMPL:
					String wr = props.getProperty(Constants.KAPTCHA_WORDRENDERER_IMPL);
					if (wr == null)
						return new DefaultWordRenderer(props);
					try
					{
						WordRenderer ren = (WordRenderer)Class.forName(wr).newInstance();
						ren.setProperties(props);
						return ren;
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						return new DefaultWordRenderer(props);
					}
				case TEXTPRODUCER_IMPL:
					String txp = props.getProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL);
					if (txp == null)
						return new DefaultTextCreator(props);
					try
					{
						TextProducer txpP = (TextProducer)Class.forName(txp).newInstance();
						txpP.setProperties(props);
						return txpP;
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						return new DefaultTextCreator(props);
					}
				case PRODUCER_IMPL:
					String cp = props.getProperty(Constants.KAPTCHA_PRODUCER_IMPL);
					if (cp == null)
						return new DefaultKaptcha(props);
					try
					{
						Producer p = (Producer)Class.forName(cp).newInstance();
						p.setProperties(props);
						return p;
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						return new DefaultKaptcha(props);
					}

				default:
					break;
			}
			return null;
		}
	}
}
