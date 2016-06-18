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

/*************************************************************************
 * Represents the topmost element in <i>Roget's Thesaurus
 * Tabular Synopsis of Categories</i>. It is represented by its
 * number, name, subclass name if it is a subclass of an original
 * Roget Class, and range of Sections that it contains. For example,
 * Class <i>4. Intellect: the exercise of the mind (Formation of
 * ideas)</i> is represented as:
 * <ul>
 *     <li><b>Class number</b>: 4</li>
 *     <li><b>Class number in string format</b>: Class four</li>
 *     <li><b>Class Name</b>: Intellect: the exercise of the mind</li>
 *     <li><b>First section</b>: 16</li>
 *     <li><b>Last section</b>: 22</li>
 *</ul>
 *
 * @author Mario Jarmasz
 * @version 1.0 Oct 2000
 **************************************************************************/


 
public class RogetClass {
   // Attributes
   private int       classNum;
   private String    strClassNum;
   private String    className;
   // subClasses do not exist any more...
   private String    subClassName;
   // sectionStart and End? Not convinced that they should be used
   private int       sectionStart;
   private int       sectionEnd;
   private ArrayList sectionList;

   // Constructors
   // 1. No params
   // 2. RogetClass num, name
   // 3. RogetClass num, strNum, name
   // 4. RogetClass num, strNum, name, subClassName
   // 5. RogetClass num, name, sectionStart, sectionEnd
   // 6. RogetClass num, strNum, name, sectionStart, SectionEnd
   // 7. RogetClass num, strNum, name, subClassName, Section S&E

  /*************************************
   * Default constructor.
   *************************************/
   public RogetClass() {
      classNum     = 0;
      strClassNum  = new String();
      className    = new String();
      subClassName = new String();
      sectionStart = 0;
      sectionEnd   = 0;
      sectionList  = new ArrayList();
   }

  /****************************************************************
   * Constructor which sets the Class number and name.
   ******************************************************************/
   public RogetClass(int num, String name) {
      this();
      classNum  = num;
      className = name;
   }
 
  /****************************************************************
   * Constructor which sets the Class number, Class number in
   * string format, Class and Sub-class name. 
   ******************************************************************/
   public RogetClass(int num, String snum, String name, String subClass) {
      this(num, snum, name);
      subClassName = subClass;
   }

  /****************************************************************
   * Constructor which sets the Class number and name, as well
   * as the first and last Section number.
   ******************************************************************/
   public RogetClass(int num, String name, int start, int end) {
      this(num, name);
      sectionStart = start;
      sectionEnd   = end;
   }

  /****************************************************************
   * Constructor which sets the Class number, Class number
   * in string format, Class name, as well
   * as the first and last Section number.
   ******************************************************************/
   public RogetClass(int num, String snum, String name, int start
                                                      , int end) {
      this(num, name, start, end);
      strClassNum = snum;
   }

  /****************************************************************
   * Constructor which sets the Class number, Class number in
   * string format, Class name, Sub-class name as well
   * as the first and last Section number.
   ******************************************************************/
   public RogetClass(int num, String snum, String name, String subClass
                                         , int start, int end) {
      this(num, snum, name, start, end);
      subClassName = subClass;
   }  

  /****************************************************************
   * Constructor which sets the Class number, Class number in string
   * format and Class name, while parsing the strings for the
   * Class number and name. Examples of the strings to be parsed are:
   * <BR><CODE>
   * ^classNumber>#^i>#Class one #^/i>#^/classNumber>
   * </CODE><BR><CODE> 
   * ^classTitle>#^i>#Abstract Relations #^/i>#^/classTitle>
   * </CODE>
   ********************************************************************/
   public RogetClass(int num, String strClassNum, String strClassName) {
      this();
      classNum = num;
      parseClassNum(strClassNum);
      parseClassName(strClassName);
   }

   // <classNumber>#<i>#Class one #</i>#</classNumber>
   private void parseClassNum(String strLine) {
      StringTokenizer st = new StringTokenizer(strLine, "#");
      st.nextToken();
      st.nextToken();
      strClassNum = st.nextToken();
   }

