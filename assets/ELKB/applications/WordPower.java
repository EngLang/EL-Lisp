package applications;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

import ca.site.elkb.Path;
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
 *    WordPower.java
 *    Copyright (C) 2006 Mario Jarmasz, Tad Stach and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 *    and
 *    Olena Medelyan
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */


/******************************************************************************
 * Author: Tad Stach (1587688)
 * Date: Saturday, March 2, 2002
 *
 * Program Title:  WordPower
 *
 * Program Description:  The WordPower program uses the Roget's Electronic
 * 		Thesaurus to calculate the results of Reader's
 *			Digest Word Power game questions.  The Electronic 
 *			Thesaurus has been designed and implemented by 
 *			Mario Jarmasz.  Also note that this program is based 
 *			highly on Mario Jarmasz's Semantic Distance program.			 			
 *
 * Modified: Oct. 29, 2002 - Mario Jarmasz
 *           Nov. 7,  2003 - Mario Jarmasz
 *              
 * Usage  : java WordPower <inputFile>
 *
 * Format of input file: <problem word> | <solution word> | <other word> | <other word> | <other word>
 *     A line beginning with a # is considered a comment
 *******************************************************************************/

class WordPower 
{
  public RogetELKB elkb;

  /*** CONSTRUCTOR ***/
  public WordPower() 
  {
	elkb = new RogetELKB();
  }

  /*** MAIN ***/
  public static void main(String args[]) 
  {
     if (args.length != 1) {
        System.out.println("java WordPower <inputFile>");
     } else { 
  	     WordPower wordPower = new WordPower();
        wordPower.wp(args[0]);
     }
  }

