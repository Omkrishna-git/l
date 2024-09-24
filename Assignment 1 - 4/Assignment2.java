import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Assignment2 {

    void fcfs() {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
        }
        int wt[]=new int[n];
        int ct[]=new int[n];
        int tat[]=new int[n];
        wt[0] = 0;
        ct[0] = bt[0];
        tat[0] = bt[0];

        for (int i = 1; i < n; i++) {
            ct[i] = ct[i - 1] + bt[i];
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
        }
        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void sjf() {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
        }
        int wt[]=new int[n];
        int ct[]=new int[n];
        int tat[]=new int[n];
        int completed = 0; 
        int currentTime = 0; 
        boolean[] visited = new boolean[n]; 
        int[] remainingTime = new int[n]; 
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
        }
        while (completed < n) {
            int shortestProcess = -1;
            int shortestBurst = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && at[i] <= currentTime && remainingTime[i] < shortestBurst) {
                    shortestBurst = remainingTime[i];
                    shortestProcess = i;
                }
            }
            if (shortestProcess == -1) {
                currentTime++;
                continue;
            }
            remainingTime[shortestProcess]--;
            currentTime++;
            if (remainingTime[shortestProcess] == 0) {
                ct[shortestProcess] = currentTime;
                tat[shortestProcess] = ct[shortestProcess] - at[shortestProcess];
                wt[shortestProcess] = tat[shortestProcess] - bt[shortestProcess];
                visited[shortestProcess] = true;
                completed++;
            }
        }
        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void priority() {

        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
        }
        int wt[]=new int[n];
        int ct[]=new int[n];
        int tat[]=new int[n];
        int completed = 0; 
        int currentTime = 0; 
        boolean[] visited = new boolean[n]; 
        int[] remainingTime = new int[n]; 
        int pr[]=new int[n];
        System.out.println("Enter priority for processes - ");
        for(int i=0;i<n;i++)
        {
            System.out.println(p[i]+" - ");
            pr[i]=sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
        }

        while (completed < n) {
            int highestPriorityProcess = -1;
            int highestPriority = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && at[i] <= currentTime && pr[i] < highestPriority) {
                    highestPriority = pr[i];
                    highestPriorityProcess = i;
                }
            }
            if (highestPriorityProcess == -1) {
                currentTime++;
                continue;
            }
            ct[highestPriorityProcess] = currentTime + bt[highestPriorityProcess];
            tat[highestPriorityProcess] = ct[highestPriorityProcess] - at[highestPriorityProcess];
            wt[highestPriorityProcess] = tat[highestPriorityProcess] - bt[highestPriorityProcess];
            visited[highestPriorityProcess] = true;
            completed++;
            currentTime += bt[highestPriorityProcess];
        }
        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void roundRobin() {

        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        List<Integer> process=new ArrayList<Integer>();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
        }
        int wt[]=new int[n];
        int ct[]=new int[n];
        int tat[]=new int[n];
        int[] remainingTime = new int[n];  
        int currentTime = 0; 
        Queue<Integer> readyQueue = new ArrayDeque<>();
        int quantum;
        System.out.println("Enter time quantum for Round Robin - ");
        quantum = sc.nextInt();
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
            readyQueue.offer(i); 
        }

        while (!readyQueue.isEmpty()) {
            int currentProcess = readyQueue.poll();
            int pno=currentProcess+1;
            int executionTime = Math.min(quantum, remainingTime[currentProcess]);
            remainingTime[currentProcess] -= executionTime;
            currentTime += executionTime;
            System.out.println("\nProcess executing - "+pno+"\nTime remaining - "+remainingTime[currentProcess]);
            process.add(pno);
            if (remainingTime[currentProcess] == 0) {
                int pn=currentProcess+1;
                System.out.println("\nProcess No. - "+pn+" completed !");
                ct[currentProcess] = currentTime;
                tat[currentProcess] = ct[currentProcess] - at[currentProcess];
                wt[currentProcess] = tat[currentProcess] - bt[currentProcess];
            } else {
                readyQueue.offer(currentProcess);
            }
        }

        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }
        System.out.println("\nGantt Chart - "+process+'\n');
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void display(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        System.out.println(" Process No. | Arr. Time | Burst Time | Compl. Time | Turnar. Time | Waiting Time\n");
        for (int i = 0; i < n; i++) {
            System.out.printf("     %d     |     %d     |    %d    |    %d    |    %d    |    %d\n",
                    p[i], at[i], bt[i], ct[i], tat[i], wt[i]);
        }
    }
    void get()
    {
      
    }

    public static void main(String[] args) {
        Assignment2 a = new Assignment2();
        int ch;
        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("\n\nSelect Scheduling Algorithm - \n1.FCFS Algorithm\n2.SJF Algorithm\n3.Priority Algorithm\n4.Round Robin Algorithm\nEnter choice - ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    a.fcfs();
                    break;
                case 2:
                    a.sjf();
                    break;
                case 3:
                    a.priority();
                    break;
                case 4:
                    
                    a.roundRobin();
                    break;
                default:
                    System.out.println("Invalid Choice !");
                    break;
            }
        } while (ch < 5);
    }
}