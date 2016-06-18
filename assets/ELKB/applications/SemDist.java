package applications;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
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
 *    SemDist.java
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
 * SemDist : program that performs various experiments
 *           to calculate semantic distance.
 *           This program was used to obtain the results of 
 *           Roget's Thesaurus and Semantic Similarity paper which can be 
 *           found at: http://www.site.uottawa.ca/~mjarmasz/pubs/jarmasz_roget_sim.pdf
 *
 * @author Mario Jarmasz
 * @version 1.0 Jan 2002
 * Usage  : java SemDist <inputFile> 
 * Format of input file: pairs of comma separated words and phrases, 
 *          one pair per line. Extra information can be contained
 *          on the line as long as it is separated by a comma,
 *          for example: car,automobile,3.92
 ************************************************************************/

class SemDist {
   public RogetELKB elkb;

   // Constructor
   public SemDist() {
      // Initialize Roget
      elkb = new RogetELKB();
   }

   public static void main(String args[]) {
      if (args.length != 1) {
         System.out.println("java SemDist <inputFile>");
      } else {
         try {
            // should check that we have a valid argument first...
            SemDist semDist = new SemDist();
            semDist.wordPairs(args[0]);
         } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
         }
      }
   }

   /*******************************************************************
    * To deal with Miller and Charles type experiment
    *******************************************************************/
   private void wordPairs(String fileName) {
      double distance;
      float pathLength;

      try {
         BufferedReader br = new BufferedReader(new FileReader(fileName));
                  
         for ( ; ; ) {
            String line = br.readLine();
            distance = 0;
      
            if (line == null) {
               br.close();
               break;
            } else {
               StringTokenizer st = new StringTokenizer(line, ",");
               String word1  = st.nextToken().trim();
               String word2  = st.nextToken().trim();
               
               // in all of these experiments we're only dealing 
               // with pairs of nouns
               TreeSet allPaths = elkb.getAllPaths(word1, word2, "N.");
               if (allPaths.size() != 0) {
                  PathSet ps = new PathSet(allPaths);
                  System.out.print(word1 + "," + word2 + "," 
                    + (16 - ps.getMinLength()) );
                  System.out.println( ", " + ps.getWordPair() );
               } else {
                  System.out.println(word1 + "," + word2);
               } 
            }
         }
      } catch (Exception e) {
         System.out.println("Error:" + e);
      }
   }
 
}
