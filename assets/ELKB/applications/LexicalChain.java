package applications;

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
 *    LexicalChain.java
 *    Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
 *    School of Information Technology and Engineering (SITE)
 *    University of Ottawa, 800 King Edward St.
 *    Ottawa, Ontario, Canada, K1N 6N5
 *    and
 *    Olena Medelyan
 *    Department of Computer Science, The University of Waikato
 *    Privat Bag 3105, Hamilton, New Zealand
 */


/**
 * Lexical Chain class: allows to build lexical chains in a given text
 *
 * Requires: +input text
 *           +debug option
 *
 * Outputs:  If debug option is selected, the output are the actual lexical chains
 *           and information on their generation, while the text is being processed
 *           If debug option is not selected, the output is the list of keywords,
 *           which are the most frequent terms in each chain.
 *           
 * Notes:
 *   + The Active Significant Word List is an array of word & sentence
 *     number pairs. Initially consists of all words in a text with 
 *     the stop words removed.
 *
 * @author Mario Jarmasz
 * @author Olena Medelyan
 * @version 2.0 Apr 2006
 **/


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import ca.site.elkb.Reference;
import ca.site.elkb.RogetELKB;

public class LexicalChain {


   // Constants - correct this using classpath some day...
   public static final String STOPWORDS = "./stops.txt";

   // USED indicated that a word has been used in a chain
   public static final Integer USED = new Integer(77);
   public static final Integer END  = new Integer(99);

   // Attributes
   private HashSet   <String>stopWords;
   private ArrayList ASWL;      // Active Significant Word List
   private ArrayList <String>candiWL;   // Must be removed when code will be cleaned up. DO NOT USE!
   private RogetELKB elkb;
   private HashSet   <String>wsdHeadSet;

  /** Name of input file */
  String m_fileName = null;

  /** Name of score Function */
    String m_scoreFunction = "Elkb";

  /** Debugging mode? */
  boolean m_debug = false;

   // Constructors
   // 1. No params?!
   // 2. stop word list, file to process

   public LexicalChain() {
       stopWords = new HashSet<String>();
       ASWL      = new ArrayList();
       candiWL   = new ArrayList<String>();
       wsdHeadSet= new HashSet<String>();

      loadStopWordList(STOPWORDS);
      elkb = new RogetELKB();
   }

//    public LexicalChain(String fileName) {
//       this();
//       loadFile(fileName);
//       System.out.println("Step1: Select a set of candidate words");
//       printCandidateWords();
//    }
      


  /**
   * Get the value of debug.
   *
   * @return Value of debug.
   */
  public boolean getDebug() {
    
    return m_debug;
  }
  
  /**
   * Set the value of debug.
   *
   * @param newdebug Value to assign to debug.
   */
  public void setDebug(boolean newdebug) {
    
    m_debug = newdebug;
  }

  /**
   * Get the value of fileName.
   *
   * @return Value of fileName.
   */
  public String getFileName() {
    
    return m_fileName;
  }


  /**
   * Set the value of fileName.
   *
   * @param newfileName Value to assign to fileName.
   */
  public void setFileName(String newfileName) {
    
    m_fileName = newfileName;
  }

  /**
   * Get the value of scoreFunction.
   *
   * @return Value of scoreFunction.
   */
  public String getScoreFunction() {
    
    return m_scoreFunction;
  }


  /**
   * Set the value of fileName.
   *
   * @param newScoreFunction Value to assign to scoreFunction.
   */
  public void setScoreFunction(String newScoreFunction) {
    
    m_scoreFunction = newScoreFunction;
  }



  /**
   * Gets the current option settings.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  public String [] getOptions() {

    String [] options = new String [5];
    int current = 0;

    options[current++] = "-f"; 
    options[current++] = "" + (getFileName());
    options[current++] = "-s"; 
    options[current++] = "" + (getScoreFunction());
    if (getDebug()) {
      options[current++] = "-d";
    }

    while (current < options.length) {
      options[current++] = "";
    }
    return options;
  }



  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options
   */
  public Enumeration listOptions() {

    Vector <Option>newVector = new Vector<Option>(1);

    newVector.addElement(new Option("\tSpecifies name of the input file.","f", 1, "-f <file name>"));
    newVector.addElement(new Option("\tSpecifies name of the scoring function.","s", 1, "-s <scoring function>"));
    newVector.addElement(new Option("\tTurns debugging mode on.","d", 0, "-d"));

    return newVector.elements();
  }



