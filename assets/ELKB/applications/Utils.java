package applications;
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    Utils.java
 *    Copyright (C) 2006 Eibe Frank,Len Trigg,Yong Wang
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */

// package weka.core;

// import java.util.StringTokenizer;
// import java.util.Properties;
// import java.io.File;
// import java.io.FileInputStream;

/**
 * Class implementing some simple utility methods.
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @author Yong Wang (yongwang@cs.waikato.ac.nz)
 * @author Len Trigg (trigg@cs.waikato.ac.nz)
 * @version $Revision: 1.1 $
 */
public final class Utils {

  /**
   * Checks if the given array contains any non-empty options.
   *
   * @param strings an array of strings
   * @exception Exception if there are any non-empty options
   */
  public static void checkForRemainingOptions(String [] options) 
    throws Exception {
    
    int illegalOptionsFound = 0;
    StringBuffer text = new StringBuffer();

    if (options == null) {
      return;
    }
    for (int i = 0; i < options.length; i++) {
      if (options[i].length() > 0) {
	illegalOptionsFound++;
	text.append(options[i] + ' ');
      }
    }
    if (illegalOptionsFound > 0) {
      throw new Exception("Illegal options: " + text);
    }
  }
  
  /**
   * Checks if the given array contains the flag "-Char". Stops
   * searching at the first marker "--". If the flag is found,
   * it is replaced with the empty string.
   *
   * @param flag the character indicating the flag.
   * @param strings the array of strings containing all the options.
   * @return true if the flag was found
   * @exception Exception if an illegal option was found
   */

  public static boolean getFlag(char flag, String [] options) 
    throws Exception {

    if (options == null) {
      return false;
    }
    for (int i = 0; i < options.length; i++) {
      if ((options[i].length() > 1) && (options[i].charAt(0) == '-')) {
	try {
	  Double dummy = Double.valueOf(options[i]);
	} catch (NumberFormatException e) {
	  if (options[i].length() > 2) {
	    throw new Exception("Illegal option: " + options[i]);
	  }
	  if (options[i].charAt(1) == flag) {
	    options[i] = "";
	    return true;
	  }
	  if (options[i].charAt(1) == '-') {
	    return false;
	  }
	}
      }
    }
    return false;
  }

  /**
   * Gets an option indicated by a flag "-Char" from the given array
   * of strings. Stops searching at the first marker "--". Replaces 
   * flag and option with empty strings.
   *
   * @param flag the character indicating the option.
   * @param options the array of strings containing all the options.
   * @return the indicated option or an empty string
   * @exception Exception if the option indicated by the flag can't be found
   */
  public static String getOption(char flag, String [] options) 
    throws Exception {

    String newString;

    if (options == null)
      return "";
    for (int i = 0; i < options.length; i++) {
      if ((options[i].length() > 0) && (options[i].charAt(0) == '-')) {
	
	// Check if it is a negative number
	try {
	  Double dummy = Double.valueOf(options[i]);
	} catch (NumberFormatException e) {
	  if (options[i].length() > 2) {
	    throw new Exception("Illegal option: " + options[i]);
	  }
	  if (options[i].charAt(1) == flag) {
	    if (i + 1 == options.length) {
	      throw new Exception("No value given for -" + flag + " option.");
	    }
	    options[i] = "";
	    newString = new String(options[i + 1]);
	    options[i + 1] = "";
	    return newString;
	  }
	  if (options[i].charAt(1) == '-') {
	    return "";
	  }
	}
      }
    }
    return "";
  }


  /**
   * Computes the mean for an array of doubles.
   *
   * @param vector the array
   * @return the mean
   */
  public static double mean(double[] vector) {
  
    double sum = 0;

    if (vector.length == 0) {
      return 0;
    }
    for (int i = 0; i < vector.length; i++) {
      sum += vector[i];
    }
    return sum / (double) vector.length;
  }


  /**
   * Computes the variance for an array of doubles.
   *
   * @param vector the array
   * @return the variance
   */
  public static double variance(double[] vector) {
  
    double sum = 0, sumSquared = 0;

    if (vector.length <= 1) {
      return 0;
    }
    for (int i = 0; i < vector.length; i++) {
      sum += vector[i];
      sumSquared += (vector[i] * vector[i]);
    }
    return (sumSquared - (sum * sum / (double) vector.length)) / 
      (double) (vector.length - 1);
  }

}
  
