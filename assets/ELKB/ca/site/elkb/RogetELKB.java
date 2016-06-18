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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Main class of the <i>Roget's Thesaurus Electronic
 * Lexical KnowledgeBase</i>. 
 * It is made up of three major components:
 * <UL>
 *    <LI>the Index</LI>
 *    <LI>the Tabular Synopsis of Categories</LI>
 *    <LI>the Text</LI>
 * </UL>
 *
 * Required files:
 * <UL>
 *   <LI><TT>elkbIndex.dat</TT>: The Index in binary file format.</LI>
 *   <LI><TT>rogetMap.rt</TT>: The <i>Tabular Synopsis of Categories</i>.</LI>
 *   <LI><TT>./heads/head*</TT>: The 999 heads</LI>
 *   <LI><TT>AmBr.lst</TT>: The American to British spelling word list.</LI>
 *   <LI><TT>noun.exc, adj.exc, verb.exc, adv.exc</TT>: exception lists used
 *                          for the morphological transformations.</LI>
 * </UL>
 * These files are found in the <TT>$HOME/roget_elkb</TT> directory.
 * @version 1.0 2001 - 2003
 */



public class RogetELKB {

  /*************************************************
   * Location of user's <TT>Home</TT> directory.
   *************************************************/
   public static final String USER_HOME = System.getProperty("user.home");
  /************************************************************************
   * Location of the <i>ELKB</i> data directory.
   ************************************************************************/
   public static final String ELKB_PATH = System.getProperty( "elkb.path", USER_HOME + "/roget_elkb" );
  /************************************************************************
   * Location of the <i>ELKB</i> Index.
   ***********************************************************************/
   public static final String INDEX = ELKB_PATH + "/elkbIndex.dat";
  /************************************************************************
   * Location of the <i>ELKB Tabular Synopsis of Categories</i>.
   ***********************************************************************/
   public static final String CATEG = ELKB_PATH + "/rogetMap.rt";   
  /************************************************************************
   * Location of the Heads.
   ***********************************************************************/
   public static final String HEADS = ELKB_PATH + "/heads/head";

  /******************************************************************
   * The <i>ELKB</i> Index.
   ******************************************************************/ 
   public Index index;
  /******************************************************************
   * The <i>ELKB Tabular Synopisis of Categories</i>.
   ******************************************************************/
   public Category category;
  /******************************************************************
   * The <i>ELKB</i> Text.
   ******************************************************************/
   public RogetText text;

   private int iHead = 0;
   private String sKey = new String();
   private String sPOS = new String();

  /*************************************
   * Default constructor.
   *************************************/
   public RogetELKB() {
      // 1. load the index
      System.out.println("Loading the Index...");
      try {
         ObjectInputStream in = new
            ObjectInputStream(new FileInputStream(INDEX));
         index = (Index)in.readObject();      
      } catch(Exception e) {
    	  e.printStackTrace();
	  System.exit(1);
      }


      // 2. load the Text
      System.out.println("Loading the Text...");
      text = new RogetText(HEADS); 

      // 3. load the categories
      // categories can't be loaded yet.
      System.out.println("Loading the Categories...");  
      category = new Category(CATEG);
   }
   
   /*********************************************************************
    * Allows the <i>ELKB</i> to be used via the command line.
    ********************************************************************/
   public static void main(String args[]) {
     
      RogetELKB elkb = new RogetELKB();
      
      BufferedReader br = new BufferedReader(new
                              InputStreamReader(System.in));

      int i = 0;
      String choice;
      try {
          while (i != 3) {
            printMenu();
            choice = br.readLine();
			choice = choice.trim();
			i = Integer.parseInt(choice);
      
            switch (i) {
              case 1  : elkb.lookUpWordInIndex()       ; break;
              case 2  : elkb.lookUpPairInIndex()       ; break;
              case 3  : System.out.println("\nGoodbye"); break;
              default : System.out.println("\n#" + i + 
                                       " is not a valid option");
            }
          }
      } catch (IOException ioe) {
                System.out.println("IO error:" + ioe);
      }
   }


