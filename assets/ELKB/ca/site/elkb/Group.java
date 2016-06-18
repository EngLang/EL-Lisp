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

/**
 * Represents a <i>Roget's Thesaurus</i> Head group. For example:
 * <UL>
 * 79 Generality &nbsp;&nbsp;&nbsp; 80 Speciality
 * </UL>
 * A <TT>Group</TT> can contain 1,2 or 3 <TT>HeadInfo</TT> objects.
 *
 * @author Mario Jarmasz
 * @version 1.0 2000 - 2006
 */

public class Group {

   // Attributes
   private int headCount;
   private int headStart;
   private ArrayList headList;

  /*************************************
   * Default constructor.
   *************************************/
   public Group() {
      headCount = 0;
      headStart = 0;
      headList  = new ArrayList();
   }

  /*******************************************************
   * Constructor that takes an integer to indicate first 
   * Head number of the Group.
   *******************************************************/
   public Group(int start) {
      headCount = 0;
      headStart = start;
      headList  = new ArrayList();
   }

  /****************************************************************
   * Returns the array of <TT>HeadInfo</TT> objects.
   ***************************************************************/
   public ArrayList getHeadList() {
      return headList;
   }

  /****************************************************************
   * Add a <TT>HeadInfo</TT> object to this Group.
   ***************************************************************/
   public void addHead(HeadInfo head) {
      headList.add(head);
      headCount++;
   }
   
  /***************************************************************
   * Returns the number of Heads in this Group.
   **************************************************************/
   public int getHeadCount() {
      return headCount;
   }

  /***************************************************************
   * Sets the number of the first Head in this Group.
   **************************************************************/
   public void setHeadStart(int start) {
      headStart = start;
   }

  /***************************************************************
   * Returns the number of the first Head in this Group.
   **************************************************************/
   public int getHeadStart() {
      return headStart;
   }

   // print - something like Roget's synopsis of categories
   // 79 Generality       80 Speciality
   private void print() {
      System.out.println( toString() );     
   }   

  /************************************************************************************
   * Converts to a string representation the <TT>Group</TT> object.
   *************************************************************************************/
   public String toString() {
      String info = new String();
      if (headCount >= 1) { info += headList.get(0); }
      if (headCount >= 2) { info += "\t\t" + headList.get(1); }
      if (headCount >= 3) { info += "\n\t\t" + headList.get(2); }
      return info; 
   }

   // Require and inList method to check for an inclusion of a head
   // in a given group
}
