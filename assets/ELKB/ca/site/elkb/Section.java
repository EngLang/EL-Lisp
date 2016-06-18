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
 * Represents a <i>Roget's Thesaurus</i> Section.
 * A Section is defined by the following attributes:
 * <ul>
 *     <li>Section number</li>
 *     <li>Section number in string format</li>
 *     <li>Section name</li>
 *     <li>number of the first Head</li>
 *     <li>number of the last Head</li>
 *     <li>array of Heads</li>
 * </ul>
 * A Section can contain <TT>Head</TT> or <TT>HeadInfo</TT> objects, depending
 * on the use.
 * 
 * @author Mario Jarmasz
 * @version 1.0 2000 - 2002
 */ 

public class Section {
   // Attributes
   private int       sectionNum;
   // Could have method to convert 1 to one instead
   private String    strSectionNum;
   private String    sectionName;
   private int       headStart;
   private int       headEnd;
   private ArrayList headInfoList;
   private ArrayList headList;
   // private ArrayList subSectionList

   // Constructors
   // 1. No params
   // 2. number, name
   // 3. number, name, headStart, headEnd
   // I must add new constructors due to ** strSectionNum **

  /*************************************
   * Default constructor.
   *************************************/
   public Section() {
      sectionNum    = 0;
      strSectionNum = new String();
      sectionName   = new String();
      headStart     = 0;
      headEnd       = 0;
      headInfoList  = new ArrayList();
      headList      = new ArrayList();
   }

  /****************************************************************
   * Constructor which sets the Section number and name.
   ******************************************************************/
   public Section(int number, String name) {
      this();
      sectionNum    = number;
      sectionName   = name;
   }

  /****************************************************************
   * Constructor which sets the Section number and name, as well
   * as the number of the first and last Head.
   ******************************************************************/
   public Section(int number, String name, int start, int end) {
      this(number, name);
      headStart = start;
      headEnd   = end;
   } 

  /****************************************************************
   * Constructor which sets the Section number, name,
   * and Section number in string format and Class name,
   * while parsing the strings for the
   * Section number and name. Examples of the strings to be parsed are:
   * <BR><CODE>
   * ^sectionNumber>#Section one #^/sectionNumber>
   * </CODE><BR><CODE>
   * ^sectionTitle>#^size=-1>#^b>#Existence #^/b>#^/size>#^/sectionTitle>
   * </CODE>
   ********************************************************************/
   public Section(int number, String strNum, String strName) {
      this();
      sectionNum = number;
      parseSectNum(strNum);
      parseSectName(strName);
   }

   // <sectionNumber>#Section one #</sectionNumber>
   private void parseSectNum(String strLine) {
      StringTokenizer st = new StringTokenizer(strLine, "#");
      st.nextToken();
      strSectionNum = st.nextToken();
   }

   // <sectionTitle>#<size=-1>#<b>#Existence #</b>#</size>#</sectionTitle>
   private void parseSectName(String strLine) {
      StringTokenizer st = new StringTokenizer(strLine, "#");
      st.nextToken();
      st.nextToken();
      st.nextToken();
      sectionName = st.nextToken();
   }      

   // Methods
   // Get and set
   // toString
   // print

  /***************************************************************
   * Returns the number of this Section.
   **************************************************************/
   public int getSectionNum() {
      return sectionNum;
   }

  /***************************************************************
   * Sets the number of this Section.
   **************************************************************/
   public void setSectionNum(int num) {
      sectionNum = num;
   }

  /***************************************************************
   * Returns the number of this Section in string format.
   **************************************************************/
   public String getStrSectionNum() {
      return strSectionNum;
   }

  /***************************************************************
   * Sets the number of this Section in string format.
   **************************************************************/
   public void setStrSectionNum(String snum) {
      strSectionNum = snum;
   }

  /***************************************************************
   * Returns the name of this Section.
   **************************************************************/
   public String getSectionName() {
      return sectionName;
   }

  /***************************************************************
   * Sets the number of this Section in string format.
   **************************************************************/
   public void setSectionName(String name) {
      sectionName = name;
   }

  /***************************************************************
   * Returns the number of the first Head of this Section.
   **************************************************************/
   public int getHeadStart() {
      return headStart;
   }

  /***************************************************************
   * Sets the number of the first Head of this Section.
   **************************************************************/      
   public void setHeadStart(int start) {
      headStart = start;
   }

  /***************************************************************
   * Returns the number of the last Head of this Section.
   **************************************************************/
   public int getHeadEnd() {
      return headEnd;
   }

  /***************************************************************
   * Sets the number of the last Head of this Section.
   **************************************************************/      
   public void setHeadEnd(int end) {
      headEnd = end;
   }

  /******************************************************************
   * Adds a <TT>HeadInfo</TT> object to this Section.
   *****************************************************************/
   public void addHeadInfo(HeadInfo head) {
      headInfoList.add(head);
   }

  /*******************************************************************
   * Returns the array of <TT>HeadInfo</TT> objects of this Section.
   *******************************************************************/
   public ArrayList getHeadInfoList() {
      return headInfoList;
   }

  /***************************************************************
   * Returns the number of Heads in this Section.
   **************************************************************/
   public int headCount() {
      return headInfoList.size();
   }

  /************************************************************************************
   * Converts to a string representation the <TT>Section</TT> object.
   *************************************************************************************/
   public String toString() {
      String info = new String();
      info  = super.toString();
      info += "@" + getSectionNum() + "@" + getSectionName();
      info += "@" + getHeadStart()  + "@" + getHeadEnd();
      return info;
   } 

  /****************************************************************
   * Prints the content of this Section to the standard output.
   ***************************************************************/
   public void print() {
      String info = new String();
      info += getSectionNum() + " " + getSectionName();
      // Maybe I should add code that adds a variable # of tabs ?
      info += "\t" + getHeadStart() + "-" + getHeadEnd();
      System.out.println(info);
   }

  /****************************************************************
   * Prints the information regarding the Heads contained
   * in this Section to the standard output.
   ***************************************************************/  
   public void printHeadInfo() {
      if ( headInfoList.isEmpty() ) {
         System.out.println("This section does not contain any Heads");
      } else {
         System.out.println("SECTION: " + sectionNum + " " + sectionName);
         Iterator iter = headInfoList.iterator();
         while ( iter.hasNext() ) {
            HeadInfo head = (HeadInfo) iter.next();
            head.print();
         }
      }
   }
   
} // End of Section class
