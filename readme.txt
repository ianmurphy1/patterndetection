This contains 3 different versions of the LineDetector, the only differences in them are the data structures used to store and the points into a line and then those lines into another set of lines.

The class in the arraylistimplementation package uses ArrayList<ArrayList<Point>> as it's primary holding mechanism.
The class in the setimplentation package uses an ArrayList<Set<Point>> 
The class in mapinplementation uses Map<Set<Point>> as it's method of extracting points from the text files.
Each uses a different method for removing duplicate lines that can occur when running the program.


Contained in the inputs folder are files containing points similar to the one given to us at the start of the assignment, these were gotten from http://coursera.cs.princeton.edu/algs4/testing/collinear/.

The outputs folder contains images of the resulting files. 

An x and y axes were added to the resulting image and also labels.

And as an added bonus, audio plays once the program has run through and found all collinear points.