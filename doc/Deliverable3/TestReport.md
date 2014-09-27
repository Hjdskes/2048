# Test Report #
In this document we will explain how we tested our game. We will start by explaining how often we tested our game. Afterwards, we will explain what kinds of testing we have done. Lastly, we will present the results of the testing procedure.

## Test frequency ##
In this section we will discuss how frequently we tested our game. Due to the design patters we implemented, testing was essential. We tested using our Unit tests (local and on Devhub as well) and visual tests.
Implementing iterators caused some problems that only arised during our visual tests, these were, however, resolved immediately.

## Testing methods ##

Visual tests involved actually playing the game and analyzing logging output manually.
Unit tests simply check objects properties with certain input.

## Test results ##

EclEmma is the tool we used for analyzing and measuring our test coverage. As before, we analyzed our entire project using three different metrics: line, branch and instruction coverage.
The results are the following:

1. Line coverage: 64.9 %
2. Branch coverage:50.7 % 
3. Instruction coverage: 62.8 %

As with previous deliverables, we faced the same issues with code that requires graphically rendering our game. 

## Conclusion ##
Although our test results are lower than we had planned to achieve, we believe our project has been tested sufficiently.