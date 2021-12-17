
public class Process{
    private int arrivalTime;
    private int finishTime;
    private int burstTime;
    private int pid;

    private int turnaround;
    private int waitingTime;

    private double avgTurnaround;
    private double avgWaitingTime;

    private int priority;

    public Process(int pid,int arrivalTime,int burstTime, int priority){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public Process(int pid,int arrivalTime,int burstTime, int priority,int finishTime){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.finishTime = finishTime;
    }

    // ----------Accessor of the class-------------- //
    
    public int getPid() {
        return pid;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
    public int getTurnaround() {
        return turnaround;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
    

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getFinishTime() {
        return finishTime;
    }
    
    public int getBurstTime(){
        return burstTime;
    }

    public double getAvgTurnaround() {
        return avgTurnaround;
    }

    public double getAvgWaitingTime() {   
        return avgWaitingTime;
    }

    public int getPriority() {
        return priority;
    }

    // ----------Accessor of the class-------------- //


    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int calTurnaround() {
        turnaround = finishTime - arrivalTime;
        return turnaround;
    }

    public int calWaitingTime() {
        waitingTime = turnaround - burstTime;
        return waitingTime;
    }

    public String toString(){
        return "Process" + pid;
    }
    
}