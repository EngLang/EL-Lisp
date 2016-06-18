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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the Text of <i>Roget's Thesaurus</i>.
 * The following information is maintained for the Text:
 * <UL>
 * <li>number of Heads</li>
 * <li>number of Paragraphs</li>
 * <li>number of words and phrases</li>
 * <li>number of Semicolon Groups</li>
 * <li>number of Cross-references</li>
 * <li>number of See references</li>
 * </UL>
 * This information is also kept for all nouns, adjectives, verbs, adverbs
 * and interjections.
 *
 * @author Mario Jarmasz
 * @version 1.0 Oct 2000
 */

public class RogetText implements Serializable {
   // Attributes
   private ArrayList headList;
   
   private int wordCount;
   private int headCount;
   private int paraCount;
   private int sgCount;
   private int cRefCount;
   private int seeCount;

   private int nCount;
   private int adjCount;
   private int vbCount;
   private int advCount;
   private int intCount;

   private int nParaCount;
   private int adjParaCount;
   private int vbParaCount;
   private int advParaCount;
   private int intParaCount;  

   private int       nSGCount;  
   private int       adjSGCount;
   private int       vbSGCount; 
   private int       advSGCount;
   private int       intSGCount;
   
   private int       nCRefCount;
   private int       adjCRefCount;
   private int       vbCRefCount;
   private int       advCRefCount;
   private int       intCRefCount;
   
   private int       nSeeCount;   
   private int       adjSeeCount;   
   private int       vbSeeCount;  
   private int       advSeeCount; 
   private int       intSeeCount;   

   private String    sPath;

   // Constructors
   // 1. No params
   // 2. Range of heads to load

   private void init() {
      wordCount = 0;
      headCount = 0;
      paraCount = 0;
      sgCount   = 0;
      cRefCount = 0;
      seeCount  = 0;
      
      nCount     = 0;
      adjCount   = 0;
      vbCount    = 0;
      advCount   = 0;
      intCount   = 0; 
      
      nParaCount   = 0; 
      adjParaCount = 0;
      vbParaCount  = 0;
      advParaCount = 0;
      intParaCount = 0;

      nSGCount   = 0;
      adjSGCount = 0;
      vbSGCount  = 0;
      advSGCount = 0;
      intSGCount = 0;
   
      nCRefCount   = 0;
      adjCRefCount = 0;
      vbCRefCount  = 0;
      advCRefCount = 0;
      intCRefCount = 0;
   
      nSeeCount   = 0; 
      adjSeeCount = 0; 
      vbSeeCount  = 0; 
      advSeeCount = 0; 
      intSeeCount = 0;
   }

   private void addEmptyHead() {
      // Add an empty Head so that our index is correct
      Head newHead = new Head();
      headList.add(newHead);
   }

  /*************************************
   * Default constructor.
   *************************************/      
   public RogetText() {
      init();
      headList = new ArrayList();
      addEmptyHead();
   }

  /**********************************************
   * Constructor which specifies the number of 
   * Heads contained in this RogetText.
   **********************************************/
   public RogetText(int capacity) {
      init();
      headList = new ArrayList(capacity);
      addEmptyHead();
   }

  /*****************************************************************************
   * Constructor that builds the <TT>RogetText</TT> object by specifying
   * the number of Heads and using the information
   * contained files which end with <TT>.txt</TT>. 
   *****************************************************************************/
   public RogetText(int capacity, String fileName) {
      this(capacity);
      loadFromFile(capacity, fileName, ".txt");
   }

  /*****************************************************************************
   * Constructor which specifies the directory in which the Heads are found.
   *****************************************************************************/ 
   public RogetText(String path) {	   	
      this();
      sPath = path;      
   }
      
  /*****************************************************************************
   * Constructor that builds the <TT>RogetText</TT> object by specifying
   * the number of Heads and using the information
   * contained files which end with the given extension. 
   *****************************************************************************/
   public RogetText(int capacity, String fileName, String extension) {
      this(capacity);
      loadFromFile(capacity, fileName, extension);
   }

