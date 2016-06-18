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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Represents the computer index of the words
 * and phrases of <i>Roget's Thesaurus</i>. According to Kirkpatrick (1998) "The
 * index consists of a list of items, each of which is followed by one or
 * more references to the text. These references consist of a Head
 * number, a <i>keyword</i> in italics, and a part of speech label
 * (n. for nouns, adj. for adjectives, vb. for verbs, adv. for adverbs,
 * and int. for interjections). The <i>keyword</i> is given to identify
 * the paragraph which contains the word you have looked up; it also gives
 * and indication of the ideas contained in that paragraph, so it can be 
 * used as a clue where a word has several meanings and therefire several
 * references." An example of an Index Entry is:
 * <ul><b>stork</b>
 *     <ul><i>obstetrics</i> 167 n.<br>
 *         <i>bird</i> 365 n.
 *     </ul>
 * </ul>
 * In this example <b>stork</b> is an Index Item and 
 * <i>obstetrics</i> 167 n. is a Reference. This <TT>Index</TT> object
 * consists of a hashtable of Index Entries, hashed on the String value
 * of the Index Item. For every key (Index Item) the value is a list of 
 * Reference objects. The hashtable is implemented using a HashMap.
 *
 * @serial itemCount;
 * @serial refCount;
 * @serial itemsMap;
 * @serial refList;
 *
 * @author Mario Jarmasz
 * @version 1.0 Oct 2000
 */

public class Index implements Serializable {
   // Attributes
   private int       itemCount;
   private int       refCount;
   private HashMap   itemsMap;
   private ArrayList refList;

   private Variant rtVar   = new Variant();
   private Morphy  rtMorph = new Morphy();      

   // Add toString method
   
   // Constructors:
   // 1. No params
   // 2. Filename
   // 3. Filename, num of elements   

  /*************************************
   * Default constructor.
   *************************************/ 
   public Index() {
      itemCount = 0;
      refCount  = 0;
      itemsMap  = new HashMap();
      refList   = new ArrayList();
   }

  /*****************************************************************************
   * Constructor that builds the <TT>Index</TT> object using the information
   * contained in a file. The default file for the <i>ELKB</i> is
   * <TT>elkbIndex.dat</TT> contained in the <TT>$HOME/roget_elkb</TT> directory.
   *****************************************************************************/
   public Index(String filename) {
	   this();
	   loadFromFile(filename);
   }

  /*****************************************************************************
   * Constructor that builds the <TT>Index</TT> object using the information
   * contained in a file and sets the initial size of the index hashtable.
   * The default file for the <i>ELKB</i> is
   * <TT>elkbIndex.dat</TT> contained in the <TT>$HOME/roget_elkb</TT> directory.
   *****************************************************************************/
   public Index(String fileName, int size) {
	  itemCount  = 0;
      refCount   = 0;
      itemsMap = new HashMap(size);
      refList  = new ArrayList();
      
      // Should maybe also give option of specifying size of list...
      loadFromFile(fileName);
	  
	   	
   }

   // Add an Entry, which is some kind of Item and Reference pair
   // I should do some checking here...   
   // When you add an Entry it must be labelled original or added

   /*******************************************************************
    * addEntry
    * Associates an index entry with its references
    * The references are stored as a long string of pointers separated
    * by colons, ex: 1234:7632:8732:
    *
    * If a phrase contains exactly two words, then it both of the words
    * of the phrase are indexed
    * ex: running track, running, track
    *******************************************************************/
   private void addEntry(String item, String refs) {
      // Will have to do somethig about the original and added tags
      // Maybe convince Szpak of not doing it after all...
      // What's taking up the memory?

      addToEntries(item, refs);

      /** deal with two word phrases
       *  This is an interesting idea, yet it does not work
       *  It simply creates too many index entries
       **/
       StringTokenizer st = new StringTokenizer(item, " ");
       if ( st.countTokens() == 2) {
           String entry1 = (String) st.nextToken();
           String entry2 = (String) st.nextToken();
       
           addToEntries(entry1, refs);
           addToEntries(entry2, refs);
       }
    }

