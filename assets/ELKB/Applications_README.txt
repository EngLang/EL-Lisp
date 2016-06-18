Roget's Thesaurus Electronic Lexical Knowledge Base (ELKB)

Applications README

The Package "applications" contains several application for exploring the ELKB, Electronic Lexical Knowledge Base based on the electronic version of Roget's thesaurus (free version, 1911).

All scripts were developed as a part of Mario Jarmasz' Master thesis at the University of Ottawa, Canada (See http://www.site.uottawa.ca/~mjarmasz/thesis/index.html). 


CONTENT

1. LexicalChain - detects lexical chains in a text
2. SemDist - measures semantic distance between two words or phrases
3. WordCluster -  clusters words or phrases in a list according to their semantic similarity
4. WordPower - answers Reader's Digest WordPower type questions


DESCRIPTION AND USAGE

Before using the applications, make sure that ELKB is installed on your computer. See ELKB_README.txt for details. The folder Resources contains files that can be used as input for each of these applications.

1. LEXICAL CHAIN

This program analyzes words and phrases that appear in the document and how are
they related to each other according to Roget's thesaurus. It builds lexical chains, i.e. 
sequences of related words, that reflect the topics of this document.

Usage: java applications/LexicalChain -f <input_file> (-s <Elkb|HIndex>) (-d)

where 
<input_file> is a text version of the document you want to process, e.g. Resources/train.txt
-s specifies the scoring function (either Elkb (default) or HIndex)
-d is a debugging option that turns on verbose output of the program.

Note: HIndex was implemented by O.Medelyan according to functions proposed in Barzilay & Elhadad (1997). When selected, the score of a lexical chain is computed as:
		score(chain) = length*homogeneity
		length - number of all members in the chain
		homogeneity = 1 - distinct members/length
Only top chains that satisfy the following condition are presented:
		score(chain) > average(scores) + 2*StandardDeviation(scores).
Their members are presented only once in the chain, sorted by their frequency. The leading member is the one with the highest frequency, which can be seen as a keyword.


2. SEMANTIC DISTANCE

SemDist.java, in the SemDist folder:
This program measures the semantic distance between two words or phrases, on a scale from 4 (not similar) to 16 (very similar). There are two versions of the program: 

a) SemDist.java - requires an input file, where words or phrases must be supplied in comma separated pairs on one line. An example of an input file is MillerCharles.txt and of an output file MillerCharles_res.txt in the folder Resources.

Usage: java applications/SemDist <input file>

where <input file> is a file with words pairs as described above, e.g. Resources/MillerCharles.txt

b) SemDist2Words.java - takes two words as an input and computes their semantic similarity

Usage: java applications/SemDist2Words <word1> <word2>

where <word1> and <word2> are two valid words or phrases, e.g. "painter" and "artist". When entering a phrase consisting of more than 1 word, just take it into apostrophes.

3. WORD CLUSTERS

This program measures the semantic distance between all combinations of words and phrases in a list. It also clusters them according to their membership in Roget's Heads. A sample input file is radioactive_materials.txt and output file radioactive_materials_res.txt, in the folder Resources.

Usage: java applications/WordCluster <input file> <output file>


4. WORD POWER GAME

This program answers Reader's Digest WordPower type questions. E.g. Which of these words can be traced back to the ancient Greek language? 
For fantasize:  a) to imagine 	b) endear 	c) enlarge 	d) lie 

A sample input file is Resources/rd_july2000.txt. You will find answers detected with two versions of Roget's: rd_july2000_res.txt (1987) and rd_july2000_res1911.txt

Usage: java applications/WordPower <input file>


------------------

LICENSE
These programs are free software; you can redistribute it and/or modify
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
