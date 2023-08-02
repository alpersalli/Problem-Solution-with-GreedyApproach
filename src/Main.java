import java.io.*;
import java.util.ArrayList;

public class Main {
    public static int Greedy(int n, int p, int c, ArrayList<Integer> salaries, ArrayList<Integer> yearDemands){

        int totalCost = 0;

        for(int i = 0 ; i < n ; i++){
            int demand = yearDemands.get(i);
            if (i==0 && (yearDemands.get(i)-p) > 0){
                totalCost = (yearDemands.get(i)-p)*c;
            }

            if (i+1 != n){
                int salaryCalc= yearDemands.get(i+1)-p;

                if (salaryCalc>0) {
                    int howMuchSalary = salaries.get(salaryCalc-1);

                    int howMuchHireCoach = salaryCalc * c;
                    if (howMuchSalary >= howMuchHireCoach) {

                        totalCost = totalCost + howMuchHireCoach;
                    }
                    else if(yearDemands.get(i)<=p){

                        totalCost = totalCost + howMuchHireCoach;

                    }
                    else if(yearDemands.get(i) < yearDemands.get(i+1)){

                        int howManyNeeded = yearDemands.get(i+1) - yearDemands.get(i);
                        int howManyNeeded2 = yearDemands.get(i) - p;
                        totalCost= totalCost+howManyNeeded*c;
                        howMuchSalary = salaries.get(howManyNeeded2-1);
                        totalCost = totalCost + howMuchSalary;

                    }
                    else {


                        totalCost = totalCost + howMuchSalary;
                    }
                }
            }

        }

        return totalCost;
    }

    public static void main(String[] args) {

        int n=10, p=5, c=5;

        File file = new File("players_salary.txt");
        ArrayList<Integer> salaries = new ArrayList<>();
        ArrayList<Integer> yearDemands = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("j")) {
                    String[] howMuchSalary = line.split("\\s+");
                    salaries.add(Integer.parseInt(howMuchSalary[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file2 = new File("yearly_player_demand.txt"); // Replace with the actual file path
        try (FileInputStream fis = new FileInputStream(file2);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line2;
            while ((line2 = br.readLine()) != null) {
                if (!line2.startsWith("Y")) {
                    String[] howMuchDemand = line2.split("\\s+");
                    yearDemands.add(Integer.parseInt(howMuchDemand[1]));

                    if(yearDemands.size() == n) break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int result =Greedy(n,p,c,salaries,yearDemands);
        System.out.println("Greedy Result: "+ result);


    }
}