  /*** WP ***/
  /* This method does the main calculation */
  private void wp(String fileName)
  {
	// 1. read a line
	// 2. parse the 4 words
	// 3. save answer
	// 4. calculate paths
	// 5. find shortest
	// 6. compare to answer
  
	int index = 1;
	int correct = 0;
	int tieBroken = 0;
	int tieLost = 0;
	int notInIndex = 0;
	String answer = "";
	// String not_found[] = new String [500];

        ArrayList qWNotFound = new ArrayList(); // question words
        ArrayList aWNotFound = new ArrayList(); // answer words
        ArrayList oWNotFound = new ArrayList(); // other words

	int nfcount = 0;
	int nf;

        // variables used for keeping stats:
        int questnotfound=0;	// Question not found in Index
        int answernotfound=0;	// Answer not found in Index  
        int othernotfound=0;    // Other word not found in Index      

	try 
	{
	  BufferedReader br = new BufferedReader(new FileReader(fileName));
	  for ( ; ; )
	  {
		String line = br.readLine();
                //line = line.trim();

		if (line == null)
		{
		  br.close();
		  index--;
		  break;
		} 

                // do not read blank lines or line that start with #line 
		else if ( ( line.startsWith("#") ||
                            line.equals("") ) 
                           == false )
		{ 

		  System.out.println("Question " + index + ": " + line);
		  StringTokenizer st = new StringTokenizer(line, "|");

		  String problem  = st.nextToken().trim();
		  String solution = st.nextToken().trim();
		  String word2    = st.nextToken().trim();
		  String word3    = st.nextToken().trim();
		  String word4    = st.nextToken().trim();

		  TreeSet probToSol = elkb.getAllPaths(problem, solution);
		  TreeSet probToW2  = elkb.getAllPaths(problem, word2);
		  TreeSet probToW3  = elkb.getAllPaths(problem, word3);
		  TreeSet probToW4  = elkb.getAllPaths(problem, word4);

		  nf=0;
	      
		  ArrayList inIndex;

		  if ( inindex(problem) == 0) 
		  {
			System.out.println(problem + " (PROBLEM) not found in the index!!");
			qWNotFound.add(problem); 
			nfcount++;
			questnotfound++;
			nf++;
		  }
		  else
		  {

			/*** Obtain the shortest path for each pair and stuff them into a TreeSet so they are sorted ***/
			TreeSet shortest = new TreeSet();
			Path probToSolPath = new Path();


			/*** Problem to Solution ***/
			if (probToSol.size() != 0) 
			{
			  PathSet ps1 = new PathSet(probToSol);
                          ps1.setOrigWord1(problem);
                          ps1.setOrigWord2(solution);
			  System.out.println(ps1);
			  shortest.add(ps1);
			} 
			else
			{
			  if ( findbest(problem,solution).equals("NO GOOD"))
			  {
				System.out.println(solution +
                                         " (ANSWER) is NOT IN THE INDEX");
				aWNotFound.add(solution);
                                answernotfound++;
                                notInIndex++;
				nf++;
			  }
			  else
			  {
				String solbest = findbest(problem,solution);
				//solution = solbest;
				probToSol = elkb.getAllPaths(problem, solbest);
				PathSet ps1 = new PathSet(probToSol);
                                ps1.setOrigWord1(problem);
                                ps1.setOrigWord2(solution);
				System.out.println(ps1);
				shortest.add(ps1);
			  }	
			}

			/*** Problem to Word2 ***/ 
			if (probToW2.size() != 0)
			{
			  PathSet ps2 = new PathSet(probToW2);
                          ps2.setOrigWord1(problem);
                          ps2.setOrigWord2(word2);
			  System.out.println(ps2);
			  shortest.add(ps2);
			} 
			else
			{
			  if ( findbest(problem,word2).equals("NO GOOD"))
			  {
				System.out.println(word2 + " is NOT IN THE INDEX");
				othernotfound++;
                                oWNotFound.add(word2);
                                notInIndex++;
				nf++;
			  }
			  else
			  {
				String solbest = findbest(problem,word2);
				//word2 = solbest;
				probToW2 = elkb.getAllPaths(problem, solbest);
				PathSet ps2 = new PathSet(probToW2);
                                ps2.setOrigWord1(problem);
                                ps2.setOrigWord2(word2);
				System.out.println(ps2);
				shortest.add(ps2);
			  }	
			}

			/*** Problem to Word3 ***/
			if (probToW3.size() != 0) 
			{
			  PathSet ps3 = new PathSet(probToW3);
                          ps3.setOrigWord1(problem);
                          ps3.setOrigWord2(word3);
			  System.out.println(ps3);
			  shortest.add(ps3);
			} 
			else
			{
			  if ( findbest(problem,word3).equals("NO GOOD"))
			  {
				System.out.println(word3 + " is NOT IN THE INDEX");
			        othernotfound++;
                                oWNotFound.add(word3);
                        	notInIndex++;
				nf++;
			  }
			  else
			  {
				String solbest = findbest(problem,word3);
				//word3 = solbest;
				probToW3 = elkb.getAllPaths(problem, solbest);
				PathSet ps3 = new PathSet(probToW3);
                                ps3.setOrigWord1(problem);
                                ps3.setOrigWord2(word3);
				System.out.println(ps3);
				shortest.add(ps3);
			  }	
			}

			/*** Problem to Word4 ***/
			if (probToW4.size() != 0) 
			{
			  PathSet ps4 = new PathSet(probToW4);
			  System.out.println(ps4);
                          ps4.setOrigWord1(problem);
                          ps4.setOrigWord2(word4);
			  shortest.add(ps4);
			} 
			else
			{
			  if ( findbest(problem,word4).equals("NO GOOD"))
			  {
				System.out.println(word4 + " is NOT IN THE INDEX");
				othernotfound++;
                                oWNotFound.add(word4);
                                notInIndex++;
				nf++;
			  }
			  else
			  {
				String solbest = findbest(problem,word4);
				//word4 = solbest;
				probToW4 = elkb.getAllPaths(problem, solbest);
				PathSet ps4 = new PathSet(probToW4);
                                ps4.setOrigWord1(problem);
                                ps4.setOrigWord2(word4);
				System.out.println(ps4);
				shortest.add(ps4);
			  }	
			}

			if (nf < 4)
			{ 
			  /*** Extract the shortets path ***/
			  PathSet psShortest = (PathSet)shortest.first();
			  //answer = psShortest.getWord2();
                          answer = psShortest.getOrigWord2();

                          // there is a problem here as what is returned 
                          // from the path set may not be 

			  System.out.println("Roget thinks that " + problem + " means " + answer);
			  if (answer == solution)
			  {
				if (nf < 2)
				{
				  /*** Lets check to see if a tie was broken ***/
				  Iterator iter = shortest.iterator();
				  iter.next();
				  PathSet ps2 = (PathSet)iter.next();
				  if ( (psShortest.getMinLength() == ps2.getMinLength() ) && (psShortest.getCTotal() > ps2.getCTotal()) )
				  {
					System.out.println("TIE BROKEN");
					tieBroken++;
				  }
				}
				System.out.println("CORRECT");
				correct++;
			  } 
			  else
			  {
				if (nf < 2)
				{
				  /*** Lets check to see if tie was lost make sure that the next shortest path contains the answer ***/
				  Iterator iter = shortest.iterator();
				  iter.next();
				  PathSet ps2 = (PathSet)iter.next();
				  if ( (psShortest.getMinLength() == ps2.getMinLength() ) && (psShortest.getCTotal() > ps2.getCTotal()) && (ps2.getWord2().equals(solution)) )
				  {
					System.out.println("TIE LOST");
					tieLost++;
				  }
				}
				System.out.println("INCORRECT");
			  }
			}
		  }
		  System.out.println();
		  index++;
		}
	  }
	  System.out.println("Final score: " + correct + "/" + index + ". " + tieBroken + " ties broken, " + tieLost + " ties lost.");
	  System.out.println();
	  System.out.println("The answer was not in the index " + notInIndex + " times. ");
	  System.out.println();
	  System.out.println("The question was not in the index " + questnotfound + " times.");
	  System.out.println();
          
          // New stats - MJ
          System.out.println("-- NEW STATS --");
          System.out.println("Question word not in index: " + 
             questnotfound + " times.");
          System.out.println("Answer word not in index: " + 
             answernotfound + " times.");
          System.out.println("Other word not in index: " +
             othernotfound + " times.");

	  // This loop can be added to list words and phrases not found in 
          // 	
          System.out.println();

          System.out.println("The following question words were not found"
             + " in Roget: " + qWNotFound);

          System.out.println("The following answer words were not found"
             + " in Roget: " + aWNotFound);

          System.out.println("Other words that were not found"
             + " in Roget: " + oWNotFound);


	}
	catch (Exception e)
	{
	  System.out.println("Error:" + e);   
	}
  }


