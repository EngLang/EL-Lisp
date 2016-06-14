# EngLang

##Introduction
EngLang is a syntax-lenient programming language that essentially takes human text and parses it into executable code. For example, the sentence:
```EngLang
You should create a text file in /users/me/docs with the contents 'I love chicken'
```
Will create a file in the directory `/users/me/docs` with the filename `chicken.txt` and the contents `I love chicken.`

However, it is possible to create even more complex sentences that do some spectcular things.
```EngLang
Let's count to five, saying each number aloud as we go, and send the result to http://mywebsite.com/api/count as 'mycount'
```
This statement is equivalent to creating a variable, running through a loop 5 times that increments the number and prints it to the console, and then makes a `POST` call to `http://mywebsite.com/api/count` JSON content `{mycount: 5}`.

Fancy right? Well using our goal of super-abstraction, you can even write code that is very minimal but outputs complex results, such as the following:
```EngLang
Take the population of the US and create an ascii bar graph showing the change over time.
```
Using resources such as the Wolfram|Alpha API and Wikipedia, this method with gather the population information and show the desired graph in the console.

Impressed yet? Let's get started.

##Key Terminology
There are some key terms used within the documentation of EngLang that is considered unconventional. Therefore, below is a list of terminology that may be helpful:


`Sentence` - 
`Verb` - 
`Subject` -
`Parameter` - 
`Chapter` -
`Paragraph` - 
`Alias` - 