  /******************************************************************
   * printMenu()
   ******************************************************************/
   private static void printMenu() {
      System.out.println("\n         ROGET'S THESAURUS ELKB\n");
      System.out.println("1.  Look up a word or phrase");
      System.out.println("2.  Look up a pair of words or phrases");
      System.out.println("3.  Quit\n");
   }

  /******************************************************************
   * pause()
   ******************************************************************/
   private String pause() {
      String sMsg = new String();
      System.out.println("\nPress [Enter] to continue or type 'quit' to terminate\n");
      BufferedReader br = new BufferedReader(new
                                 InputStreamReader(System.in));
      try { 
         sMsg = br.readLine();
      } catch (IOException ioe) {
                System.out.println("IO error:" + ioe);
      }
      
      return sMsg;
   }   

  /******************************************************************
   * lookUpPairInIndex - looks up to words or phrases and calculates
   *                     all the possible distances between the
   *                     references.
   ******************************************************************/   
   public void lookUpPairInIndex() {
      BufferedReader br = new BufferedReader(new
                                           InputStreamReader(System.in));
      String word1 = new String();
      String word2 = new String();
      String more  = new String();

      try {
         do {
            System.out.print
                    ("\nEnter a word or phrase: ");
            word1 = br.readLine();

            System.out.print
                    ("\nEnter a second word or phrase: ");
            word2 = br.readLine();

            // Obtain all paths
            TreeSet sortedPath = getAllPaths(word1, word2);            

            if (sortedPath.size() != 0) { 
               System.out.println ("\nPath between " + word1 + " and " + word2
                                    + " (" + sortedPath.size() +" paths in total)" );
               System.out.println();

               // Display sorted paths
               Iterator iter = sortedPath.iterator();
               while ( iter.hasNext() ) {
                  System.out.println(iter.next());
                  System.out.println();
                  String sMsg = pause();
                  if ( sMsg.equals("quit") ) {
                     break;
                  }
            }
            } else {
               // If no paths are found display error message
               System.out.println();
               System.out.println(notInIndex(word1, word2));
            }

            System.out.print("\nCalculate more paths [y/n] ? ");
            more = br.readLine();
         } while ( !(more.equals("n")) );

      } catch (IOException ioe) {
                System.out.println("IO error:" + ioe);
      }
   }

  /******************************************************************
   * Returns all the paths between two words or phrases. 
   * The paths are sorted from the smallest to the biggest distance.
   * If set of size 0 represents that an error occurred when
   * determining the paths.
   ******************************************************************/
   public TreeSet getAllPaths(String strWord1, String strWord2) {
      // obtain the references

      ArrayList refList1 = index.getStrRefList(strWord1);
      ArrayList refList2 = index.getStrRefList(strWord2);  

      TreeSet sortedPath = new TreeSet();      

      // make sure we obtain some result!
      if ( (refList1.size() != 0 || refList2.size() != 0) == true ) {

         int iCount1 = refList1.size();
         int iCount2 = refList2.size();
    
         for (int i=0; i<iCount1; i++) {
            for (int j=0; j<iCount2; j++) {
               // System.out.println("Calculating the path");
               Path rtPath = path(strWord1, (String)refList1.get(i),   
                             strWord2, (String)refList2.get(j) );
               sortedPath.add(rtPath);           
            }
         }
      }
      return sortedPath;
   }

   /******************************************************************
    * Returns all the paths between two words or phrases of a given 
    * part-of-speech.
    * The part-of-speech can be any of N., VB., ADJ., ADV.
    * The paths are sorted from the smallest to the biggest distance.
    *****************************************************************/
    public TreeSet getAllPaths(String strWord1, String strWord2,
                               String POS) {
      // obtain the references
    
      ArrayList refList1 = index.getStrRefList(strWord1);
      ArrayList refList2 = index.getStrRefList(strWord2);
    
      TreeSet sortedPath = new TreeSet();
    
      // make sure we obtain some result!
      if ( (refList1.size() != 0 || refList2.size() != 0) == true ) {
   
         int iCount1 = refList1.size();
         int iCount2 = refList2.size();
    
         for (int i=0; i<iCount1; i++) {
            for (int j=0; j<iCount2; j++) {
               // System.out.println("Calculating the path");
               Reference ref1 = new Reference( (String)refList1.get(i) );
               Reference ref2 = new Reference( (String)refList2.get(j) );

               if ( ref1.getPos().equals(POS) && 
                    ref2.getPos().equals(POS) ) {

                  Path rtPath = path(strWord1, (String)refList1.get(i),
                             strWord2, (String)refList2.get(j) );
                  sortedPath.add(rtPath);
               }
            }
         }
      }
      return sortedPath;
   }