  /***********************************************************************
   * addToEntries
   * very similar to addEntry except that only deals with single words
   * and it is possible that the entry may already exist in the itemsMap
   **********************************************************************/   
   private void addToEntries(String entry, String refs) {   
      if ( itemsMap.containsKey(entry) == false) {
         itemsMap.put(entry, refs);
         itemCount++;
      // else the entry exists, a little more tricky
      } else {
    	
        String entryRefs = (String) itemsMap.get(entry);
        entryRefs = combineReferences(entryRefs, refs);
        itemsMap.put(entry, entryRefs);

      }
   }

  /********************************************************************
   * combineReferences
   * combines two list of entries represented as Strings separated by
   * colons into a single and unique list
   ********************************************************************/
   private String combineReferences(String list1, String list2) {
      String strEntryList = new String();

      TreeSet entrySet = new TreeSet();
      StringTokenizer st1 = new StringTokenizer(list1, ":");
      StringTokenizer st2 = new StringTokenizer(list2, ":");

      while ( st1.hasMoreTokens() ) {
         entrySet.add( st1.nextToken() );
      }

      while ( st2.hasMoreTokens() ) {
         entrySet.add( st2.nextToken() );
      }

      Iterator iter = entrySet.iterator();
      while ( iter.hasNext() ) {
         strEntryList += iter.next() + ":";
      }

      return strEntryList;
   }  

   private void deleteEntry(String item) {
   // I want to remove an entry
   // I should also adjust the count
   }

  /***************************************************************
   * Returns the number of entries in this index.
   **************************************************************/
   public int getItemCount() {
      return itemCount;
   }

  /***************************************************************
   * Returns the number of references in this index.
   **************************************************************/
   public int getRefCount() {
      return refCount;
   }

  /***************************************************************
   * Returns the number of unique references in this index.
   **************************************************************/
   public int getUniqRefCount() {
      return refList.size();
   }

  /***************************************************************
   * Returns the number of items contained in the hash map of this index.
   **************************************************************/
   public int getItemsMapSize() {
      return itemsMap.size();
   }

  /***************************************************************
   * Returns <TT>true</TT> if the specified entry is contained
   * in this index.
   **************************************************************/
   public boolean containsEntry(String key) {
      return itemsMap.containsKey(key);
   }


  /****************************************************************
   * Prints the index entry along with its references to
   * the standard output. 
   ***************************************************************/
   public void printEntry(String key) {
      printEntry(key, -1);
   }

  /****************************************************************
   * Prints the index entry along with its numbered references to
   * the standard output. The number of the first reference must
   * be specified. The number is printed in front of each reference.
   ***************************************************************/
   public void printEntry(String key, int itemNo) {
      TreeSet ptrList = getEntry(key);      
      if ( ptrList.isEmpty() ) {
         System.out.println("** " + key + " is not in the Index **");
      } else {
         System.out.println("** " + key + " **");
         // 1. Iterate through the set
         Iterator iter = ptrList.iterator();
         while (iter.hasNext()) {
            // 2. Retrieve reference & print
            String strRef = getStrRef((String)iter.next());
            if (itemNo >= 0) {
               System.out.println(itemNo + ". " + strRef );
               itemNo++;
            } else {
               System.out.println(strRef);
            }
         }
      }
   }

   /*****************************************************************
    * Returns the list of references for a given word or phrase
    * in the index.
    ******************************************************************/
   public ArrayList getEntryList(String key) {
      return getEntryList(key, -1);
   }

   /*****************************************************************
    * Returns the list of references for a given word or phrase
    * in the index preceded by a number to identify the reference.
    ******************************************************************/
   public ArrayList getEntryList(String key, int itemNo) {
      ArrayList entryList = new ArrayList();
      TreeSet ptrList = getEntry(key);

      // If we actually found something
      if ( ptrList.isEmpty() == false) {
         // 1. Iterate through set
         Iterator iter = ptrList.iterator();
         while (iter.hasNext()) {
            // 2. Retrieve reference & print
            String strRef = getStrRef((String)iter.next());
            if (itemNo >= 0) {
               entryList.add(itemNo + ". " + strRef);
               itemNo++;
            } else {
               entryList.add(strRef);
            }
         }
      }
      return entryList;
   }



