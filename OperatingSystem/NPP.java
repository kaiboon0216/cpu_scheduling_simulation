import java.util.ArrayList;

public class NPP{
    private ArrayList<Process> processList = new ArrayList<Process>();
    private ArrayList<Process> finishedProcess = new ArrayList<>();
    private ArrayList<Process> readyList = new ArrayList<>();
    private int totalBurst;
    private int currentTime;

    public NPP(ArrayList<Process> processList) {
        this.processList = processList;
        arrangeProcessInOrder();
    }

    public ArrayList<Process> getProcessList() {
        return processList;
    }

    public Process getEachProcess(int i){
        return processList.get(i);
    }

    public ArrayList<Process> getFinishedProcess() {
        return finishedProcess;
    }

    private void arrangeProcessSameAtDiffPrior(){
        for(int m = 0; m < processList.size();m++){
            for(int n = m+1; n < processList.size();n++){
                if((processList.get(0).getArrivalTime() == processList.get(n).getArrivalTime()) && (processList.get(0).getPriority() > processList.get(n).getPriority())){
                    Process temp = null;
                    temp = processList.get(m);
                    processList.set(0,processList.get(n));
                    processList.set(n,temp);
                }
            }
        }
    }

    private void arrangeProcessAt(){
        for(int i=0;i<processList.size();i++){
            totalBurst += processList.get(i).getBurstTime();
            for(int j=i+1; j<processList.size(); j++){
                if(processList.get(j).getArrivalTime() > processList.get(j).getArrivalTime()){
                    Process temp = null;
                    temp = processList.get(i);
                    processList.set(i,processList.get(j));
                    processList.set(j,temp);
                }
            }
        }
    }

    private void executeProcess(int i){
        processList.get(i).setFinishTime(processList.get(i).getBurstTime()+processList.get(i).getArrivalTime());
        finishedProcess.add(processList.get(i));
        currentTime = processList.get(i).getFinishTime();
        processList.remove(i);
    }

    private void arrangeProcessInOrder(){
        Process temp = null;

        //First Round//
        arrangeProcessAt();                      // First round, we can just arrange according to arrival time
        arrangeProcessSameAtDiffPrior();         // Then check whether the processes have clashing arrival time, if yes compare the priority, the highest priority will get executed
        executeProcess(0);                       // Execute the process
        //First Round//

        //Second Round and onwards//
        while(!processList.isEmpty()){
            for(int i = 0; i < processList.size();i++){                     // Then, compare the arrivalTime with the current time
                if(processList.get(i).getArrivalTime() <= currentTime){     // if the arrivalTime is smaller than the current time, meaning it is ready
                    readyList.add(processList.get(i));                      // and it will be added to the readyList
                }
            }

            for(int m = 0; m < readyList.size();m++){                                           // Since arrival time is not important here, we just arrange
                for(int n = m +1 ; n < readyList.size(); n++){                                  // according the the priority, the highest priority
                    if(readyList.get(m).getPriority() > readyList.get(n).getPriority()){        // will be in the front
                        temp = readyList.get(m);
                        readyList.set(m,readyList.get(n));
                        readyList.set(n,temp);
                    }
                }
            }
            if (readyList.size() != 0){
                int tempTime = readyList.get(0).getBurstTime();                                     // get the first process's burst timein the readyList      
                readyList.get(0).setFinishTime(currentTime+readyList.get(0).getBurstTime());        // execute the process
                currentTime = currentTime + tempTime;                                               // update current time

                finishedProcess.add(readyList.get(0));                                              // add the process into finishedProcess list
                processList.remove(readyList.get(0));                                               // remove the process from the processList
                readyList.clear();
            }
            else{
                currentTime = currentTime + 1;
            }                                                                  // clear the readyList, so that there wouldn't have duplicated
        }                                                                                       // process later on
        //Second Round and onwards//
    
    }
   
}