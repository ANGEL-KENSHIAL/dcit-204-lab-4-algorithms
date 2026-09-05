# DCIT 204 Practical Lab 4: Student Records Management System

## Linear Search, Binary Search, and Stable Insertion Sort

---

## 1. Lab Assignment Overview

**Course:** DCIT 204 - Data Structures and Algorithms  
**Lab Title:** Algorithm Implementation – Search and Sort  
**Duration:** 1 hour laboratory + 1 hour report writing  
**Submission Requirements:** Java source file(s), screenshots of console runs, and lab report

### 1.1 Real-Life Scenario

The Department of Computer Science requires a console-based program to assist a course tutor in managing student assessment records. The tutor must be able to:

- Search for a specific student by ID to verify records
- Sort students by academic score to generate rankings
- Understand which algorithms are appropriate for small class lists and why

**Key Emphasis:** This lab practises algorithmic thinking using fundamental concepts only—**no database systems, GUI, or external libraries**. The goal is to understand how simple arrays and loops can efficiently solve real problems.

---

## 2. Learning Outcomes

By completing this lab, you will be able to:

✓ **Implement linear search** on unsorted arrays and explain its behavior  
✓ **Implement binary search** on sorted arrays and state the required precondition  
✓ **Apply stable insertion sort** to preserve relative order of equal elements  
✓ **State loop invariants** for linear search, binary search, and insertion sort  
✓ **Analyze time and space complexity** of implemented algorithms  
✓ **Justify algorithm selection** for a given problem context

---

## 3. Implementation Scope

### 3.1 What Is Allowed

- Simple arrays and loops only
- Manual implementation of all algorithms from scratch
- Hardcoded sample data or console input for testing
- Basic I/O and control flow constructs
- Temporary variables for algorithm execution

### 3.2 What Is NOT Allowed

- ❌ ArrayList, LinkedList, HashMap, PriorityQueue
- ❌ Databases or external data sources
- ❌ GUI (Graphical User Interface)
- ❌ External libraries or frameworks
- ❌ Arrays.sort() or Collections.sort() methods
- ❌ Copied code templates or AI-generated code
- ❌ Advanced Java features not covered in class

**Submission Note:** Only original code will be accepted. All work must be written from scratch by the student.

---

## 4. Dataset Overview

### 4.1 Sample Student Records

| Index | Student ID | Name  | Score |
| ----- | ---------- | ----- | ----- |
| 0     | UG24CS001  | Ama   | 78    |
| 1     | UG24CS002  | Kojo  | 85    |
| 2     | UG24CS003  | Afia  | 85    |
| 3     | UG24CS004  | Yaw   | 61    |
| 4     | UG24CS005  | Esi   | 92    |
| 5     | UG24CS006  | Kofi  | 74    |
| 6     | UG24CS007  | Akua  | 92    |
| 7     | UG24CS008  | Kwame | 69    |
| 8     | UG24CS009  | Sena  | 78    |
| 9     | UG24CS010  | Abena | 55    |

### 4.2 Data Input Method

The sample data may be:

- **Hardcoded into arrays** (for demonstration and testing, as shown in `Main.java`)
- **Entered via console input** (optional for extended functionality)

The final program **must accept a search key from the console** for menu options 2, 5, and 6.

---

## 5. Console Application Menu

The program displays an interactive menu with the following options:

```
==================================================
       DCIT 204 PRACTICAL LAB 4 - MENU
==================================================
1. Show original student records
2. Linear search for a student ID
3. Stable insertion sort by score in descending order
4. Sort student IDs in ascending order
5. Binary search for a student ID after ID sorting
6. Compare linear search and binary search
7. Exit
==================================================
```

### 5.1 Menu Options Explanation

