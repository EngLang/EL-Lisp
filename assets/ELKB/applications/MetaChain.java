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
 *    MetaChain.java
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 *    and
 *    Olena Medelyan
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */


/**
 * MetaChain class: represents a MetaChain used to build LexicalChains
 * A MetaChain is defined by:
 *       + chainHead: the word for which the chain is being built
 *       + startLine: the line at which the chain starts
 *       + senseNo  : the Head number of the head of the chain [unique sense]
 *       + score    : the score of the MetaChain (cf. Jarmasz, 2003)
 *       + words    : array of words that are in the chain
 *       + rels     : array of relations that link the words to the chain
 *       + lines    : array of line numbers of words in the chain
 *
 * This class implements the comparable interface.
 * If two chains have the same score, the one with the smallest
 * line number is the greatest.
 *
 * @author Mario Jarmasz
 * @version 1.0 Apr 2003
 **/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MetaChain implements Comparable {
 
   /******************************************
    * Iterator (inner clas)
    *****************************************/
 
   private class ChainIter implements Iterator {
      private Iterator wordsIter;
      private Iterator relsIter;
      private Iterator linesIter;
       	
	   private ChainIter() {
	      wordsIter = words.iterator();
	      relsIter  = rels.iterator();
	      linesIter = lines.iterator();
	   }

	   public Object next() {
	      if (! hasNext())
	         throw new NoSuchElementException();
         relsIter.next();
         linesIter.next();
         return wordsIter.next();
      }
       
	   public boolean hasNext() {
	      return wordsIter.hasNext();
	   }

   	public void remove() {
	      wordsIter.remove();
	      relsIter.remove();
	      linesIter.remove();
	   }
   }


   /******************************************
    * Constants
    *****************************************/
   public static final double T0_WEIGHT         = 1.00;
   public static final double T1_WEIGHT_NEAR    = 1.00;
   public static final double T1_WEIGHT_VICINAL = 0.75;
   public static final double T1_WEIGHT_FAR     = 0.50;

   /******************************************
    * Attributes
    *****************************************/
   private String    chainHead;
   private int       startLine;
   private int       senseNo;
   private ArrayList words;
   private ArrayList rels;
   private ArrayList lines;
   
   /******************************************
    * Constructors
    *****************************************/
   public MetaChain() {
      chainHead = new String();
      startLine = 0;
      senseNo   = 0;
      words     = new ArrayList();
      rels      = new ArrayList();
      lines     = new ArrayList();
   }

   public MetaChain(String head, int start, int sense) {
      this();
      chainHead = head;
      startLine = start;
      senseNo   = sense;
   }
   
   /********************************************************
    * add
    * Add a word, relation, line number triple to MetaChain
    *******************************************************/
   public void add(String word, String rel, Integer line) {
       words.add(word);
       rels.add(rel);
       lines.add(line);
   }
 
   /**********************************************************
    * remove
    * Remove a word, relation, line number triple to MetaChain
    **********************************************************/   
   public void remove(int index) throws IndexOutOfBoundsException {
      words.remove(index);
      rels.remove(index);
      lines.remove(index);
   }
   
   /************************************************************
    * Property get methods
    ***********************************************************/
    public String getChainHead() {
       return chainHead;
    }
    
    public int getStartLine() {
       return startLine;
    }
    
    public int getSenseNumber() {
       return senseNo;
    }
    
    public Iterator iterator() {
	    return new ChainIter();
    }
     
   /************************************************************
    * getScore
    * Computing the chain scores according to Barzilay & Elhadad (1997)
    ************************************************************/
   public double getScoreHIndex() {
      double score = 0;

      // number of occurrences of members in the chain
      int length = 0;
      // number of distinct occurrences
      int distinct = 0;
      // homogeneity index
      double homogeneity;

      HashMap map = new HashMap();

      String word = "";

      Iterator iter = words.iterator();      
      while (iter.hasNext()) {

	  word = (String)iter.next();
	  if (map.get(word) == null) {
	      map.put(word, new Integer(1));
	      distinct++;
	  }
	  length++;
      }
      if (length > 0) {
	  homogeneity = 1 - distinct/length;
      } else {
	  homogeneity = 0;
      }
      score = length*homogeneity;
      return score;
   }


   /************************************************************
    * getScoreElkb
    * Will be more elaborate in the future
    * (Mario's computation for chain scores) 
    ************************************************************/
   public double getScoreElkb() {
      double score = 0;
      double delta = 0;
      int prevLineNo = 0;
      int curLineNo  = 0;
      String strRel;
      
      Iterator iterRel    = rels.iterator();
      Iterator iterLineNo = lines.iterator();
   
      prevLineNo = startLine;
      
      // both arrays are the same size, therefore only one check
      // should be enough
      while ( iterRel.hasNext() ) {
         strRel = (String) iterRel.next();
         curLineNo = ((Integer)iterLineNo.next()).intValue();
         
         if ( strRel.equals("T0") ) {
            score += T0_WEIGHT; 
        } else if ( strRel.equals("T1") ) {
            delta = curLineNo - prevLineNo;
            
            if ( (delta >= 0) && (delta <= 2) ) {
               score += T1_WEIGHT_NEAR;
            } 
            else if ( (delta >= 3) && (delta <= 5) ) {
               score += T1_WEIGHT_VICINAL ;
            } else {
               score += T1_WEIGHT_FAR;
            } 
            
         }
         
         prevLineNo = curLineNo;
      } // end while

      return score;
   }
 
   
   /************************************************************
    * toString
    * String representation of MetaChain: 
    *    chainHead, words [score: , sense:, line: ]
    * example:
    *    regard, events, reference, event, respect [score: 5, sense: 10, line: 4]
    ************************************************************/ 
   public String toString() {
      StringBuffer result = new StringBuffer();

      Iterator iter = words.iterator();
      if ( iter.hasNext() )
         result.append( (String)iter.next() );
      
      while (iter.hasNext()) {
         result.append(", ");
         result.append( (String)iter.next() );
      }
      
      result.append(" [score: ");
      result.append(getScoreElkb());
      result.append(", sense: ");
      result.append(senseNo);
      result.append(", line: ");
      result.append(startLine);
      result.append("]");
      
      return result.toString();
   }

   /************************************************************
    * findKeyword
    * Selects the most frequent word in a MetaChain 
    * example:
    *    Input: regard, event, reference, event, respect 
    *    Output: event (regard, event, reference, respect)
    ************************************************************/ 
   public String findKeyword() {
      String result = "";
      Map map = new HashMap();
      Integer tmp;
      String word;
      String keyword = "";
      Integer maxFreq = new Integer(0);

      Iterator iter = words.iterator();      
      while (iter.hasNext()) {
	  word = (String)iter.next();
	  tmp = (Integer)map.get(word);

	  if (tmp == null) {
	      map.put(word, new Integer(1));
	  } else {
	      map.put(word, new Integer(tmp.intValue() + 1));
	  }
      }
      for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
	  word = (String)it.next();

	  if (result != "") {
	      result = result + ", " + word;
	  } else {
	      result = word;
	  }

	  tmp = (Integer)map.get(word);
	  if (tmp.compareTo(maxFreq) == 1) {
	      keyword = word;
	      maxFreq = tmp;
	  }
      }
      result = keyword + "\t(" + result + ")";
      return result;
   }
 

   /***************************************************************
    * compareTo
    * Comparison is done according to the score
    * If two chains have the same score, then the line
    * number is used to break the tie, the smaller the line number,
    * the greater the score
    ***************************************************************/  
   public int compareTo(Object obj) {
      double result;
      MetaChain other = (MetaChain) obj;
           
      result = (other.getScoreElkb()*100) - (this.getScoreElkb()*100);
      if (result == 0) {
         result = this.startLine - other.startLine;
         // if result == 0 it means that two chains are identical
         // for this to be true, all of the elements would have
         // to be the same
         if (result == 0) {
            result = -1;
         }
      }  
      
      Double dblRes = new Double(result);
      return dblRes.intValue();
   }
   
   // toStringVerbose() - same but in more detail
}