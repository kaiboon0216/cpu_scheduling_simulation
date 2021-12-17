import java.util.ArrayList;
import java.util.Iterator;

public class RRQ3{
    private ArrayList<Process> processList = new ArrayList<>();
    private ArrayList<Process> finishedProcess = new ArrayList<>();
    private ArrayList<Process> readyList = new ArrayList<>();
    private ArrayList<Process> noDuplicateProcessList = new ArrayList<>();
    private ArrayList<Process> burstTimeList = new ArrayList<>();
    
    private int quantum;
    private int currentTime;


    public RRQ3(ArrayList<Process> processLists,int quantum){
        this.processList = processLists;
        this.quantum = quantum;
        arrangeProcessInOrder();
        removeDuplicate();
        setbackBurstTime();
    }

    public ArrayList<Process> getProcessList(){
        return processList;
    }

    public ArrayList<Process> getburstTimeList(){
        return burstTimeList;
    }

    private void arrangeProcessInOrder(){
        
        Iterator<Process> iterator = processList.iterator();
       
        while(iterator.hasNext()){
            Process p = iterator.next();
            Process newP = new Process(p.getPid(),p.getArrivalTime(),p.getBurstTime(),p.getPriority());
            burstTimeList.add(newP);
        }

        arrangeProcessAt();
        readyList.add(processList.get(0));
        currentTime = readyList.get(0).getArrivalTime();
        while (!processList.isEmpty()){
            if (readyList.size() > 0){
                if(readyList.get(0).getBurstTime() < quantum){
                    processList.remove(readyList.get(0));
                    currentTime = currentTime + readyList.get(0).getBurstTime();
                    readyList.get(0).setBurstTime(0);
                    readyList.get(0).setFinishTime(currentTime);
                    noDuplicateProcessList.add(readyList.get(0));
                    finishedProcess.add(new Process(readyList.get(0).getPid(),readyList.get(0).getArrivalTime(),readyList.get(0).getBurstTime(),1,readyList.get(0).getFinishTime()));
                    readyList.remove(0);

                    for(int i = 0 ; i <processList.size();i++){
                        if(processList.get(i).getArrivalTime() <= currentTime && readyList.contains(processList.get(i))== false){
                            readyList.add(processList.get(i));
                        }
                    }
                }
                else if(readyList.get(0).getBurstTime() > quantum){
                    currentTime = currentTime + quantum;
                    readyList.get(0).setFinishTime(currentTime);
                    readyList.get(0).setBurstTime(readyList.get(0).getBurstTime()- quantum);
                    for(int i = 0 ; i <processList.size();i++){
                        if(processList.get(i).getArrivalTime() <= currentTime && readyList.contains(processList.get(i))== false){
                            readyList.add(processList.get(i));
                        }
                    }
                    noDuplicateProcessList.add(readyList.get(0));
                    finishedProcess.add(new Process(readyList.get(0).getPid(),readyList.get(0).getArrivalTime(),readyList.get(0).getBurstTime(),1,readyList.get(0).getFinishTime()));
                    readyList.add(readyList.remove(0));;
                }
                else{
                    processList.remove(readyList.get(0));
                    currentTime = currentTime + quantum;
                    readyList.get(0).setBurstTime(0);
                    readyList.get(0).setFinishTime(currentTime);
                    noDuplicateProcessList.add(readyList.get(0));
                    finishedProcess.add(new Process(readyList.get(0).getPid(),readyList.get(0).getArrivalTime(),readyList.get(0).getBurstTime(),1,readyList.get(0).getFinishTime()));
                    for(int i = 0 ; i <processList.size();i++){
                        if(processList.get(i).getArrivalTime() <= currentTime && readyList.contains(processList.get(i))== false){
                            readyList.add(processList.get(i));
                        }
                    }
                    readyList.remove(0);
                }
            }
            else{
                currentTime += 1;
                for(int i = 0 ; i <processList.size();i++){
                    if(processList.get(i).getArrivalTime() <= currentTime && readyList.contains(processList.get(i))== false){
                        readyList.add(processList.get(i));
                    }
                }
            }
        }
        
    }

    public ArrayList<Process> getFinishedProcess() {
        return finishedProcess;
    }

    public void removeDuplicate(){
        ArrayList<Process> temp = new ArrayList<>();

        for (Process i : noDuplicateProcessList){
            if(!temp.contains(i)){
                temp.add(i);
            }
        }

        noDuplicateProcessList = temp;
    }

    public ArrayList<Process> getNoDuplicateProcessList() {
        return noDuplicateProcessList;
    }

    private void arrangeProcessAt(){
        for(int i = 0; i < processList.size();i++){
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

    private void setbackBurstTime(){
        for(int i = 0 ; i < noDuplicateProcessList.size();i++){
            for(int j = 0 ; j < burstTimeList.size();j++){
                if(noDuplicateProcessList.get(i).getPid() == burstTimeList.get(j).getPid()){
                    int tempBurst = burstTimeList.get(j).getBurstTime();
                    noDuplicateProcessList.get(i).setBurstTime(tempBurst);
                }
            }
            
        }
    }
}