| Option | Operation                             | Input Required | Output                                                  |
| ------ | ------------------------------------- | -------------- | ------------------------------------------------------- |
| **1**  | Display all records in original order | None           | Formatted table of current records                      |
| **2**  | Search for a student ID (linear)      | Student ID     | Found/Not Found with index and comparisons              |
| **3**  | Rank students by score (descending)   | None           | Records sorted by score, stable order maintained        |
| **4**  | Sort student IDs alphabetically       | None           | Records sorted by ID (ascending), enables binary search |
| **5**  | Search after ID sorting (binary)      | Student ID     | Found/Not Found with index and comparisons              |
| **6**  | Compare search algorithms             | Student ID     | Both searches executed with comparison counts           |
| **7**  | Exit program                          | None           | Program terminates                                      |

---

## 6. Algorithm Implementations

### 6.1 Linear Search

#### Pseudocode

```
LINEAR-SEARCH(A, n, x)
    for i = 0 to n - 1 do
        if A[i] == x then
            return i
    return -1
```

#### Loop Invariant

**At the start of each iteration i:** The key x has not been found in positions 0 to i - 1.

#### Implementation Details

- **Time Complexity (Worst Case):** O(n) — must check all elements
- **Time Complexity (Best Case):** O(1) — found on first check
- **Space Complexity:** O(1) — uses constant extra space
- **Precondition:** None (array can be unsorted)
- **Key Feature:** Works on any array regardless of order

#### Java Implementation

```java
private static int linearSearch(String[] arr, int n, String key) {
    int comparisons = 0;
    for (int i = 0; i < n; i++) {
        comparisons++;
        if (arr[i].equalsIgnoreCase(key)) {
            System.out.printf("FOUND: %s (%s) with Score %d at Index %d.%n",
                              studentNames[i], arr[i], studentScores[i], i);
            System.out.printf("Total comparisons made: %d%n%n", comparisons);
            return i;
        }
    }
    System.out.printf("NOT FOUND: %s does not exist in records.%n", key);
    System.out.printf("Total comparisons made: %d%n%n", comparisons);
    return -1;
}
```

---

### 6.2 Stable Insertion Sort by Score (Descending)

#### Pseudocode

```
STABLE-INSERTION-SORT-BY-SCORE-DESC(ID, NAME, SCORE, n)
    for i = 1 to n - 1 do
        keyID = ID[i]
        keyName = NAME[i]
        keyScore = SCORE[i]
        j = i - 1
        while j >= 0 and SCORE[j] < keyScore do
            ID[j + 1] = ID[j]
            NAME[j + 1] = NAME[j]
            SCORE[j + 1] = SCORE[j]
            j = j - 1
        ID[j + 1] = keyID
        NAME[j + 1] = keyName
        SCORE[j + 1] = keyScore
```

#### Loop Invariant

**At the start of each outer-loop iteration i:** Records in positions 0 to i - 1 are sorted in descending order by score, and students with equal scores remain in their original relative order.

#### Stability Condition (CRITICAL)

✓ **Use:** `SCORE[j] < keyScore` (strict less-than)  
✗ **Do NOT use:** `SCORE[j] <= keyScore` (less-than-or-equal)

The strict comparison ensures that equal scores are not swapped, preserving the original order.

#### Implementation Details

- **Time Complexity (Worst Case):** O(n²) — when input is reverse sorted
- **Time Complexity (Best Case):** O(n) — when input is already sorted
- **Time Complexity (Average Case):** O(n²)
- **Space Complexity:** O(1) — uses temporary variables only
- **Stable:** Yes — equal scores maintain original order
- **In-Place:** Yes — modifies arrays directly

#### Java Implementation

```java
private static void stableInsertionSortByScoreDesc(String[] id, String[] name, int[] score, int n) {
    for (int i = 1; i < n; i++) {
        String keyID = id[i];
        String keyName = name[i];
        int keyScore = score[i];
        int j = i - 1;

        // SCORE[j] < keyScore preserves relative order of duplicate values (STABLE)
        while (j >= 0 && score[j] < keyScore) {
            id[j + 1] = id[j];
            name[j + 1] = name[j];
            score[j + 1] = score[j];
            j = j - 1;
        }
        id[j + 1] = keyID;
        name[j + 1] = keyName;
        score[j + 1] = keyScore;
    }
}
```