   // <classTitle>#<i>#Abstract Relations #</i>#</classTitle
   private void parseClassName(String strLine) {
      StringTokenizer st = new StringTokenizer(strLine, "#");
      st.nextToken();
      st.nextToken();
      className = st.nextToken();
   }    

   // Methods
   // Get and set
   // toString
   // print
   // sectionCount
   // headCount

  /***************************************************************
   * Returns the number of this RogetClass.
   **************************************************************/
   public int getClassNum() {
      return classNum;
   }

  /***************************************************************
   * Sets the number of this RogetClass.
   **************************************************************/
   public void setClassNum(int num) {
      classNum = num;
   }   
   
  /***************************************************************
   * Returns the number of this RogetClass in string format.
   **************************************************************/
   public String getStrClassNum() {
      return strClassNum;
   }

  /***************************************************************
   * Sets the number of this RogetClass in string format.
   **************************************************************/
   public void setStrClassNum(String snum) {
      strClassNum = snum;
   }

  /***************************************************************
   * Returns the name of this RogetClass.
   **************************************************************/
   public String getClassName() {
      return className;
   }

  /***************************************************************
   * Sets the name of this RogetClass.
   **************************************************************/    
   public void setClassName(String name) {
      className = name;
   }

  /***************************************************************
   * Returns the Sub-class name of this RogetClass.
   **************************************************************/
   public String getSubClassName() {
      return subClassName;
   }

  /***************************************************************
   * Sets the Sub-class name of this RogetClass.
   **************************************************************/    
   public void setSubClassName(String subClass) {
      subClassName = subClass;
   }

  /***************************************************************
   * Returns the number of the first section of this RogetClass.
   **************************************************************/
   public int getSectionStart() {
      return sectionStart;
   }

  /***************************************************************
   * Sets the number of the first section of this RogetClass.
   **************************************************************/      
   public void setSectionStart(int start) {
      sectionStart = start;
   }

  /***************************************************************
   * Returns the number of the last section of this RogetClass.
   **************************************************************/
   public int getSectionEnd() {
      return sectionEnd;
   }

  /***************************************************************
   * Sets the number of the last section of this RogetClass.
   **************************************************************/   
   public void setSectionEnd(int end) {
      sectionEnd = end;
   }

  /*************************************************************************
   * Adds a Section to this RogetClass.
   *************************************************************************/    
   public void addSection(Section section) {
      sectionList.add(section);
   }

  /*************************************************************************
   * Returns the array of <TT>Section</TT> objects in this RogetClass.
   *************************************************************************/
   public ArrayList getSectionList() {
      return sectionList;
   }

  /*************************************************************************
   * Returns the number of Sections of this RogetClass.
   *************************************************************************/
   public int sectionCount() {
      return sectionList.size();
   }

  /*************************************************************************
   * Returns the number of Heads of this RogetClass.
   *************************************************************************/
   public int headCount() {
      int count = 0;
      if ( sectionList.isEmpty() ) {
         count = 0;
      } else {
         Iterator iter = sectionList.iterator();
         while ( iter.hasNext() ) {
            Section section = (Section) iter.next();
            count += section.headCount();
         }
      }
      return count;
   }         

  /************************************************************************************
   * Converts to a string representation the <TT>RogetClass</TT> object.
   *************************************************************************************/
   public String toString() {
      String info = new String();
      info  = super.toString();
      info += "@" + getClassNum()     + "@" + getStrClassNum();
      info += "@" + getClassName()    + "@" + getSubClassName();
      info += "@" + getSectionStart() + "@" + getSectionEnd(); 
      return info;
   } 

  /****************************************************************
   * Prints the contents of this RogetClass to the standard output.
   ***************************************************************/
   public void print() {
      String strNum = new String();
      
      if ( strClassNum.equals("") ) {
         strNum = "Class " + classNum + ": ";
      } else {
         strNum = strClassNum + ": ";
      }
      System.out.println(strNum + className);
      
      if ( !(subClassName.equals("")) ) {
         System.out.println(subClassName);
      }
      
      if ( sectionList.isEmpty() ) {
         System.out.println("There are no Sections in this Class");
      } else {
         Iterator iter = sectionList.iterator();
         while ( iter.hasNext() ) {
            Section section = (Section) iter.next();
            section.print();
         }
      }
   }

}
