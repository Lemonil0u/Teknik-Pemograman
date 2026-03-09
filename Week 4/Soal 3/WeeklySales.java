// *************************************************************
// WeeklySales.java
//
// Sorts the sales staff in descending order by sales.
// ************************************************************

import java.util.Scanner;

public class WeeklySales
{
    public static void main(String[] args)
    {
        /* This one is hard coded, but it is just to demonstrate the sorting of Salesperson objects.
        
        Salesperson[] salesStaff = new Salesperson[10];
        
        salesStaff[0] = new Salesperson("Jane", "Jones", 3000);
        salesStaff[1] = new Salesperson("Daffy", "Duck", 4935);
        salesStaff[2] = new Salesperson("James", "Jones", 3000);
        salesStaff[3] = new Salesperson("Dick", "Walter", 2800);
        salesStaff[4] = new Salesperson("Don", "Trump", 1570);
        salesStaff[5] = new Salesperson("Jane", "Black", 3000);
        salesStaff[6] = new Salesperson("Harry", "Taylor", 7300);
        salesStaff[7] = new Salesperson("Andy", "Adams", 5000);
        salesStaff[8] = new Salesperson("Jim", "Doe", 2850);
        salesStaff[9] = new Salesperson("Walt", "Smith", 3000);
        
        Sorting.insertionSort(salesStaff);
        
        System.out.println ("\nRanking of Sales for the Week\n");
        
        for (Salesperson s : salesStaff)
            System.out.println (s);*/

        Scanner scan = new Scanner(System.in);
        int numStaff;

        System.out.print("How many salespeople do you want to enter? ");
        numStaff = scan.nextInt();
        scan.nextLine(); // Membersihkan buffer setelah nextInt()

        Salesperson[] salesStaff = new Salesperson[numStaff];

        for (int i = 0; i < numStaff; i++)
        {
            System.out.println("\nEnter data for salesperson " + (i + 1));
            
            System.out.print("First Name: ");
            String first = scan.nextLine();
            
            System.out.print("Last Name: ");
            String last = scan.nextLine();
            
            System.out.print("Total Sales: ");
            int sales = scan.nextInt();
            scan.nextLine(); // Membersihkan buffer lagi

            salesStaff[i] = new Salesperson(first, last, sales);
        }

        Sorting.insertionSort(salesStaff);

        System.out.println("\nRanking of Sales for the Week (Descending)\n");
        for (Salesperson s : salesStaff)
            System.out.println(s);

        scan.close();
    }
}