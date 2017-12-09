package me.outfiay.bots.FilterBot4;

public class EDUMath
{
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

