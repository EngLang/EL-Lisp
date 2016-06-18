package applications;
import java.util.ArrayList;
import java.util.Collection;

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
 */

/*
 *    ComparableArray.java
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 *    and
 *    Olena Medelyan
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */

/*****************************************************************************
 * ComparableArray: an ArrayList than can be compared according to its size
 *
 * Author : Mario Jarmasz
 * Created: October, 2003
 *****************************************************************************/

class ComparableArray extends ArrayList implements Comparable {

   public ComparableArray () {
      super();
   }

   public ComparableArray (Collection c) {
      super(c);
   }
      
   /*******************************************************************
    * Compares two ArrayList.
    * They are equal is they contain the same elements and are of the
    * same size
    *******************************************************************/
   public int compareTo(Object other) {
      int result;
      ComparableArray otherArray = (ComparableArray)other;
      result = size() - otherArray .size();

      // if they're the same size, they may not be equal!
      if (result == 0) {
         if (equals(otherArray) == true) {
            result = 0;
         } else {
            // same size, different elements, the first will
            // be bigger
            result = 1;
         }
      } 
      return result;
   }
   
}
