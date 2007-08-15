package com.google.code.kaptcha.text.impl;

import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.text.TextProducer;

/**
 * 
 */
public class DefaultTextCreator implements TextProducer
{
	public Random generator = new Random();

	@SuppressWarnings("unused")
	private Properties props = null;

	private int capLength = 5;
	private char[] captchars =
		new char[] {
			'a',
			'b',
			'c',
			'd',
			'e',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'g',
			'f',
			'y',
			'n',
			'm',
			'n',
			'p',
			'w',
			'x' };

	public DefaultTextCreator(Properties props)
	{
		this.props = props;
		setProperties(props);
	}

	public void setCharArray(char[] chars)
	{
		this.captchars = chars;
	}
	 
	public void setProperties(Properties props)
	{
		if (props != null && props.containsKey(Constants.KAPTCHA_TEXTPRODUCER_CHARR))
		{
			String charString = props.getProperty(Constants.KAPTCHA_TEXTPRODUCER_CHARR);
			if (charString != null && !charString.equals(""))
			{

				StringTokenizer token = new StringTokenizer(charString, ",");
				this.captchars = new char[token.countTokens()];
				int cnt = 0;
				while (token.hasMoreTokens())
				{
					captchars[cnt] = ((String)token.nextElement()).toCharArray()[0];
					cnt++;
				}

			}

			String l = props.getProperty(Constants.KAPTCHA_TEXTPRODUCER_CHARRL);
			if (l != null && !l.equals(""))
			{
				try
				{
					capLength = Integer.parseInt(l);
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				if (capLength < 2)
					capLength = 5;
			}
		}
	}

	public String getText()
	{
		int car = captchars.length - 1;

		String capText = "";
		for (int i = 0; i < capLength; i++)
		{
			capText += captchars[generator.nextInt(car) + 1];
		}

		return capText;
	}
}
