package me.outfiay.bots.FilterBot4;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EDURemind {
	
	static ArrayList<Item> remindList = new ArrayList<Item>();

	
	public static String remind(String input)
	{
        boolean proceed = true;
        String msg = "", time= "";
        if (input.contains("help"))
        {
        	return "Hey there! Here are the remind functions:\nAdd Reminder: e.g. edu remind add Chemistry Test 2017-12-02-20:00\nRemove Reminder: e.g. edu remind remove Chemistry Test";
        }
        
        else if (input.contains ("add"))
        {
            StringTokenizer st = new StringTokenizer (input);
            while (!st.nextToken ().equals ("add"))
            {
            }
            do
            {
                String currentWord = st.nextToken ();
                try
                {
                    Integer.parseInt (currentWord.charAt (0) + "");
                    time = currentWord;
                    proceed = false;
                }
                
                catch (NumberFormatException e)
                {
                    msg += currentWord + " ";
                }
            }
            while (proceed);
            return addItem(msg,time);

            

        }//if, checks for add keyword
        
        else if (input.contains("remove"))
        {
        	StringTokenizer st = new StringTokenizer (input);
            while (!st.nextToken ().equals ("add"))
            {
            }
            do
            {
                String currentWord = st.nextToken ();
                try
                {
                    Integer.parseInt (currentWord.charAt (0) + "");
                    time = currentWord;
                    proceed = false;
                }
                
                catch (NumberFormatException e)
                {
                    msg += currentWord + " ";
                }
            }
            while (proceed);
            
        	return removeItem(msg);
		
        }//else if, remove keyword
        

        else
        	 return "Invalid input.Here are the remind functions:\nAdd Reminder: e.g. edu remind add Chemistry Test 2017-12-02-20:00\nRemove Reminder: e.g. edu remind remove Chemistry Test";
	}//process method, handles the user input
	
	public static String removeItem(String msg)
	{
		for (int i = 0; i<remindList.size(); i++)
		{
			if (remindList.get(i).message.contains(msg))
			{
				remindList.remove(i);	
				return "Successfully removed message";
			}
			
				
		}
		return "Could not find reminder with corresponding description";
		
		
	}
	
	public static String addItem (String msg, String time)
    {
        long milli = 1483228800;
        
        
        int months = 0;
        long[] millimonths = {2678400000L, 2419200000L, 2678400000L, 2592000000L, 2678400000L, 2592000000L, 2678400000L, 2678400000L, 2592000000L, 2678400000L, 2592000000L, 2678400000L};

        if (!(time.charAt (4) == ('-') && time.charAt (7) == ('-') && time.charAt (10) == ('-') && time.charAt (13) == (':')))
        {
            return "Invalid input.Here are the remind functions:\nAdd Reminder: e.g. edu remind add Chemistry Test 2017-12-02-20:00\nRemove Reminder: e.g. edu remind remove Chemistry Test";
        }
        try
        {
            milli += (Integer.parseInt (time.substring (0, 4)) - 2017) * 31540000000L;
            months = Integer.parseInt (time.substring (5, 7)) - 1;

            for (int j = 0 ; j < months ; j++)
            {
                milli += millimonths [j];
            }


            milli += Integer.parseInt (time.substring (8, 10)) * 86400000;
            milli += Integer.parseInt (time.substring (11, 13)) * 3600000;
            milli += Integer.parseInt (time.substring (14, time.length ())) * 60000;

            remindList.add (new Item (msg, milli));
            return "Successfully created reminder " + msg;
        }
        catch (NumberFormatException e)
        {
        	 return "Invalid input.Here are the remind functions:\nAdd Reminder: e.g. edu remind add Chemistry Test 2017-12-02-20:00\nRemove Reminder: e.g. edu remind remove Chemistry Test";
        }
    }//addItem

	

}


