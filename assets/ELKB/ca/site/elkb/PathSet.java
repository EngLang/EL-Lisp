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
 *
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 */

package ca.site.elkb;

import java.util.Iterator;
import java.util.TreeSet;

/*********************************************************************
 * A set that contains all of the paths between two 
 * words and phrases as well as the number of minimum
 * length paths.
 * This class is used to measure semantic distance.
 * <p>
 * The PathSet also contains the original strings before any
 * morphological transformations of modifications of phrases
 * These are contained in <TT>origWord1</TT> and <TT>origWord2</TT>.
 * </p>
 * @author Mario Jarmasz
 * @version 1.0 Jan 2002
 **********************************************************************/


public class PathSet implements Comparable {
   private TreeSet allPaths;
   private int minPathCount;
   private int minLength;
   private float pathAdjustment;  // real value between 0 and 2
   private String word1;
   private String word2;
   private String pos1;
   private String pos2;
   private String origWord1 = "";
   private String origWord2 = ""; 

  /*************************************
   * Default constructor.
   *************************************/ 
   public PathSet() {
      allPaths = new TreeSet();
      minPathCount = 0;
      minLength = 0;
   }

  /*****************************************************
   * Constructor that initialized this <TT>PathSet</TT>
   * object with a PathSet.
   *****************************************************/ 
   public PathSet(TreeSet pathSet) {
      allPaths = pathSet;
      Path p = (Path)pathSet.first();
      word1 = p.getWord1();
      pos1  = p.getPos1();
      word2 = p.getWord2();
      pos2  = p.getPos2();
      calculateMinPathCount();
      //calculatePathAdjustment();
   }

   /****************************************************************
    * Calculates the total number of minim length paths.
    * The value is stored in <TT>minPathCount</TT>.
    ***************************************************************/
   private void calculateMinPathCount() {
      boolean more = true;
      minPathCount = 0;
      minLength = 0;

      if (allPaths.size() != 0) {

         Iterator iter = allPaths.iterator();
         Path p = (Path)iter.next();
         minLength = p.size();
         minPathCount  = 1;

         while (iter.hasNext() && (more==true) ) {
            Path p2 = (Path)iter.next();
            if ( minLength == p2.size() ) {
               minPathCount++;
            } else {
               // we don't have to count anymore
               more = false;
            }
         }
      }
   }

   /****************************************************************
    * Method that calculates the total number of paths for the
    * following categories:
    * + category 1: paths of length 0,2,4 (group, paragraph, pos)
    * + category 2: paths of length 6,8   (head, headGroup)
    * + category 3: paths of length 10,12 (subSection, Section)
    * + category 4: paths of length 14    (Class)
    * + category 5: paths of length 16    (Infinite)
    ****************************************************************/
    private int categoryPathTotal(int length) {
       int start = 0;
       int end   = 16;
       int total = 0;
       int pLength = 0;
       boolean more = true;

       // figure out what category we are in
       if (length <=4) {
          start = 0;
          end   = 4;
       } else if (length <= 8) {
          start = 6;
          end   = 8;
       } else if (length <= 12) {
          start = 10;
          end   = 12;
       } else {
          start = length;
          end   = length;
       }

       if (allPaths.size() != 0 && (more == true) ) {
    
         Iterator iter = allPaths.iterator();
         while (iter.hasNext()) {
            Path p = (Path)iter.next();
            pLength = p.size();
            if (pLength >= start && pLength <= end) {
               total++;
            }
            if (pLength > end) { more = false; } 
         }
    
       }
       return total;
   }


   /****************************************************************
    * The pathAdjustment is a value between 0 and 2
    * It is the sum of the lengths of all paths, divided by the
    * total number of paths. This gives a value between 0 and 16.
    * It is therefore divided by 8 to obtain the pathAdjustment
    ****************************************************************/
   private void calculatePathAdjustment() {
      pathAdjustment = 0;
      float sumLength  = 0;
      float totalPaths = 0;
      
      Iterator iter = allPaths.iterator();
      while ( iter.hasNext() ) {
         Path p    = (Path)iter.next();
         sumLength += p.size();
         totalPaths++;
      }

      pathAdjustment = ((sumLength / totalPaths) / 8);
   }