---

### 6.3 Insertion Sort by Student ID (Ascending)

#### Pseudocode

```
INSERTION-SORT-BY-ID-ASC(ID, NAME, SCORE, n)
    for i = 1 to n - 1 do
        keyID = ID[i]
        keyName = NAME[i]
        keyScore = SCORE[i]
        j = i - 1
        while j >= 0 and ID[j] > keyID do
            ID[j + 1] = ID[j]
            NAME[j + 1] = NAME[j]
            SCORE[j + 1] = SCORE[j]
            j = j - 1
        ID[j + 1] = keyID
        NAME[j + 1] = keyName
        SCORE[j + 1] = keyScore
```

#### Implementation Details

- **Purpose:** Arrange student IDs in ascending order to **satisfy the precondition for binary search**
- **Time Complexity:** O(n²) worst case
- **Space Complexity:** O(1)
- **Comparison Method:** `String.compareTo()` for lexicographic ordering

---

### 6.4 Binary Search

#### Pseudocode

```
BINARY-SEARCH(ID, n, x)
    low = 0
    high = n - 1
    while low <= high do
        mid = low + (high - low) / 2
        if ID[mid] == x then
            return mid
        else if ID[mid] < x then
            low = mid + 1
        else
            high = mid - 1
    return -1
```

#### Loop Invariant

**If the key exists, it must be inside the current active interval [low, high].**

#### Precondition (REQUIRED)

⚠️ **The ID array MUST be sorted in ascending order before binary search is applied.**

This is verified in the program by:

1. Checking the `isIDArraySorted` flag
2. Rejecting binary search if sorting has not been performed
3. Allowing users to sort before search if needed

#### Implementation Details

- **Time Complexity:** O(log n) — divides search space by half each iteration
- **Space Complexity:** O(1) — uses constant extra space
- **Precondition Required:** Sorted array in ascending order
- **Best Case:** O(1) — element found at middle position
- **Worst Case:** O(log n) — approximately log₂(n) comparisons
- **Sample: For n=10, worst case ≈ 4 comparisons**

#### Java Implementation

```java
private static int binarySearch(String[] id, int n, String key) {
    int low = 0;
    int high = n - 1;
    int comparisons = 0;

    while (low <= high) {
        int mid = low + (high - low) / 2;
        comparisons++;

        int cmp = id[mid].compareToIgnoreCase(key);
        if (cmp == 0) {
            System.out.printf("FOUND: %s (%s) with Score %d at Index %d.%n",
                              studentNames[mid], id[mid], studentScores[mid], mid);
            System.out.printf("Total comparisons made: %d%n%n", comparisons);
            return mid;
        } else if (cmp < 0) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    System.out.printf("NOT FOUND: %s does not exist in records.%n", key);
    System.out.printf("Total comparisons made: %d%n%n", comparisons);
    return -1;
}
```

---

## 7. Expected Behavior for the Sample Data

### 7.1 Test Case: Search for Student ID `UG24CS007` (Akua)

| Operation                          | Expected Result                                                            | Observations                           |
| ---------------------------------- | -------------------------------------------------------------------------- | -------------------------------------- |
| **Linear Search (original array)** | Found at index 6                                                           | ~7 comparisons (worst case for sample) |
| **Stable Score Ranking**           | Esi(92) before Akua(92); Kojo(85) before Afia(85); Ama(78) before Sena(78) | Relative order of equals preserved     |
| **ID Sorting**                     | IDs arranged as UG24CS001, UG24CS002, ..., UG24CS010                       | Records are now in ascending ID order  |
| **Binary Search (after sorting)**  | Found at index 6                                                           | ~4 comparisons (log₂(10) ≈ 3-4)        |
| **Binary Search (before sorting)** | Rejected with error message                                                | Precondition not met                   |

