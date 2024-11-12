
#include <bits/stdc++.h>
using namespace std;
 
class Scheduling {
public:

 // Display function
    void display(int n, vector<int>& p, vector<int>& at, vector<int>& bt, vector<int>& wt, vector<int>& tat, vector<int>& ct) {
        cout << "\n Process No. | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time\n";
        for (int i = 0; i < n; ++i) {
            cout << "     " << p[i] << "         |     " << at[i] << "       |     " << bt[i]
                 << "       |       " << ct[i] << "         |       " << tat[i] << "        |     " << wt[i] << "\n";
        }
    }

    // FCFS Scheduling
    void fcfs() {
        int n;
        cout << "Enter number of processes - ";
        cin >> n;
        vector<int> p(n), at(n), bt(n), wt(n), ct(n), tat(n);

        for (int i = 0; i < n; ++i) {
            cout << "\nEnter process no - ";
            cin >> p[i];
            cout << "Enter arrival time - ";
            cin >> at[i];
            cout << "Enter burst time - ";
            cin >> bt[i];
        }

        // Sorting based on arrival time
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (at[i] > at[j]) {
                    swap(p[i], p[j]);
                    swap(at[i], at[j]);
                    swap(bt[i], bt[j]);
                }
            }
        }

        ct[0] = at[0] + bt[0];
        tat[0] = ct[0] - at[0];
        wt[0] = tat[0] - bt[0];

        for (int i = 1; i < n; ++i) {
            ct[i] = max(ct[i - 1], at[i]) + bt[i];
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
        }

        display(n, p, at, bt, wt, tat, ct);
    }

    // SJF Scheduling (Non-Preemptive)
    void sjf() {
        int n;
        cout << "Enter number of processes - ";
        cin >> n;
        vector<int> p(n), at(n), bt(n), wt(n), ct(n), tat(n);

        for (int i = 0; i < n; ++i) {
            cout << "\nEnter process no - ";
            cin >> p[i];
            cout << "Enter arrival time - ";
            cin >> at[i];
            cout << "Enter burst time - ";
            cin >> bt[i];
        }

        vector<bool> completed(n, false);
        int completedCount = 0, currentTime = 0;

        while (completedCount < n) {
            int shortestProcess = -1, minBurst = INT_MAX;

            for (int i = 0; i < n; ++i) {
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

        display(n, p, at, bt, wt, tat, ct);
    }

    // Priority Scheduling (Non-Preemptive)
    void priority() {
        int n;
        cout << "Enter number of processes - ";
        cin >> n;
        vector<int> p(n), at(n), bt(n), pr(n), wt(n), ct(n), tat(n);

        for (int i = 0; i < n; ++i) {
            cout << "\nEnter process no - ";
            cin >> p[i];
            cout << "Enter arrival time - ";
            cin >> at[i];
            cout << "Enter burst time - ";
            cin >> bt[i];
            cout << "Enter priority - ";
            cin >> pr[i];
        }

        vector<bool> completed(n, false);
        int completedCount = 0, currentTime = 0;
        
        while (completedCount < n) {
            int highestPriorityProcess = -1, highestPriority = INT_MAX;

            for (int i = 0; i < n; ++i) {
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

        display(n, p, at, bt, wt, tat, ct);
    }

    // Round Robin Scheduling
    void roundRobin() {
        int n, quantum;
        cout << "Enter number of processes - ";
        cin >> n;
        vector<int> p(n), at(n), bt(n), wt(n), ct(n), tat(n), remainingTime(n);
        queue<int> readyQueue;
        int currentTime = 0;
        vector<int> processOrder;

        for (int i = 0; i < n; ++i) {
            cout << "\nEnter process no - ";
            cin >> p[i];
            cout << "Enter arrival time - ";
            cin >> at[i];
            cout << "Enter burst time - ";
            cin >> bt[i];
            remainingTime[i] = bt[i];
            readyQueue.push(i);
        }

        cout << "Enter time quantum for Round Robin - ";
        cin >> quantum;

        while (!readyQueue.empty()) {
            int currentProcess = readyQueue.front();
            readyQueue.pop();
            int executionTime = min(quantum, remainingTime[currentProcess]);
            remainingTime[currentProcess] -= executionTime;
            currentTime += executionTime;
            processOrder.push_back(currentProcess + 1);

            if (remainingTime[currentProcess] == 0) {
                ct[currentProcess] = currentTime;
                tat[currentProcess] = ct[currentProcess] - at[currentProcess];
                wt[currentProcess] = tat[currentProcess] - bt[currentProcess];
            } else {
                readyQueue.push(currentProcess);
            }
        }

        cout << "\nGantt Chart - ";
        for (int proc : processOrder) cout << "P" << proc << " ";
        cout << "\n";

        display(n, p, at, bt, wt, tat, ct);
    }

   
};

int main() {
    Scheduling sched;
    int choice;
    do {
        cout << "\nSelect Scheduling Algorithm - \n1.FCFS Algorithm\n2.SJF Algorithm\n3.Priority Algorithm\n4.Round Robin Algorithm\nEnter choice - ";
        cin >> choice;
        switch (choice) {
            case 1:
                sched.fcfs();
                break;
            case 2:
                sched.sjf();
                break;
            case 3:
                sched.priority();
                break;
            case 4:
                sched.roundRobin();
                break;
            default:
                cout << "Invalid Choice!" << endl;
                break;
        }
    } while (choice >= 1 && choice <= 4);

    return 0;
}

