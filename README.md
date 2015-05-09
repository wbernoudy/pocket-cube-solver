# pocket-cube-solver
A simple and very fast pocket cube (2x2x2 Rubik's cube) solver written in Java.

## Design

The code was written in the way that I solve the pocket cube. This just makes it a series of messy methods that perform my algorithms. While this means the total move count comes out to be very long, it is quite fast.

## Usage

The code takes as input a series of faces.

Compile the java code, and then run

```
java PocketSolver.java < inputcubes/testcube3.txt
```

or any of the testcubes provided. The code should then output an unwieldy but correct solve.