   private void loadFromFile(int capacity, String fileName, 
                             String extension) {
	   
      for (int i=1; i<=capacity; i++) {
         // System.out.print("#");
         try {
            Head headObj = new Head(fileName + i + extension);
            addHead(headObj);
         } catch (Exception e) {
            Head headObj = new Head(headCount + 1, "EMPTY", 0, 0);
            addHead(headObj);
            System.out.println(fileName + i + extension);
            System.out.println(e);
         }
      }
   }    

 /**************************************************************
  * Adds a <TT>Head</TT> object to this RogetText.
  **************************************************************/
   public void addHead(Head headObj) {
      // System.out.println(headObj);
      headList.add(headObj);
      headCount++;
 
      // Adjust the other values
      paraCount += headObj.getParaCount();
      sgCount   += headObj.getSGCount();
      wordCount += headObj.getWordCount();
      cRefCount += headObj.getCRefCount();
      seeCount  += headObj.getSeeCount();

      nCount    += headObj.getNCount();
      adjCount  += headObj.getAdjCount();
      vbCount   += headObj.getVbCount();
      advCount  += headObj.getAdvCount();
      intCount  += headObj.getIntCount();

      nParaCount   += headObj.getNParaCount();
      adjParaCount += headObj.getAdjParaCount();
      vbParaCount  += headObj.getVbParaCount();
      advParaCount += headObj.getAdvParaCount();
      intParaCount += headObj.getIntParaCount();

      nSGCount   += headObj.getNSGCount();
      adjSGCount += headObj.getAdjSGCount();
      vbSGCount  += headObj.getVbSGCount();
      advSGCount += headObj.getAdvSGCount();
      intSGCount += headObj.getIntSGCount();

      nCRefCount   += headObj.getNCRefCount();
      adjCRefCount += headObj.getAdjCRefCount();
      vbCRefCount  += headObj.getVbCRefCount();
      advCRefCount += headObj.getAdvCRefCount();
      intCRefCount += headObj.getIntCRefCount();
      
      nSeeCount   += headObj.getNSeeCount();
      adjSeeCount += headObj.getAdjSeeCount();
      vbSeeCount  += headObj.getVbSeeCount();
      advSeeCount += headObj.getAdvSeeCount();
      intSeeCount += headObj.getIntSeeCount();
   }

 /**************************************************************
  * Adds a Head which is contained in the specified file
  * to this RogetText.
  **************************************************************/
   public void addHead(String fileName) {
      Head headObj = new Head(fileName);
      addHead(headObj);
   }

 /**************************************************************
  * Returns the Head with the specified number.
  **************************************************************/
   public Head getHead(int headNum) {
      // This method has changed 16.11.01
      // return (Head) headList.get(headNum);
      Head head = new Head(sPath + headNum + ".txt");
      return head;
   }


  /***************************************************************
   * Returns the number of Heads in this RogetText.
   **************************************************************/
   public int getHeadCount() {
      return headCount;
   }

  /***************************************************************
   * Returns the number of Paragraphs in this RogetText.
   **************************************************************/
   public int getParaCount() {
      return paraCount;
   }

  /***************************************************************
   * Returns the number of noun Paragraphs in this RogetText.
   **************************************************************/
   public int getNParaCount() {
      return nParaCount;
   }

  /***************************************************************
   * Returns the number of adjective Paragraphs in this RogetText.
   **************************************************************/
   public int getAdjParaCount() {
      return adjParaCount;
   }

  /***************************************************************
   * Returns the number of verb Paragraphs in this RogetText.
   **************************************************************/
   public int getVbParaCount() {
      return vbParaCount;
   }

  /***************************************************************
   * Returns the number of adverb Paragraphs in this RogetText.
   **************************************************************/
   public int getAdvParaCount() {
      return advParaCount;
   }

  /***************************************************************
   * Returns the number of interjection Paragraphs in this RogetText.
   **************************************************************/
   public int getIntParaCount() {
      return intParaCount;
   }

  /***************************************************************
   * Returns the number of Semicolon Groups in this RogetText.
   **************************************************************/
   public int getSGCount() {
      return sgCount;
   }

  /***************************************************************
   * Returns the number of noun Semicolon Groups in this RogetText.
   **************************************************************/
   public int getNSGCount() {
      return nSGCount;
   }