  /**
   * Parses a given list of options controlling the behaviour of this object.
   * Valid options are:<p>
   *
   * -f "input file name"<br>
   * Specifies name of the input file.<p>
   *
   * -s "input scoring function"<br>
   * Specifies name of the scoring function.<p>
   *
   * -d<br>
   * Turns debugging mode on.<p>
   *
   * @param options the list of options as an array of strings
   * @exception Exception if an option is not supported
   */
  public void setOptions(String[] options) throws Exception {

    String fileName = Utils.getOption('f', options);
    if (fileName.length() > 0) {
      setFileName(fileName);
    } else {
      setFileName(null);
      throw new Exception("Name of the input file required argument.");
    }

    String scoreFunction = Utils.getOption('s', options);
    if (scoreFunction.length() > 0) {
      setScoreFunction(scoreFunction);
    } else {
      setScoreFunction("Elkb");
    }

    setDebug(Utils.getFlag('d', options));

    Utils.checkForRemainingOptions(options);
   }


   /******************************************************************
    * Main
    ******************************************************************/
   public static void main(String[] ops) {
       LexicalChain lc = new LexicalChain();
       try {
	// Checking and Setting Options selected by the user:
	lc.setOptions(ops);      
	System.err.print("Building lexical chains with options: ");

	// Reading Options, which were set above and output them:
	String[] optionSettings = lc.getOptions();
	for (int i = 0; i < optionSettings.length; i++) {
	    System.err.print(optionSettings[i] + " ");
	}
	System.err.println();

	lc.loadFile();
	lc.printCandidateWords();
	lc.buildLCFinal();

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getMessage());
      System.err.println("\nOptions:\n");
      Enumeration en = lc.listOptions();
      while (en.hasMoreElements()) {
	Option option = (Option) en.nextElement();
	System.err.println(option.synopsis());
	System.err.println(option.description());
      }
    }
   }

   /*******************************************************************
    * Format of stop word list: one word per line
    ******************************************************************/
   public void loadStopWordList(String fname) {
      try {
         BufferedReader br = new BufferedReader(new FileReader(fname));
         
         for ( ; ; ) {
            String line = br.readLine();

            if (line == null) {
               br.close();
               break;
            }

            else {
		stopWords.add(line);
            }
         }

     } catch (Exception e) {
       System.out.println("Error: " + e);
     }
   }

  /***********************************************************************
   * LoadFile: loads the file for which the lexical chains will be built
   * Stores file in the Active Significant Word List. Stop words and 
   * punctuation are removed at this stage.
   * The format of the file must be one sentence per line as the lexical
   * chain building algorithm requires that the line number of a sentence
   * be known.
   *
   * candiWL contains the same words as ASWL without repetitions
   * Sept 12: candiWL is not rquired anymore. Left in code for
   *          compatibility reasons. Same goes for uniqWL
   *          allHeadSet and wsdHeadSet are required
   *          wsdHeadSet represents all of the Heads that are used more
   *          than once in the text, i.e. the disambiguated meanings
   **********************************************************************/
   public void loadFile() {
      int cLine = 0; // current line      
      StringTokenizer st;
      HashSet uniqWL = new HashSet();

      // Sept 12 - new allHeadSet & wsdHeadSet (private var of class)
      HashSet allHeadSet = new HashSet();
      // Sept 16 - new headDistri to calculate distribution of heads
      // TreeMap headDistri = new TreeMap();

      try {
         BufferedReader br = new BufferedReader(new FileReader(m_fileName));
   
         for ( ; ; ) {
            String line = br.readLine();

            if (line == null) {
               br.close();
               break;
            }

            // else if we are not reading a blank line
            else if (line.equals("") == false) {
               cLine++;

               // Remove all punctuation as well as numbers
               // using regular expressions!
               line = line.replaceAll("[,:;!\"\\.\\d]", "");

               // to this.
               st = new StringTokenizer(line, " ");
               while (st.hasMoreTokens()) {
                  String word = st.nextToken();
                  String wordLC = word.toLowerCase();

                  /***********************************************************
                   * Change if want to consider all POS */
                  if ( stopWords.contains(wordLC) == false ) {
                     String[] WordLinePair = { word,
                                               String.valueOf(cLine) };
                     ASWL.add(WordLinePair);
                  }
                  /***********************************************************/
 

                  // IF the word is NOT in the stop word list, AND
                  // it is considered as a NOUN by the ELKB OR
                  // the word is NOT known by the ELKB THEN
                  // ADD to ASWL
                  /***** ONLY NOUNS ****
                  if ( ( stopWords.contains(wordLC) == false ) &&
                       ( elkb.index.getRefPOS(wordLC).matches("^N.*|NULL") ) )                     
                  {
                     String[] WordLinePair = { word,
                                               String.valueOf(cLine) };
                     ASWL.add(WordLinePair);
                  }
                  ******/
               }
            }
            candiWL = ASWL; // Must be removed in the future. DO NOT USE!
         }  // end for
       
      } catch (Exception e) {
         System.out.println("Error: " + e);   
      }
   }


   /***********************************************************************
    * printCandidateWords: method to display word & sentence line pairs 
    * contained in ASWL
    *********************************************************************/
   public void printCandidateWords() {
       if (m_debug) {
	   System.out.println("Step1: Select a set of candidate words");
       }

      Iterator iter = ASWL.iterator();
      while (iter.hasNext()) {
         String[] wlp = (String[])(iter.next());
	 if (m_debug) {
	     System.out.println(wlp[0] + "\t\t" + elkb.index.getRefPOS(wlp[0].toLowerCase()) +"\t\t" + wlp[1]);
	 }
      }
   } 

   /********************************************************************
    * buildLCFinal: Final implementation of LexicalChain builder
    * Attempt to build Lexical Chains a la Silber and McCoy
    ********************************************************************/
    public void buildLCFinal() {
	ArrayList text     = new ArrayList(ASWL);
	HashSet   <Integer>senseSet = new HashSet<Integer>();    

       // required vars
       String curWord;
       String curSent;
       String posWord;
       String posSent;
       
       double metaScore;
       double newScore;

       // wlp = word line pair
       String[] wlp    = {"", ""};
       String[] wlpTxt = {"", ""};

       String sRelation; // type of Thesaural relation used

       MetaChain bestMetaChain;     
       TreeSet   <MetaChain>chainSet   = new TreeSet<MetaChain>();
       HashSet   <String>usedWord      = new HashSet<String>();
       ArrayList arrRefs       = new ArrayList();

       boolean bAddToChain; // to decide is metaChain will be added to chain

       // go through candiWL
   
       //buildLexChain:
       Iterator iter = ASWL.iterator();
       // Big O calculations
       //System.out.println("ASWL size = " + ASWL.size());
       while (iter.hasNext()) {
          wlp = (String[])(iter.next());
          curWord = wlp[0];
          curSent = wlp[1];
          metaScore = 0;
          bestMetaChain = null;
                  
          // If a word has already been used to start a chain, 
          // do not attempt to build a chain again
          // This reduces the complexity by a large factor!
          if ( usedWord.add(curWord) == false) 
             // we're done at this position in the text 
             text.remove(0);
         
          else {
            
             // get all HeadNo in which the word can be found
             try {
                arrRefs = elkb.index.getRefObjList(curWord);
             } catch (Exception e) {
                System.out.println("Error: " + e);
                System.out.println("Current word: " + curWord);
             }

             text.remove(0);
      
             // Build a metaChain for _every_ sense of word
             // A sense is defined by the location in a head
          
             // Should check to see if any of these senses are used in the 
             // remaining text
             
             Iterator headIter = arrRefs.iterator();
             while ( headIter.hasNext() ) {
             
                Reference ref = (Reference)headIter.next();
                String sRefName = ref.getRefName(); 
                int iRefHeadNo  = ref.getHeadNum();
                String sRefPOS  = ref.getPos();
                 
                // Don't bother building a MetaChain if it has already been built for this sense
                
                /***************************************
                 * Change for all POS
                 ***************************************/
                if (senseSet.contains(new Integer(iRefHeadNo)) == false) {
                
                /*** ONLY NOUNS ***
                
                if ( (senseSet.contains(new Integer(iRefHeadNo)) == false) &&
                        (sRefPOS.equals("N.")) ) {
                ****/
                
		    senseSet.add(new Integer(iRefHeadNo)); 
		    newScore  = 0;
                    
                   MetaChain metaChain = new MetaChain(curWord, 
                                             Integer.parseInt(curSent),
                                             iRefHeadNo);
                   // a MetaChain could contain refName & POS information...
                   // but not in this implementation!
                   metaChain.add(curWord, "T0", Integer.valueOf(curSent));
                          
                   Iterator textIter = text.iterator();

                   while (textIter.hasNext()) {
                      wlpTxt = (String[])(textIter.next());
                      posWord = wlpTxt[0];
                      posSent = wlpTxt[1];
                    
                      sRelation = elkb.t1Relation(curWord, iRefHeadNo, sRefName, sRefPOS, posWord);
                      if ( sRelation != null ) {
                         metaChain.add( posWord, sRelation, Integer.valueOf(posSent) );
			 if (m_debug) {
			     System.out.println(metaChain); 
			 }
                      } // end if
                
                   } // end while
                   
                   // Compute score. Store only _best_ meta chain
                   newScore = metaChain.getScoreElkb();
                   if (newScore > metaScore) {
                      metaScore = newScore;
                      bestMetaChain = metaChain;
                   }
                } // end if for building MetaChain
             } // End of HeadNo sense loop 
             
             // Do not keep singleton chains
             if (metaScore > MetaChain.T0_WEIGHT) {
		 chainSet.add(bestMetaChain);
		 if (m_debug) {
		     System.out.print(".");
		}
             }
             
             
          } // end if word in usedWord HashSet
                
       } // end of buildLexChain  
       
       if (m_debug) {
	   System.out.println("Step3: Keep best meta-chain for each word");
       	   printLCFinal(chainSet); 
       }
       chainSet = selectFinalLC(chainSet);


       if (m_debug) {
	   System.out.println("\nPrinting Lexical Chain");
	   System.out.println("----------------------");
       }
       if (m_scoreFunction.equals("Elkb")) {
	   printLCFinal(chainSet); 
       } else {
	   printLC(chainSet); 
       }
    }

   /*********************************************************************
    * selectFinalLC: method that selects the final set of lexical
    *    chains given a set of meta chains
    ********************************************************************/
    private TreeSet selectFinalLC(TreeSet chainSet) {
       
       chainSet = keepBestSense(chainSet);
       chainSet = wordInOneChain(chainSet);
               
       return chainSet;
    }
    
   /*********************************************************************
    * keepBestSense: method that keeps the meta chain that has the
    *   best score for a given sense
    * For example, the chain 1 is kept amongst these two chains:
    * 1. train, travelling, rails, direction, travelling, train, train, train, takes, takes, train,
    *     train [score: 12, sense: 267, line: 1] 
    * 2. travelling, rails, direction, travelling, train, train, train, takes, takes, train,
    *     train [score: 11, sense: 267, line: 1]
    ********************************************************************/
    private TreeSet keepBestSense(TreeSet resultSet) {
	HashMap <Integer,Double>senseScoreMap = new HashMap<Integer,Double>();
       Integer curSenseNo;
       Double curScore;
       MetaChain chain = new MetaChain();
    
       Iterator iter = resultSet.iterator();
      
       while ( iter.hasNext() ) {
          chain      = (MetaChain) iter.next();
          curSenseNo = new Integer( chain.getSenseNumber() );
          curScore   = new Double ( chain.getScoreElkb() );
          
          if ( senseScoreMap.containsKey(curSenseNo) ) {
	      if ( curScore.compareTo((Double)senseScoreMap.get(curSenseNo)) < 0 ) {
                iter.remove();
             }
          } else {
             senseScoreMap.put(curSenseNo, curScore);
          } 
       }                   
          
       return resultSet;
    }
  
   /*****************************************************************
    * wordInOneChain: a word can only belong to one chain
    * The word belongs in the chain with the highest score
    * Remove it from other chains
    ****************************************************************/
    private TreeSet wordInOneChain(TreeSet chainSet) {
       ArrayList chainArray = new ArrayList(chainSet);
       TreeSet sortedSet    = new TreeSet();
       MetaChain mc1, mc2;
       String word1, word2;
       int chainSize = chainArray.size();
       int i = 0;    
       double score;

       // variables to compute top scored lexical chains
       double threshold = 0.0;
       double[] scores = new double[chainSize];

       // loop through all of the MetaChains
       while ( i < chainSize ) {
          mc1 = (MetaChain) chainArray.get(i);
	  
	  if (m_scoreFunction.equals("HIndex")) {
	      scores[i] = mc1.getScoreHIndex();
	  }

          // iterate through the words of the first MetaChain
          Iterator iter1 = mc1.iterator();
          while (iter1.hasNext()) {
             word1 = (String) iter1.next();
             
             // obtain the other MetaChains
             for (int j = i+1; j < chainSize; j++) {
                mc2 = (MetaChain) chainArray.get(j);
                Iterator iter2 = mc2.iterator();
                // iterate through the words of the second MetaChain
                while ( iter2.hasNext() ) {
                   word2 = (String) iter2.next();
                   //System.out.println(mc1);
                   //System.out.println(mc2);                   
                   //System.out.println("W1 = " + word1 + "; W2 = " + word2);
                   if ( word2.equals(word1) ) {
                      // remove from MetaChain since this word is already
                      // in a stronger chain
                      iter2.remove();
                   }
                } // end while iter2
             } // end for j
          } // end while iter1      
          
          // Sort array and update variables
          sortedSet  = new TreeSet(chainArray);
          chainArray = new ArrayList(sortedSet);
          i++;
          
       } // end of loop through all MetaChains

       if (m_scoreFunction.equals("HIndex")) {
	   threshold = Utils.mean(scores) + Math.sqrt(Utils.variance(scores));
       }

       if (m_debug && m_scoreFunction.equals("HIndex")) {
	   System.err.println("Score threshold = " + threshold);
       }
       
       // Do not keep singleton chains
       // For some reason this operation does not work correctly in the loop
       Iterator iter1 = sortedSet.iterator();
       while ( iter1.hasNext() ) {
          mc1 = (MetaChain) iter1.next();
	  if (m_scoreFunction.equals("HIndex")) {
	      score = mc1.getScoreHIndex();
	      if (score < threshold) {
		  iter1.remove();
	      }
	  } else {
	      score = mc1.getScoreElkb();
	      if (score <= MetaChain.T0_WEIGHT) {
		  iter1.remove();
	      }
	  }
       }
              
       return sortedSet;
    }
    
    
   /*********************************************************************
    * printLCFinal: method that prints a lexical chain
    ********************************************************************/
    public void printLCFinal(Collection chainSet) {
	MetaChain mc;
       Iterator iter = chainSet.iterator();
       while (iter.hasNext()) {
	   mc = (MetaChain)iter.next();
	   
	   System.out.println(mc);
       }
    }     


   /*********************************************************************
    * printLC: method that prints a normalized lexical chain
    ********************************************************************/
    public void printLC(Collection chainSet) {
	MetaChain mc;
	Iterator iter = chainSet.iterator();
	while (iter.hasNext()) {
	    mc = (MetaChain)iter.next();
	    if (m_scoreFunction.equals("HIndex")) {
		System.out.println(mc.findKeyword() + " [" + mc.getScoreHIndex() + "]");
	    } else {
		System.out.println(mc.findKeyword() + " [" + mc.getScoreElkb() + "]");
	    }
	}
    }     



