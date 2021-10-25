import java.util.*;
import java.io.*;
/**
 * The "Yoda Calculator"
 * Latest update: October 25, 2021
 * @author Abby Bock
 * @author Vitaly Ford
 */
public class Main {
    public static void main(String[] args) throws Exception {

        /**
         * This program will run from the file input or from console. 
         */
        // Scanner sc = new Scanner(new File("Calculator/src/testInput.txt"));

        Scanner sc = new Scanner(System.in);
        System.out.println("WELCOME TO THE CALCULATOR!! ");

        //will continue to take in equations until user chooses to exit
        while (true) {

            System.out.println("Input your equation or enter q to quit");
            String line = sc.nextLine();
            if (line.equals("q")) {
                System.out.println("Goodbye");
                break;
            }

            ArrayList<String> output = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            String num = "";

            //will loop through every character in the line of input
            for (int i = 0; i < line.length(); i++) {
                String c = line.substring(i, i + 1);
                if (c.equals(" ")) {
                    i++;
                    c = line.substring(i, i + 1);
                }

               /**try-catch to see if the character is an int
                * if not, then will add completed number to output 
                * and will sort any operators to polish notation in output
                * using a stack
                */

                try {
                    Integer.parseInt(c);
                    num += c;

                } catch (NumberFormatException e) {
                    if (!num.equals(""))
                        output.add(num);
                    if (stack.isEmpty()) {
                        stack.push(c);
                    } else {
                        while (!stack.isEmpty() && opValue(stack.peek()) >= opValue(c) && !c.equals("(")
                                && !c.equals(")")) {
                            output.add(stack.pop());
                        }
                        if (c.equals("(")) {
                            stack.push(c);
                        } else if (c.equals(")")) {
                            while (!stack.peek().equals("(")) {
                                output.add(stack.pop());
                            }
                            stack.pop();
                        } else {
                            stack.push(c);
                        }
                    }
                    num = "";
                }
            }
            output.add(num);
            while (!stack.isEmpty()) {
                output.add(stack.pop());
            }

            /**
             * loops through output and completes the arithmetic 
             * of the given equation
             */
            for (int i = 0; i < output.size(); i++) {
                String c = output.get(i);
                try {
                    Integer.parseInt(c);
                    stack.push(c);
                } catch (NumberFormatException e) {
                    String ans = applyOp(Double.parseDouble(stack.pop()), Double.parseDouble(stack.pop()), c.charAt(0));
                    stack.push(ans);
                }
            }
            System.out.println(stack.pop());
        }
    }

    /**
     * This method performs the basic arithmetic of two numbers at a time. 
     * @param num1 - most recent number 
     * @param num2 - last number before num 1
     * @param op   - the operator
     * @return the string of the answer
     */
    public static String applyOp(double num1, double num2, char op) {
        double ans = 0;
        switch (op) {
        case '+':
            ans = num1 + num2;
            return "" + ans;
        case '-':
            ans = num2 - num1;
            return "" + ans;
        case '*':
            ans = num1 * num2;
            return "" + ans;
        case '/':
            ans = num2 / num1;
            return "" + ans;
        default:
            return " invalid ";
        }
    }

    /**
     * This method clarifies the ranking of arithmetic operators
     * @param op - the operator being clarified
     * @return an int value for easy comparison
     */
    public static int opValue(String op) {
        if (op.equals("+") || op.equals("-")) {
            return 1;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        }
        return 0;
    }
}