---

## 8. How to Run the Program

### 8.1 Compilation

```bash
javac Main.java
```

### 8.2 Execution

```bash
java Main
```

### 8.3 Expected Startup

```
==================================================
       DCIT 204 PRACTICAL LAB 4 - MENU
==================================================
1. Show original student records
2. Linear search for a student ID
3. Stable insertion sort by score in descending order
4. Sort student IDs in ascending order
5. Binary search for a student ID after ID sorting
6. Compare linear search and binary search
7. Exit
==================================================
Enter your choice (1-7):
```

---

## 9. Screenshots Documentation

### 📸 Required Screenshots for Lab Report

Include the following screenshots demonstrating program functionality:

#### Screenshot 1: Initial Menu Display

- **When to take:** After running `java Main`
- **What to show:** Full menu options displayed
- **Caption:** "Main menu of Student Records Management System"

#### Screenshot 2: Original Records (Option 1)

- **When to take:** Select option 1
- **What to show:** All 10 students in original order
- **Caption:** "Original student records before any operations"

#### Screenshot 3: Linear Search Success (Option 2)

- **When to take:** Select option 2, enter `UG24CS007`
- **What to show:** "FOUND: Akua (UG24CS007) with Score 92 at Index 6. Total comparisons made: 7"
- **Caption:** "Linear search finds student UG24CS007 after 7 comparisons"

#### Screenshot 4: Linear Search Failure (Option 2)

- **When to take:** Select option 2, enter a non-existent ID like `UG24CS999`
- **What to show:** "NOT FOUND" message with all 10 comparisons
- **Caption:** "Linear search completes all 10 comparisons when student not found"

#### Screenshot 5: Stable Sort by Score (Option 3)

- **When to take:** Select option 3
- **What to show:** Records sorted in descending score order with stable order: Esi(92) before Akua(92), Kojo(85) before Afia(85), Ama(78) before Sena(78)
- **Caption:** "Stable insertion sort by score (descending) - equal scores preserve original order"

#### Screenshot 6: ID Sort (Option 4)

- **When to take:** Select option 4
- **What to show:** Records sorted by Student ID in ascending order
- **Caption:** "Student IDs sorted in ascending order to enable binary search"

#### Screenshot 7: Binary Search Success (Option 5)

- **When to take:** First do option 4 to sort IDs, then select option 5, enter `UG24CS007`
- **What to show:** "FOUND: Akua (UG24CS007) with Score 92 at Index 6. Total comparisons made: 4"
- **Caption:** "Binary search finds student UG24CS007 in only 4 comparisons (log₂(10))"

#### Screenshot 8: Binary Search Without Sorting (Option 5)

- **When to take:** Without doing option 4 first, select option 5
- **What to show:** Error message: "[PRECONDITION ERROR] Binary search cannot be performed! Reason: Student IDs must be sorted in ascending order first"
- **Caption:** "Binary search correctly rejects unsorted array due to failed precondition"

#### Screenshot 9: Compare Searches (Option 6)

- **When to take:** Select option 6, enter `UG24CS007`, then choose to sort
- **What to show:** Both linear (7 comparisons) and binary (4 comparisons) results
- **Caption:** "Comparison of linear and binary search efficiency on the same data"

#### Screenshot 10: Program Exit (Option 7)

- **When to take:** Select option 7
- **What to show:** "Exiting application. Goodbye!"
- **Caption:** "Graceful program termination"

---

## 10. Complexity Analysis & Lab Report Questions

### 10.1 Time Complexity Analysis

#### Linear Search - Time Complexity

- **Worst Case:** O(n)  
  _Explanation:_ The element is at the end or doesn't exist; all n elements must be checked.
- **Best Case:** O(1)  
  _Explanation:_ The element is at the first position.
- **Average Case:** O(n)  
  _Explanation:_ On average, the element is found after checking n/2 elements, which simplifies to O(n).

#### Binary Search - Time Complexity

