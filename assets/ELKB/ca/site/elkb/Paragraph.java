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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents a <i>Roget's Thesaurus</i> Paragraph.
 * A Paragraph is defined by the following attributes:
 * <UL>
 * <li>Head number</li>
 * <li>Paragraph name</li>
 * <li>Paragraph keyword</li>
 * <li>Part-of-speech</li>
 * <li>list of Semicolon Groups</li>
 * <li>number of Semicolon Groups</li>
 * <li>number of words and phrases</li>
 * <li>number of Cross-references</li>
  * <li>number of See references</li>
 * </UL>
 * @author Mario Jarmasz
 * @version 1.0 Oct 2000
 */

public class Paragraph {
   // Attributes
   private int       headNum;
   private int       paraNum;
   private String    paraKey;
   private String    pos;
   //private ArrayList wordIndex[];
   private ArrayList sgList;
   private int       sgCount;
   private int       wordCount;
   private int       cRefCount;
   private int       seeCount;

   // Constructors
   // 1. No params
   // 2. headNum, paraNum, pos 
   // 3. headNum, paraNum, paraKey, pos

  /*************************************
   * Default constructor.
   *************************************/
   public Paragraph() {
      headNum    = 0;
      paraNum    = 0;
      paraKey    = new String();
      pos        = new String();
      //for (int i=0; i<3; i++) {
      //   wordIndex[i]  = new ArrayList();
      //}
      sgList     = new ArrayList();
      sgCount    = 0;
   }

  /****************************************************************
   * Constructor which sets the Head number, Paragraph number and
   * part-of-speech.
   ******************************************************************/ 
   public Paragraph(int head, int para, String p) {
      this();
      headNum = head;
      paraNum = para;
      pos     = p;
   }

  /****************************************************************
   * Constructor which sets the Head number, Paragraph number, 
   * keyword, and part-of-speech.
   ******************************************************************/
   public Paragraph(int head, int para, String key, String p) {
      this(head, para, p);
      paraKey = key;
   }

   // Methods
   // Get, set
   // getSGCount
   // getWordCount etc.
   // generateWordIndex

  /***************************************************************
   * Returns the Head number of this Paragraph.
   **************************************************************/
   public int getHeadNum() {
      return headNum;
   }

  /***************************************************************
   * Sets the Head number of this Paragraph.
   **************************************************************/
   public void setHeadNum(int num) {
      headNum = num;
   }

  /***************************************************************
   * Returns the number of this Paragraph.
   **************************************************************/
   public int getParaNum() {
      return paraNum;
   }

  /***************************************************************
   * Sets the number of this Paragraph.
   **************************************************************/   
   public void setParaNum(int num) {
      paraNum = num;
   }

  /***************************************************************
   * Returns the keyword of this Paragraph.
   **************************************************************/
   public String getParaKey() {
      return paraKey;
   }

  /***************************************************************
   * Sets the keyword of this Paragraph.
   **************************************************************/ 
   public void setParaKey(String key) {
      paraKey = key;
   } 
   
  /***************************************************************
   * Returns the part-of-speech of this Paragraph.
   **************************************************************/
   public String getPOS() {
      return pos;
   }

  /***************************************************************
   * Sets the part-of-speech of this Paragraph.
   **************************************************************/ 
   public void setPOS(String p) {
      pos = p;
   }

  /***************************************************************
   * Returns the number of words in this Paragraph.
   **************************************************************/
   public int getWordCount() {
      return wordCount;
   }

  /***************************************************************
   * Returns the number of Cross-references in this Paragraph.
   **************************************************************/
   public int getCRefCount() {
      return cRefCount;
   }

  /***************************************************************
   * Returns the number of See references in this Paragraph.
   **************************************************************/
   public int getSeeCount() {
      return seeCount;
   }

  /***************************************************************
   * Returns the number of Semicolon Groups in this Paragraph.
   **************************************************************/
   public int getSGCount() {
      return sgCount;
   }   

  /***************************************************************
   * Returns the array of Semicolon Groups of this Paragraph.
   **************************************************************/
   public ArrayList getSGList() {
      return sgList;
   }

  /********************************************************************************
   * Adds a Semicolon Group, repreented as a string, to the Paragraph.
   * This method parses a string similar to: 
   * <CODE>^sg>^i>frenzy, ^/i>monomania, furore, frenetic condition,;^/sg></CODE>.
   ********************************************************************************/
   public void addSG(String sg) {
      // remove the <sg> </sg> tags
      sg = sg.replaceAll("<sg>", "");
      sg = sg.replaceAll("</sg>", "");

      sgCount++;

      SG sgObj = new SG(sgCount, paraNum, headNum, sg, pos);
      sgList.add(sgObj);
      wordCount += sgObj.getWordCount();
      cRefCount += sgObj.getCRefCount();
      seeCount  += sgObj.getSeeCount();

      // Shortest semicolon group ?
      // Longest semicolon group ?
   }

  /***************************************************************
   * Returns the the first Semicolon Group in this Paragraph
   * which contains the given word.
   **************************************************************/
   public SG getSG(String word) {
   // Get a semicolon group in the paragrapg that contains
   // the word or phrase equal to the string word
   // Will have to include keywords also
      SG elkbSG = new SG();   
      ArrayList wordList = new ArrayList();

      Iterator iter = sgList.iterator();
      while ( iter.hasNext() ) {
         elkbSG = (SG) iter.next();
	 wordList = elkbSG.getAllWordList();
	 if (wordList.contains(word)) {
	    break;
	 }
      }
      return elkbSG;
   }
   
