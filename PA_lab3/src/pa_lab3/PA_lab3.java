package pa_lab3;

import java.util.Scanner;


public class PA_lab3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nr problema: ");
        int n = scan.nextInt();
        switch(n){
            case 1:
                HomeworkPlanner.run();
                break;
            case 2:
                MatrixPath.run();
                break;
            case 3:
                Bill.run();
                break;
        }
    }

}
