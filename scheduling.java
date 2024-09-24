import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class scheduling {
    
    // FCFS Scheduling
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
        int wt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        
        // Sorting based on arrival time for FCFS
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (at[i] > at[j]) {
                    swap(p, i, j);
                    swap(at, i, j);
                    swap(bt, i, j);
                }
            }
        }

        ct[0] = at[0] + bt[0];
        tat[0] = ct[0] - at[0];
        wt[0] = tat[0] - bt[0];

        for (int i = 1; i < n; i++) {
            ct[i] = Math.max(ct[i - 1], at[i]) + bt[i];
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

    // Non-Preemptive SJF Scheduling
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
        int wt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        boolean[] completed = new boolean[n];
        int completedCount = 0, currentTime = 0;

        while (completedCount < n) {
            int shortestProcess = -1, minBurst = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime && bt[i] < minBurst) {
                    minBurst = bt[i];
                    shortestProcess = i;
                }
            }

            if (shortestProcess == -1) {
                currentTime++;
                continue;
            }

            ct[shortestProcess] = currentTime + bt[shortestProcess];
            tat[shortestProcess] = ct[shortestProcess] - at[shortestProcess];
            wt[shortestProcess] = tat[shortestProcess] - bt[shortestProcess];

            currentTime += bt[shortestProcess];
            completed[shortestProcess] = true;
            completedCount++;
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

    // Priority Scheduling
    void priority() {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int pr[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
            System.out.println("Enter priority - ");
            pr[i] = sc.nextInt();
        }
        int wt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        boolean[] completed = new boolean[n];
        int completedCount = 0, currentTime = 0;

        while (completedCount < n) {
            int highestPriorityProcess = -1, highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime && pr[i] < highestPriority) {
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

            currentTime += bt[highestPriorityProcess];
            completed[highestPriorityProcess] = true;
            completedCount++;
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

    // Round Robin Scheduling
    void roundRobin() {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        List<Integer> process = new ArrayList<>();
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
        int wt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
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
            int executionTime = Math.min(quantum, remainingTime[currentProcess]);
            remainingTime[currentProcess] -= executionTime;
            currentTime += executionTime;
            process.add(currentProcess + 1);

            if (remainingTime[currentProcess] == 0) {
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
        System.out.println("\nGantt Chart - " + process + '\n');
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    // Displaying the process details
    void display(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        System.out.println(" Process No. | Arr. Time | Burst Time | Compl. Time | Turnar. Time | Waiting Time\n");
        for (int i = 0; i < n; i++) {
            System.out.printf("     %d     |     %d     |    %d    |    %d    |    %d    |    %d\n",
                    p[i], at[i], bt[i], ct[i], tat[i], wt[i]);
        }
    }

    // Helper method to swap values
    void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        scheduling a = new scheduling();
        int ch;
        Scanner sc = new Scanner(System.in);
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

