// Amogh Anoo ASA210011

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a file name for the inventory file: ");
        String invFile = input.next();
        System.out.print("Enter a name for the transaction file: ");
        String tFile = input.next();
        BinTree<?> tree = new BinTree<>(invFile);
        Scanner scnr = null;
        // Read and process transactions from the specified transaction file.
        try{
            scnr = new Scanner(new File(tFile));
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }

        while (scnr.hasNext()) {
            String opcode = scnr.next();
            processOperation(opcode, scnr, tree);
        }
        tree.toString(); // Perform an inorder traversal of the tree (but the result is not used or displayed).
    }

    // Process a transaction based on the opcode.
    private static void processOperation(String opcode, Scanner scnr, BinTree<?> tree) {
        if (Objects.equals(opcode, "add")) {
            addDVD(scnr, tree);
        } else if (Objects.equals(opcode, "remove")) {
            removeDVD(scnr, tree);
        } else if (Objects.equals(opcode, "rent")) {
            rentDVD(scnr, tree);
        } else {
            returnDVD(scnr, tree);
        }
    }

    // Process the "add" operation to add DVDs to the inventory.
    private static void addDVD(Scanner scnr, BinTree<?> tree) {
        String title = scnr.nextLine();
        String[] hold = title.split(",");
        title = hold[0].replaceAll("\"", "");
        title = title.substring(0, 0) + title.substring(1);
        int numToUse = Integer.parseInt(hold[1]);
        DVD<?> holdDVD = tree.findNode(title);

        if (holdDVD != null) {
            // If the DVD exists, update the available count.
            holdDVD.setAvailable(holdDVD.getAvailable() + numToUse);
        } else {
            // If the DVD doesn't exist, insert it into the tree.
            tree.insertHelper(title, numToUse);
        }
    }

    // Process the "remove" operation to remove DVDs from the inventory.
    private static void removeDVD(Scanner scnr, BinTree<?> tree) {
        String title = scnr.nextLine();
        String[] hold = title.split(",");
        title = hold[0].replaceAll("\"", "");
        title = title.substring(0, 0) + title.substring(1);
        int numToUse = Integer.parseInt(hold[1]);
        DVD<?> holdDVD = tree.findNode(title);

        // Decrease the available count of the DVD.
        holdDVD.setAvailable(holdDVD.getAvailable() - numToUse);

        if (holdDVD.getAvailable() == 0 && holdDVD.getRented() == 0) {
            // If available and rented counts are both zero, remove the DVD from the tree.
            Node<?> TF = tree.BSTRemoveNode(holdDVD.getTitle());
        }
    }

    // Process the "rent" operation to rent a DVD.
    private static void rentDVD(Scanner scnr, BinTree<?> tree) {
        String title = scnr.nextLine();
        title = title.replaceAll("\"", "");
        title = title.substring(0, 0) + title.substring(1);
        DVD<?> hold = tree.findNode(title);

        // Decrease available count and increase rented count for the rented DVD.
        hold.setAvailable(hold.getAvailable() - 1);
        hold.setRented(hold.getRented() + 1);
    }

    // Process the "return" operation to return a rented DVD.
    private static void returnDVD(Scanner scnr, BinTree<?> tree) {
        String temp = scnr.nextLine();
        temp = temp.replaceAll("\"", "");
        String title = temp.substring(0, 0) + temp.substring(1);
        DVD<?> hold = tree.findNode(title);

        // Increase available count and decrease rented count for the returned DVD.
        hold.setAvailable(hold.getAvailable() + 1);
        hold.setRented(hold.getRented() - 1);
    }
}
