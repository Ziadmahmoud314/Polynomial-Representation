/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polynomiallinkedlist;

import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
class Node {

    int coefficient;
    int power;
    Node next;

    public Node(int coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
        this.next = null;
    }
}

class PolynomialLinkedList {

    Node head;
    //constrector 

    public PolynomialLinkedList() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;

    }

    // Method to insert a new term in the polynomial
    public void insertTerm(int coefficient, int power) {
        // Create a new node with the given coefficient and power
        Node newNode = new Node(coefficient, power);
        // If the list is empty or the new term has the highest power, insert at the beginning       
        if (head == null || head.power < power) {
            //at new node in beginning
            newNode.next = head;
            head = newNode;
        } else {
            // Start from the head of the list
            Node p = head;
            /*Traverse the list to find the insertion point where the new term's 
            power is less than the next term's power*/
            while (p.next != null && p.next.power > power) {
                p = p.next;
            }
            //insert at node in correct position in the list
            newNode.next = p.next;
            p.next = newNode;
        }

    }

    public double evaluate(int x) {
        // Initialize the evaluation result to 0
        double eva = 0;
        // Start with the head of the linked list
        Node p = head;
        // move on each node in the linked list
        while (p != null) {
            // Calculate the term's value and add it to the evaluation result
            eva += p.coefficient * Math.pow(x, p.power);
            // Move to the next node in the list
            p = p.next;
        }
        // Return the final evaluated result
        return eva;

    }

    // Method to add two polynomials
    public static PolynomialLinkedList addPolynomials(PolynomialLinkedList poly1, PolynomialLinkedList poly2) {
        //Runing reference for traversing the two input polynomials
        Node p1 = poly1.head, p2 = poly2.head;
        // The resulting polynomial linked list
        PolynomialLinkedList result = new PolynomialLinkedList();
        // Loop until both polynomials are null
        while (p1 != null || p2 != null) {
            if (p1 == null) {
                //if p1 is empty insert coefficient and power 
                result.insertTerm(p2.coefficient, p2.power);
                //then move next node 
                p2 = p2.next;
            } else if (p2 == null) {
                //if p2 is empty insert coefficient and power 
                result.insertTerm(p1.coefficient, p1.power);
                //then move next node 
                p1 = p1.next;
            } else if (p1.power == p2.power) {
                // If powers are equal, add coefficients and insert the sum if it's not zero   
                int sum = p1.coefficient + p2.coefficient;
                if (sum != 0) {
                    result.insertTerm(sum, p1.power);
                }
                // Move both pointers forward
                p1 = p1.next;
                p2 = p2.next;
            } else if (p1.power > p2.power) {
                //If the power p1 is greater, then there is nothing else like it insert in result
                result.insertTerm(p1.coefficient, p1.power);
                //then move next node 
                p1 = p1.next;
            } else {
                //If the power p2 is greater, then there is nothing else like it insert in result
                result.insertTerm(p2.coefficient, p2.power);
                //then move next node 
                p2 = p2.next;
            }
        }

        return result;
    }

    public static PolynomialLinkedList subtractPolynomials(PolynomialLinkedList poly1, PolynomialLinkedList poly2) {
        //Runing reference for traversing the two input polynomials
        Node p1 = poly1.head, p2 = poly2.head;
        // The resulting polynomial linked list
        PolynomialLinkedList result = new PolynomialLinkedList();
        // Loop until both polynomials are null
        while (p1 != null || p2 != null) {
            if (p1 == null) {
                //if p1 is empty insert coefficient and power But in the negative because this is the second equation
                result.insertTerm(-p2.coefficient, p2.power);
                p2 = p2.next;
            } else if (p2 == null) {
                //if p2 is empty insert coefficient and power 
                result.insertTerm(p1.coefficient, p1.power);
                p1 = p1.next;
            } else if (p1.power == p2.power) {
                // If powers are equal, subtract coefficients and insert the difference if it's not zero   

                int difference = p1.coefficient - p2.coefficient;
                if (difference != 0) {
                    result.insertTerm(difference, p1.power);
                }
                // Move both pointers forward 
                p1 = p1.next;
                p2 = p2.next;
            } else if (p1.power > p2.power) {
                //If the power p1 is greater, then there is nothing else like it insert in result
                result.insertTerm(p1.coefficient, p1.power);
                //then move next node 
                p1 = p1.next;
            } else {
                //If the power p2 is greater, then there is nothing else like it insert in resultBut in the negative because this is the second equation
                result.insertTerm(-p2.coefficient, p2.power);
                //then move next node 
                p2 = p2.next;
            }
        }
        return result;
    }

    // Method to multiply two polynomial linked lists
    public static PolynomialLinkedList multiply(PolynomialLinkedList poly1, PolynomialLinkedList poly2) {
        // Create a new polynomial linked list to store the result
        PolynomialLinkedList result = new PolynomialLinkedList();
        // Runing to traverse the first polynomial
        Node p1 = poly1.head;
        Node p2; // Runing to traverse the second polynomial
        // Loop through each term in the first polynomial
        while (p1 != null) {
            // refer head polynomail 2
            p2 = poly2.head;
            // loop through each term in the second polynomial
            while (p2 != null) {
                // Multiply the coefficients of the current terms
                int coeff = p1.coefficient * p2.coefficient;
                // Add the powers of the current terms
                int exp = p1.power + p2.power;
                // Insert the new term into the result polynomial
                result.insertTerm(coeff, exp);
                // Move to the next term in the second polynomial
                p2 = p2.next;
            }
            // Move to the next term in the first polynomial
            p1 = p1.next;
        }
        // Combine terms in the result polynomial that have the same exponent
        result.combineLikeTerms();
        // Return the resulting polynomial
        return result;
    }

    /**/
    // Method to combine terms with the same power
    public void combineLikeTerms() {
        // refer head polynomail that call method

        Node current = head;
        // Loop through each term in the polynomial 
        while (current != null) {
            Node temp = current;
            //stop reach the last node in linkedlist
            while (temp.next != null) {
                //if two node the same power Merge them
                if (temp.next.power == current.power) {
                    current.coefficient += temp.next.coefficient;
                    //Delete the second node that has the same power
                    temp.next = temp.next.next;
                } else {
                    //move the next node 
                    temp = temp.next;
                }
            }
            //move the next node 
            current = current.next;
        }
    }

    // Method to print the polynomial
     public void printPolynomial() {
        Node temp = head; // Start with the head of the linked list.
        while (temp != null) { // Continue looping as long as 'temp' is not pointing to null (end of the list).
            if (temp == head) {// Check if 'temp' is the first node in the list.
                if (temp.power == 0) {// If the power of the term is 0, it's a constant term.
                    System.out.print(temp.coefficient);
                } else if (temp.power == 1) {
                    // If the coefficient is not 1 or -1, print it followed by 'x'.
                    if (temp.coefficient != 1 && temp.coefficient != -1) {
                        System.out.print(temp.coefficient + "x");
                    } else if (temp.coefficient == 1) {
                        System.out.print("x");
                    } else {
                        System.out.print("-x");
                    }
                } else {//if power not equal 0 or 1
                    if (temp.coefficient == 1) {
                        System.out.print("x^" + temp.power);
                    } else if (temp.coefficient == -1) {
                        System.out.print("-x^" + temp.power);
                    } else {
                        System.out.print(temp.coefficient + "x^" + temp.power);
                    }
                }
            } else { // For nodes other than the head
                if (temp.power == 0) {//, check if the term is a constant.
                    System.out.print(Math.abs(temp.coefficient));
                } else {// For non-constant
                    if (temp.power == 1) {
                        if (temp.coefficient != 1 && temp.coefficient != -1) {
                            System.out.print(Math.abs(temp.coefficient) + "x");
                        } else {
                            System.out.print("x");
                        }
                    } else {
                        if (temp.coefficient == 1 || temp.coefficient == -1) {
                            System.out.print("x^" + temp.power);
                        } else {
                            System.out.print(Math.abs(temp.coefficient) + "x^" + temp.power);
                        }
                    }
                }
            }
            // Move to the next node in the list.
            temp = temp.next;
            if (temp != null) {
                if (temp.coefficient > 0) { // If the next coefficient is positive, print ' + '.
                    System.out.print(" + ");
                } else { // If the next coefficient is negative, print ' - '.
                    System.out.print(" - ");
                }
            }

        }
        //new line
        System.out.println("");
    }

}

