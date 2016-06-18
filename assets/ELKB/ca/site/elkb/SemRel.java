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


/**
 * Represents a <i>Roget's Thesaurus</i> relation between
 * a word or phrase. This can be a Cross-reference or a 
 * See reference. For example: 
 * <ul>
 *    <li>See <i>drug taking</i></li>
 *    <li>646 <i>perfect</i></li>
 * </ul>
 * Relation types currently used by the <i>ELKB</i> are
 * <TT>cref</TT> and <TT>see</TT>.
 * @author Mario Jarmasz
 * @version 1.0 Oct 2000
 */


public class SemRel extends Reference {
   // Attributes
   private String type;

   // Constructors
   // 1. No params
   // 2. Roget reference (type, headNum, refName)
   // 3. ELKB reference (same as Roget plus pos, paraNum, sgNum)

  /*************************************
   * Default constructor.
   *************************************/
   public SemRel() {
      super();
      type = new String();
   }

  /****************************************************************
   * Constructor which sets the relation type, Head number and
   * Reference name.
   ******************************************************************/
   public SemRel(String t, int headNum, String refName) {
      this();
      type = t;
      setHeadNum(headNum);
      setRefName(refName);
   }

   private SemRel(String t, int headNum, String refName, 
                           String pos, int paraNum, int sgNum) {
      this(t, headNum, refName);
      setPos(pos);
      //setParaNum(paraNum);
      //setSgNum(sgNum);
   }

   // Methods

  /**************************************************************
   * Returns the relation type. 
   **************************************************************/ 
   public String getType() {
      return type;
   }

  /**************************************************************
   * Sets the relation type. 
   **************************************************************/ 
   public void setType(String t) {
      type = t;
   }

  /************************************************************************************
   * Converts to a string representation the <TT>SemRel</TT> object.
   ********************************************************************/
   public String toString() {
      String info = new String();
      info = "SemRel" + "@" + Integer.toHexString(hashCode());
      info += "@" + getType();
      info += "@" + getHeadNum() + "@" + getRefName();
      //info += getPos()  + "@" + getParaNum() + "@" + getSgNum();
      return info;
   }

  /****************************************************************
   * Prints this relation to the standard output.
   ********************************************************************/
   public void print() {
      String info = new String();
      info = getType() + ": " + getHeadNum() + " " + getRefName();
      System.out.println(info);
   }

} // End of SemRel class 
