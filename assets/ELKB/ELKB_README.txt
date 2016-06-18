Roget's Thesaurus Electronic Lexical Knowledge Base (ELKB)

README

This page presents the Electronic Lexical Knowledge Base ELKB, software for accessing and exploring the Roget's thesaurus. It also provides solutions for various natural language processing tasks.

CONTENT

1. Roget's thesaurus

	Directory "roget_elkb" contains the electronic version of the Roget's Thesaurus from 1911, obtained from the Gutenberg Project (http://www.gutenberg.org/etext/10681). Roget's thesaurus has been converted to be used in distributed application and consists of 
	* the index file (newIndex.txt), 
	* the upper structure of the thesaurus with classes, sections, head groups and head names, in a XML like format (rogetMap.rt)
	* 1044 head files (in the directory "heads") that correspond to single head entries in Roget.
	The same directory also contains the license for usage this version of the thesaurus, obtained from the Gutenberg website.

2. ELKB package (ca.site.elkb)

	The ELKB (Electronic Lexical Knowledge Base) was created to access the Roget's thesaurus. It was originally used with the 1987 Penguin edition, but is used here with the free available version described above. 

3. Applications package (applications)

	Practical applications that make use of the ELKB are summarized in this package. For example, a program for detecting lexical chains in a document, or scripts for measuring semantic distance between two words by analysing Roget's structure. 

4. Script for testing and usage examples

	TestELKB.java allows to query the ELKB for information about a word or a phrase, and about two words or phrases and how they relate to each other.

5. Resources
	
	A few lexical resources are:
		.exc files in "roget_elkb" are exception list files for the morphological processing rules used by WordNet 1.7.1. and are described in: http://wordnet.princeton.edu/man/morphy.7WN
		AmBr.lst in "roget_elkb" is a list of equivalent British and American 646 spellings variations. 
		and stop.txt in the main folder, containing 980 stopwords.

	The folder Resources contains sample input files and output files produced in the testing stage of ELKB, in it's original version (1987) and the new public available one (1911).

6. Documentation
	
	The folder doc contains Javadoc for ELKB


All scripts were originally developed as a part of Mario Jarmasz' Master thesis
at the University of Ottawa, Canada (See http://www.site.uottawa.ca/~mjarmasz/thesis/index.html).


USAGE

1. Extract the content of the archive and move folder "roget_elkb" into your home directory.

2. In the ELKB directory, run "java TestELKB" and select the option "1". This will create the index.

3. In the same application select 2 or 3, and follow the instructions to query the ELKB.

******************************************************
"2
Loading the Index...
Loading the Text...
Loading the Categories...

Enter a word or phrase: existence

** existence **
1. substantiality 3 N.
2. existence 1 N.
3. destiny 159 N.
4. life 371 N.
5. piety 1031 N.
6. perpetuity 118 N.
Enter the number of the reference to be looked up: 2

========================== Head 1 =========================
N.
existence, being, entity, ens, esse, subsistence;
reality, actuality;
positiveness;
fact, matter of fact, sober reality;
truth; <cref: 494 impossibility>
actual existence;
presence; <cref: 186 existence in space>
coexistence; <cref: 120 chronometry>
stubborn fact, hard fact;
not a dream; <cref: 515 ignorance>
no joke;
center of life, essence, inmost nature, inner reality, vital principle;
ontology;
<br>;

SG: existence, being, entity, ens, esse, subsistence;

Look up another word or phrase [y/n] ? n"
******************************************************


4. See Applications_REAME.txt for details and the usage of further applications.


Note: The java files were compiled with Java 1.5, if you don't have this java version, you might have to recompile the code:
 
$ELKB$ javac ca/site/elkb/*.java
$ELKB$ javac tools/*.java
$ELKB$ javac *.java


LICENSE
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.


COPYRIGHT
Copyright (C) 2006 Mario Jarmasz and Stan Szpakowicz
School of Information Technology and Engineering (SITE)
University of Ottawa, 800 King Edward Avenue
Ottawa, Ontario, Canada, K1N 6N5
and
Olena Medelyan
Department of Computer Science, The University of Waikato
Private Bag 3105, Hamilton, New Zealand



