# JKagu

JKagu is a software which maybe will help you when you need to search and replace text with special requirments.

- The idea is you first to select the documents which you want to edit. If you open several one after other the text files will be appended to one another.

- The You select the filtering operation. There is for now several options:
1. Use the whole file(s) text
2. Select only the rows that contain certain word
3. Advance search. Use something like  ' select row+3 where row has 'aaa' 'bbb' 'ccc' ' -> which will result of selecting the row which contains aaa, bbb, ccc and next 3 rows below
4. Use Search from until functionality which will select the row that has from keyword then all the other rows until the row which has until keyword or the end of the file

- Then you can use copy save or the replace functionality. For the replace functionality you have:
1. Normal find word replace by another word.
2. Find each first occurnace of the word for replacement on the row and replace it
3. Find each last occurnace of the word for replacement nt the row and replace it
4. Find a word which you will replace with an incrementing number. Also you have to set here the incrmentation step and the begginign number