   /****************************************************************
    * CTotal represents the number of paths of minimum length
    * This value is used to break ties between words and phrases
    * which have the same semantic distance value using edge-counting.
    * The pair of words or phrases which have the largest CTotal
    * is considered to be more similar.
    ****************************************************************/
   public int getCTotal() {
      return categoryPathTotal(minLength);
   }

  /******************************************************************
   * Returns all Paths in this PathSet.
   *****************************************************************/
   public TreeSet getAllPaths() {
      return allPaths;
   }

  /******************************************************************
   * Returns the number of minimum length Paths in this PathSet.
   *****************************************************************/
   public int getMinPathCount() {
      return minPathCount;
   }

  /******************************************************************
   * Returns the length of the shortest Path in this PathSet.
   *****************************************************************/
   public int getMinLength() {
      return minLength;
   }

   private float getPathAdjustment() {
      return pathAdjustment;
   }

 /*********************************************************************
  * Returns the first word or phrase after the morphological
  * transformations are applied in this PathSet.
  *********************************************************************/
   public String getWord1() {
      return word1;
   }

 /*********************************************************************
  * Returns the second word or phrase after the morphological
  * transformations are applied in this PathSet.
  *********************************************************************/
   public String getWord2() {
      return word2;
   }

 /*********************************************************************
  * Returns the part-of-speech of the first word or phrase in this 
  * PathSet.
  *********************************************************************/
   public String getPos1() {
      return pos1;
   }

 /*********************************************************************
  * Returns the part-of-speech of the second word or phrase in this 
  * PathSet.
  *********************************************************************/ 
   public String getPos2() {
      return pos2;
   }

   /*********************************************************************
    * Compares two PathSets according to the length of the shortest path.
    * If there is a tie, try the following in sequence:
    * <UL>
    *    <LI>compare using the CategoryTotal (CTotal) value calculated in  
    *        the categoryPathTotal method.</LI>
    *    <LI>compare using the minPathCount calculated in the
    *        calculateMinPathCount methiod.</LI>
    * </UL>
    *********************************************************************/
   public int compareTo(Object other) {
      int result;
      PathSet otherPS = (PathSet)other;
      
      result = getMinLength() - otherPS.getMinLength();

      if (result == 0) {
         result = otherPS.getCTotal() - getCTotal();
         if (result == 0) {
            result = otherPS.getMinPathCount() - getMinPathCount(); 
         }
      }
      
      return result;
   }

  /************************************************************************************
   * Converts to a string representation the <TT>PathSet</TT> object -
   * used for debugging.
   * Returns the words and phrases used in the PathSet
   * as well as their parts-of-speech.
   ****************************************************************/
   public String getWordPair() {
      String showOrig1;
      String showOrig2;
      
      showOrig1 = ( getOrigWord1().equals("")
                       ? ""
                       : " [" + getOrigWord1() + "]");
      
      showOrig2 = ( getOrigWord2().equals("")
                       ? ""
                       : " [" + getOrigWord2() + "]");
      
      return (word1 + " " + pos1 + showOrig1 + " to " +
              word2 + " " + pos2 + showOrig2);
   }

  /************************************************************************************
   * Converts to a string representation the <TT>PathSet</TT> object.
   ***********************************************************************************/ 
   public String toString() {
      String showOrig1;
      String showOrig2;

      showOrig1 = ( getOrigWord1().equals("") 
                       ? ""
                       : " [" + getOrigWord1() + "]");

      showOrig2 = ( getOrigWord2().equals("")  
                       ? ""
                       : " [" + getOrigWord2() + "]");

      return (word1 + " " + pos1 + showOrig1 + " to " +
              word2 + " " + pos2 + showOrig2 + ", "   +
              "length = " + getMinLength() + ", " +
              getCTotal() + " path(s) of this length");

   }

  /*************************************************************************
   * Returns the original form of the first word or phrase in this PathSet.
   *************************************************************************/
   public String getOrigWord1() {
      return this.origWord1;
   }

  /*************************************************************************
   * Sets the original form of the first word or phrase in this PathSet.
   *************************************************************************/
   public void setOrigWord1(String word) {
      this.origWord1 = word;
   }

  /*************************************************************************
   * Returns the original form of the second word or phrase in this PathSet.
   *************************************************************************/
   public String getOrigWord2() {
      return this.origWord2;
   }

  /*************************************************************************
   * Sets the original form of the second word or phrase in this PathSet.
   *************************************************************************/    
   public void setOrigWord2(String word) {
      this.origWord2 = word;
   }

}
