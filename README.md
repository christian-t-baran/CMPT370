RoboSport370

Contributors (uploaded with the permission of):
Christian Baran
Peggy Anderson
Jonathan Baxter
Mark Epp
Atri Lahiji

Introduction:
This project was for the CMPT370 course (Intermediate Software Engineering) at the University of Saskatchewan.
It's posted as is at the point of submission (although further tests and error detection/handling) would have been included
	given more time.
Documents and meeting proceedings that were part of the course work have been omitted.

Synopsis:
This software is used to run and test robots in a simulated battle with other
	robots that the user supplies from the robot repository.
Robot scripts are written in a subset of Forth.


Installation:
This software need to be compiled and ran on the tuxworld server at the
University of Saskatchewan. It also requires that Java 1.8 is installed. To run
the project should be imported into Eclipse and the RoboSport370 class should compiled as the main class. 

Run:
To run under eclipse:
	Open the RoboSport370 file
	press the play button in eclipse
This wil bring up the main splash screen
	press New Game
	Select the desired team size
	Select the Board Size
Next you will presented with the team builder window
	Select a team to build
	Select the robot that you wish to add to your team
	press the add button
	Repeat selecting robots and teams
	when all teams are selected press select at the bottom
 Choose Running Style
	simulation: this runs a battle 
		select the play button to start
		Fast forward increases game speed
		pause will pause the game
		stop ends the game
		Fast forward to completion makes the game go to the end
	
	testBench works the same as the simulation but it uses the play button to
	continue step by step
