# JKagu

JKagu is a software which maybe will help you when you need to search and replace text with special requirments.

 <p>The idea is for you to first select the document that you want to edit. If you open several text files at once, they will be appended to one another.</p>

<p>Then you select the filtering operation. There are several options for now:</p>
<ul>
  <li>Use the whole file(s) text</li>
  <li>Select only the rows that contain a certain word</li>
  <li>Advanced search. Use something like "select row+3 where row has 'aaa' 'bbb' 'ccc' " -> which will result in selecting the row which contains aaa, bbb, ccc and the next 3 rows below</li>
  <li>Use Search from keyword until another keyword functionality which will select the row that has the first keyword then all the other rows until the row which has the last keyword or the end of the file</li>
</ul>

<p>Then you can use the copy, save or replace functionality. For the replace functionality you have:</p>
<ul>
  <li>Normal find word replace by another word.</li>
  <li>Find each first occurrence of the word for replacement on the row and replace it</li>
  <li>Find each last occurrence of the word for replacement on the row and replace it</li>
  <li>Find a word which you will replace with an incrementing number. Also you have to set here the incrementation step and the beginning number</li>
</ul>
The program was created with Java and Java FX and for the building it was used Amazon Correto 17