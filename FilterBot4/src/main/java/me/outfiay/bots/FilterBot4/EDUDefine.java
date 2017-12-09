package me.outfiay.bots.FilterBot4;

import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class EDUDefine
{
	public static String define (String input) throws Exception
	{
		String output = "";
		input = input.substring(input.indexOf(" ") + 1);

		output = defineSearch (input.substring(input.indexOf(" ") + 1));
				
		return output;
	}
	
	public static String help()
	{
		return "English commands\n" +
				"Keywords: 'define', 'synonym'\n" +
				"Use keywords followed by the word in quotations\n";
	}
	
	public static String defineSearch (String word) throws IOException
	{
		Document dictionary;
		String output = "", rawText, url = "http://www.dictionary.com/browse/" + word + "?s=t";
		boolean inBounds = true;
		int index = 0;
				
		try
		{
			dictionary = Jsoup.connect(url).get();
			rawText = dictionary.text();
			
			while (inBounds)
			{
				try
				{			
					if (rawText.charAt (index) == '1')
					{
						index++;
						
						if (rawText.charAt(index) == '.')
						{
							index++;
							
							if (rawText.charAt(index) == ' ')
							{
								index++;
								
								while (rawText.charAt(index) != '2')
								{
									output += "" + rawText.charAt(index);
									
									index++;
								}
								
								inBounds = false;
							}
						}
					}
					
					index++;
				}
				catch (StringIndexOutOfBoundsException e)
				{
					inBounds = false;
				}
			}
		}
		catch (HttpStatusException e)
		{
			output = "Word does not exist";
		}
        
		return output;
	}
}