   /*******************************************************************
    * Determines the thesaural relation that exists between a specific
    * sense of a words or phrases and another word or phrase.
    * There are two kinds of thesaural relations:
    * <UL>
    *    <LI>T0: reiteration of the same string.</LI>
    *    <LI>T1: both words or phrases belong to the same head, paragraph
    *            and part-of-speech.</LI>
    * This method can be used to build lexical chains.
    * The parameters head number, part-of-speech and reference name
    * as parameter are used to identify a specific sense of a word
    * or phrase.
    *******************************************************************/
   public String t1Relation(String strWord1, int iHeadNum1, String sRefName1,
                            String sPos1, String strWord2) {
   
      String sRelation = null;
      
      int iHeadNum2;    
      String sPos2;
      String sRefName2;
 
      // obtain the references
      ArrayList refList2 = index.getStrRefList(strWord2);
    
      // T0 relation
      if ( strWord1.equals(strWord2) ) sRelation = "T0";    

      // check for thesaural relations, make sure we have words to look at!
      else if ( refList2.size() != 0)  
      {       
         int iCount2 = refList2.size();
    
         findRelation:            
         for (int j=0; j<iCount2; j++) {
            Reference ref2 
               = new Reference((String)refList2.get(j));    
            iHeadNum2 = ref2.getHeadNum();
	         sPos2     = ref2.getPos();
	         sRefName2 = ref2.getRefName();
	         
	         // System.out.println(strWord1 + " ** " + sRefName1 + " ** " + sRefName2);
	            
            // T1 relation
            if ( (iHeadNum1 == iHeadNum2) && 
                 (sRefName1.equals(sRefName2)) &&
                 (sPos1.equals(sPos2)) ) {
               sRelation = "T1";
               //sRelation = "T1" 
               //   + "." + String.valueOf(iHeadNum1);
               break findRelation;
            } 
         }            
      }
      
      return sRelation;
   }   




   /*******************************************************************
    * Determines the thesaural relation that exists between two
    * words or phrases.
    * There are two kinds of thesaural relations:
    * <UL>
    *    <LI>T0: reiteration of the same string.</LI>
    *    <LI>T1: both words or phrases belong to the same head, paragraph
    *            and part-of-speech.</LI>
    * This method can be used to build lexical chains.
    *******************************************************************/
   public String t1Relation(String strWord1, String strWord2) {
      String sRelation = null;
      int iHeadNum1;
      int iHeadNum2;
      
      String sPos1;
      String sPos2;
      
      String sKeyWord1;
      Head   head1;
      Paragraph para1;
 
      // obtain the references
      ArrayList refList1 = index.getStrRefList(strWord1);
      ArrayList refList2 = index.getStrRefList(strWord2);
    
      // T0 relation
      if ( strWord1.equals(strWord2) ) sRelation = "T0";    

      // check for thesaural relations, make sure we have words to look at!
      else if ( (refList1.size() == 0 || refList2.size() == 0) == false ) 
      {       
         int iCount1 = refList1.size();
         int iCount2 = refList2.size();
    
         findRelation:
         for (int i=0; i<iCount1; i++) {
            Reference ref1 
               = new Reference((String)refList1.get(i));
            // Restrict only to nouns for the time being
            // but lexical chains should be built using
            // all POS
            iHeadNum1 = ref1.getHeadNum();
            sPos1 = ref1.getPos();
            sKeyWord1 = ref1.getRefName();
                       
            for (int j=0; j<iCount2; j++) {
               Reference ref2 
                 	= new Reference((String)refList2.get(j));    
               iHeadNum2 = ref2.getHeadNum();
	            sPos2 = ref2.getPos();
	                          
               // T1 relation
               if ((iHeadNum1 == iHeadNum2)) {
                 	sRelation = "T1" 
                     + "." + String.valueOf(iHeadNum1);
                 	break findRelation;
               }           
               
            }
            
         }
      }
      return sRelation;
   }   