  /*** FINDBEST ***/
  /* This procedure cuts up a phrase in relation to the solution to determine 
     which word in the phrase corresponds best */  
  public String findbest(String prob, String phrase)
  {
 	TreeSet sol = new TreeSet();
	TreeSet set[] = new TreeSet[10];
	String st[] = new String[10];
	int i = 0;
	String best = "";
	PathSet ps[]= new PathSet[10];
	StringTokenizer subst = new StringTokenizer(phrase," ");
	while (subst.hasMoreTokens())
	{
	  st[i]  = subst.nextToken().trim();
	  if ( inindex(st[i]) == 1 &&  !st[i].equals("to") && !st[i].equals("be") && !st[i].equals("and"))
	  {
		set[i] = elkb.getAllPaths(prob,st[i]);
		ps[i] = new PathSet(set[i]);
		sol.add(ps[i]);
	  }
	  i++;
	}
	if (sol.size() != 0 )
	{
	  PathSet psShort = (PathSet)sol.first();
	  best = psShort.getWord2();
	  return best;
	}
	else
	{
	  return ("NO GOOD");
	}
  }

  /*** ININDEX ***/
  /* This procedure returns 1 if the word is in the index, 0 otherwise */
  public int inindex(String x)
  {
	int y = 1;
	ArrayList inIndex = elkb.index.getStrRefList(x);      
	if (inIndex.size() == 0)
	{
	  y = 0;
	}
	return y;
  }
}	
