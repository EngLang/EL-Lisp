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
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Allows to obtain a variant of an English spelling. 
 * A British spelling variant can be obtained form an American
 * spelling and vice-versa.
 *
 * <p>
 * The default American and British word list is <TT>AmBr.lst</TT>
 * contained in the <TT>$HOME/roget_elkb</TT> directory.
 * It is loaded by the default constructor.
 * </p>
 *
 * @author Mario Jarmasz
 * @version 1.0 Mar 2002
 */

public class Variant implements Serializable {

  /*************************************************
   * Location of user's <TT>Home</TT> directory.
   *************************************************/
   public static final String USER_HOME = System.getProperty("user.home");
  /************************************************************************
   * Location of the <i>ELKB</i> data directory.
   ************************************************************************/
   public static final String ELKB_PATH = System.getProperty( "elkb.path", USER_HOME + "/roget_elkb" );   
  /************************************************************************
   * Location of the default American and British spelling word list.
   ************************************************************************/   
   // Name of file that contains American to British spelling
   public static final String AMBR_FILE = ELKB_PATH + "/AmBr.lst";

   // only contains one hastable?
   private HashMap amBrHash;
   private HashMap brAmHash;

   // Constructors
   // 1. Default
   // 2. Name of the text file to load

  /*************************************
   * Default constructor.
   *************************************/
   public Variant() {
      amBrHash = new HashMap();
      brAmHash = new HashMap();
      loadFromFile(AMBR_FILE);
   }

  /*****************************************************************************
   * Constructor that builds the <TT>Variant</TT> object using the information
   * contained in the specified file. This file must contain only the American
   * and British spellings in the following format:
   * <BR>
   * <CODE>American spelling:British spellling</CODE>.
   * </BR>
   * For example:
   * <BR>
   * <CODE>airplane:aeroplane</CODE>
   * <BR>
   *****************************************************************************/
   public Variant(String filename) {
      amBrHash = new HashMap();
      brAmHash = new HashMap();
      loadFromFile(filename);
   }

   private void loadFromFile(String filename) {
      try {
         BufferedReader br = new BufferedReader(new FileReader(filename));
         StringTokenizer st;

         for ( ; ; ) {
            String line     = new String();
            String american = new String();
            String british  = new String();

            line = br.readLine();
               
            if (line == null) {
               br.close();
               break;
            } else {
               st = new StringTokenizer(line, ":");
               american = st.nextToken();
               british  = st.nextToken();
               amBrHash.put( american, british );
               brAmHash.put( british, american );
            }
         }
       } catch (Exception e) {
    	   e.printStackTrace();         
      }
   }

  /*************************************************************
   * Returns the British spelling of a word, or <TT>null</TT>
   * if the word cannot be found.
   ************************************************************/
   public String amToBr(String american) {
      return (String) amBrHash.get(american);
   }

  /*************************************************************
   * Returns the American spelling of a word, or <TT>null</TT>
   * if the word cannot be found.
   ************************************************************/
   public String brToAm(String british) {
      return (String) brAmHash.get(british);
   }

               
}
