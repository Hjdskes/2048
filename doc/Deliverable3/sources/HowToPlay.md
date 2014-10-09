# 2048 #

Let's play!
----------

2048 is a very popular game created by by Gabriele Cirulli. Based on 1024 by Veewo Studio and conceptually similar to Threes by Asher Vollmer.

Move the tiles with the arrow keys. Each time 2 tiles with the same number touch, the numbers are added and the two tiles merge. 

This document briefly describes how to play 2048 and provides information on the functionality it has, such as playing the game alone, with friends and how to use the logging features.

Good luck and have fun!

P.S.: We, the developers, are not reliable in case of frustration. ;)


## Singleplayer game ##

After starting the application you will see the main menu. In the main menu, click the "Singleplayer" button to start your singleplayer game.
Join the numbers of equal values and try to reach the 2048 tile by merging them. 

## Multiplayer game ##

The mulitplayer version is identical to the singleplayer, except you will compete against a friend, colleague, coworker or your worst enemy: over LAN or the internet. Your opponent does not have to be in the same room with you, they can even be on the other side of the planet and you can still kick their asses!

This section briefly explains how to connect to eachother. Please refer to the documentation of your networking equipment or software in case you experience networking problems.

### Joining a game ###
The application will try to connect to the remote address you entered, on port 2048, using TCP.

###Hosting a game ###
In the main menu, click "Host a game". The application will bind to port 2048/tcp on all the system's network interfaces. In case you wish to play over the internet, please make sure connections on this port are forwarded to your local address on your NAT device.


## Logging (commandline arguments) ##

The game supports several commandline arguments for logging.
By default, the application will log to the standard output. If enabled, however, errors will be logged to STDERR.

The supported arguments are:
> jarfile.jar **[logLevel]** **[file]**

or, otherwise:

> Launcher.java **[logLevel]** **[file]**

Both of these fiels are parsed case insensitively.

An example:

> Launcher.java **debug**

will run the game and log all debug and info messages. 

> Launcher.java **error** **file**

will run the game and log all debug, error and info messages to the system's output streams (stdout & stderr) and write them to a new file as well.

Please see the corresponding section below for more information.

----------

**[logLevel]**

where logLevel can be one of the following:

> **all:** logs all messages
> 
> **info:** logs info messages only
> 
> **error:** log error messages and info messages
> 
> **debug:** log debug, error and info messages
> 
> **none:** disables logging

----------

**[file]**

Setting the 'file' flag will write messages of the previously set logLevel to file.
By default, a new file with the format **2048_YYYYMMDD_HHmmss.log** will be created, where **YYYMMMDDD_HHmmss** is the time of application start.