//    /*********************************************************************
//     * calculateHeadDistribution: similar to previous method. Builds
//     *        a TreeSet that inidcates which Head numbers and what 
//     *        words are used in the text  
//     ********************************************************************/
//     private void calculateHeadDistribution(TreeMap headDistri, 
//                                            String word) {
//        Integer headNo;
//        TreeSet wordSet;

//        TreeSet arrHeadNo = elkb.index.getHeadNumbers(word);
//        Iterator iter = arrHeadNo.iterator();
//        while ( iter.hasNext() ) {
//           headNo = new Integer( (String)iter.next() );

//           if ( headDistri.containsKey(headNo) ) {
//              wordSet = (TreeSet)headDistri.get(headNo);
//              wordSet.add(word);
//           } else {
//              wordSet = new TreeSet();
//              wordSet.add(word);
//           }
          
//           headDistri.put(headNo, wordSet);
//        }
//     }

   /*********************************************************************
    * printHeadDistri
    ********************************************************************/
    private void printHeadDistri(TreeMap headDistri) {

       Integer headNo;
       TreeSet wordSet;
       Set headSet = headDistri.keySet();
  
       Iterator iter = headSet.iterator();
       while ( iter.hasNext() ) {
          headNo = (Integer)iter.next();
          wordSet = (TreeSet)headDistri.get(headNo);
          System.out.print(headNo + ":\t");
          System.out.println(wordSet);
       }
    }
       

} // end of Class

