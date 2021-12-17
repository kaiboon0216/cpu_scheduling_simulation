import java.util.ArrayList;


public class NPSJF {
    private ArrayList<Process> processList = new ArrayList<Process>();
    private ArrayList<Process> finishedProcess = new ArrayList<>();
    private ArrayList<Process> readyList = new ArrayList<>();
    private int totalBurst;
    private int currentTime;

    public NPSJF(ArrayList<Process> processList){
        this.processList = processList;
        arrangeProcessInOrder();
    }

    public ArrayList<Process> getProcessList(){
        return processList;
    }

    public Process getEachProcess(int i){
        return processList.get(i);
    }

    private void arrangeProcessInOrder(){
        Process temp = null;
        
        //First Round //

        arrangeProcessAt();                     // First round, we can just arrange according to arrival time
        arrangeProcessSameAtDiffBt();          // Then check whether the processes have clashing arrival time, if yes compare the burst time, the lower burst time will get executed
        executeProcess(0);                    // Execute the process
        
        //First Round //


        //Second Round and onwards//
        while(!processList.isEmpty()){
            for(int i=0; i <processList.size();i++){
                if(processList.get(i).getArrivalTime() <= currentTime){     // Then, compare the arrivalTime with the current time
                    readyList.add(processList.get(i));                      // if the arrivalTime is smaller than the current time, meaning it is ready
                }                                                           // and it will be added to the readyList
            }

            for(int m = 0; m < readyList.size();m++){                                           //Since arrival time is not important here, we just arrange
                for(int n = m +1 ; n < readyList.size(); n++){                                  // according the the burst time, the smallest burst time
                    if(readyList.get(m).getBurstTime() > readyList.get(n).getBurstTime()){      // will be in the front
                        temp = readyList.get(m);
                        readyList.set(m, readyList.get(n));
                        readyList.set(n,temp);
                    }
                }
            }
            if (readyList.size() != 0){
                int tempTime = readyList.get(0).getBurstTime();                                     //get the first process's burst timein the readyList
                readyList.get(0).setFinishTime(currentTime + readyList.get(0).getBurstTime());      // execute the process
                currentTime = currentTime + tempTime;                                               // update current time

                finishedProcess.add(readyList.get(0));                                              //add the process into finishedProcess list
                processList.remove(readyList.get(0));                                               // remove the process from the processList
                readyList.clear();                                                                  // clear the readyList, so that there wouldn't have duplicated
            }
            else{
                currentTime = currentTime + 1;
            }
        }
        //Second Round and onwards//
    }

    public ArrayList<Process> getFinishedProcess() {
        return finishedProcess;
    }

    private void arrangeProcessSameAtDiffBt(){

        for(int m = 0; m < processList.size();m++){
            for(int j = m +1 ; j < processList.size(); j++){
                if(processList.get(0).getArrivalTime() == processList.get(j).getArrivalTime() && processList.get(0).getBurstTime() >processList.get(j).getBurstTime()){
                    Process temp = null;
                    temp = processList.get(m);
                    processList.set(0, processList.get(j));
                    processList.set(j,temp);
                }
            }
        }
    }

    private void arrangeProcessAt(){
        for(int i = 0; i < processList.size();i++){
            totalBurst += processList.get(i).getBurstTime();
            for(int j = i +1 ; j < processList.size(); j++){
                if(processList.get(i).getArrivalTime() > processList.get(j).getArrivalTime()){
                    Process temp = null;
                    temp = processList.get(i);
                    processList.set(i, processList.get(j));
                    processList.set(j,temp);
                }
            }
        }
    }

    private void executeProcess(int i){
        processList.get(i).setFinishTime(processList.get(i).getBurstTime()+ processList.get(i).getArrivalTime());
        finishedProcess.add(processList.get(i));
        currentTime = processList.get(i).getFinishTime();
        processList.remove(i);
    }


}