   /********************************************************************
    * loadFromFile
    ********************************************************************/
   private void loadFromFile(String fileName) {
      String line = new String();
   
      try {
         BufferedReader br = new BufferedReader(new FileReader(fileName));
         StringTokenizer st, st2;

         for ( ; ; ) {

            line = br.readLine();

            if (line == null) {
               br.close();
               break;
            }

            else if (line.startsWith("<indexItem>")) {
               // Pointers to elements in the array of references
               String strPtr = new String();
               st = new StringTokenizer(line, ":");
               st.nextToken();
               st.nextToken();

               String entry = new String(st.nextToken());
               entry = entry.trim();
               
               // Read references & store in unique vector of references
               while (! (line.startsWith("</reference>"))
                          || (line == null)) {

                  // Change from String to StringBuffer
                  // What's the average length of a reference??
            	   StringBuffer sRefBuf = new StringBuffer();

                  if (line.startsWith("<i>")) { 
                     
		     st2 = new StringTokenizer(line, ":");
		     st2.nextToken();
 
	             // keyword
                     sRefBuf.append(st2.nextToken());
                     sRefBuf.append(" ");
                     st2.nextToken();
                     // head number
                     sRefBuf.append(st2.nextToken());
                     sRefBuf.append(" ");
                     // POS
                     sRefBuf.append(st2.nextToken());
                     strPtr = addReference(strPtr,sRefBuf.toString());
                  }
	          if (line.startsWith("<see>")) {
		     st2 = new StringTokenizer(line, ":");
		     st2.nextToken();
		     st2.nextToken();
		     sRefBuf.append("<see>:");
                     sRefBuf.append(st2.nextToken());
                     strPtr = addReference(strPtr,sRefBuf.toString());
		  }
                  line = br.readLine(); 
               }               
               addEntry(entry, strPtr);
            }
         }
      } catch (Exception e) {
    	  e.printStackTrace();
    	  //System.out.println(line);
         //System.out.println("Error:" + e);
      }
   }

   /*****************************************************************
    * addReference
    *****************************************************************/
   private String addReference(String strPtr, String sRef) {
      // if object is not found
	   
      if ( !refList.contains(sRef) ) {
         refList.add(sRef);
      }              
      strPtr += refList.indexOf(sRef) + ":";
      refCount++;
	   
      return strPtr; 
   }
   
   /*****************************************************************
    * Returns all references for a given word or phrase
    * in the index.
    * This is where the American to British spelling changes should
    * be done, as well as the other tricks to access phrases.
    * There are a few things to note:
    *    1. Multiple spellings have been included in Roget's, for 
    *       example tire and tyre. The meanings can be different
    *       for each spelling...
    *    2. Often the space between phrases has been removed
    * How come this method does not return null???
    *
    * Returns all of the cross references for a given word or phrase
    *
    * New modifications as of June 27, 2002
    * This method should return the first of the following words that 
    * is found:
    *    + the supplied word
    *    + the biritsh spelling of the word
    *    + the base form of the word (Morphy)
    * A future version should return all entries found in the index
    *
    * November 4, 2002: I believe all entries are being returned
    *****************************************************************/
   public TreeSet getEntry(String key) {
      // 1. build list
      // 2. loop to find untill an entry is found

      TreeSet wordList = new TreeSet();
      TreeSet ptrSet = new TreeSet();
      String entry   = new String();

      wordList.add(key);

      try {
         wordList.add(rtVar.amToBr(key));
      } catch (NullPointerException npe) {
         // continue execution
    	  
      } catch (Exception e) {
    	  	e.printStackTrace();
         //System.out.println("Error: " + e);
      }

      try {
         wordList.addAll(rtMorph.getBaseForm(key));
      } catch (NullPointerException npe) {
    	  
         // continue execution
      } catch (Exception e) {
    	  e.printStackTrace();
    	  // System.out.println("Error: " + e);
      }

      Iterator iter = wordList.iterator();

      while ( iter.hasNext() ) {
         entry = (String)iter.next();
         if ( itemsMap.containsKey( entry ) ) {
            String ptrList = (String)itemsMap.get(entry);
             
            StringTokenizer st = new StringTokenizer(ptrList, ":\n");
            while (st.hasMoreTokens()) {
               try {
                  ptrSet.add(st.nextToken());
               } catch (NullPointerException npe) {
                  // continue execution
               } catch (Exception e) {
            	   e.printStackTrace();
                  //System.out.println("Error: " + e);
               }  // end try - catch
            }
         }
      } 

      return ptrSet;
   }