   /**
    * Returns a message stating which words or phrases, at most two
    * are not in index.
    * A bit weird but useful for calculatinf the distance
    **/
   private String notInIndex(String strWord1, String strWord2) {
      // obtain the references
      ArrayList refList1 = index.getStrRefList(strWord1);
      ArrayList refList2 = index.getStrRefList(strWord2);
            
      String notFound = new String();
      
      if (refList1.size() == 0 && refList2.size() == 0) {
         notFound = strWord1 + " and " + strWord2 + " are not in the Index.";
      } else if (refList1.size() == 0) {
         notFound = strWord1 + " is not in the Index.";
      } else if (refList2.size() == 0) {
         notFound = strWord2 + " is not in the Index.";
      }
      
      return notFound;
   }

   /**************************************************************
    * Calculates the path between two senses of words or phrases.
    * The references are used to identify the senses.
    * They must be supplied in the following format:
    * <UL>
    *    <LI>cad 938 N.</LI>
    *    <LI>cat 365 N.</LI>
    * </UL>
    **************************************************************/
   public Path path(String strWord1, String strRef1,
                     String strWord2,  String strRef2) {      
	   
      ArrayList rtPath = new ArrayList();
      ArrayList headList  = category.getHeadList();
      ArrayList classList = category.getClassList();
      Reference ref1 = new Reference(strRef1);
      Reference ref2 = new Reference(strRef2);
      boolean bSameSG = false;

      // one of the methods could fail... should index try-catch block
      try {

      // Obtain all of the path info for the 1st word
      int        iHead1       = ref1.getHeadNum();
      String     sKeyWord1    = ref1.getRefName();
      String     sPos1        = ref1.getPos();
      
      HeadInfo headInfo1 = new HeadInfo();
      try {
         headInfo1 = (HeadInfo)headList.get(iHead1 - 1);
      } catch (IndexOutOfBoundsException e) {
    	  e.printStackTrace();
         //System.out.println("RogetELKB [headList]: " + e);
      }

      String     sHeadName1   = iHead1 + ". " + headInfo1.getHeadName();
      int        iClass1      = headInfo1.getClassNum();
      int        iSection1    = headInfo1.getSectNum();
      String     sSubSection1 = headInfo1.getSubSectName();
      ArrayList  headGroup1   = headInfo1.getHeadGroup();
  
      RogetClass rogClass1 = new RogetClass();
      try {
         rogClass1 = (RogetClass)classList.get(iClass1 - 1);
      } catch (IndexOutOfBoundsException e) {
    	  e.printStackTrace();
    	  //System.out.println("RogetELKB [classList]: " + e);
      }

      String     sRogClass1   = rogClass1.getStrClassNum() + ": " 
                                + rogClass1.getClassName();
      ArrayList  sectList1    = rogClass1.getSectionList();
      
      Section section1 = new Section();
      try {
    	  section1 = (Section)sectList1.get(iSection1 - 1);
      } catch (IndexOutOfBoundsException e) {
    	  e.printStackTrace();
    	  //System.out.println("RogetELKB [sectList]: " + e);
      }
      
      String     sSection1    = section1.getStrSectionNum() + ": "
                                + section1.getSectionName();

      // Obtain all of the path info for the 2nd word
      int        iHead2       = ref2.getHeadNum();
      String     sKeyWord2    = ref2.getRefName();
      String     sPos2        = ref2.getPos();

      HeadInfo headInfo2 = new HeadInfo();
      try {
         headInfo2 = (HeadInfo)headList.get(iHead2 - 1);
      } catch (IndexOutOfBoundsException e) {
         e.printStackTrace();
         //System.out.println("RogetELKB [headList]: " + e);
      }  

      String     sHeadName2   = iHead2 + ". " + headInfo2.getHeadName();
      int        iClass2      = headInfo2.getClassNum();
      int        iSection2    = headInfo2.getSectNum();  
      String     sSubSection2 = headInfo2.getSubSectName();
      ArrayList  headGroup2   = headInfo2.getHeadGroup();

      RogetClass rogClass2 = new RogetClass();
      try {
         rogClass2 = (RogetClass)classList.get(iClass2 - 1);
      } catch (IndexOutOfBoundsException e) {
    	  e.printStackTrace();
    	  //System.out.println("RogetELKB [classList]: " + e);
      }

      String     sRogClass2   = rogClass2.getStrClassNum() + ": "
                                + rogClass2.getClassName();
      ArrayList  sectList2    = rogClass2.getSectionList();

      Section section2 = new Section();
      try {
         section2 = (Section)sectList2.get(iSection2 - 1);
      } catch (IndexOutOfBoundsException e) {
    	  e.printStackTrace();
    	  //System.out.println("RogetELKB [sectList]: " + e);
      }

      String     sSection2    = section2.getStrSectionNum() + ": "
                                + section2.getSectionName();

      // Add the references and the full path to the top of ontology
      // and the two words to the path
      rtPath.add(strRef1);			// 0
      rtPath.add(sPos1);			// 1
      rtPath.add(strRef2);			// 2
      rtPath.add(sPos2);			// 3
      rtPath.add(headInfo1.toString());		// 4
      rtPath.add(headInfo2.toString());		// 5
      rtPath.add(strWord1);			// 6
      rtPath.add(strWord2);			// 7
      // this defines the path info. All other information starts
      // at position 8


      //System.out.println("Path between " + strRef1 + " and " + strRef2);
      //System.out.println(headInfo1);
      //System.out.println(headInfo2);

      // Same Paragrapgh
      // length = 2
      if (  (iHead1 == iHead2) 
            && ( sKeyWord1.equals(sKeyWord2) )
            && ( sPos1.equals(sPos2) ) ) {
         // 1st lets check if in the same SG

         Head      head1 = text.getHead(iHead1);
         Paragraph para1 = head1.getPara(sKeyWord1, sPos1);
         SG        SG1   = para1.getSG(strWord1);

         Head      head2 = text.getHead(iHead2);
         Paragraph para2 = head2.getPara(sKeyWord2, sPos2);
         SG        SG2   = para2.getSG(strWord2);
 
         // If the two semicolon groups are equal
         // length = 0
         if ( SG1.format().equals(SG2.format()) ) {
            rtPath.add( SG1.format() );         // root
            bSameSG = true;
         } else { 
            // if not, simply in the same paragraph
            rtPath.add(sKeyWord1); 		// root
         }

      // Same POS
      // length = 4      
      } else if ( (iHead1 == iHead2)
                   && ( sKeyWord1.equals(sKeyWord2) == false)
                   && ( sPos1.equals(sPos2) ) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1); 			// root
         rtPath.add(sKeyWord2);

      // Same Head
      // length = 6
      } else if ( (iHead1 == iHead2)
                   && ( sKeyWord1.equals(sKeyWord2) == false)
                   && ( sPos1.equals(sPos2) == false) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1);
         rtPath.add(sHeadName1); 		// root
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);

