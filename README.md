# TI2206 - Group 21 (2048)

This team consists of the following students:

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
| 01 |       |         |      |
| 02 |       |         |      |
| 03 |       |         |   X  |
| 04 |       |         |      |
| 05 |       |         |      |
| 06 |       |         |      |
| 07 |       |         |      |
| 08 |       |         |      |
| 09 |       |         |      |
| 10 |       |         |   X  |
| 11 |       |         |   X  |
| 12 |       |         |      |
| 13 |       |         |      |

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
