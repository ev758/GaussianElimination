import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GaussianElimination {

    public static void main(String[] args) {
        //declarations
        double equations[][];
        ArrayList<Integer> scaleFactorVector = new ArrayList<Integer>();
        ArrayList<Integer> indexVector = new ArrayList<Integer>();
        ArrayList<Integer> pivots = new ArrayList<Integer>();
        Scanner input = new Scanner(System.in);
        Scanner inputFile = new Scanner(System.in);
        int scaleFactor = 0;
        int rowPivot = 0;
        int equation = 0;
        int option = 0;
        double valX = 0.0;
        double valY = 0.0;
        double valZ = 0.0;

        //get user input
        System.out.print("Enter the number of equations: ");
        equation = input.nextInt();

        if (equation < 0 || equation > 10) {
            System.out.println("The number of equations need to be from 0 to 10.");
            System.out.println("Exiting");
            System.exit(0);
        }

        equations = new double[equation][4];

        //selects an option of menu
        while (true) {
            System.out.println("1. Enter coefficients");
            System.out.println("2. Enter text file");
            System.out.print("Enter an option: ");
            option = input.nextInt();
            System.out.print("\n");

            if (option == 1) {

                for (int i = 0; i < equation; i++) {
                    System.out.print("Enter the coefficient of x of equation " + (i + 1) + ": ");
                    equations[i][0] = input.nextDouble();
                    System.out.print("\n");

                    System.out.print("Enter the coefficient of y of equation " + (i + 1) + ": ");
                    equations[i][1] = input.nextDouble();
                    System.out.print("\n");

                    System.out.print("Enter the coefficient of z of equation " + (i + 1) + ": ");
                    equations[i][2] = input.nextDouble();
                    System.out.print("\n");

                    System.out.print("Enter the b value of equation " + (i + 1) + ": ");
                    equations[i][3] = input.nextDouble();
                    System.out.print("\n");
                }

                break;

            }
            else if (option == 2) {

                try {
                    int i = 0;
                    System.out.print("Enter text file: ");
                    String file = inputFile.nextLine();
                    File textFile = new File(file);
                    input = new Scanner(textFile);

                    while (input.hasNextLine()) {
                        String textInput[] = input.nextLine().split(" ");

                        equations[i][0] = Double.parseDouble(textInput[0]);
                        equations[i][1] = Double.parseDouble(textInput[1]);
                        equations[i][2] = Double.parseDouble(textInput[2]);
                        equations[i][3] = Double.parseDouble(textInput[3]);

                        i++;
                    }

                }
                catch (FileNotFoundException fileError) {
                    System.out.println("File not found");
                    System.exit(0);
                }
                finally {
                    input.close();
                    inputFile.close();
                    break;
                }
            }
        }

        //gets scale factor vector
        for (int i = 0; i < equations.length; i++) {
            int max = 0;

            for (int j = 0; j < equations[0].length - 1; j++) {
                scaleFactor = (int)Math.abs(equations[i][j]);

                if (max < scaleFactor) {
                    max = scaleFactor;
                }

            }

            scaleFactorVector.add(max);

        }

        //calculates coefficient matrix
        for (int i = 0; i < equations[0].length - 1; i++) {
            ArrayList<Double> ratio = new ArrayList<Double>();
            double max = 0.0;

            display(equations);

            //calculates scaled ratios
            for (int j = 0; j < equations.length; j++) {
                ratio.add((double)Math.abs(equations[j][i] / scaleFactorVector.get(j)));
            }

            System.out.print("Ratios: ");

            //gets pivot row
            for (int j = 0; j < ratio.size(); j++) {

                if (!pivots.contains(j)) {
                    System.out.printf("%.2f" + " ", ratio.get(j));
                }

                if (max < ratio.get(j)) {

                    if (pivots.contains(j)) {
                        continue;
                    }

                    max = ratio.get(j);
                    rowPivot = j;
                }

            }

            System.out.println("\n");
            pivots.add(rowPivot);
            System.out.println("Row " + (rowPivot + 1) + " is pivot.");
            System.out.println("\n");

            indexVector.add(rowPivot);

            //calculates row of coefficient matrix
            for (int j = 0; j < equations.length; j++) {
                double multiplier = equations[j][i] / equations[rowPivot][i];

                if (pivots.contains(j)) {
                    continue;
                }

                for (int j1 = i; j1 < equations[0].length; j1++) {
                    equations[j][j1] = equations[j][j1] - (equations[rowPivot][j1] * multiplier);
                }

            }
        }

        //calculates x, y, and z
        int vector = indexVector.get(indexVector.size() - 1);

        valZ = equations[vector][2];
        valZ = equations[vector][equations[0].length - 1] / valZ;
        indexVector.remove(indexVector.size() - 1);

        vector = indexVector.get(indexVector.size() - 1);

        valY = equations[vector][1];
        indexVector.remove(indexVector.size() - 1);
        valY = (equations[vector][3] - (equations[vector][2] * valZ)) / valY;

        vector = indexVector.get(indexVector.size() - 1);

        valX = equations[vector][0];
        indexVector.remove(indexVector.size() - 1);
        valX = (equations[vector][3] - ((equations[vector][1] * valY) + (equations[vector][2] * valZ))) / valX;

        //output
        System.out.printf("x = %.0f\n", valX);
        System.out.printf("y = %.0f\n", valY);
        System.out.printf("z = %.0f\n", valZ);
    }

    //displays coefficient matrix
    public static void display(double[][] equationsPar) {
        for (int i = 0; i < equationsPar.length; i++) {

            for (int j = 0; j < equationsPar[0].length; j++) {
                System.out.printf("%.0f ", equationsPar[i][j]);
            }

            System.out.print("\n");

        }
    }
}
