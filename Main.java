import java.util.Scanner;

/**
 * DCIT 204 Practical Lab 4: Student Records Management System
 * 
 * This console application demonstrates the implementation and comparison of:
 * - Linear Search (O(n) time complexity)
 * - Stable Insertion Sort by Score (O(n²) time complexity, stable)
 * - Insertion Sort by ID for binary search precondition (O(n²) time complexity)
 * - Binary Search (O(log n) time complexity, requires sorted array)
 * 
 * IMPORTANT CONSTRAINTS:
 * - No ArrayList, LinkedList, HashMap, or external libraries allowed
 * - No use of Arrays.sort() or Collections.sort()
 * - All algorithms implemented from scratch using only simple arrays and loops
 * - No AI-generated or copied code
 * 
 * @author [Student Name]
 * @date [Date of Lab]
 * @course DCIT 204 - Data Structures and Algorithms
 */

public class Main {

    // ==================== GLOBAL DATASET ====================
    // All 10 student records hardcoded for demonstration
    
    private static String[] studentIDs = {
        "UG24CS001", "UG24CS002", "UG24CS003", "UG24CS004", "UG24CS005",
        "UG24CS006", "UG24CS007", "UG24CS008", "UG24CS009", "UG24CS010"
    };

    private static String[] studentNames = {
        "Ama", "Kojo", "Afia", "Yaw", "Esi",
        "Kofi", "Akua", "Kwame", "Sena", "Abena"
    };

    private static int[] studentScores = {
        78, 85, 85, 61, 92,
        74, 92, 69, 78, 55
    };

    private static final int N = studentIDs.length;  // n = 10 students
    
