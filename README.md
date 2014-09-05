# TI2206 - Group 21 (2048)

This team consists of the following students:

| Name                 | Student number |
|----------------------|----------------|
| Piet van Agtmaal     | 4321278        |
| Jochem Heijltjes     | 1534041        |
| Arthur Hovanesyan    |
| Paul Bakker          |
| Jente Hidskes        | 4335732        |

## Todo

* Overleggen met SA (volgende week) over onze aanpak m.b.t Maven en LibGDX.
* Iedereen CheckStyle installeren.

## Sprint backlog

See doc/xx-Sprint-Backlog.md (where xx is the current sprint number) for task explanations.

| ## |  WIP  | Testing | Done |
|----|-----  |---------|------|
| 01 |       |         |   X  |
| 02 |       |         |   X  |
| 03 |       |         |   X  |
| 04 |       |         |      |
| 05 |       |   X     |      |
| 06 |       |   X     |      |
| 07 |       |   X     |      |
| 08 |       |   X     |      |
| 09 |       |         |      |
| 10 |       |         |      |
| 11 |       |   X     |      |
| 12 |       |   X     |      |
| 13 |       |   X     |      |
| 14 |       |   X     |      |
| 15 |       |   X     |      |
| 16 |       |   X     |      |
| 17 |       |   X     |      |
| 18 |       |         |      |
| 19 |       |         |      |
| 20 |       |         |      |
| 21 |       |         |      |
| 22 |       |         |      |
| 23 |       |         |   X  |
| 24 |       |         |   X  |
| 25 |       |         |   X  |
| 26 |       |         |   X  |

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
`git config --add remote.all.url ssh://github.com/Unia/2048.git`

Now use `git push all` to push to both remotes simultaneously.

## How to start this game

1. Create a new binary of the game by executing `mvn clean package` in the root folder of the project.
2. In the folder `target` you will find a binary file called `2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar`. This file contains all the code, config files and images which are required to run the game.
3. The game can be started by executing this JAR file. You can either double click on it, or execute it from the command-line using the command: `java -jar 2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar`.
