package applications;
import java.util.TreeSet;

import ca.site.elkb.PathSet;
import ca.site.elkb.RogetELKB;

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
 *    SemDist2Words.java
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 *    and
 *    Olena Medelyan
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */


/**************************************************************************
 * SemDist2Words : program that calculates semantic distance between two nouns
 *                 on a scale from 16 (very similar) to 4 (no similarity)
 *
 * @authors Mario Jarmasz, Olena Medelyan
 * @version 1.0 May 2006
 * Usage  : java SemDist2Words <word1> <word2> 
 ************************************************************************/

class SemDist2Words {
   public RogetELKB elkb;

   // Constructor
   public SemDist2Words() {
      // Initialize Roget
      elkb = new RogetELKB();
   }

   public static void main(String args[]) {
      if (args.length != 2) {
         System.out.println("java SemDist2Words <word1> <word2>");
      } else {
         try {
            // should check that we have a valid argument first...
            SemDist2Words semDist = new SemDist2Words();
            semDist.wordPairs(args[0], args[1]);
         } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
         }
      }
   }

   /*******************************************************************
    * To deal with Miller and Charles type experiment
    *******************************************************************/
   private void wordPairs(String word1, String word2) {
		double distance;
		float pathLength;

		// in all of these experiments we're only dealing
		// with pairs of nouns
		TreeSet allPaths = elkb.getAllPaths(word1, word2, "N.");
		if (allPaths.size() != 0) {
			PathSet ps = new PathSet(allPaths);
			System.out.print(word1 + "," + word2 + ","
					+ (16 - ps.getMinLength()));
			System.out.println(", " + ps.getWordPair());
		} else {
			System.out.println(word1 + "," + word2);
		}

	}
 
}
