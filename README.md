# hitting-set-solver

Efficient divide-and-conquer algorithms for the NP-hard Hitting Set problem, with full analysis and experiments.
A personal project exploring four divide-and-conquer strategies for the Hitting Set problem, complete with theoretical analysis and empirical evaluation.

## Overview

The Hitting Set problem is a classic NP-hard combinatorial optimization task: given a collection of sets, find the smallest subset of elements that “hits” (intersects) every set. In this project I:

1. Designed and analyzed four pivot-selection strategies:
   - **Random Pivot**  
   - **Max-Occurrence Pivot** (element in most sets)  
   - **Min-Subset Pivot** (smallest set)  
   - **Hybrid Pivot** (min-subset with max-occurrence tiebreak)
2. Proved asymptotic bounds and compared expected performance.
3. Implemented each algorithm in Java.
4. Ran extensive experiments on both synthetic and real-world instances, producing runtime curves and box-plot comparisons.
5. Packaged results, plots, and insights into a concise report.

## Environment & Requirements

- **Tested on:** Windows 11 Home, AMD Ryzen 7 5700U @1.8 GHz (8 core/16 thread), 16 GB RAM :contentReference[oaicite:2]{index=2}  
- **Java:** JDK 11+  
- **Build:** Plain `javac` or your preferred build tool (Maven/Gradle optional)

## Build & Run

### Compile
javac HittingSet.java

### Correctness check on sample data
java HittingSet sets.dat

### Experiment 1
java HittingSet 1

### Experiment 2
java HittingSet 2

## Results & Plots

See Experiment_Results.pdf for detailed runtime curves, box-plots, and stability analysis.
See Predictions_For_Experiments.pdf for brief predictions of each algorithm for the experiments 
