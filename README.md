# Pick-A-Pic
Android App Final Project
### Second Milestone:


***Connor McLaughlin***: Second Milestone This week I took what I made for the first milestone and transfered it over to Android Studio. I submitted the folder labeled DB. In this folder there is a Database in the DB folder and class called File Read that is used to get values from a text file that is formatted: Image_Ref Category Discription Alt_Discription(Optional). At the moment I'm having issues with the final line of this code when I try to put it in the database. I'm hoping to resolve this in class Tuesday. I figured I'd submit just the folder because It would be easier to put the database into the UI project than the UI into the Database project.

*First Milestone - I’ve worked on the Database. I now have small database that has Tokens for the picture ID, Category, and descriptions. The descriptions get more general as they go on so that people can get more points for degree of difficulty. Right now the database is in Microsoft Access but I plan on converting it to something that Java can read. I also have queries to find a random object then find objects that have the same category to increase difficulty.*

***Connor McGrory***: Second Milestone - I have uploaded the Java code for the main functionality of the game.  I have been attempting to find the best method for converting my code written for pc, over to Android Studio.  Unfortunately I've been encountering lots of problems with the conversion.  I tried using a bitmap at first to edit the individual pixels of an ImageView.  This worked but was very slow.  I am currently trying various other methods and will select the best one for the final product.


*First Milestone - I have created the image pixelizer program that will be used as the main game feature for this project.  When the program is run, the image is drawn using only a few large pixels (averaging all underlying colors).  Slowly the number of pixels is increased giving a “coming into focus” effect.  I wrote all of this in Java, but not in Android Studio.  It started as a computer application so I will need to make major changes to make it compatible.*

***Samir Khoulani***:  
![goat](https://github.com/uml-app1-2016/Pick-A-Pic/blob/Second-Milestone/mainMenu.png)

The next item of business was to actually create a basic UI. The current design involves a title screen with three buttons. A "Start Game" button, "High Scores" button, and a "Settings" button. Respectively, the buttons will start the game, show high scores, and allow the user to turn the music on or off. Currently, we are deciding between using a three heart system or a system that uses unlimited tries to get the answer correct. We are leaning towards implementing google play APIs for the high scores page.

*First Milestone - Thus far I have researched various UI design patterns for games. The current idea we are currently moving forward involves a fullscreen activity which will be completely taken up by the game. There will be three views: the Main Menu, the Game Screen, and the Game Over screen. Additionally, although it is my plan to eventually implement google play high scores, for now we will be relying on a local "Best" score on the Game Over screen so the user can keep track of their highest score so far.*