// Example usage
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PolynomialLinkedList poly1 = new PolynomialLinkedList();
        PolynomialLinkedList poly2 = new PolynomialLinkedList();
        int i = 0;
        char ch;
        do {
            if (i == 0) {
                System.out.println("Choose Operations");
                System.out.println("1. Enter polynomail ");
                System.out.println("2. Add two polynomail ");
                System.out.println("3. Subtract Polynomials");
                System.out.println("4. Multiply two polynomail");
                System.out.println("5. Dispaly polynomail 1");
                System.out.println("6. Display polynomail 2");
                System.out.println("7. Evaluate polynomail 1");
                System.out.println("8. Evaluate polynomail 2");
                i++;
            } else {
                System.out.println("Choose Operations");
                System.out.println("1. add new term  polynomail ");
                System.out.println("2. Add two polynomail ");
                System.out.println("3. Subtract Polynomials");
                System.out.println("4. Multiply two polynomail");
                System.out.println("5. Dispaly polynomail 1");
                System.out.println("6. Display polynomail 2");
                System.out.println("7. Evaluate polynomail 1");
                System.out.println("8. Evaluate polynomail 2");

            }
            int choice = sc.nextInt();
            switch (choice) {

                case 1:
                    char c;
                    System.out.println("Enter number of coefficient and power fuction 1");
                    do {
                        System.out.println("Enter number of coefficient");
                        int x = sc.nextInt();
                        System.out.println("Enter number of power");

                        int x1 = sc.nextInt();
                        if (x != 0) {
                            poly1.insertTerm(x, x1);
                        }
                        System.out.println("Do you want to continue input term to continue input --> 'y'    No-->' n'   \n");

                        c = sc.next().charAt(0);
                    } while (c == 'Y' || c == 'y');
                    poly1.combineLikeTerms();
                    System.out.print("polynomail 1 :");
                    poly1.printPolynomial();
                    char cr;
                    System.out.println("Enter number of coefficient and power fuction 2");

                    do {
                        System.out.println("Enter number of coefficient");
                        int x = sc.nextInt();
                        System.out.println("Enter number of power");
                        int x1 = sc.nextInt();
                        if (x != 0) {

                            poly2.insertTerm(x, x1);
                        }
                        System.out.println("Do you want to continue input term to continue input --> 'y'   No-->' n'    \n");

                        cr = sc.next().charAt(0);
                    } while (cr == 'Y' || cr == 'y');
                    poly2.combineLikeTerms();
                    System.out.print("polynomail 2 :");
                    poly2.printPolynomial();
                    break;
                case 2:
                    PolynomialLinkedList sum = PolynomialLinkedList.addPolynomials(poly1, poly2);
                    sum.combineLikeTerms();
                    System.out.print("Sum: ");
                    sum.printPolynomial();
                    break;
                case 3:
                    PolynomialLinkedList subtract = PolynomialLinkedList.subtractPolynomials(poly1, poly2);
                    subtract.combineLikeTerms();
                    System.out.print("subtract: ");
                    subtract.printPolynomial();

                    break;

                case 4:
                    PolynomialLinkedList mul = PolynomialLinkedList.multiply(poly1, poly2);
                    System.out.print("multiply: ");
                    mul.printPolynomial();
                    break;

                case 5:
                    System.out.print("polynomail 1");
                    poly1.combineLikeTerms();
                    poly1.printPolynomial();

                    break;

                case 6:
                    System.out.print("polynomail 2");
                    poly2.combineLikeTerms();
                    poly2.printPolynomial();

                    break;
                case 7:
                    System.out.println("Enter number of x");
                    int x = sc.nextInt();
                    System.out.print("Evalute fuction 1 :");
                    System.out.println(poly1.evaluate(x));

                    break;
                case 8:
                    System.out.println("Enter number of x");
                    int x1 = sc.nextInt();
                    System.out.print("Evalute fuction  2 : ");
                    System.out.println(poly2.evaluate(x1));
                    break;

                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            System.out.println("Do you want to continue *operation* to continue input --> 'y'  No-->' n'   \n");
            ch = sc.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');

    }
}
