import java.util.*;

public class Assignment4 {

    public static void fifo(int[] refs, int memorySize) {
        Queue<Integer> memory = new LinkedList<>();
        int hits = 0;

        for (int ref : refs) {
            if (!memory.contains(ref)) {
                // Page fault occurs
                if (memory.size() == memorySize) {
                    memory.poll(); // Remove the oldest page
                }
                memory.add(ref);
            } else {
                hits++;
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page found: " + hits + ", Page missed: " + misses);
    }

    public static void lru(int[] refs, int memorySize) {
        List<Integer> memory = new ArrayList<>();
        int hits = 0;

        for (int i = 0; i < refs.length; i++) {
            int ref = refs[i];

            if (!memory.contains(ref)) {
                // Page fault occurs
                if (memory.size() == memorySize) {
                    // Find least recently used page
                    int furthestIndex = -1;
                    int lruIndex = 0;
                    for (int j = 0; j < memory.size(); j++) {
                        int page = memory.get(j);
                        int nextIndex = findNextOccurrence1(refs, i, page);
                        if (nextIndex > furthestIndex) {
                            furthestIndex = nextIndex;
                            lruIndex = j;
                        }
                    }
                    memory.set(lruIndex, ref);
                } else {
                    memory.add(ref);
                }
            } else {
                hits++;
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page found: " + hits + ", Page missed: " + misses);
    }

    private static int findNextOccurrence1(int[] refs, int start, int page) {
        for (int i = start + 1; i < refs.length; i++) {
            if (refs[i] == page) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    public static void optimal(int[] refs, int memorySize) {
        List<Integer> memory = new ArrayList<>();
        int hits = 0;

        for (int i = 0; i < refs.length; i++) {
            int ref = refs[i];

            if (!memory.contains(ref)) {
                // Page fault occurs
                if (memory.size() == memorySize) {
                    // Find page that will be used farthest in the future
                    int maxGap = -1;
                    int pageToReplace = 0;
                    for (int page : memory) {
                        int gap = findNextOccurrence(refs, i, page);
                        if (gap > maxGap) {
                            maxGap = gap;
                            pageToReplace = page;
                        }
                    }
                    memory.remove((Integer) pageToReplace);
                    memory.add(ref);
                } else {
                    memory.add(ref);
                }
            } else {
                hits++;
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page found: " + hits + ", Page missed: " + misses);
    }

    private static int findNextOccurrence(int[] refs, int start, int page) {
        for (int i = start + 1; i < refs.length; i++) {
            if (refs[i] == page) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter total number of processes : ");
        n = sc.nextInt();

        int[] array = new int[n];
        System.out.println("Enter processes : ");
        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }
        int choice;

        do {
            System.out.println("Select operations \n 1-FIFO\n 2-LRU\n 3-Optimal\n 4-exit\nEnter your choice : ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    fifo(array, 3);
                    break;
                case 2:
                    lru(array, 3);
                    break;
                case 3:
                    optimal(array, 3);
                    break;
                default:
                    break;
            }
        } while (choice != 4);
        // int[] array = { 1, 3, 0, 3, 5, 6, 3, 4, 7, 1, 0, 8 };
        // fifo(array, 3);
        // int[] refs = { 1, 3, 0, 3, 5, 6, 3, 4, 7, 1, 0, 8 };
        // lru(refs, 3);
        // int[] refs1 = { 1, 3, 0, 3, 5, 6, 3, 4, 7, 1, 0, 8 };
        // optimal(refs1, 3);
    }

}
