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
 *    Driver.java
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 */

/********************************************************************
 * Driver : program that tests the various classes of the ELKB
 * Author : Mario Jarmasz
 * Created: September, 2000 - February, 2001
 * Required files:
 *    + elkbIndex.txt or newIndex.txt
 *    + head1.txt to head990.txt in a ./heads directory
 *    + categories.txt
 *    + comm.txt
 ********************************************************************/

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import ca.site.elkb.Index;
import ca.site.elkb.RogetELKB;

class TestELKB {
   // Constants
   
   // Set Roget Thesaurus Path.
   public static final String USER_HOME = System.getProperty("user.home");
   public static final String ELKB_PATH = System.getProperty( "elkb.path", USER_HOME + "/roget_elkb" );
   
   public static final String INDEX = ELKB_PATH + "/elkbIndex.dat";

   public static final String INDEX_FILE = ELKB_PATH + "/newIndex.txt";
      
   public static final String CATEG = ELKB_PATH + "/rogetMap.rt";
   
   public static final String HEADS = ELKB_PATH + "/heads/head";

  /******************************************************************
   * main(String args[])
   ******************************************************************/
   public static void main(String args[]) {
      System.out.println("#==================#");
      System.out.println("# ELKB Test v1.0 #");
      System.out.println("#==================#\n");

      TestELKB elkbTest = new TestELKB();
      BufferedReader br = new BufferedReader(new
                              InputStreamReader(System.in));

      int i = 0;
      String choice;
      try {
          while (i != 4) {
            elkbTest.printMenu();
            choice = br.readLine();
			choice = choice.trim();
			i = Integer.parseInt(choice);
      
            switch (i) {
              case 1  : elkbTest.createIndex()   ; break;
              case 2  : elkbTest.testELKBClass() ; break;              
              case 3  : elkbTest.testELKBClass2() ; break;
              case 4 : System.out.println("\nGoodbye"); break;
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
   private void printMenu() {
      System.out.println("\n         M E N U\n");
      System.out.println("1.  Create Roget's Index");
      System.out.println("2.  Look up a word or a phrase in ELKB");
      System.out.println("3.  Look up a pair of words or phrases in ELKB");      
      System.out.println("4.  Quit\n");
   }

  /******************************************************************
   * createIndex()
   ******************************************************************/
   private void createIndex() {
	   
	   System.out.println("\nCreate Roget's Index");
	   System.out.println("----------------------\n");
	   System.out.println("Creating the index. Please wait...\n");
	   try {

		   System.out.println(INDEX_FILE);
		   Index elkbIndex = new Index(INDEX_FILE, 125000);
		   
		   System.out.println("# of index items : " 
				   + elkbIndex.getItemCount());
		   System.out.println("# of references  : " 
				   + elkbIndex.getRefCount());
		   System.out.println("# of unique refs : "
				   + elkbIndex.getUniqRefCount());   
		   System.out.println("Items Map size   : " 
				   + elkbIndex.getItemsMapSize());
		   
		   System.out.println("\nSaving Index object. Please wait...");
		   
		   try {
			   ObjectOutputStream out = new ObjectOutputStream(new
					   FileOutputStream(INDEX));
			   out.writeObject(elkbIndex);
			   out.close();
		   } catch(Exception e) {
			   e.printStackTrace();
		   }
	   }	catch(Exception e1) {
		   e1.printStackTrace();
	   }  	
	   
   }

   private void testELKBClass() {
		RogetELKB elkb = new RogetELKB();
		elkb.lookUpWordInIndex();
	}

	private void testELKBClass2() {
		RogetELKB elkb = new RogetELKB();
		elkb.lookUpPairInIndex();

	}
      
}