    /**
     * Flag to track whether the ID array is sorted in ascending order.
     * This is CRITICAL for binary search precondition validation.
     * Binary search can only be performed when isIDArraySorted = true
     */
    private static boolean isIDArraySorted = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 7) {
            displayMenu();
            System.out.print("Enter your choice (1-7): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.println();
                handleChoice(choice, scanner);
            } else {
                System.out.println("Invalid input! Please enter a number between 1 and 7.\n");
                scanner.nextLine(); // clear invalid input
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("==================================================");
        System.out.println("       DCIT 204 PRACTICAL LAB 4 - MENU            ");
        System.out.println("==================================================");
        System.out.println("1. Show original student records");
        System.out.println("2. Linear search for a student ID");
        System.out.println("3. Stable insertion sort by score in descending order");
        System.out.println("4. Sort student IDs in ascending order");
        System.out.println("5. Binary search for a student ID after ID sorting");
        System.out.println("6. Compare linear search and binary search");
        System.out.println("7. Exit");
        System.out.println("==================================================");
    }

    private static void handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                displayRecords("CURRENT STUDENT RECORDS");
                break;

            case 2:
                System.out.print("Enter Student ID to linear search (e.g., UG24CS007): ");
                String keyLinear = scanner.nextLine().trim();
                int compLinear = linearSearch(studentIDs, N, keyLinear);
                break;

            case 3:
                stableInsertionSortByScoreDesc(studentIDs, studentNames, studentScores, N);
                System.out.println("Records successfully sorted by score (Descending, Stable)!");
                displayRecords("RECORDS SORTED BY SCORE (DESCENDING)");
                // Sorting by score disrupts ascending ID order
                isIDArraySorted = false; 
                break;

            case 4:
                insertionSortByIDAsc(studentIDs, studentNames, studentScores, N);
                isIDArraySorted = true;
                System.out.println("Records successfully sorted by Student ID in ascending order!");
                displayRecords("RECORDS SORTED BY STUDENT ID (ASCENDING)");
                break;

            case 5:
                if (!isIDArraySorted) {
                    System.out.println("[PRECONDITION ERROR] Binary search cannot be performed!");
                    System.out.println("Reason: Student IDs must be sorted in ascending order first (Option 4).\n");
                } else {
                    System.out.print("Enter Student ID to binary search (e.g., UG24CS007): ");
                    String keyBinary = scanner.nextLine().trim();
                    binarySearch(studentIDs, N, keyBinary);
                }
                break;

            case 6:
                compareSearches(scanner);
                break;

            case 7:
                System.out.println("Exiting application. Goodbye!");
                break;

            default:
                System.out.println("Invalid menu choice. Try again.\n");
        }
    }

    private static void displayRecords(String title) {
        System.out.println("--------------------------------------------------");
        System.out.println(title);
        System.out.println("--------------------------------------------------");
        System.out.printf("%-7s | %-12s | %-10s | %-5s%n", "Index", "Student ID", "Name", "Score");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < N; i++) {
            System.out.printf("%-7d | %-12s | %-10s | %-5d%n", i, studentIDs[i], studentNames[i], studentScores[i]);
        }
        System.out.println("--------------------------------------------------\n");
    }

    // 5.1 Linear Search Implementation
    /**
     * Implements LINEAR-SEARCH algorithm from pseudocode.
     * 
     * PSEUDOCODE (from lab):
     *   LINEAR-SEARCH(A, n, x)
     *     for i = 0 to n - 1 do
     *       if A[i] == x then
     *         return i
     *     return -1
     * 
     * LOOP INVARIANT:
     *   At the start of each iteration i, the key x has not been found 
     *   in positions 0 to i - 1.
     * 
     * TIME COMPLEXITY:
     *   - Worst Case: O(n) - when element is at end or not found
     *   - Best Case: O(1) - when element is at first position
     *   - Average Case: O(n)
     * 
     * SPACE COMPLEXITY: O(1) - uses only constant extra space (comparisons counter)
     * 
     * PRECONDITION: None - array can be unsorted
     * 
     * @param arr The array to search
     * @param n The size of the array
     * @param key The student ID to find
     * @return The index of the key if found, -1 otherwise
     */
    private static int linearSearch(String[] arr, int n, String key) {
        int comparisons = 0;
        
        // Loop through all elements from index 0 to n-1
        for (int i = 0; i < n; i++) {
            comparisons++;  // Count this comparison for analysis
            
            // Check if current element matches the key (case-insensitive)
            if (arr[i].equalsIgnoreCase(key)) {
                // FOUND: Display result with comparison count
                System.out.printf("FOUND: %s (%s) with Score %d at Index %d.%n", 
                                  studentNames[i], arr[i], studentScores[i], i);
                System.out.printf("Total comparisons made: %d%n%n", comparisons);
                return i;
            }
        }
        
        // NOT FOUND: All n elements checked without finding key
        System.out.printf("NOT FOUND: %s does not exist in records.%n", key);
        System.out.printf("Total comparisons made: %d%n%n", comparisons);
        return -1;
    }

    // 5.2 Stable Insertion Sort by Score (Descending)
    /**
     * Implements STABLE-INSERTION-SORT-BY-SCORE-DESC algorithm from pseudocode.
     * 
     * PSEUDOCODE (from lab):
     *   STABLE-INSERTION-SORT-BY-SCORE-DESC(ID, NAME, SCORE, n)
     *     for i = 1 to n - 1 do
     *       keyID = ID[i]
     *       keyName = NAME[i]
     *       keyScore = SCORE[i]
     *       j = i - 1
     *       while j >= 0 and SCORE[j] < keyScore do
     *         ID[j + 1] = ID[j]
     *         NAME[j + 1] = NAME[j]
     *         SCORE[j + 1] = SCORE[j]
     *         j = j - 1
     *       ID[j + 1] = keyID
     *       NAME[j + 1] = keyName
     *       SCORE[j + 1] = keyScore
     * 
     * LOOP INVARIANT:
     *   At the start of each outer-loop iteration i, records in positions 0 to i - 1 
     *   are already sorted in descending order by score and equal scores remain 
     *   in their original relative order (STABLE).
     * 
     * TIME COMPLEXITY:
     *   - Worst Case: O(n²) - when input is reverse sorted
     *   - Best Case: O(n) - when input is already sorted
     *   - Average Case: O(n²)
     * 
     * SPACE COMPLEXITY: O(1) - uses only temporary variables (keyID, keyName, keyScore, j)
     * 
     * STABILITY: YES - Uses strict '<' comparison to preserve order of equal elements
     *   IMPORTANT: Must use 'SCORE[j] < keyScore' NOT 'SCORE[j] <= keyScore'
     *   Example: When score 92 appears twice (Esi, Akua), Esi stays before Akua
     *            because condition '92 < 92' is false, preventing unnecessary swap
     * 
     * @param id Array of student IDs (modified in-place)
     * @param name Array of student names (modified in-place)
     * @param score Array of student scores (modified in-place)
     * @param n The size of arrays
     */
    private static void stableInsertionSortByScoreDesc(String[] id, String[] name, int[] score, int n) {
        // Iterate through each element starting from index 1
        for (int i = 1; i < n; i++) {
            // Save current element as "key" to be inserted
            String keyID = id[i];
            String keyName = name[i];
            int keyScore = score[i];
            
            // Start comparing from position i - 1 (to the left)
            int j = i - 1;

            // Shift elements to the right while they are SMALLER than keyScore
            // Note: STRICT '<' comparison ensures stability with duplicate scores
            while (j >= 0 && score[j] < keyScore) {
                id[j + 1] = id[j];
                name[j + 1] = name[j];
                score[j + 1] = score[j];
                j = j - 1;
            }
            
            // Insert the key at its correct position
            id[j + 1] = keyID;
            name[j + 1] = keyName;
            score[j + 1] = keyScore;
        }
    }

    // Insertion Sort by ID (Ascending) to meet Binary Search Precondition
    /**
     * Implements insertion sort by Student ID in ASCENDING order.
     * 
     * PURPOSE: Arrange student IDs in sorted order to satisfy the PRECONDITION 
     * required by binary search. Binary search only works on sorted data.
     * 
     * TIME COMPLEXITY:
     *   - Worst Case: O(n²)
     *   - Best Case: O(n) - when already sorted
     *   - Average Case: O(n²)
     * 
     * SPACE COMPLEXITY: O(1) - in-place sorting using temporary variables
     * 
     * COMPARISON METHOD: String.compareTo() - lexicographic (alphabetic) ordering
     *   - Negative if id[j] < keyID
     *   - Zero if id[j] == keyID
     *   - Positive if id[j] > keyID
     * 
     * EFFECT: After this operation, isIDArraySorted flag becomes TRUE,
     *         allowing binary search to be executed in menu option 5.
     * 
     * @param id Array of student IDs (modified in-place, sorted ascending)
     * @param name Array of student names (modified accordingly)
     * @param score Array of student scores (modified accordingly)
     * @param n The size of arrays
     */
    private static void insertionSortByIDAsc(String[] id, String[] name, int[] score, int n) {
        // Iterate through each element starting from index 1
        for (int i = 1; i < n; i++) {
            String keyID = id[i];
            String keyName = name[i];
            int keyScore = score[i];
            int j = i - 1;

            // Shift elements to the right while they are GREATER than keyID (lexicographically)
            while (j >= 0 && id[j].compareTo(keyID) > 0) {
                id[j + 1] = id[j];
                name[j + 1] = name[j];
                score[j + 1] = score[j];
                j = j - 1;
            }
            
            // Insert the key at its correct position
            id[j + 1] = keyID;
            name[j + 1] = keyName;
            score[j + 1] = keyScore;
        }
    }

    // 5.3 Binary Search Implementation
    /**
     * Implements BINARY-SEARCH algorithm from pseudocode.
     * 
     * PSEUDOCODE (from lab):
     *   BINARY-SEARCH(ID, n, x)
     *     low = 0
     *     high = n - 1
     *     while low <= high do
     *       mid = low + (high - low) / 2
     *       if ID[mid] == x then
     *         return mid
     *       else if ID[mid] < x then
     *         low = mid + 1
     *       else
     *         high = mid - 1
     *     return -1
     * 
     * LOOP INVARIANT:
     *   If the key exists, it must be inside the current active interval [low, high].
     *   After each iteration, the search space is reduced by half.
     * 
     * TIME COMPLEXITY:
     *   - Worst Case: O(log n) - for n=10, approximately log₂(10) ≈ 3.32 ≈ 4 comparisons
     *   - Best Case: O(1) - when element is at middle position on first check
     *   - Average Case: O(log n)
     * 
     * SPACE COMPLEXITY: O(1) - uses only constant extra space (low, high, mid, comparisons)
     * 
     * *** CRITICAL PRECONDITION: Array MUST be sorted in ascending order ***
     *   Without this precondition, binary search produces INCORRECT results.
     *   Why? The algorithm eliminates half the search space based on comparison results
     *   that are only valid for sorted arrays.
     * 
     * COMPARISON ADVANTAGE:
     *   - Linear Search: 7 comparisons (on n=10 for UG24CS007)
     *   - Binary Search: 4 comparisons (on n=10 for UG24CS007)
     *   - Efficiency gain: 43% reduction
     * 
     * @param id The array of student IDs (must be sorted ascending)
     * @param n The size of the array
     * @param key The student ID to find
     * @return The index of the key if found, -1 otherwise
     */
    private static int binarySearch(String[] id, int n, String key) {
        int low = 0;           // Start of search interval
        int high = n - 1;      // End of search interval
        int comparisons = 0;   // Count comparisons for analysis

        // Continue while search interval is valid
        while (low <= high) {
            // Calculate middle position (avoids overflow with (high - low) formula)
            int mid = low + (high - low) / 2;
            comparisons++;

            // Compare middle element with key
            int cmp = id[mid].compareToIgnoreCase(key);
            
            if (cmp == 0) {
                // FOUND: Middle element matches key
                System.out.printf("FOUND: %s (%s) with Score %d at Index %d.%n", 
                                  studentNames[mid], id[mid], studentScores[mid], mid);
                System.out.printf("Total comparisons made: %d%n%n", comparisons);
                return mid;
            } else if (cmp < 0) {
                // Key is greater than middle element, search right half
                low = mid + 1;
            } else {
                // Key is less than middle element, search left half
                high = mid - 1;
            }
        }
        
        // NOT FOUND: Entire search space exhausted
        System.out.printf("NOT FOUND: %s does not exist in records.%n", key);
        System.out.printf("Total comparisons made: %d%n%n", comparisons);
        return -1;
    }

    // Option 6: Comparison Demonstrator
    /**
     * Demonstrates the efficiency difference between linear and binary search.
     * 
     * WORKFLOW:
     * 1. Accepts a student ID from user
     * 2. Executes linear search on current array (no sorting required)
     * 3. Offers user the choice to sort the array for binary search
     * 4. Executes binary search after sorting
     * 5. Displays comparison counts for both algorithms
     * 
     * LEARNING OBJECTIVE:
     * Shows that while binary search requires preprocessing (sorting),
     * it significantly reduces the number of comparisons for the actual search.
     * For n=10: Linear=7 comparisons, Binary=4 comparisons (43% reduction)
     * 
     * @param scanner Scanner for user input
     */
    private static void compareSearches(Scanner scanner) {
        System.out.print("Enter Student ID to compare both searches: ");
        String key = scanner.nextLine().trim();

        // STEP 1: Linear Search on unsorted array
        System.out.println("\n--- 1. Linear Search on Current Array ---");
        linearSearch(studentIDs, N, key);

        // STEP 2: Binary Search (with optional sorting)
        System.out.println("--- 2. Binary Search Check ---");
        while (true) {
            System.out.print("Do you want to sort the array first? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                if (!isIDArraySorted) {
                    System.out.println("Sorting ID array in ascending order to satisfy binary search precondition...");
                    insertionSortByIDAsc(studentIDs, studentNames, studentScores, N);
                    isIDArraySorted = true;
                }
                break;
            } else if (choice.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
        
        // STEP 3: Execute binary search
        binarySearch(studentIDs, N, key);
    }
}