   /******************************************************************
    * Returns a string containing the part-of-speech of the references
    * for a given index entry. 
    * For example, getRefPOS("respect") will return "N.VB.ADV."
    ******************************************************************/
   public String getRefPOS(String key) {
      StringBuffer sbRefPOS = new StringBuffer();
      String       strRef;
   
      boolean bN   = false;
      boolean bADJ = false;
      boolean bVB  = false;
      boolean bADV = false;
      boolean bINT = false;
      
      // get all references
      TreeSet ptrSet = getEntry(key);
      Iterator iter = ptrSet.iterator();

      // identify POS
      while ( iter.hasNext() ) {    
         strRef = getStrRef( (String)iter.next() );
         if ( strRef.endsWith("N.") )   bN   = true;
         if ( strRef.endsWith("ADJ.") ) bADJ = true;
         if ( strRef.endsWith("VB.") )  bVB  = true;
         if ( strRef.endsWith("ADV.") ) bADV = true;
         if ( strRef.endsWith("INT.") ) bINT = true; 
      }  
   
      if (bN  ) sbRefPOS.append("N.");
      if (bADJ) sbRefPOS.append("ADJ.");
      if (bVB ) sbRefPOS.append("VB.");
      if (bADV) sbRefPOS.append("ADV.");
      if (bINT) sbRefPOS.append("INT.");
      if (!bN && !bADJ && !bVB && !bADV && !bINT) sbRefPOS.append("NULL.");
        
      return sbRefPOS.toString();   
   }




   /*****************************************************************
    * Returns a list of references in string format instead of pointers.
    * For example box 194 N. instead of 778
    *****************************************************************/
   public ArrayList getStrRefList(String key) {

      ArrayList entryList = new ArrayList();
      TreeSet ptrSet = getEntry(key);
   
      Iterator iter = ptrSet.iterator();
      while ( iter.hasNext() ) {
         entryList.add(getStrRef( (String)iter.next() ));
      }
      
      return entryList;
   }

   /****************************************************************
    * Returns a reference in String format as printed in 
    * <i> Roget's Thesaurus</i>.
    * For example: way 624 N.
    ****************************************************************/
   public String getStrRef(String strIndex) {
      Integer index   = new Integer(strIndex);
      return new String((String)refList.get(index.intValue()));
   }

   /****************************************************************
    * Returns an array of <TT>Reference</TT> objects.
    ****************************************************************/
   public ArrayList getRefObjList(String key) {

      ArrayList refObjList = new ArrayList();
      TreeSet ptrList = getEntry(key);

      Iterator iter = ptrList.iterator(); 
      while ( iter.hasNext() ) {
         Reference ref = new Reference(getStrRef( (String)iter.next() ));
         ref.setIndexEntry(key);
         refObjList.add(ref);
      }

      return refObjList;
   }

   /*****************************************************************
    * Returns a set of head numbers in which a word or phrase can be
    * found. Heads are stored as Strings.
    *****************************************************************/
   public TreeSet getHeadNumbers(String key) {
      TreeSet refHeadNo = new TreeSet();
      TreeSet ptrList = getEntry(key);

      Iterator iter = ptrList.iterator();
      while ( iter.hasNext() ) {
         Reference ref = new Reference(getStrRef( (String)iter.next() ));
         refHeadNo.add( String.valueOf(ref.getHeadNum()) );
      }

      return refHeadNo;
    }	
    
} /************************ end of index class ************************/	
