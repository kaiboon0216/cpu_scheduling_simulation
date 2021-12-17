import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    private static ArrayList<Process> processList = new ArrayList<>();
    private static int arrival,burst,type,priority;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String args[]){
        welcomeMessage();
        System.out.print("Please choose the type of Process Scheduling :");
        type = scan.nextInt();
        if(type > 0 && type < 4){
            System.out.print("Enter number of process (3-10): ");
            int processNum = scan.nextInt();
            for(int i = 0; i < processNum;i++){
                priority = 0;
                System.out.print("Enter the arrival time of process " + (i) + ":");
                arrival = scan.nextInt();
                System.out.print("Enter the burst time of process " + (i) + ":");
                burst = scan.nextInt();
                if(type == 2 || type == 3){
                    System.out.print("Enter the priority of process " + (i) + ":");
                    priority = scan.nextInt();
                }
                processList.add(new Process(i,arrival,burst,priority));
            }
            switch(type){
                case 1:
                    RRQ3_PS(processList,processNum); //Round robin with quantum 3
                    break;
                case 2:
                    NPSJF_PS(processList,processNum); //Non Preemptive SJF Process Scheduling
                    break;
                case 3:
                    NPP_PS(processList,processNum); //Non Preemptive Priority Process Scheduling
                    break;
                default:
                    System.out.println("default");
            }
        }else{
            System.out.println("Invalid Input");
        }
    }

    public static void welcomeMessage(){
        System.out.println("+----------------------------------------------+");
        System.out.println("| Welcome to OS Process Scheduling Simulator!  |");
        System.out.println("+----------------------------------------------+");
        System.out.println("| 1.Round Robin with Quantum 3                 |");
        System.out.println("| 2.Non Preemptive SJF                         |");
        System.out.println("| 3.Non Preemptive Priority                    |");
        System.out.println("+----------------------------------------------+");
    }

    public static void NPSJF_PS(ArrayList<Process> processList,int processNum){
        NPSJF npsjf = new NPSJF(processList);
        double totalTurnAround = 0;
        double totalWaiting = 0;
        System.out.println(" ");
        System.out.println("Calculation: ");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println("|  Process  |  Arrival Time  |  Burst Time  |  Priority  |  Finish Time  |  TurnAround Time  |  WaitingTime  |");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        for(int i = 0; i < npsjf.getFinishedProcess().size(); i++){
            totalTurnAround += npsjf.getFinishedProcess().get(i).getFinishTime()-npsjf.getFinishedProcess().get(i).getArrivalTime();
            totalWaiting += npsjf.getFinishedProcess().get(i).getFinishTime()-npsjf.getFinishedProcess().get(i).getArrivalTime()-npsjf.getFinishedProcess().get(i).getBurstTime();
            System.out.printf("|     P%d    ",npsjf.getFinishedProcess().get(i).getPid());
            System.out.printf("|    %10d  ",npsjf.getFinishedProcess().get(i).getArrivalTime());
            System.out.printf("|  %10d  ",npsjf.getFinishedProcess().get(i).getBurstTime());
            System.out.printf("|  %8d  ",npsjf.getFinishedProcess().get(i).getPriority());
            System.out.printf("|  %11d  ",npsjf.getFinishedProcess().get(i).getFinishTime());
            System.out.printf("|  %15d  ",(npsjf.getFinishedProcess().get(i).getFinishTime()-npsjf.getFinishedProcess().get(i).getArrivalTime()));
            System.out.printf("|  %11d  ",(npsjf.getFinishedProcess().get(i).getFinishTime()-npsjf.getFinishedProcess().get(i).getArrivalTime()-npsjf.getFinishedProcess().get(i).getBurstTime()));
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                 Total                                  |");
        System.out.printf("    %12f   ",totalTurnAround);
        System.out.printf("|   %10f  |",totalWaiting);
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                Average                                 |");
        System.out.printf("    %12f   ",(totalTurnAround/processNum));
        System.out.printf("|   %10f  |",(totalWaiting/processNum));
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println("Gantt Chart: ");

        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<npsjf.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
            
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end

        for(int j=0; j<npsjf.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print("|");
            }
            System.out.printf("   P%d   |",npsjf.getFinishedProcess().get(j).getPid());
            }
        System.out.println("");
        
        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<npsjf.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
            
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end
        
        for(int j=0; j<npsjf.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print(npsjf.getFinishedProcess().get(j).getArrivalTime());
            }
            System.out.printf("   %6d",npsjf.getFinishedProcess().get(j).getFinishTime());
            }
        System.out.println("");

        

    }

    public static void NPP_PS(ArrayList<Process> processList,int processNum){
        NPP npp = new NPP(processList);
        double totalTurnAround = 0;
        double totalWaiting = 0;
        System.out.println(" ");
        System.out.println("Calculation: ");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println("|  Process  |  Arrival Time  |  Burst Time  |  Priority  |  Finish Time  |  TurnAround Time  |  WaitingTime  |");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        for(int i=0; i < npp.getFinishedProcess().size(); i++){
            totalTurnAround += npp.getFinishedProcess().get(i).getFinishTime()-npp.getFinishedProcess().get(i).getArrivalTime();
            totalWaiting += npp.getFinishedProcess().get(i).getFinishTime()-npp.getFinishedProcess().get(i).getArrivalTime()-npp.getFinishedProcess().get(i).getBurstTime();
            System.out.printf("|     P%d    ",npp.getFinishedProcess().get(i).getPid());
            System.out.printf("|    %10d  ",npp.getFinishedProcess().get(i).getArrivalTime());
            System.out.printf("|  %10d  ",npp.getFinishedProcess().get(i).getBurstTime());
            System.out.printf("|  %8d  ",npp.getFinishedProcess().get(i).getPriority());
            System.out.printf("|  %11d  ",npp.getFinishedProcess().get(i).getFinishTime());
            System.out.printf("|  %15d  ",(npp.getFinishedProcess().get(i).getFinishTime()-npp.getFinishedProcess().get(i).getArrivalTime()));
            System.out.printf("|  %11d  ",(npp.getFinishedProcess().get(i).getFinishTime()-npp.getFinishedProcess().get(i).getArrivalTime()-npp.getFinishedProcess().get(i).getBurstTime()));
            System.out.print("|");
            System.out.println("");

        }
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                 Total                                  |");
        System.out.printf("    %12f   ",totalTurnAround);
        System.out.printf("|   %10f  |",totalWaiting);
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                Average                                 |");
        System.out.printf("    %12f   ",(totalTurnAround/processNum));
        System.out.printf("|   %10f  |",(totalWaiting/processNum));
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println(" ");
        System.out.println("Gantt Chart: ");

        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<npp.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
            
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end

        for(int j=0; j<npp.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print("|");
            }
            System.out.printf("   P%d   |",npp.getFinishedProcess().get(j).getPid());
            }
        System.out.println("");

        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<npp.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
            
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end

        for(int j=0; j<npp.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print(npp.getFinishedProcess().get(j).getArrivalTime());
            }
            System.out.printf("   %6d",npp.getFinishedProcess().get(j).getFinishTime());
            }
        System.out.println("");

    }

    public static void RRQ3_PS(ArrayList<Process> processList,int processNum) {
        RRQ3 rrq3 = new RRQ3(processList,3);
        double totalTurnAround = 0;
        double totalWaiting = 0;
        int totalBurstTime = 0;
        System.out.println(" ");
        System.out.println("Calculation: ");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println("|  Process  |  Arrival Time  |  Burst Time  |  Priority  |  Finish Time  |  TurnAround Time  |  WaitingTime  |");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        
        for(int i=0; i < rrq3.getNoDuplicateProcessList().size(); i++){
            totalBurstTime += rrq3.getNoDuplicateProcessList().get(i).getBurstTime();
            totalTurnAround += rrq3.getNoDuplicateProcessList().get(i).getFinishTime()-rrq3.getNoDuplicateProcessList().get(i).getArrivalTime();
            totalWaiting += rrq3.getNoDuplicateProcessList().get(i).getFinishTime()-rrq3.getNoDuplicateProcessList().get(i).getArrivalTime()-rrq3.getNoDuplicateProcessList().get(i).getBurstTime();
            System.out.printf("|     P%d    ",rrq3.getNoDuplicateProcessList().get(i).getPid());
            System.out.printf("|    %10d  ",rrq3.getNoDuplicateProcessList().get(i).getArrivalTime());
            System.out.printf("|  %10d  ",rrq3.getNoDuplicateProcessList().get(i).getBurstTime());
            System.out.printf("|  %8d  ",rrq3.getNoDuplicateProcessList().get(i).getPriority());
            System.out.printf("|  %11d  ",rrq3.getNoDuplicateProcessList().get(i).getFinishTime());
            System.out.printf("|  %15d  ",(rrq3.getNoDuplicateProcessList().get(i).getFinishTime()-rrq3.getNoDuplicateProcessList().get(i).getArrivalTime()));
            System.out.printf("|  %11d  ",(rrq3.getNoDuplicateProcessList().get(i).getFinishTime()-rrq3.getNoDuplicateProcessList().get(i).getArrivalTime()-rrq3.getNoDuplicateProcessList().get(i).getBurstTime()));
            System.out.print("|");
            System.out.println("");

        }
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                 Total                                  |");
        System.out.printf("    %12f   ",totalTurnAround);
        System.out.printf("|   %10f  |",totalWaiting);
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.print("|                                Average                                 |");
        System.out.printf("    %12f   ",(totalTurnAround/processNum));
        System.out.printf("|   %10f  |",(totalWaiting/processNum));
        System.out.println("");
        System.out.println("+-----------+----------------+--------------+------------+---------------+-------------------+---------------+");
        System.out.println(" ");
        System.out.println("Gantt Chart: ");

        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<rrq3.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
            
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end

        for(int j=0; j<rrq3.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print("|");
            }
            System.out.printf("   P%d   |",rrq3.getFinishedProcess().get(j).getPid());
            }
        System.out.println("");

        //Print Gantt chart line
        System.out.print("+");
        for(int m=0; m<rrq3.getFinishedProcess().size(); m++){
            if(m == 0){
                System.out.print("--------");
            }else{
                System.out.print("+--------");
            }
        }
        System.out.print("+");
        System.out.println("");
        //Print Gantt chart line end

        
        for(int j=0; j<rrq3.getFinishedProcess().size(); j++){
            if(j == 0){
                System.out.print(rrq3.getFinishedProcess().get(j).getArrivalTime());
            }
            System.out.printf("   %6d",rrq3.getFinishedProcess().get(j).getFinishTime());
            }
        System.out.println("");        
    }
    
}