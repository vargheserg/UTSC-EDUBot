package me.outfiay.bots.FilterBot4;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.security.auth.login.LoginException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.io.IOException;

import java.util.*;
//import javax.swing.Timer;

public class App extends ListenerAdapter// implements ActionListener	
{
	//Global variables for message ID and message content 
	String botName = "edu";
	MessageChannel objChannel;
	//main method - creating the Discord bot
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException
    {
    	App a = new App();
    	JDA jdabot = new JDABuilder(AccountType.BOT).setToken("Mzg2Njg1MTY5OTgzMjkxMzk0.DQTgLQ.xCDo-edkwCCizBUSG9KSQStnqsk").buildBlocking();
    	jdabot.addEventListener(a);
//    	Timer time = new Timer(1000,a);
//    	time.start();
    }
    
//    public void actionPerformed(ActionEvent e)
//    {
//    	Item item = EDURemind.checkTime(System.currentTimeMillis());
//    	if(item != null) 
//    	{
//    		objChannel.sendMessage(item.message).queue();
//    	}
//    }
    
    //Commands that will be performed when a user enters a message into the channel, depending on the message
    @Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        Message objMsg = e.getMessage();
        objChannel = e.getChannel();
        User objUser = e.getAuthor(); 
        
        //objUser.openPrivateChannel().sendMessage("Hi dude").queue;
        if(!objUser.isBot()) {
        if (objMsg.getContent ().contains (botName))
        {
        	if (objMsg.getContent ().contains ("find roots"))
            {
        		objChannel.sendMessage((findRoots(objMsg.getContent ())));
            }
        	else if (objMsg.getContent ().contains ("define"))
            {
        		try
        		{
        			String input = objMsg.getContent(), output = "";
            		input = input.substring(input.indexOf(" ") + 1);
            		input = input.substring(input.indexOf(" ") + 1);

            		Document dictionary;
            		String rawText, url = "http://www.dictionary.com/browse/" + input + "?s=t";
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
            				catch (StringIndexOutOfBoundsException i)
            				{
            					inBounds = false;
            				}
            			}
            		}
            		catch (HttpStatusException h)
            		{
            			output = "Word does not exist";
            		}
                    
            				
        			objChannel.sendMessage(output);
        		}
        		catch (Exception x)
        		{
        			
        		}
            }
        	else if (objMsg.getContent ().contains ("remind"))
            {
        		objChannel.sendMessage((remind(objMsg.getContent ())));
            }
        	else if (objMsg.getContent ().equals (botName + " help"))
            {
        		objChannel.sendMessage("```css\nHey there! Here are your commands:\n```").queue();
        		objChannel.sendMessage("```css\nCommand syntax: [Reference Name] [Section] [Command] [Parameters]\n```").queue();
        		objChannel.sendMessage("```css\nCurrent Sections are: 'math english remind'```").queue();
        		objChannel.sendMessage("Your current [Reference Name] is: " + botName).queue();
        		objChannel.sendMessage("Try something like '" + botName + " math help' to see the math commands").queue();
        		objChannel.sendMessage("You can change the bot reference name by entering: " + botName + " changeName [New Name]'").queue();
            }
        	else if(objMsg.getContent ().equalsIgnoreCase (botName))
            {
        		objChannel.sendMessage("```Hey bud, try using '[Reference Name] help' if you wanna figure out the commands```").queue();
            }        	
        	else if(objMsg.getContent ().contains ("changeName"))
            {
        		String input = objMsg.getContent ();
        		StringTokenizer st = new StringTokenizer (input); 
        			botName = (st.nextToken ());
        			botName = (st.nextToken ());
        			objChannel.sendMessage("Reference Name is now equal to: " + botName).queue();
            	}
        	}
        }                   
    }
    // EDUDefine
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
    
	//EDURemind

    	
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

    	

    
	
	
	    public static String findRoots (String equation)
	    {
	        String number = "";
	        String roots = "";
	        int counter = 0, finDegree, degree, degreeEnd = 0;
	         
	        String replaceString = equation.replace("factor ", "");
	        equation = replaceString;

	        for (int i = 0 ; i < equation.length () ; i++)
	        {
	            if (equation.charAt (i) == ',')
	            {
	                if (equation.charAt (equation.indexOf (',') + 1) == ' ')
	                {
	                    degreeEnd = i + 2;
	                }
	                else
	                    degreeEnd = i + 1;
	                break;
	            }
	            else
	            {
	                number += equation.charAt (i);
	            }
	        }
	        finDegree = Integer.parseInt (number);
	        int equationValues[] = new int [finDegree + 1];
	        number = "";

	        for (int f = degreeEnd ; f < (equation.length ()) ; f++)
	        {
	            if (equation.charAt (f) == ' ')
	            {

	            }
	            else if (equation.charAt (f) == equation.charAt (equation.length () - 1))
	            {
	                number += equation.charAt (f) + "";
	                equationValues [counter] = Integer.parseInt (number);
	            }
	            else if (equation.charAt (f) != ',')
	            {
	                number += equation.charAt (f) + "";
	            }

	            else
	            {
	                equationValues [counter] = Integer.parseInt (number);
	                counter++;
	                number = "";
	            }
	        }

	        int length1, length2;

	        double[] p = new double [Math.abs (equationValues [equationValues.length - 1])];
	        length1 = findFactors (p, Math.abs (equationValues [equationValues.length - 1]));


	        double[] q = new double [Math.abs (equationValues [0])];
	        length2 = findFactors (q, Math.abs (equationValues [0]));


	        double value;

	        for (int k = 0 ; k < length2 ; k++)
	        {
	            for (int l = 0 ; l < length1 ; l++)
	            {
	                degree = finDegree;
	                value = 0;
	                for (int m = 0 ; m < equationValues.length ; m++)
	                {
	                    value += equationValues [m] * Math.pow (p [l] / q [k], degree);
	                    degree--;
	                }
	                degree = finDegree;
	                if (value == 0)
	                {
	                    roots += Math.round (p [l] / q [k] * 100) / 100.0 + ", ";
	                }
	                value = 0;
	                for (int m = 0 ; m < equationValues.length ; m++)
	                {
	                    value += equationValues [m] * Math.pow (-p [l] / q [k], degree);
	                    degree--;
	                }
	                if (value == 0)
	                {
	                    roots += Math.round (-p [l] / q [k] * 100) / 100.0 + ", ";
	                }
	            }
	        }

	        if (roots.equals (""))
	        {
	            return "there are no real roots.";
	        }
	        else
	        {
	            return " the roots are " + roots + ".";
	        }
	    }


	    public static int findFactors (double[] factors, int num)
	    {
	        int i = 0, counter = 1;
	        while (counter <= num)
	        {
	            if (num % counter == 0)
	            {
	                factors [i] = counter;
	                i++;
	            }
	            counter++;
	        }
	        return i;

	    }
	

}