#include<bits/stdc++.h>
using namespace std;

// Function to take input for blocks and processes
void inputBlocksAndProcesses(vector<int>& blockSize, vector<int>& processSize, int m, int n) {
    cout << "Enter block sizes:\n";
    for (int i = 0; i < m; i++) {
        cout << "Block " << (i + 1) << " size: ";
        cin >> blockSize[i];
    }

    cout << "Enter process sizes:\n";
    for (int i = 0; i < n; i++) {
        cout << "Process " << (i + 1) << " size: ";
        cin >> processSize[i];
    }
}

// Function to print the allocation
void printAllocation(const vector<int>& processSize, int n, const vector<int>& allocation, const vector<int>& remblockSize) {
    cout << "\nProcess No\tProcess Size\tBlock No\tRemaining Block Size\n";
    for (int i = 0; i < n; i++) {
        cout << (i + 1) << "\t\t" << processSize[i] << "\t\t";
        if (allocation[i] != -1) {
            cout << (allocation[i] + 1) << "\t\t" << remblockSize[i];
        } else {
            cout << "Not Allocated";
        }
        cout << "\n";
    }
}


void firstFit(vector<int> blockSize, int b, vector<int>& processSize, int p, vector<int>& remblockSize) {
    vector<int> allocation(p, -1);

    for (int i = 0; i < p; i++) {
        for (int j = 0; j < b; j++) {
            if (blockSize[j] >= processSize[i]) {
                allocation[i] = j;
                blockSize[j] = blockSize[j] - processSize[i];
                remblockSize[i] = blockSize[j];
                break;
            }
        }
    }

    printAllocation(processSize, p, allocation, remblockSize);
}

void nextFit(vector<int> blockSize, int m, vector<int>& processSize, int n, vector<int>& remblockSize) {
    vector<int> allocation(n, -1);
    int j = 0;

    for (int i = 0; i < n; i++) {
        int count = 0;
        while (count < m) {
            if (blockSize[j] >= processSize[i]) {
                allocation[i] = j;
                blockSize[j] = blockSize[j] - processSize[i];
                remblockSize[i] = blockSize[j];
                break;
            }
            j = (j + 1) % m;
            count++;
        }
    }

    printAllocation(processSize, n, allocation, remblockSize);
}

void bestFit(vector<int> blockSize, int m, vector<int>& processSize, int n, vector<int>& remblockSize) {
    vector<int> allocation(n, -1);

    for (int i = 0; i < n; i++) {
        int bestIdx = -1;
        for (int j = 0; j < m; j++) {
            if (blockSize[j] >= processSize[i]) {
                if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j]) {
                    bestIdx = j;
                }
            }
        }
        if (bestIdx != -1) {
            allocation[i] = bestIdx;
            blockSize[bestIdx] = blockSize[bestIdx] - processSize[i];
            remblockSize[i] = blockSize[bestIdx];
        }
    }

    printAllocation(processSize, n, allocation, remblockSize);
} 
 

void worstFit(vector<int> blockSize, int m, vector<int>& processSize, int n, vector<int>& remblockSize) {
    vector<int> allocation(n, -1);

    for (int i = 0; i < n; i++) {
        int wstIdx = -1;
        for (int j = 0; j < m; j++) {
            if (blockSize[j] >= processSize[i]) {
                if (wstIdx == -1 || blockSize[wstIdx] < blockSize[j]) {
                    wstIdx = j;
                }
            }
        }
        if (wstIdx != -1) {
            allocation[i] = wstIdx;
            blockSize[wstIdx] -= processSize[i];
            remblockSize[i] = blockSize[wstIdx];
        }
    }

    printAllocation(processSize, n, allocation, remblockSize);
}

int main() {
    int m, n;

    while (true) {
        cout << "\nEnter choice:\n1 - Best Fit\n2 - First Fit\n3 - Next Fit\n4 - Worst Fit\n5 - Exit\n";
        int choice;
        cin >> choice;

        if (choice == 5) {
            cout << "Exiting program...\n";
            break;
        }

        cout << "Enter the number of blocks: ";
        cin >> m;
        vector<int> blockSize(m);
        vector<int> remblockSize(m);

        cout << "Enter the number of processes: ";
        cin >> n;
        vector<int> processSize(n);

        // Taking input for block sizes and process sizes
        inputBlocksAndProcesses(blockSize, processSize, m, n);

        // Perform the selected memory allocation method
        switch (choice) {
            case 1:
                bestFit(blockSize, m, processSize, n, remblockSize);
                break;
            case 2:
                firstFit(blockSize, m, processSize, n, remblockSize);
                break;
            case 3:
                nextFit(blockSize, m, processSize, n, remblockSize);
                break;
            case 4:
                worstFit(blockSize, m, processSize, n, remblockSize);
                break;
            default:
                cout << "Invalid choice! Please choose a valid option.\n";
        }
    }

    return 0;
}