  /***************************************************************
   * Returns the number of ajective Semicolon Groups in this RogetText.
   **************************************************************/      
   public int getAdjSGCount() {
      return adjSGCount;
   }

  /***************************************************************
   * Returns the number of verb Semicolon Groups in this RogetText.
   **************************************************************/      
   public int getVbSGCount() {
      return vbSGCount;
   }

  /***************************************************************
   * Returns the number of adverb Semicolon Groups in this RogetText.
   **************************************************************/      
   public int getAdvSGCount() {
      return advSGCount;
   }

  /***************************************************************
   * Returns the number of interjection Semicolon Groups in this RogetText.
   **************************************************************/      
   public int getIntSGCount() {
      return intSGCount;
   }   

  /***************************************************************
   * Returns the number of Cross-references in this RogetText.
   **************************************************************/
   public int getCRefCount() {
      return cRefCount;
   }

  /***************************************************************
   * Returns the number of See referencs in this RogetText.
   **************************************************************/
   public int getSeeCount() {
      return seeCount;
   }

  /***************************************************************
   * Returns the number of noun See referencs in this RogetText.
   **************************************************************/
   public int getNSeeCount() {
      return nSeeCount;
   }

  /***************************************************************
   * Returns the number of adjective See referencs in this RogetText.
   **************************************************************/      
   public int getAdjSeeCount() {
      return adjSeeCount;
   }

  /***************************************************************
   * Returns the number of verb See referencs in this RogetText.
   **************************************************************/      
   public int getVbSeeCount() {
      return vbSeeCount;
   }

  /***************************************************************
   * Returns the number of adverb See referencs in this RogetText.
   **************************************************************/      
   public int getAdvSeeCount() {
      return advSeeCount;
   }

  /***************************************************************
   * Returns the number of interjection See referencs in this RogetText.
   **************************************************************/      
   public int getIntSeeCount() {
      return intSeeCount;
   }

  /***************************************************************
   * Returns the number of noun Cross-references in this RogetText.
   **************************************************************/
   public int getNCRefCount() {
      return nCRefCount;
   }

  /***************************************************************
   * Returns the number of adjective Cross-references in this RogetText.
   **************************************************************/      
   public int getAdjCRefCount() {
      return adjCRefCount;
   }

  /***************************************************************
   * Returns the number of verb Cross-references in this RogetText.
   **************************************************************/      
   public int getVbCRefCount() {
      return vbCRefCount;
   }

  /***************************************************************
   * Returns the number of adverb Cross-references in this RogetText.
   **************************************************************/      
   public int getAdvCRefCount() {
      return advCRefCount;
   }

  /***************************************************************
   * Returns the number of interjection Cross-references in this RogetText.
   **************************************************************/      
   public int getIntCRefCount() {
      return intCRefCount;
   }

  /***************************************************************
   * Returns the number of words and phrases in this RogetText.
   **************************************************************/
   public int getWordCount() {
      return wordCount;
   }

  /***************************************************************
   * Returns the number of nouns in this RogetText.
   **************************************************************/
   public int getNCount() {  
      return nCount;  
   }

  /***************************************************************
   * Returns the number of adjectives in this RogetText.
   **************************************************************/
   public int getAdjCount() { 
      return adjCount; 
   }

  /***************************************************************
   * Returns the number of verbs in this RogetText.
   **************************************************************/
   public int getVbCount() {
      return vbCount;
   }

  /***************************************************************
   * Returns the number of adverbs in this RogetText.
   **************************************************************/
   public int getAdvCount() { 
      return advCount; 
   }

  /***************************************************************
   * Returns the number of interjections in this RogetText.
   **************************************************************/
   public int getIntCount() {
      return intCount;
   }
   
  /************************************************************************************
   * Converts to a string representation the <TT>RogetText</TT> object.
   *************************************************************************************/
   public String toString() {
      String info = new String();
      info = super.toString();
      info += "@" + getHeadCount() + "@" + getParaCount();
      info += "@" + getSGCount()   + "@" + getWordCount();
      return info;
   }

  /****************************************************************
   * Prints the contents of a Head specified by its number 
   * to the standard output.
   ***************************************************************/
   public void printHead(int headNum) {
      Head headObj = getHead(headNum);
      headObj.print();
   }
}

// calculate path
