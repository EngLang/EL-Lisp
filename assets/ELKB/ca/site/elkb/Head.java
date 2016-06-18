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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/** 
 * Represents a <i>Roget's Thesaurus</i> Head.
 * A Head is defined by the following attributes:
 * <UL>
 * <li>Head number</li>
 * <li>Head name</li>
 * <li>Class number</li>
 * <li>Section num</li>
 * <li>list of paragraphs</li>
 * <li>number of paragraphs</li>
 * <li>number of semicolon groups</li>
 * <li>number of words and phrases</li>
 * <li>number of cross-references</li>
  * <li>number of see references</li>
 * </UL>
 * The relative postions of the noun, adjective
 * verb, adverb and interjection paragraphs in the
 * array of paragarphs is kept by the 
 * <TT>nStart</TT>, <TT>adjStart</TT>,
 * <TT>vbStart</TT>, <TT>advStart</TT>,
 * and <TT>intStart</TT> attributes.
 *
 * @author Mario Jarmasz
 * @version 1.0 2000 - 2002
 */

public class Head {
   // Attributes
   private int       headNum;
   private String    headName;
   private int       classNum;
   private int       sectionNum;

   private ArrayList nParaList;
   private ArrayList adjParaList;
   private ArrayList vbParaList;
   private ArrayList advParaList;
   private ArrayList intParaList;   

   private int       nStart;
   private int       adjStart;
   private int       vbStart;
   private int       advStart;
   private int       intStart;

   private int       cRefCount;
   private int       seeCount;
   // Should I repeat these for each POS?
   private int       wordCount;
   private int       sgCount;
   private int       paraCount;

   private int       nCount;
   private int       adjCount;
   private int       vbCount;
   private int       advCount;
   private int       intCount;

   private int       nParaCount;
   private int       adjParaCount;
   private int       vbParaCount;
   private int       advParaCount;
   private int       intParaCount;

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
   
   private BufferedReader br;

   // Constructors
   // 1. No params
   // 2. headNum, headName, classNum, sectionNum
   // 3. fileName


