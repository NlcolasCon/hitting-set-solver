
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class HittingSet {

    public static void main(String args[]) {

        try {
            int flag = Integer.parseInt(args[0]);
            switch (flag) {
                case 0:
                    correctivityRun();
                    break;
                case 1:
                    experimentRun();
                    break;
                case 2:
                    experimentRun2();
                    break;
                default:
                    System.out.print("Invalid number: \nFlag = 0 -> correctivityRun()\nFlag = 1 -> experimentRun()\nFlag = 2 -> experimentRun2()\n");
            }
        } catch (Exception e) {
            correctivityRun();
        }
    }

    //For correctivity runs
    private static void correctivityRun() {
        String fileName = "sets.dat";
        try {
            int[][] B;
            int N, M, C, K;
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            String paramLine = buffer.readLine();
            String params[] = paramLine.split(" ");
            N = Integer.parseInt(params[0]);    //get n
            M = Integer.parseInt(params[1]);    //get m
            C = Integer.parseInt(params[2]);    //get c
            K = Integer.parseInt(params[3]);    //get k

            B = new int[M][C];
            for (int i = 0; i < M; i++) {       //fill B array with the m sets with max range of c elements
                String setLine = buffer.readLine();
                params = setLine.split(" ");
                for (int j = 0; j < params.length; j++) {
                    if (!params[j].equals("")) {
                        B[i][j] = Integer.parseInt(params[j]);
                    }
                }

            }

            buffer.close();

            printSets(B, M, C);
            System.out.println();
            runAlgorithms(B, N, M, C, K);

        } catch (IOException e) {
            System.err.println("Error while reading file: " + fileName);
            System.exit(-1);
        }
    }

    //For correctivity runs
    private static void runAlgorithms(int[][] B, int N, int M, int C, int K) {
        Random rand = new Random();
        System.out.print("N = " + N + ", ");
        System.out.print("M = " + M + ", ");
        System.out.print("C = " + C + ", ");
        System.out.println("K = " + K + "\n");

        System.out.println("Trying input for Algorithm 1: ");
        long startTime = System.nanoTime();
        int[] hittingSet = HittingSetDefault(B, K, M, C, rand, new int[K]);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Total runtime: " + (totalTime / 1_000_000.0) + " ms");
        if (hittingSet != null) {
            System.out.println("Hiting Set Algorithm 1:");
            for (int i = 0; i < hittingSet.length; i++) {
                if (hittingSet[i] != 0) {
                    System.out.print(hittingSet[i] + " ");
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No Hitting Set of size " + K + " found for algorithm 1!\n");
        }
        System.out.println("Trying input for Algorithm 2: ");
        startTime = System.nanoTime();
        hittingSet = HittingSetCriticalPoint(B, K, M, C, N, new Random(), new int[K], new int[M]);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Total runtime: " + (totalTime / 1_000_000.0) + " ms");
        if (hittingSet != null) {
            System.out.println("Hiting Set Algorithm 2:");
            for (int i = 0; i < hittingSet.length; i++) {
                if (hittingSet[i] != 0) {
                    System.out.print(hittingSet[i] + " ");
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No Hitting Set of size " + K + " found for algorithm 2!\n");
        }

        System.out.println("Trying input for Algorithm 3: ");
        startTime = System.nanoTime();
        hittingSet = HittingSetSmallestSet(B, K, M, C, new int[K], 0);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Total runtime: " + (totalTime / 1_000_000.0) + " ms");

        if (hittingSet != null) {
            System.out.println("Hiting Set Algorithm 3:");
            for (int i = 0; i < hittingSet.length; i++) {
                if (hittingSet[i] != 0) {
                    System.out.print(hittingSet[i] + " ");
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No Hitting Set of size " + K + " found for algorithm 3!\n");
        }

        System.out.println("Trying input for Algorithm 4: ");
        startTime = System.nanoTime();
        hittingSet = HittingSetCriticalAndSmallest(B, K, M, C, N, new int[N], 0);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Total runtime: " + (totalTime / 1_000_000.0) + " ms");

        if (hittingSet != null) {
            System.out.println("Hiting Set Algorithm 4:");
            for (int i = 0; i < hittingSet.length; i++) {
                if (hittingSet[i] != 0) {
                    System.out.print(hittingSet[i] + " ");
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No Hitting Set of size " + K + " found for algorithm 4!\n");
        }
    }

    //For experimental runs
    private static void experimentRun() {
        int N = 2000;
        int M = 100;
        int C = 10;
        Random rand = new Random();

        //Create M sets B with C different numbers each one 
        int[][] B = new int[M][C];
        for (int i = 0; i < M; i++) {
            int size = rand.nextInt(C) + 1;
            boolean[] used = new boolean[N + 1];
            int j = 0;
            while (j < size) {
                int number = rand.nextInt(N) + 1;
                if (!used[number]) {
                    B[i][j++] = number;
                    used[number] = true;
                }
            }
        }

        printSets(B, M, C);
        runAlgorithmsExperimental(B, N, M, C);

    }

    //For experimental runs
    private static void runAlgorithmsExperimental(int[][] B, int N, int M, int C) {
        Random rand = new Random();
        int stop = 0;
        boolean[] stopTrying = new boolean[4];
        int K = 1;
        while (stop < 4) {
            System.out.print("N = " + N + ", ");
            System.out.print("M = " + M + ", ");
            System.out.print("C = " + C + ", ");
            System.out.println("K = " + K + "\n");

            if (!stopTrying[0]) {
                System.out.println("Trying input for Algorithm 1: ");

                long totalTime = 0;
                int[] hittingSet = null;
                boolean moreThanHour = false;

                for (int iterations = 0; iterations < 3; iterations++) {
                    long startTime = System.nanoTime();     //Count the total time for that K
                    hittingSet = HittingSetDefault(B, K, M, C, rand, new int[K]);
                    long endTime = System.nanoTime();
                    long runTime = endTime - startTime;
                    System.out.println("Run " + (iterations + 1) + " time: " + (runTime / 1_000_000.0) + " ms");
                    if (runTime >= 3_600_000_000_000L) {  //If the algorithm took more than 1 hour to complete never run again and print message
                        System.out.println("More Than 1 hour has passed for Algorithm 1, K = " + K + "\n");
                        stop++;
                        stopTrying[0] = true;
                        moreThanHour = true;
                        break;
                    }
                    totalTime += runTime;
                }

                if (!moreThanHour) {
                    long avgTime = totalTime / 3;
                    System.out.println("Average runtime for K = " + K + ": " + (avgTime / 1_000_000.0) + " ms");

                    if (hittingSet != null) {
                        System.out.println("Hitting Set Algorithm 1:");
                        for (int i = 0; i < hittingSet.length; i++) {
                            if (hittingSet[i] != 0) {
                                System.out.print(hittingSet[i] + " ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("No Hitting Set of size " + K + " found for algorithm 1!\n");
                    }
                }
            }

            if (!stopTrying[1]) {
                System.out.println("Trying input for Algorithm 2: ");

                long totalTime = 0;
                int[] hittingSet = null;
                boolean moreThanHour = false;

                for (int iterations = 0; iterations < 3; iterations++) {
                    long startTime = System.nanoTime();     //Count the total time for that K
                    hittingSet = HittingSetCriticalPoint(B, K, M, C, N, new Random(), new int[K], new int[M]);
                    long endTime = System.nanoTime();
                    long runTime = endTime - startTime;
                    System.out.println("Run " + (iterations + 1) + " time: " + (runTime / 1_000_000.0) + " ms");
                    if (runTime >= 3_600_000_000_000L) {  //If the algorithm took more than 1 hour to complete never run again and print message
                        System.out.println("More Than 1 hour has passed for Algorithm 2, K = " + K + "\n");
                        stop++;
                        stopTrying[1] = true;
                        moreThanHour = true;
                        break;
                    }
                    totalTime += runTime;
                }

                if (!moreThanHour) {
                    long avgTime = totalTime / 3;
                    System.out.println("Average runtime for K = " + K + ": " + (avgTime / 1_000_000.0) + " ms");

                    if (hittingSet != null) {
                        System.out.println("Hitting Set Algorithm 2:");
                        for (int i = 0; i < hittingSet.length; i++) {
                            if (hittingSet[i] != 0) {
                                System.out.print(hittingSet[i] + " ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("No Hitting Set of size " + K + " found for algorithm 2!\n");
                    }
                }
            }

            if (!stopTrying[2]) {
                System.out.println("Trying input for Algorithm 3: ");

                long totalTime = 0;
                int[] hittingSet = null;
                boolean moreThanHour = false;

                for (int iterations = 0; iterations < 3; iterations++) {
                    long startTime = System.nanoTime();     //Count the total time for that K
                    hittingSet = HittingSetSmallestSet(B, K, M, C, new int[K], 0);
                    long endTime = System.nanoTime();
                    long runTime = endTime - startTime;
                    System.out.println("Run " + (iterations + 1) + " time: " + (runTime / 1_000_000.0) + " ms");
                    if (runTime >= 3_600_000_000_000L) {  //If the algorithm took more than 1 hour to complete never run again and print message
                        System.out.println("More Than 1 hour has passed for Algorithm 3, K = " + K + "\n");
                        stop++;
                        stopTrying[2] = true;
                        moreThanHour = true;
                        break;
                    }
                    totalTime += runTime;
                }

                if (!moreThanHour) {
                    long avgTime = totalTime / 3;
                    System.out.println("Average runtime for K = " + K + ": " + (avgTime / 1_000_000.0) + " ms");

                    if (hittingSet != null) {
                        System.out.println("Hitting Set Algorithm 3:");
                        for (int i = 0; i < hittingSet.length; i++) {
                            if (hittingSet[i] != 0) {
                                System.out.print(hittingSet[i] + " ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("No Hitting Set of size " + K + " found for algorithm 3!\n");
                    }
                }
            }

            if (!stopTrying[3]) {
                System.out.println("Trying input for Algorithm 4: ");

                long totalTime = 0;
                int[] hittingSet = null;
                boolean moreThanHour = false;

                for (int iterations = 0; iterations < 3; iterations++) {
                    long startTime = System.nanoTime();     //Count the total time for that K
                    hittingSet = HittingSetCriticalAndSmallest(B, K, M, C, N, new int[N], 0);
                    long endTime = System.nanoTime();
                    long runTime = endTime - startTime;
                    System.out.println("Run " + (iterations + 1) + " time: " + (runTime / 1_000_000.0) + " ms");
                    if (runTime >= 3_600_000_000_000L) {  //If the algorithm took more than 1 hour to complete never run again and print message
                        System.out.println("More Than 1 hour has passed for Algorithm 4, K = " + K + "\n");
                        stop++;
                        stopTrying[3] = true;
                        moreThanHour = true;
                        break;
                    }
                    totalTime += runTime;
                }

                if (!moreThanHour) {
                    long avgTime = totalTime / 3;
                    System.out.println("Average runtime for K = " + K + ": " + (avgTime / 1_000_000.0) + " ms");

                    if (hittingSet != null) {
                        System.out.println("Hitting Set Algorithm 4:");
                        for (int i = 0; i < hittingSet.length; i++) {
                            if (hittingSet[i] != 0) {
                                System.out.print(hittingSet[i] + " ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("No Hitting Set of size " + K + " found for algorithm 4!\n");
                    }
                }
            }
            K++;
        }
    }

    //For experimental runs of last part of project
    private static void experimentRun2() {
        int N = 125;
        int M = 40;
        int C = 8;
        int K = 15;
        Random rand = new Random();

        //Create M sets B with max C different numbers each one 
        int[][] B = new int[M][C];
        for (int i = 0; i < M; i++) {
            int size = rand.nextInt(C - 6 + 1) + 6;
            boolean[] used = new boolean[N + 1]; // to avoid duplicates
            for (int j = 0; j < size; j++) {
                int value = rand.nextInt(N) + 1; // random number from 1 to N
                if (!used[value]) {
                    B[i][j] = value;
                    used[value] = true;
                }
            }
        }

        printSets(B, M, C);
        System.out.println();
        for (int iterations = 0; iterations < 10; iterations++) {
            System.out.println("Iteration " + (iterations + 1) + ": ");
            runAlgorithms(B, N, M, C, K);
        }

    }

    //O(c^(k+1) * k * m)
    public static int[] HittingSetDefault(int B[][], int k, int m, int c, Random rand, int HS[]) {
        if (m == 0 && k >= 0) {
            return HS;
        }
        if (k <= 0) {
            return null;
        }

        int pick = rand.nextInt(m);                                             //choose a random set

        for (int index = 0; index < c; index++) {                               //check for all the numbers of that random set
            if (B[pick][index] != 0) {                                          //get the next actual (not 0) number in that set
                int[][] newSet = removeSets(B, m, B[pick][index], c);           //create newSet with subtraction of B[pick][index]
                int[] currentHS = createNewHS(HS, B[pick][index], HS.length);   //create current Hitting Set
                int[] isHittingSet = HittingSetDefault(newSet, k - 1, newSet.length, c, rand, currentHS);
                if (isHittingSet != null) {
                    return isHittingSet;
                }
            }
        }
        return null;
    }

    //O((c^2 * m)^k)
    public static int[] HittingSetCriticalPoint(int B[][], int k, int m, int c, int n, Random rand, int HS[], int criticalPoints[]) {
        if (m == 0 && k >= 0) {
            return HS;
        }
        if (k <= 0) {
            return null;
        }

        int[] Times = new int[n];           //In the Times array the ith index holds how many times the number i+1 is found in the sets
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < c; j++) {
                if (B[i][j] != 0) {
                    Times[B[i][j] - 1]++;
                }
            }
        }
        int pick = rand.nextInt(m);                                             //choose a random set
        criticalPoints = mostFoundNumbers(B, c, Times, pick);                          //create most found numbers between sets descending in the array

        for (int i = 0; i < c; i++) {              //for that random set run from the most to the least critical numbers of it
            if (criticalPoints[i] == 0) {
                continue;
            }
            int[][] newSet = removeSets(B, m, criticalPoints[i], c);            //create newSet with subtraction of criticalPoints[i]
            int[] currentHS = createNewHS(HS, criticalPoints[i], HS.length);    //create current Hitting Set
            int[] isHittingSet = HittingSetCriticalPoint(newSet, k - 1, newSet.length, c, n, rand, currentHS, criticalPoints);
            if (isHittingSet != null) {
                return isHittingSet;
            }
        }
        return null;
    }

    //O(c^(k+1) * (m + k))
    public static int[] HittingSetSmallestSet(int B[][], int k, int m, int c, int HS[], int smallestSet) {
        if (m == 0 && k >= 0) {
            return HS;
        }
        if (k <= 0) {
            return null;
        }

        smallestSet = smallestFoundSet(B, m, c);                                //find smallest sized set

        for (int element = 0; element < c; element++) {                     //check all the elements of the smallest set
            if (B[smallestSet][element] != 0) {
                int[][] newSet = removeSets(B, m, B[smallestSet][element], c);           //create newSet with subtraction pf B[smallestSet][element]
                int[] currentHS = createNewHS(HS, B[smallestSet][element], HS.length);   //create current Hitting Set
                int[] isHittingSet = HittingSetSmallestSet(newSet, k - 1, newSet.length, c, currentHS, smallestSet);
                if (isHittingSet != null) {
                    return isHittingSet;
                }
            }
        }

        return null;
    }

    //O(c^(k+1) * (m*c + k))
    public static int[] HittingSetCriticalAndSmallest(int B[][], int k, int m, int c, int n, int HS[], int smallestSet) {
        if (m == 0 && k >= 0) {
            return HS;
        }
        if (k <= 0) {
            return null;
        }

        smallestSet = smallestFoundSet(B, m, c);     //find smallest sized set

        int[] Times = new int[n];
        for (int i = 0; i < m; i++) {               //find how many times each number is found sets
            for (int j = 0; j < c; j++) {
                if (B[i][j] != 0) {
                    Times[B[i][j] - 1]++;
                }
            }
        }

        int[] elements = criticalOfSmallest(B, smallestSet, c, Times); //elements array has the elements of the smallest set in critical order

        for (int index = 0; index < c; index++) {       //check for all the numbers of that set
            if (elements[index] == 0) {
                continue;
            }
            int[][] newSet = removeSets(B, m, elements[index], c);   //create newSet with subtraction pf B[pick][index]
            int[] currentHS = createNewHS(HS, elements[index], HS.length);   //create current Hitting Set
            int[] isHittingSet = HittingSetCriticalAndSmallest(newSet, k - 1, newSet.length, c, n, currentHS, smallestSet);
            if (isHittingSet != null) {
                return isHittingSet;
            }
        }
        return null;
    }

    //O(m*c)
    private static int[][] removeSets(int B[][], int m, int a, int c) {
        boolean R[] = new boolean[m];
        int pl = 0;

        for (int i = 0; i < m; i++) {   //for each set, if element a exists in that set, then set its R[i] to true => will be removed
            for (int j = 0; j < c; j++) {
                if (B[i][j] != 0 && B[i][j] == a && R[i] == false) {
                    R[i] = true;
                    pl++;               //pl holds how many sets will be removed
                    break;
                }
            }
        }

        int[][] newSet = new int[m - pl][c];    //create a newSet for the sets that will not be removed
        int index = 0;
        for (int i = 0; i < m; i++) {           //for each set (not yet removed) check if that set is to be removed
            if (R[i] == false) {                //if its not to be removed, add all of its elements in the newSet array in the next available row
                for (int j = 0; j < c; j++) {
                    newSet[index][j] = B[i][j];
                }
                index++;
            }
        }

        return newSet;
    }

    //O(k)
    private static int[] createNewHS(int HS[], int a, int k) {
        int[] newHS = new int[k];       //create a newHS array of size k (max size of the hitting set)
        boolean done = false;
        for (int i = 0; i < k; i++) {   //check all the elements of the last hitting set
            if (HS[i] == 0 && !done) {  //if the element if the last HS is 0 and a has not been already put in the new HS => done = true and put a in newHS[i]
                done = true;
                newHS[i] = a;
            } else {                    //else just copy paste the previous elements
                newHS[i] = HS[i];
            }
        }
        return newHS;
    }

    //O(c^2)
    private static int[] mostFoundNumbers(int B[][], int c, int[] Times, int pick) {

        int[] max = new int[c];     //Create the array max, max[0] = size of the array 
        int index = 0;

        for (int i = 0; i < c; i++) {       //For each non 0 number in the set, put it in the next index of max array
            if (B[pick][i] != 0) {
                max[index++] = B[pick][i];
            }
        }

        for (int i = 1; i < index - 1; i++) {                   //Sort max[] based on Times[]
            for (int j = i + 1; j < index; j++) {
                if (Times[max[j] - 1] > Times[max[i] - 1]) {
                    int temp = max[i];
                    max[i] = max[j];
                    max[j] = temp;
                }
            }
        }

        while (index < c) {
            max[index++] = 0;
        }

        return max;
    }

    //O(m*c)
    private static int smallestFoundSet(int B[][], int m, int c) {

        int[] count = new int[m];
        for (int i = 0; i < m; i++) {           //find the size of each set, put size of set i in count[i]
            for (int j = 0; j < c; j++) {
                if (B[i][j] != 0) {
                    count[i]++;
                }
            }
        }

        int min = Integer.MAX_VALUE;            //find the smallest set
        int minSetIndex = 0;
        for (int i = 0; i < m; i++) {
            if (min > count[i]) {
                min = count[i];
                minSetIndex = i;
            }
        }

        return minSetIndex;                     //return the index to the smallest set

    }

    //O(c^2)
    private static int[] criticalOfSmallest(int B[][], int smallestSet, int c, int Times[]) {

        int[] elements = new int[c];            //get all non 0 elements of that smallest set
        int size = 0;

        for (int j = 0; j < c; j++) {
            if (B[smallestSet][j] != 0) {
                elements[size++] = B[smallestSet][j];
            }
        }

        for (int r = 0; r < size - 1; r++) {            //sort elements by how many times they appear in all sets
            for (int j = r + 1; j < size; j++) {
                if (Times[elements[j] - 1] > Times[elements[r] - 1]) {
                    int temp = elements[r];
                    elements[r] = elements[j];
                    elements[j] = temp;
                }
            }
        }

        while (size < c) {          //fill the remainder of the set with its zeros
            elements[size++] = 0;
        }
        return elements;
    }

    //O(m*c)
    private static void printSets(int sets[][], int m, int c) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < c; j++) {
                if (sets[i][j] != 0) {
                    System.out.print(sets[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

}
