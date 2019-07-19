/*
Alex Beaty
Coursework AI Methods COB107
Last Update - 10/12/2017

Write a program that takes A, B, and C as input and generates the set of all possible states that can be reached from the start state (0, 0, 0).
*/

//Import scanner for console and arraylist
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<ArrayList<Integer>> queue = new ArrayList<>();
    public static ArrayList<Integer> jugCapacities = new ArrayList<>();

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        //Get input from user for jug capacities, ParseInt input and populate jugCapacities Arraylist
        System.out.println("Enter Jug Input:         eg. 2,3 or 4,5,1");
        String input = sc.nextLine();
        String[] inputSplit = input.split(",");
        for(int i = 0; i < inputSplit.length; i++){
            //Take string input to integer input
            jugCapacities.add( Integer.parseInt(inputSplit[i]));
        }
        //Make root state same size as jugCapacities e.g(0,0,0)
        ArrayList<Integer> root = new ArrayList<>();
        for(int i:jugCapacities){
            root.add(0);
        }
        System.out.println("Input is: " + jugCapacities + " Start state is: " + root);

        //Add the root state to start of queue
        queue.add(root);
        //Starting with the root state, recursively take the next state from the queue, expand it's nodes and the new unique states to the queue
        makeStates();
        //Print all possible states
        printStates();
    }

    public static void makeStates(){
        //Loop through states in queue
        for(int j = 0; j < queue.size(); j++) {
            ArrayList<Integer> state = queue.get(j);
            System.out.println("");
            System.out.println("State: " + state);
            System.out.println("Unique Children States:");
            System.out.println("");

            //iterate over each jug in current state. Do empty jugs and fill jugs
            for (int i = 0; i < state.size(); i++) {
                int jugValue = state.get(i);

                //Empty Jugs
                ArrayList<Integer> newEmptyState = (ArrayList<Integer>) state.clone();
                if (jugValue != 0) {
                    //Jug is not empty, empty it
                    newEmptyState.set(i, 0);

                    //Check if newState is already in queue, if not, add it to queue
                    boolean inQueue = false;
                    for (int z = 0; z < queue.size(); z++) {
                        if (queue.get(z).equals(newEmptyState)) {
                            inQueue = true;
                        }
                    }
                    if (!inQueue) {
                        System.out.println("    " + newEmptyState + " * ");
                        queue.add(newEmptyState);
                    }
                    else {
                        //System.out.println("    " + newEmptyState);
                    }
                }
                else {
                    //System.out.println("    " + newEmptyState);
                }

                //Fill Jugs
                ArrayList<Integer> newFillState = (ArrayList<Integer>) state.clone();
                if (jugValue != jugCapacities.get(i)) {
                    //Jug is not full, fill it
                    newFillState.set(i, jugCapacities.get(i));

                    //Check if newState is already in queue, if not, add it to queue
                    boolean inQueue = false;
                    for (int z = 0; z < queue.size(); z++) {
                        if (queue.get(z).equals(newFillState)) {
                            inQueue = true;
                        }
                    }
                    if (!inQueue) {
                        System.out.println("    " + newFillState + " * ");
                        queue.add(newFillState);
                    }
                    else {
                        //System.out.println("    " + newFillState);
                    }
                }
                else {
                    //System.out.println("    " + newFillState);
                }
            }

            //Try all combinations of moves between jugs, exclude trying to move between the same jug eg jugA - jugA
            for (int x = 0; x < state.size(); x++) {
                for (int y = 0; y < state.size(); y++) {
                    if (x != y) {
                        ArrayList<Integer> newMoveState = (ArrayList<Integer>) state.clone();
                        //Get values of jugs in current state
                        int jugOut = state.get(x);
                        int jugIn = state.get(y);
                        //Get value, if any, of 'free space' in receiving jug
                        int jugSpace = (jugCapacities.get(y) - jugIn);
                        //Only do if receiving jug has some space in it
                        if (jugSpace != 0) {
                            //Receiving jug can take ALL of giving jug
                            if (jugSpace >= jugOut) {
                                jugIn += jugOut;
                                jugOut = 0;
                            }
                            //Receiving jug can take SOME of giving jug
                            else {
                                jugIn += jugSpace;
                                jugOut -= jugSpace;
                            }
                            //Set values of new state
                            newMoveState.set(x, jugOut);
                            newMoveState.set(y, jugIn);

                            //Check if newState is already in queue, if not, add it to queue
                            boolean inQueue = false;
                            for (int z = 0; z < queue.size(); z++) {
                                if (queue.get(z).equals(newMoveState)) {
                                    inQueue = true;
                                }
                            }
                            if (!inQueue) {
                                System.out.println("    " + newMoveState + " * ");
                                queue.add(newMoveState);
                            }
                            else {
                                //System.out.println("    " + newMoveState);
                            }
                        }
                        else {
                            //System.out.println("    " + newMoveState);
                        }
                    }
                }
            }
        }
    }

    public static void printStates(){
        System.out.println("");
        System.out.println("List of possible states:");
        for(ArrayList<Integer> state: queue){
            System.out.println(state);

        }
        System.out.println("There are: " + queue.size() + " possible states.");
    }
}