- **Worst Case:** O(log n)  
  _Explanation:_ The element is not found or at the leaf of the binary search tree. For n=10, maximum iterations ≈ log₂(10) ≈ 4 comparisons.
- **Best Case:** O(1)  
  _Explanation:_ The element is found at the middle position on the first check.
- **Average Case:** O(log n)  
  _Explanation:_ Binary search consistently divides the search space in half.

#### Insertion Sort - Time Complexity

- **Worst Case:** O(n²)  
  _Explanation:_ When input is reverse sorted; each element must shift left past all previously sorted elements. Total comparisons: 1 + 2 + 3 + ... + (n-1) = n(n-1)/2 ≈ O(n²).
- **Best Case:** O(n)  
  _Explanation:_ When input is already sorted; no shifting is needed, only one pass of n-1 comparisons.
- **Average Case:** O(n²)  
  _Explanation:_ On average, each element is compared with half of the previously sorted elements.

### 10.2 Space Complexity Analysis

| Algorithm      | Auxiliary Space | Explanation                                            |
| -------------- | --------------- | ------------------------------------------------------ |
| Linear Search  | O(1)            | Only counter variable; no extra data structures        |
| Binary Search  | O(1)            | Only low, high, mid, comparisons counters              |
| Insertion Sort | O(1)            | Only temporary variables (keyID, keyName, keyScore, j) |

All algorithms operate **in-place** with constant extra space.

### 10.3 Lab Report Questions to Answer

#### Question 1: Linear Search Time Complexity

**Q:** What is the worst-case time complexity of linear search? Explain using n.

**Answer Framework:**

- Worst-case: O(n)
- Explanation: When searching for an element at the end of the array or when the element does not exist, the algorithm must compare against all n elements. In the sample data (n=10), searching for UG24CS007 requires 7 comparisons, and searching for a non-existent ID requires all 10 comparisons.

#### Question 2: Binary Search Precondition

**Q:** What is the worst-case time complexity of binary search? State the required precondition.

**Answer Framework:**

- Worst-case: O(log n)
- For sample data: log₂(10) ≈ 3-4 comparisons
- **Precondition:** The array MUST be sorted in ascending order
- Without this precondition, binary search produces incorrect results because it eliminates half the search space based on comparison results that are only valid for sorted arrays.

#### Question 3: Insertion Sort Complexity & Nearly-Sorted Input

**Q:** What is the worst-case time complexity of insertion sort? What happens when the input is nearly sorted?

**Answer Framework:**

- Worst-case: O(n²)
- Occurs when input is reverse sorted; each element slides past all previous elements
- When input is nearly sorted: Performance improves significantly toward O(n) because elements require few comparisons and minimal shifts
- This is why insertion sort is used in practice for small datasets and nearly-sorted data

#### Question 4: Auxiliary Space Complexity

**Q:** What is the auxiliary space complexity of your sorting approach when you use only temporary variables?

**Answer Framework:**

- Auxiliary space: O(1)
- The implementation uses only constant extra space: keyID, keyName, keyScore, and index variable j
- No additional arrays or data structures are created
- Sorting is performed in-place by rearranging the original arrays

#### Question 5: Stability of Insertion Sort

**Q:** Why is the insertion sort version in this lab stable? Refer to the comparison used in the while-loop.

**Answer Framework:**

- Stability: Students with equal scores maintain their original relative order
- Key reason: The while-loop uses the condition `SCORE[j] < keyScore` (strict less-than)
- Not `SCORE[j] <= keyScore` (less-than-or-equal)
- Example: Esi (score 92) appears before Akua (score 92) after sorting because when keyScore=92 (Akua), the condition `92 < 92` is false, so Akua is inserted after Esi rather than before
- This design choice ensures stability while maintaining descending order

---

## 11. Practical Observations & Validation

### 11.1 Verification Checklist