  /***************************************************************
   * Returns the Semicolon Group  at the specified position
   * in the array of Semicolon Groups. 
   **************************************************************/
   public SG getSG(int index) {
      // I should check if the index is out of bounds...
      return (SG) sgList.get(index);
   }
	 
   // public delSG ??

   private boolean generateWordIndex() {
      return true;
   }

   /**
    * Extracts the keyword from a Semicolon Group represented
    * as a string. For example, the keyword <TT>existence</TT>
    * is returned when the following string is supplied:
    * <CODE>^sg>^i>existence, ^/i>being, entity;^/sg></CODE>
    */
   public String parseParaKey(String line) {
      String key = new String();
      
      Boolean keyfound = false;
      
      if ( line.startsWith("<sg>") ) {
    	  line = line.replaceAll("<sg>", "");
          line = line.replaceAll("</sg>", "");
          
         StringTokenizer st = new StringTokenizer(line, ",");
         // new (changed O. Medelyan, May 06)
         while (st.hasMoreTokens() && keyfound == false) {
        	 
        	 key = (String) st.nextToken();
        	 key = key.trim();	 
        	 
        	 if (key.startsWith("<i>")) {        		 
        		 key = key.replaceAll("<i>", "");
        		 keyfound = true;
        	 }	
         }
         //old        
         //key = (String) st.nextToken();
         
         // remove <sg>, <i>, </i>, "?" and "!"
         key = key.replaceAll("<sg>", "");
         key = key.replaceAll("</sg>", "");
         key = key.replaceAll("<i>", "");
         key = key.replaceAll("</i>", "");
         key = key.replaceAll("\\?", "");
         key = key.replaceAll("!", "");
         
         key = key.trim();
         

      } else {
         // we should raise an exception, but....
         key = "";
      }      
      return key;
   }

  /************************************************************************************
   * Converts to a string representation the <TT>Paragraph</TT> object.
   *************************************************************************************/
   public String toString() {
      String info = new String();
      info = super.toString();
      info += "@" + getHeadNum() + "@" + getParaNum();
      info += "@" + getParaKey() + "@" + getPOS();   
      info += "@" + getSGCount() + "@" + getWordCount();
      return info;
   }

  /****************************************************************
   * Prints the contents of this Paragraph to the standard output.
   ***************************************************************/
   public void print() {
      System.out.print( format() );
   }

  /************************************************************************************
   * Converts to a string representation, similar to the printed format,
   * the <TT>Paragraph</TT> object.
   *************************************************************************************/
   public String format() {
      String info = new String();
      Iterator iter = sgList.iterator();
      while ( iter.hasNext() ) {
         SG sgObj = (SG) iter.next();
         info += sgObj.format() + "\n";
      }
      return info;
   }

   /****************************************************************************************
    * Compares this paragraph to the specified object.
    * The result is <code>true</code> if the argument is not
    * <code>null</code> and is a <code>Paragraph</code> object that has the
    * same <code>paraKey</code> as this object.
    *
    * @param   anObject   the object to compare this
    *                     <code>Paragraph</code> against.
    * @return  <code>true</code> if the <code>Paragraph</code> have the
    *                            same <code>paraKey</code>;
    *          <code>false</code> otherwise.
    * @see     java.lang.String#compareTo(java.lang.String)
    * @see     java.lang.String#equalsIgnoreCase(java.lang.String)
    ******************************************************************************************/
   public boolean equals(Object anObject) {
      if (this == anObject) {
         return true;
      }
      if ((anObject != null) && (anObject instanceof Paragraph)) {
         Paragraph paraObject = (Paragraph)anObject;
         return ( paraKey.equals( paraObject.getParaKey() ) );
      }
      return false;
   }   


   /*******************************************************************
    * Returns all of the words and phrases in a paragraph.
    *******************************************************************/
   public ArrayList getAllWordList() {
      ArrayList allWords = new ArrayList();
      SG elkbSG;

      Iterator sIter = sgList.iterator();
      while ( sIter.hasNext() ) {   
         elkbSG = (SG) sIter.next();
         allWords.addAll( elkbSG.getAllWordList() );
      }
      
      return allWords;
   }

   /*******************************************************************
    * Prints all of the words and phrases in the Paragraph on
    * a separate line to the standard output.
    * The words and phrasses are printed using the following
    * format: <CODE>word:keyword:headnum:pos</CODE>
    ******************************************************************/
    // SHOULD ALSO USE getAllWordList IN THIS METHOD
    // Could be better if it returned a StringBuffer or String...

   public void printAllWords() {
      SG elkbSG;
      ArrayList wordList;

      Iterator sIter = sgList.iterator();
      while ( sIter.hasNext() ) {
         elkbSG = (SG) sIter.next();
         wordList = elkbSG.getAllWordList();
         Iterator wIter = wordList.iterator();
         while ( wIter.hasNext() ) {
            System.out.println( (String) wIter.next()
                                   + ":" + getParaKey()
                                   + ":" + getHeadNum()
                                   + ":" + getPOS() );
         }
      }      
   }
   
   /************************************************************************
    * Prints all the contents of all Semicolon Groups, including references,
    * without any special formatting.
    * Allows to compare Semicolon Groups to <i>WordNet</i> synsets.
    ************************************************************************/
   public void printAllSG() {
      SG elkbSG;
      ArrayList wordList;

      Iterator sIter = sgList.iterator();
      while ( sIter.hasNext() ) {
         elkbSG = (SG) sIter.next();
         wordList = elkbSG.getAllWordList();
         System.out.println(wordList);
      }      
   }

}
