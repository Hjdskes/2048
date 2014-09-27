# TI2206 - Group 21 (2048)

Group 21 consists of the following students:

| Name                 | Student number |
|----------------------|----------------|
| Piet van Agtmaal     | 4321278        |
| Jochem Heijltjes     | 1534041        |
| Arthur Hovanesyan    | 4322711		|
| Paul Bakker          | 4326091		|
| Jente Hidskes        | 4335732        |


## Sprint backlog

See doc/xx-Sprint-Backlog.md (where xx is the current sprint number) for task explanations.

| ## |  WIP  | Testing | Done |
|----|-----  |---------|------|
| 01 |   X   |         |      |
| 02 |   X   |         |      |
| 03 |       |         |   X  |
| 04 |       |         |   X  |
| 05 |       |         |   X  |
| 06 |       |         |   X  |
| 07 |       |         |   X  |
| 08 |   X   |         |      |
| 09 |       |         |   X  |
| 10 |       |         |   X  |
| 11 |       |         |   X  |
| 12 |   X   |         |      |

## How to add the new GitHub remote

We have decided that we will also use a GitHub (private) repository to push our
code to. Reasons behind this new repository are: GitHub is branch-friendly,
whereas DevHub is a little vague; the README is more visible now, meaning we are
more likely to pickup changes and that is it easy to for example keep the above
table for the current sprint; we can now use GitHub's Issues page.

To add the new GitHub repository, first rename the current remote:

`git remote rename origin devhub`

Now add the new repository:

`git remote add origin https://github.com/Unia/2048.git`

Since our new repository is only a mirror, we still have to push to both
(otherwise, there would be no continuous integration!). To help with this,
you can add multiple remotes under the same command:

`git config --add remote.all.url ssh://git@devhub.ewi.tudelft.nl/courses/ti2206/group-21.git`
`git config --add remote.all.url https://github.com/Unia/2048.git`

Now use `git push all` to push to both remotes simultaneously.

## How to start this game

1. Create a new binary of the game by executing `mvn clean package` in the root folder of the project.
2. In the folder `target` you will find a binary file called `2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar`. This file contains all the code, config files and images which are required to run the game.
3. The game can be started by executing this JAR file. You can either double click on it, or execute it from the command-line using the command: `java -jar 2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar`.

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