- ✓ Original records contain all 10 students in correct order
- ✓ Linear search correctly counts comparisons
- ✓ Stable sort preserves order of equal scores: Esi(92) before Akua(92), Kojo(85) before Afia(85), Ama(78) before Sena(78)
- ✓ Binary search rejects unsorted arrays with precondition error
- ✓ Binary search finds elements in ~log n comparisons after sorting
- ✓ Comparison feature demonstrates efficiency difference (7 vs ~4 comparisons for sample)

### 11.2 Sample Execution Flow

```
1. Display original records [SCREENSHOT 2]
2. Perform linear search for UG24CS007 → Found at index 6, 7 comparisons [SCREENSHOT 3]
3. Sort by score (stable) → Esi(92), Akua(92), ... [SCREENSHOT 5]
4. Sort by ID ascending → UG24CS001, UG24CS002, ... [SCREENSHOT 6]
5. Perform binary search for UG24CS007 → Found at index 6, 4 comparisons [SCREENSHOT 7]
6. Attempt binary search without sorting → Precondition error [SCREENSHOT 8]
7. Compare both searches → Show efficiency difference [SCREENSHOT 9]
```

---

## 12. Important Notes for Students

### 12.1 Code Quality Standards

✓ **Write clean, readable code** with meaningful variable names  
✓ **Include comments** explaining loop invariants and algorithm steps  
✓ **Test thoroughly** with multiple inputs, including edge cases  
✓ **Verify preconditions** before applying algorithms (especially binary search)  
✓ **Count operations** (comparisons) to validate complexity analysis

### 12.2 Common Mistakes to Avoid

❌ Using `Collections.sort()` or `Arrays.sort()` instead of implementing from scratch  
❌ Assuming binary search works on unsorted arrays  
❌ Using `<=` instead of `<` in the stability condition of insertion sort  
❌ Forgetting to track comparisons for complexity verification  
❌ Copying code from online sources or AI tools  
❌ Not preserving the relative order of equal elements

### 12.3 Submission Checklist

Before submitting, verify you have:

- [ ] All 10 required screenshots in proper order
- [ ] Java source file (`Main.java`) with original code only
- [ ] Answers to all 5 complexity analysis questions
- [ ] Discussion of algorithm selection for this problem
- [ ] Analysis of preconditions (especially for binary search)
- [ ] Explanation of stable sort and why it matters
- [ ] Time/space complexity for each algorithm
- [ ] Evidence of testing (comparison counts in screenshots)
- [ ] Lab report (1-2 pages, professional format)
- [ ] No plagiarism or copied code

---

## 13. File Structure Reference

```
DCIT 204 LAB 4/
├── Main.java                 # Complete implementation
├── Student_Records.csv       # Sample dataset
├── Expected_Observation.md   # Expected output reference
└── README.md                 # This file (comprehensive documentation)
```

---

## 14. Additional Resources & References

### 14.1 Key Concepts Reinforced

- **Array manipulation** using indices and loops
- **Algorithm correctness** through loop invariants
- **Performance analysis** using Big-O notation
- **Stable sorting** and its importance in real-world applications
- **Preconditions** and algorithm applicability

### 14.2 Related Topics for Further Study

- Merge Sort (stable sorting alternative)
- Quick Sort (average-case efficiency)
- Heap Search (worst-case guarantees)
- Hash Tables (constant-time searching)
- Complexity trade-offs between algorithms

---

## 15. Contact & Support

For questions or clarifications about this lab:

- Review the learning outcomes and complexity questions
- Check that your algorithm implementation matches the pseudocode exactly
- Verify preconditions before running algorithms
- Test with multiple input values to understand behavior
- Consult the Expected_Observation.md file for reference results

---

**Lab Version:** 1.0  
**Last Updated:** 2026  
**Institution:** Department of Computer Science  
**Course Code:** DCIT 204

---

_This comprehensive lab guide ensures students understand not just how to code the algorithms, but why they work, when to use them, and how to analyze their performance. All code must be original and submitted without AI generation or plagiarism._
