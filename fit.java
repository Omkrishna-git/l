import java.util.*;

class fit {
    
    // Function to take input for blocks and processes
    static void inputBlocksAndProcesses(int blockSize[], int processSize[], int m, int n, Scanner sc) {
        System.out.println("Enter block sizes:");
        for (int i = 0; i < m; i++) {
            System.out.print("Block " + (i + 1) + " size: ");
            blockSize[i] = sc.nextInt();
        }
        
        System.out.println("Enter process sizes:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " size: ");
            processSize[i] = sc.nextInt();
        }
    }

    static void bestFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);  // Initialize allocation array
        
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
                remblockSize[i] = blockSize[bestIdx];
            }
        }

        printAllocation(processSize, n, allocation, remblockSize);
    }

    static void firstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);  // Initialize allocation array

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remblockSize[i] = blockSize[j];
                    break;
                }
            }
        }

        printAllocation(processSize, n, allocation, remblockSize);
    }

    static void nextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);  // Initialize allocation array
        int j = 0;

        for (int i = 0; i < n; i++) {
            int count = 0;
            while (count < m) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remblockSize[i] = blockSize[j];
                    break;
                }
                j = (j + 1) % m;
                count++;
            }
        }

        printAllocation(processSize, n, allocation, remblockSize);
    }

    static void worstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);  // Initialize allocation array

        for (int i = 0; i < n; i++) {
            int wstIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (wstIdx == -1 || blockSize[wstIdx] < blockSize[j])
                        wstIdx = j;
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

    // Function to print the allocation
    static void printAllocation(int processSize[], int n, int allocation[], int remblockSize[]) {
        System.out.println("\nProcess No\tProcess Size\tBlock No\tRemaining Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            } else {
                System.out.print("Not Allocated");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m, n;

        while (true) {
            System.out.println("\nEnter choice:\n1 - Best Fit\n2 - First Fit\n3 - Next Fit\n4 - Worst Fit\n5 - Exit");
            int choice = sc.nextInt();
            
            if (choice == 5) {
                System.out.println("Exiting program...");
                break;
            }

            System.out.print("Enter the number of blocks: ");
            m = sc.nextInt();
            int blockSize[] = new int[m];
            int remblockSize[] = new int[m];
            
            System.out.print("Enter the number of processes: ");
            n = sc.nextInt();
            int processSize[] = new int[n];

            // Taking input for block sizes and process sizes
            inputBlocksAndProcesses(blockSize, processSize, m, n, sc);

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
                    System.out.println("Invalid choice! Please choose a valid option.");
            }
        }

        sc.close();  // Closing the scanner
    }
}
