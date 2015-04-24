package pa_lab3;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Point{
    int x,y,val;
    public Point(int linie, int coloana, int valoare){
        this.x = linie;
        this.y = coloana;
        this.val = valoare;
    }
    @Override
    public String toString(){
        String s = new String();
        s = s.concat("[");
        s = s.concat(String.valueOf(x));
        s = s.concat(", ");
        s = s.concat(String.valueOf(y));
        s = s.concat("]: ");
        s = s.concat(String.valueOf(val));
        return s;
    }
}

public class MatrixPath {
    int[][] map = new int[100][100];
    int[][] s = new int[100][100];
    List<Point> path= new ArrayList<>();
    int n,m;
    public static void run(){
        MatrixPath mp = new MatrixPath();
        mp.readData("/home/smith/Dropbox/ACS/PA/PA_lab3/date2.in");
        mp.findPath();
        mp.display();
    }
    public void findPath(){
        int i,j;
        s[0][0] = map[0][0];
        for(i = 1; i < m; i++){
            if(map[0][i] != -1)
                s[0][i] = s[0][i - 1] + map[0][i];
            else break;
        }
        if(i != n){
            while(i < n){
                s[0][i] = 0;
                i++;
            }
        }
        for(i = 1; i < n; i++){
            if(map[i][0] != -1)
                s[i][0] = s[i - 1][0] + map[i][0];
            else break;
        }
        if(i != n){
            while(i < n){
                s[i][0] = 0;
                i++;
            }
        }
        for(i = 1; i < n; i++){
            for(j = 1; j < m; j++){
                if(map[i][j] != -1)
                    if(s[i-1][j] == 0 && s[i][j-1] == 0) s[i][j] = 0;
                    else s[i][j] = Math.max(s[i-1][j], s[i][j-1]) + map[i][j];
            }
        }
        boolean check = true;
        i = n - 1; j = m - 1;
        path.add(new Point(i,j,s[i][j]));
        while(i > 0 && j > 0){
                if(s[i - 1][j] > s[i][j - 1]){
                    path.add(new Point(i - 1, j, s[i-1][j]));
                    i--;
                } else{
                    path.add(new Point(i, j - 1, s[i][j-1]));
                    j--;
            }
        }
        if(i > 0)
            while(i > 0){
                path.add(new Point(i, 0, s[i][0]));
                i--;
            }
            else if(j > 0){
                while(j > 0){
                    path.add(new Point(0, j, s[0][j]));
                    j--;
                }
            }
        path.add(new Point(0, 0, s[0][0]));
    }
    private void readData ( String filename ) {
		Scanner scanner = null;

		/* you should use try-catch for proper error handling! */
		try {
                        scanner = new Scanner(new File(filename));
                        n = scanner.nextInt();
                        m = scanner.nextInt();
			for (int i = 0; i < n; i++){
                            for(int j = 0; j < m; j++){
                                map[i][j] = scanner.nextInt();
                            }
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* trebuie sa inchidem buffered reader-ul */
			try {
				if (scanner != null) scanner.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
    
    public void display(){
        int i, j;
        for(i = 0; i < n; i++){
            for(j = 0; j < m; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(i = 0; i < n; i++){
            for(j = 0; j < m; j++){
                System.out.print(s[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(path);
    }
}