  /*************************************
   * Default constructor.
   *************************************/ 
   public Head() {
      headNum    = 0;
      headName   = new String();
      classNum   = 0;
      sectionNum = 0;

      nParaList   = new ArrayList();
      adjParaList = new ArrayList();
      vbParaList  = new ArrayList();
      advParaList = new ArrayList();
      intParaList = new ArrayList();      

      nStart     = -1;
      adjStart   = -1;
      vbStart    = -1;
      advStart   = -1;
      intStart   = -1;

      paraCount  = 0;
      wordCount  = 0;
      sgCount    = 0;
      cRefCount  = 0;
      seeCount   = 0;

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

  /****************************************************************
   * Constructor which sets the Head number and name, as well as
   * the Class and Section number.
   ******************************************************************/ 
   public Head(int num, String name, int clNum, int section) {
      this();
      headNum    = num;
      headName   = name;
      classNum   = clNum;
      sectionNum = section;
   }

  /*****************************************************************************
   * Constructor that builds the <TT>Head</TT> object using the information
   * contained in a file. The default location of the head files for the <i>ELKB</i> is
   * <TT>$HOME/roget_elkb/heads</TT> directory.
   *****************************************************************************/
   public Head(String fname) {
      this();
      loadFromFile(fname);
   }

   // Methods
   // loadFromFile
   // toString 
   // format
   // print
   // Get and set
   // counts

  /***************************************************************
   * Returns the number of this Head.
   **************************************************************/
   public int getHeadNum() {
      return headNum;
   }

  /***************************************************************
   * Sets the number of this Head.
   **************************************************************/
   public void setHeadNum(int num) {
      headNum = num;
   }

  /***************************************************************
   * Returns the name of this Head.
   **************************************************************/
   public String getHeadName() {
      return headName;
   }

  /***************************************************************
   * Sets the name of this Head.
   **************************************************************/
   public void setHeadName(String name) {
      headName = name;
   }

  /***************************************************************
   * Returns the Class number of this Head.
   **************************************************************/
   public int getClassNum() {
      return classNum;
   }
   
  /***************************************************************
   * Sets the Class number of this Head.
   **************************************************************/
   public void setClassNum(int num) {
      classNum = num;
   }

  /***************************************************************
   * Returns the Section number of this Head.
   **************************************************************/
   public int getSectionNum() {
      return sectionNum;
   }

  /***************************************************************
   * Sets the Section number of this Head.
   **************************************************************/
   public void setSectionNum(int num) {
      sectionNum = num;
   }

  /***************************************************************
   * Returns the index of the first noun paragraph in the array 
   * of <TT>Pragraph</TT> objects of this Head.
   **************************************************************/
   public int getNStart() {
      return nStart;   
   }
      
   private void setNStart(int start) {
      nStart = start;
   }

  /***************************************************************
   * Returns the index of the first adjective paragraph in the array 
   * of <TT>Pragraph</TT> objects of this Head.
   **************************************************************/
   public int getAdjStart() {
      return adjStart;    
   }
   
   private void setAdjStart(int start) {
      adjStart = start;
   }

  /***************************************************************
   * Returns the index of the first verb paragraph in the array 
   * of <TT>Pragraph</TT> objects of this Head.
   **************************************************************/
   public int getVbStart() {
      return vbStart;
   }
      
   private void setVbStart(int start) {
      vbStart = start;
   }

  /***************************************************************
   * Returns the index of the first adverb paragraph in the array 
   * of <TT>Pragraph</TT> objects of this Head.
   **************************************************************/
   public int getAdvStart() {
      return adjStart;
   }
      
   private void setAdvStart(int start) {
      advStart = start;
   }

  /***************************************************************
   * Returns the index of the first interjection paragraph in the array 
   * of <TT>Pragraph</TT> objects of this Head.
   **************************************************************/
   public int getIntStart() {
      return intStart;
   }
      
   private void setIntStart(int start) {
      intStart = start;
   }

  /***************************************************************
   * Returns the number of words of this Head.
   **************************************************************/
   public int getWordCount() {
      return wordCount;
   }

  /***************************************************************
   * Returns the number of noun word and phrases of this Head.
   **************************************************************/
   public int getNCount() {
      return nCount;
   }  

  /***************************************************************
   * Returns the number of adjective word and phrases of this Head.
   **************************************************************/
   public int getAdjCount() {
      return adjCount;
   } 

  /***************************************************************
   * Returns the number of verb word and phrases of this Head.
   **************************************************************/
   public int getVbCount() {
      return vbCount;
   }

  /***************************************************************
   * Returns the number of adverb word and phrases of this Head.
   **************************************************************/
   public int getAdvCount() {
      return advCount;
   }

  /***************************************************************
   * Returns the number of interjection word and phrases of this Head.
   **************************************************************/
   public int getIntCount() {
      return intCount;
   }      

  /***************************************************************
   * Returns the number of paragraphs of this Head.
   **************************************************************/
   public int getParaCount() {
      return paraCount;
   }

  /***************************************************************
   * Returns the number of noun paragraphs of this Head.
   **************************************************************/
   public int getNParaCount() {
      return nParaCount;
   }

  /***************************************************************
   * Returns the number of adjective paragraphs of this Head.
   **************************************************************/
   public int getAdjParaCount() {
      return adjParaCount;
   }   

  /***************************************************************
   * Returns the number of verb paragraphs of this Head.
   **************************************************************/
   public int getVbParaCount() {
      return vbParaCount;
   }

  /***************************************************************
   * Returns the number of adverb paragraphs of this Head.
   **************************************************************/
   public int getAdvParaCount() {
      return advParaCount;
   }

  /***************************************************************
   * Returns the number of interjection paragraphs of this Head.
   **************************************************************/
   public int getIntParaCount() {
      return intParaCount;
   }

  /***************************************************************
   * Returns the number of semicolon groups of this Head.
   **************************************************************/
   public int getSGCount() {
      return sgCount;
   }

  /***************************************************************
   * Returns the number of noun semicolon groups of this Head.
   **************************************************************/
   public int getNSGCount() {
      return nSGCount;
   }

  /***************************************************************
   * Returns the number of adjective semicolon groups of this Head.
   **************************************************************/    
   public int getAdjSGCount() {
      return adjSGCount;
   }

  /***************************************************************
   * Returns the number of verb groups of this Head.
   **************************************************************/    
   public int getVbSGCount() {
      return vbSGCount;
   }

  /***************************************************************
   * Returns the number of adverb groups of this Head.
   **************************************************************/    
   public int getAdvSGCount() {
      return advSGCount;
   }

  /***************************************************************
   * Returns the number of interjection semicolon groups of this Head.
   **************************************************************/         
   public int getIntSGCount() {
      return intSGCount;
   }   

  /***************************************************************
   * Returns the number of cross-references of this Head.
   **************************************************************/
   public int getCRefCount() {
      return cRefCount;
   }

  /***************************************************************
   * Returns the number of noun cross-references of this Head.
   **************************************************************/
   public int getNCRefCount() {
      return nCRefCount;
   }

  /***************************************************************
   * Returns the number of adjective cross-references of this Head.
   **************************************************************/    
   public int getAdjCRefCount() {
      return adjCRefCount;
   }

  /***************************************************************
   * Returns the number of verb cross-references of this Head.
   **************************************************************/    
   public int getVbCRefCount() {
      return vbCRefCount;
   }
    
  /***************************************************************
   * Returns the number of adverb cross-references of this Head.
   **************************************************************/
   public int getAdvCRefCount() {
      return advCRefCount;
   }

  /***************************************************************
   * Returns the number of interjection cross-references of this Head.
   **************************************************************/         
   public int getIntCRefCount() {
      return intCRefCount;
   }

  /***************************************************************
   * Returns the number of see references of this Head.
   **************************************************************/
   public int getSeeCount() {
      return seeCount;
   }

  /***************************************************************
   * Returns the number of noun see references of this Head.
   **************************************************************/
   public int getNSeeCount() {
      return nSeeCount;
   }

  /***************************************************************
   * Returns the number of adjective references of this Head.
   **************************************************************/
   public int getAdjSeeCount() {
      return adjSeeCount;
   }

  /***************************************************************
   * Returns the number of verb references of this Head.
   **************************************************************/
   public int getVbSeeCount() {
      return vbSeeCount;
   }

  /***************************************************************
   * Returns the number of adverb references of this Head.
   **************************************************************/
   public int getAdvSeeCount() {
      return advSeeCount;
   }

  /***************************************************************
   * Returns the number of interjection references of this Head.
   **************************************************************/
   public int getIntSeeCount() {
      return intSeeCount;
   }

   private void addPara(Paragraph para, String pos) {
      if ( pos.equals("N.") ) {
         nParaCount++;
         para.setParaNum(nParaCount); 
         
         nParaList.add(para);
         nCount     += para.getWordCount();
         nSGCount   += para.getSGCount();   
         nCRefCount += para.getCRefCount();
         nSeeCount  += para.getSeeCount();
      }
      else if ( pos.equals("ADJ.") ) { 
         adjParaCount++;
         para.setParaNum(adjParaCount);
         adjParaList.add(para);
         adjCount     += para.getWordCount();
         adjSGCount   += para.getSGCount();
         adjCRefCount += para.getCRefCount();
         adjSeeCount  += para.getSeeCount();
      }
      else if ( pos.equals("VB.") )  {
         vbParaCount++;
         para.setParaNum(vbParaCount); 
         vbParaList.add(para);
         vbCount     += para.getWordCount();
         vbSGCount   += para.getSGCount();
         vbCRefCount += para.getCRefCount();
         vbSeeCount  += para.getSeeCount();
      }
      else if ( pos.equals("ADV.") ) {
         advParaCount++;
         para.setParaNum(advParaCount); 
         advParaList.add(para);
         advCount     += para.getWordCount();       
         advSGCount   += para.getSGCount();
         advCRefCount += para.getCRefCount();
         advSeeCount  += para.getSeeCount();
      }
      else if ( pos.equals("INT.") ) {
         intParaCount++;
         para.setParaNum(intParaCount); 
         intParaList.add(para);
         intCount     += para.getWordCount();
         intSGCount   += para.getSGCount();
         intCRefCount += para.getCRefCount();
         intSeeCount  += para.getSeeCount();
      }

      // Adjust the various counts
      // paraCount++;
      wordCount += para.getWordCount();
      sgCount   += para.getSGCount();
      cRefCount += para.getCRefCount();
      seeCount  += para.getSeeCount();
   }          

  /***************************************************************
   * Returns the a <TT>Paragraph</TT> object specified
   * by the paragraph number and part-of-speech.
   **************************************************************/
   public Paragraph getPara(int paraNum, String pos) {
      Paragraph para = new Paragraph();      
      if ( pos.equals("N.") ) {
         para = (Paragraph) nParaList.get(paraNum);         
      } else if ( pos.equals("ADJ.") ) {
         para = (Paragraph) adjParaList.get(paraNum);
      } else if ( pos.equals("VB.") ) {
         para = (Paragraph) vbParaList.get(paraNum);
      } else if ( pos.equals("ADV.") ) {
         para = (Paragraph) advParaList.get(paraNum);
      } else if ( pos.equals("INT.") ) { 
         para = (Paragraph) intParaList.get(paraNum);
      }

      return para;
   }

  /***************************************************************
   * Returns the a <TT>Paragraph</TT> object specified
   * by the paragraph key and part-of-speech.
   **************************************************************/   
   public Paragraph getPara(String paraKey, String pos) {
      Paragraph para    = new Paragraph();
      Paragraph paraObj = new Paragraph();
      
      paraObj.setParaKey(paraKey);
      paraObj.setPOS(pos);
      
      int paraNum;      
       
      // Need some error handling here for the case that the
      // key is not found!!!
      if ( pos.equals("N.") ) {
    	
    	  paraNum = nParaList.indexOf(paraObj);

         if (paraNum >= 0) {         
        	 para    = (Paragraph) nParaList.get(paraNum);
        	 
         }	
      } else if ( pos.equals("ADJ.") ) {
         paraNum = adjParaList.indexOf(paraObj);
         if (paraNum >= 0) {
        	 para    = (Paragraph) adjParaList.get(paraNum);
         }	
      } else if ( pos.equals("VB.") )  {
         paraNum = vbParaList.indexOf(paraObj);
         if (paraNum >= 0) {
        	 para    = (Paragraph) vbParaList.get(paraNum);
         }	
      } else if ( pos.equals("ADV.") ) {
         paraNum = advParaList.indexOf(paraObj);
         if (paraNum >= 0) {
        	 para    = (Paragraph) advParaList.get(paraNum);
         }	
      } else if ( pos.equals("INT.") ) {
    	 paraNum = intParaList.indexOf(paraObj);
    	  if (paraNum >= 0) {
    	 	  para    = (Paragraph) intParaList.get(paraNum);
    	 }	
      }

      return para;
   }

   private void loadFromFile(String fileName) {
      String line = new String();
      String pos  = new String();
      
      try {
         br = new BufferedReader(new FileReader(fileName));
         StringTokenizer st;

         for ( ; ; ) {
            line = br.readLine();
            
            if ( line == null ) {
               br.close();
               break;
            } else if ( line.startsWith("<classNumber>") ) {
               setClassNum( parseClassNum(line) );
            } else if ( line.startsWith("<sectionNumber>") ) {
               setSectionNum ( parseSectionNum(line) );
            } else if ( line.startsWith("<headword>") ) {
               setHeadNum( parseHeadNum(line) );
               setHeadName( parseHeadName(line) );
            } else if ( line.startsWith("<pos>") ) {
               pos = parsePOS(line);
               setPOSStart(pos);
            } else if ( line.startsWith("<paragraph>") ) {
               Paragraph para = parsePara(pos);
               addPara(para, pos);
            }             
         }  
         
      } catch (Exception e) {
    	  e.printStackTrace();
         System.out.println("Error: " + e);
         System.out.println("Head : " + headNum + " " + headName);
         System.out.println("At line: " + line);
      }

   }

   /**
    * Example of a string that this method parses:
    * <ul><TT>
    *         <classNumber>#1#</classNumber>
    * </TT></ul>
    */
   private int parseClassNum(String line) {
      int num = -1;
      String token;
      if ( !(line.startsWith("<classNumber>")) ) {
         num = -1;
      } else {
         StringTokenizer st = new StringTokenizer(line,"#");
         st.nextToken();
         token = st.nextToken();
         token = token.trim();         
         num = Integer.parseInt(token);          
      }
      return num;
   }

   /**
    * Example of a string that this method parses:
    * <ul><TT>
    *         <sectionNumber>#1#</sectionNumber>
    * </ul></TT>
    */
   private int parseSectionNum(String line) {
      int num = -1;
      String token;
      if ( !(line.startsWith("<sectionNumber>")) ) {
         num = -1;
      } else {
         StringTokenizer st = new StringTokenizer(line,"#");
         st.nextToken();
         token = st.nextToken();
         token = token.trim();
         num = Integer.parseInt(token);
      }
      return num;
   }

   /**
    * Example of a string that this method parses:
    * <ul><TT>
    *         <headword>#<b>#[001] #1# Existence #</b>#</headword>
    * </ul></TT>
    */
   private int parseHeadNum(String line) {
      int num = -1;
      String token;
      if ( !(line.startsWith("<headword>")) ) {
         num = -1;
      } else {
         StringTokenizer st = new StringTokenizer(line,"#");
         st.nextToken();
         st.nextToken();
         st.nextToken();
         token = st.nextToken();
         token = token.trim();   
         num = Integer.parseInt(token);
      }
      return num;
   }


   /**
    * Example of a string that this method parses:
    * <ul><TT>
    *         <headword>#<b>#[001] #1# Existence #</b>#</headword>
    * </ul></TT>
    */
   private String parseHeadName(String line) {
      String name = new String();
      if ( !(line.startsWith("<headword>")) ) {
         name = "";
      } else {
         StringTokenizer st = new StringTokenizer(line,"#");
         st.nextToken();
         st.nextToken();
         st.nextToken();
         st.nextToken();
         name = st.nextToken(); 
         name = name.trim();
      } 
      return name;
   }

   /**
    * Example of a string that this method parses:
    * <ul><TT>
    *         <pos>#<size=-1>#<b>#N.#</b>#</size>#</pos>
    * </ul></TT>
    */
   private String parsePOS(String line) {
      String pos = new String();
      if ( !(line.startsWith("<pos>")) ) {
         pos = "";
      } else {
         StringTokenizer st = new StringTokenizer(line,"#");
         st.nextToken();
         st.nextToken();
         st.nextToken();
         pos = st.nextToken();
         pos = pos.trim();
      }
      return pos;
   }   

   /**
    * We assume here that a pos tag is sent to this function when
    * there has been a change to the pos of a paragraph in the Thesaurus
    */
   private void setPOSStart(String pos) {
      if ( pos.equals("N.") )        { nStart = paraCount;   }
      else if ( pos.equals("ADJ.") ) { adjStart = paraCount; }
      else if ( pos.equals("VB.") )  { vbStart = paraCount;  }
      else if ( pos.equals("ADV.") ) { advStart = paraCount; }      
      else if ( pos.equals("INT.") ) { intStart = paraCount; } 
   }

   /**
    * Parses the text for a paragraph and creates the object
    */
   private Paragraph parsePara(String pos) {
      String line = "";
      paraCount++;      
      Paragraph para = new Paragraph(headNum, paraCount, pos);
      try {
         line = br.readLine();
         para.setParaKey( para.parseParaKey(line) );
         while ( !(line.startsWith("</paragraph>")) ) {        	 
            para.addSG(line);
            line = br.readLine();
         }
      } catch (Exception e) {
    	  e.printStackTrace();
         System.out.println("Error:" + e);
         System.out.println("Head : " + headNum + " " + headName);
         System.out.println("At line: " + line);
      }  
                 
      return para;
   }   

  /************************************************************************************
   * Converts to a string representation the <TT>Head</TT> object.
   * The following following format is used -
   * <TT>Head:headNum:headName:classNum:sectionNum:paraCount:sgCount:wordCount</TT>. 
   *************************************************************************************/
   public String toString() {
      StringBuffer sbInfo = new StringBuffer();

      sbInfo.append("Head:");
      sbInfo.append( getHeadNum() ); sbInfo.append(":");
      sbInfo.append( getHeadName() ); sbInfo.append(":");
      sbInfo.append( getClassNum() ); sbInfo.append(":");
      sbInfo.append( getSectionNum() ); sbInfo.append(":");
      sbInfo.append( getParaCount() ); sbInfo.append(":");
      sbInfo.append( getSGCount() ); sbInfo.append(":");    
      sbInfo.append( getWordCount() ); sbInfo.append(":");
      
      return sbInfo.toString();
   } 

   private String format() {
      String info = new String("format invoked");
      return info;
   }
   
  /****************************************************************
   * Prints the contents of this Head to the standard output.
   ***************************************************************/
   public void print() {
      System.out.println("Class: " + classNum);
      System.out.println("Section: " + sectionNum);
      System.out.println("Head: " + headNum + " " + headName);
      printParaList(nParaList, nStart);     
      printParaList(adjParaList, adjStart);
      printParaList(vbParaList,  vbStart);
      printParaList(advParaList, advStart);
      printParaList(intParaList, intStart);
   }   

   private void printParaList(ArrayList paraList, int offset) {
      Iterator iter = paraList.iterator();
      int index;
      while ( iter.hasNext() ) {
         System.out.println();
         Paragraph para = (Paragraph) iter.next();
         index = para.getParaNum() + offset;
         System.out.println(index + ". " + para.getPOS());
         para.print();
      }
   }

  /****************************************************************
   * Prints all the words and phrases of this Head separated on
   * a separate line to the standard output.
   ***************************************************************/
   public void printAllWords() {
      Paragraph para;

      ArrayList allPara = new ArrayList();

      allPara.addAll(nParaList);
      allPara.addAll(adjParaList);
      allPara.addAll(vbParaList);
      allPara.addAll(advParaList);
      allPara.addAll(intParaList);

      Iterator pIter = allPara.iterator();
      while ( pIter.hasNext() ) {
         para = (Paragraph) pIter.next();
         para.printAllWords();
      }
   }       

  /****************************************************************
   * Prints all the semicolon groups of this Head separated on
   * a separate line to the standard output.
   ***************************************************************/
   public void printAllSG() {
      Paragraph para;

      ArrayList allPara = new ArrayList();

      allPara.addAll(nParaList);
      allPara.addAll(adjParaList);
      allPara.addAll(vbParaList);
      allPara.addAll(advParaList);
      allPara.addAll(intParaList);

      Iterator pIter = allPara.iterator();
      while ( pIter.hasNext() ) {
         para = (Paragraph) pIter.next();
         para.printAllSG();
      }
   }  
}