      // Same HeadGroup
      // length = 8
      } else if ( (headGroup1.equals(headGroup2) )
                   && (iHead1 != iHead2) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1);
         rtPath.add(sHeadName1);
         rtPath.add(headGroup1.toString()); 	// root
         rtPath.add(sHeadName2);    
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);

      // Same SubSection
      // length = 10
      } else if ( (iClass1 == iClass2)
                   && (iSection1 == iSection2)
                   && (sSubSection1.equals(sSubSection2))
                   && (sSubSection1.equals("?") == false) // we don't want empty SubSection
                   && ( headGroup1.equals(headGroup2) == false) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1);
         rtPath.add(sHeadName1);
         rtPath.add(headGroup1.toString());
         rtPath.add(sSubSection1); 		// root
         rtPath.add(headGroup2.toString());
         rtPath.add(sHeadName2);
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);         

      // Same Section
      // length = 12
      } else if ( (iClass1 == iClass2)
                   && (iSection1 == iSection2)
                   && (sSubSection1.equals(sSubSection2) == false)
                   || (sSubSection1.equals("?") &&  sSubSection2.equals("?")) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1);
         rtPath.add(sHeadName1);
         rtPath.add(headGroup1.toString());
         rtPath.add(sSubSection1);
         rtPath.add(sSection1); 		// root
         rtPath.add(sSubSection2);
         rtPath.add(headGroup2.toString());
         rtPath.add(sHeadName2);
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);

      // Same Class
      // length = 14
      } else if ( (iClass1 == iClass2)
                   && (iSection1 != iSection2) ) {
         rtPath.add(sKeyWord1); 
         rtPath.add(sPos1);
         rtPath.add(sHeadName1);
         rtPath.add(headGroup1.toString());
         rtPath.add(sSubSection1);
         rtPath.add(sSection1);
         rtPath.add(sRogClass1); 		// root
         rtPath.add(sSection2);
         rtPath.add(sSubSection2);
         rtPath.add(headGroup2.toString());
         rtPath.add(sHeadName2);
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);

      // Different classes - infinite distance
      // length = 16
      } else if ( (iClass1 != iClass2) ) {
         rtPath.add(sKeyWord1);
         rtPath.add(sPos1);
         rtPath.add(sHeadName1);
         rtPath.add(headGroup1.toString());
         rtPath.add(sSubSection1);
         rtPath.add(sSection1);
         rtPath.add(sRogClass1);
         rtPath.add("T"); 			// root
         rtPath.add(sRogClass2);
         rtPath.add(sSection2);
         rtPath.add(sSubSection2);
         rtPath.add(headGroup2.toString());
         rtPath.add(sHeadName2);
         rtPath.add(sPos2);
         rtPath.add(sKeyWord2);         

      } else {
         rtPath.add("NOT CALCULATED");
      } 
       
      if (bSameSG == false) {
         // add the 1st word after all the header info
         rtPath.add(8, strWord1);
         rtPath.add(strWord2);
      }
      } catch (Exception e) {
         e.printStackTrace();
      }
  
      return new Path(rtPath); // create a Path object
   }

  /******************************************************************
   * lookUpWordInIndex - looks up a word or phrase in the Index
   *                     and returns all possible references
   ******************************************************************/   
   public void lookUpWordInIndex() {
      BufferedReader br = new BufferedReader(new
                                           InputStreamReader(System.in));
      String word = new String();
      String sRefIndex;
      String sRef;  
      ArrayList refList;

      try {
     
      do {
           System.out.print
                 ("\nEnter a word or phrase: ");

            word = br.readLine();     
 

            // Find all references for a given word

            System.out.println();
            index.printEntry(word, 1);
            refList = index.getStrRefList(word);
            if (refList.size() > 0) {            

            // Look up reference
            System.out.print
                 ("Enter the number of the reference to be looked up: ");
            sRefIndex = br.readLine(); 
            Integer index   = new Integer(sRefIndex);
            sRef = (String)refList.get(index.intValue() - 1);

            ParseRef(sRef);
            
            Head elkbHead = text.getHead(iHead);
            
            Paragraph elkbPara = elkbHead.getPara(sKey, sPOS);
            SG        elkbSG = elkbPara.getSG(word);

            System.out.println();
            System.out.println
("========================== Head " + iHead +" =========================");
            System.out.println(sPOS);
            elkbPara.print();
            System.out.println();
            System.out.println("SG: " + elkbSG.format());

            } // end if

            System.out.print("\nLook up another word or phrase [y/n] ? ");
            word = br.readLine();

      } 
      while ( !(word.equals("n")) );

         } catch (IOException ioe) {
                System.out.println("IO error:" + ioe);
         }
   }

   private void Stats() {
      System.out.println("Roget's Thesaurus ELKB Statistics");
   }

   // This method should not be used at all..
   // I should also avoid using global variables...
   private void ParseRef(String sRef)
   {
      Reference ref = new Reference(sRef);
      
      sKey  = ref.getRefName();
      iHead = ref.getHeadNum();
      sPOS  = ref.getPos();
      
   }
}
