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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the <i>Roget's Thesaurus Tabular Synopsis of Categories</i>.
 * The topmost level of this ontology 
 * divides the <i>Thesaurus</i> into eight Classes:
 * <OL> 
 * <LI><i>Abstract Relations</i></LI>
 * <LI><i>Space</i></LI>
 * <LI><i>Matter</i></LI>
 * <LI><i>Intellect: the exercise of the mind (Formation of ideas)</i></LI>
 * <LI><i>Intellect: the exercise of the mind (Communication of ideas)</i></LI>
 * <LI><i>Volition: the exercise of the will (Individual volition)</i></LI>
 * <LI><i>Volition: the exercise of the will (Social volition)</i></LI>
 * <LI><i>Emotion, religion and morality</i></LI>
 * </OL>
 * <br>
 * <p>
 * Classes are further divided into Sections, Sub-sections, Head groups, and 
 * Heads.
 * </p>
 * @author Mario Jarmasz
 * @version 1.0 2000 - 2006
 */


public class Category {
   // Attributes
   private int classCount;
   private int sectionCount;
   private int subSectionCount;
   private int headGroupCount;
   private int headCount;
   private ArrayList classList;
   private BufferedReader br;
   private String line;
   private ArrayList headList;

   // Constructors
   // 1. No params
   // 2. Filename

  /*************************************
   * Default constructor.
   *************************************/ 
   public Category() {
      classCount      = 0;
      sectionCount    = 0;
      headCount       = 0;
      subSectionCount = 0;
      headGroupCount  = 0;
      classList       = new ArrayList();
      headList        = new ArrayList();
   }

  /*****************************************************************************
   * Constructor that builds the <TT>Category</TT> object using the information
   * contained in a file. The default file for the <i>ELKB</i> is
   * <TT>rogetMap.rt</TT> contained in the <TT>$HOME/roget_elkb</TT> directory.
   *****************************************************************************/
   public Category(String filename) {
      this();
      loadFromFile(filename);
   }

   // Get methods only

  /***************************************************************
   * Returns the number of <i>Roget's</i> Classes in this ontology.
   **************************************************************/
   public int getClassCount() {
      return classCount;
   }

  /***************************************************************
   * Returns the number of Sections in this ontology.
   **************************************************************/
   public int getSectionCount() {
      return sectionCount;
   }

  /***************************************************************
   * Returns the number of Sub-sections in this ontology.
   **************************************************************/
   public int getSubSectionCount() {
      return subSectionCount;
   }
   
  /***************************************************************
   * Returns the number of Head groups in this ontology.
   **************************************************************/
   public int getHeadGroupCount() {
      return headGroupCount;
   }
   
  /***************************************************************
   * Returns the number of Heads in this ontology.
   **************************************************************/
   public int getHeadCount() {
      return headCount;
   }

   // Methods for manipulating the classList

   private void addClass(RogetClass rogClass) {
      classCount++;
      rogClass.setClassNum(classCount);
      classList.add(rogClass);
   }

   private void deleteClass(RogetClass rogClass) {
      // Maybe this method should be boolean?
      // Does nothing for now
   }   
   
  /****************************************************************
   * Returns the <i>Roget's</i> Class at the specified position
   * in the array of Classes. 
   ***************************************************************/
   public RogetClass getRogetClass(int index) {
      RogetClass rogClass;
      index--;
      if ( ( index >= 0 ) && ( index < classCount) ) {
         rogClass = (RogetClass) classList.get(index);
      } else {
         rogClass = null;
      }
      return rogClass;
   }

  /****************************************************************
   * Prints the <i>Roget's</i> Class at the specified position
   * in the array of Classes to the standard output. 
   ***************************************************************/
   
   // It would be nice to have the same method with a string
   // I will have to override the equals method

   public void printRogetClass(int index) {
      RogetClass rogClass = getRogetClass(index);
      if (rogClass == null) {
         System.out.println(index + " is not a valid Class number");
      } else {
         rogClass.print();
      }
   }
   
   private void loadFromFile(String filename) {
   // Loads the entire classification system
      int iSection = 0;
      line = new String();
      RogetClass rogClass;
      String subSectInfo = new String();
      String sGroupInfo  = new String();
      String sHeadInfo   = new String();
      HeadInfo rogetHead;
      RogetClass rtClass = new RogetClass();
      Section rtSection  = new Section();

      try {
         br = new BufferedReader(new FileReader(filename));
         line = br.readLine();
         while ( !(line == null) ) {


            if ( line.startsWith("<classNumber>") ) {
               String strClassNum  = line;
               String strClassName = br.readLine(); 
               classCount++;
               iSection = 0;
               rtClass = new RogetClass(classCount, strClassNum, strClassName);
    
            
	    } else if ( line.startsWith("<sectionNumber>") ) {
               String strSectNum  = line;
               String strSectName = br.readLine();
               iSection++;
               subSectInfo = "";
               rtSection = new Section(iSection, strSectNum, strSectName);
       

            } else if ( line.startsWith("<subSectionTitle>") ) {
               subSectInfo = new String(line);
               subSectionCount++;
               // rtSection.addSubSection(subSectInfo);

            } else if ( line.startsWith("<headGroup") ) {
               sGroupInfo = new String(line);
               headGroupCount++;

            } else if ( line.startsWith("<headword") ) {
               sHeadInfo = new String(line);
               rogetHead = new HeadInfo(sHeadInfo, classCount,
                  iSection, subSectInfo, sGroupInfo);

               headList.add(rogetHead);

            } else if ( line.startsWith("</section") ) {
               rtClass.addSection(rtSection);

            } else if ( line.startsWith("</class") ) {
               classList.add(rtClass);
            }

            line = br.readLine();

         }
         br.close();
      } catch (IOException ioe) {
         System.out.println("IO error:" + ioe);
      }
   }

  /****************************************************************
   * Returns the array of <TT>RogetClass</TT> objects.
   ***************************************************************/
   public ArrayList getClassList() {
      return classList;
   }

  /****************************************************************
   * Returns the array of <TT>HeadInfo</TT> objects.
   ***************************************************************/
   public ArrayList getHeadList() {
      return headList;
   }

  /****************************************************************
   * Prints the array of <TT>HeadInfo</TT> objects
   * to the standard output. 
   ***************************************************************/
   public void printHeadInfo() {
      Iterator iter = headList.iterator();
      while (iter.hasNext())
         System.out.println( (HeadInfo)iter.next() );
   }


  /************************************************************************************
   * Converts to a string representation the <TT>Category</TT> object.
   * The following following format is used -
   * <TT>Category:classCount:sectionCount:subSectionCount:headGroupCount:headCount</TT>. 
   *************************************************************************************/
   public String toString() {
      StringBuffer sbInfo = new StringBuffer();

      sbInfo.append("Category:");
      sbInfo.append( getClassCount() ); sbInfo.append(":");
      sbInfo.append( getSectionCount() ); sbInfo.append(":");
      sbInfo.append( getSubSectionCount() ); sbInfo.append(":");
      sbInfo.append( getHeadGroupCount() ); sbInfo.append(":");
      sbInfo.append( getHeadCount() );

      return sbInfo.toString();
   }

}

// End of the Category class
