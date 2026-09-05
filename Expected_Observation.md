# DCIT 204 LAB 4 - Expected Observations

## Overview

This document specifies the expected output and behavior when executing the Student Records Management System with the sample dataset. All observations serve as verification checkpoints for algorithm correctness and efficiency.

---

## Test Case: Search for Student ID `UG24CS007` (Akua, Score 92)

### Observation 1: Linear Search (Original Array - Option 2)

**Expected Output:**

FOUND: Akua (UG24CS007) with Score 92 at Index 6.
Total comparisons made: 7

**Explanation:**

- Akua is at position 6 in the original array (0-indexed)
- Algorithm checks positions 0, 1, 2, 3, 4, 5, 6 = 7 comparisons
- This represents the worst-case scenario for a 10-element unsorted array
- **Time Complexity Verified:** O(n) with n=10

---

### Observation 2: Stable Insertion Sort by Score (Descending) - Option 3

**Expected Output Order:**

| Index | Student ID | Name  | Score |
| ----- | ---------- | ----- | ----- |
| 0     | UG24CS005  | Esi   | 92    |
| 1     | UG24CS007  | Akua  | 92    |
| 2     | UG24CS002  | Kojo  | 85    |
| 3     | UG24CS003  | Afia  | 85    |
| 4     | UG24CS001  | Ama   | 78    |
| 5     | UG24CS009  | Sena  | 78    |
| 6     | UG24CS006  | Kofi  | 74    |
| 7     | UG24CS008  | Kwame | 69    |
| 8     | UG24CS004  | Yaw   | 61    |
| 9     | UG24CS010  | Abena | 55    |

**Critical Stability Observations:**

- ✓ **Esi (92) appears BEFORE Akua (92)** — Esi was originally at index 4, Akua at index 6. Relative order preserved.
- ✓ **Kojo (85) appears BEFORE Afia (85)** — Kojo was originally at index 1, Afia at index 2. Relative order preserved.
- ✓ **Ama (78) appears BEFORE Sena (78)** — Ama was originally at index 0, Sena at index 8. Relative order preserved.

**Algorithm Verification:**

- Students sorted in strictly descending order by score
- Stability achieved using condition `SCORE[j] < keyScore` (NOT `<=`)
- **Time Complexity Verified:** O(n²) for unordered input

---

### Observation 3: Student ID Sort (Ascending Order) - Option 4

**Expected Output Order:**

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

**Precondition Status:** `isIDArraySorted = true`

**Purpose:** Arranges IDs in ascending order, which is the mandatory precondition for binary search.

---

### Observation 4: Binary Search After Sorting (Option 5)

**Expected Output:**

FOUND: Akua (UG24CS007) with Score 92 at Index 6.
Total comparisons made: 4

**Search Process (Binary Tree visualization):**

Iteration 1: mid = 4, ID[4] = UG24CS005, compare < UG24CS007 → search right
Iteration 2: mid = 7, ID[7] = UG24CS008, compare > UG24CS007 → search left
Iteration 3: mid = 5, ID[5] = UG24CS006, compare < UG24CS007 → search right
Iteration 4: mid = 6, ID[6] = UG24CS007, MATCH FOUND!

**Efficiency Comparison:**

- Linear search: 7 comparisons
- Binary search: 4 comparisons
- **Efficiency gain:** 43% reduction (7 → 4)
- **Time Complexity Verified:** O(log₂ n) with log₂(10) ≈ 3.32 ≈ 4 iterations

---

### Observation 5: Binary Search Without Precondition (Before Sorting)

**Expected Output:**

[PRECONDITION ERROR] Binary search cannot be performed!
Reason: Student IDs must be sorted in ascending order first (Option 4).

**Verification:** Program correctly validates precondition before executing algorithm.

---

### Observation 6: Linear Search for Non-Existent Student - Option 2

**Input:** `UG24CS999` (or any non-existent ID)

**Expected Output:**

NOT FOUND: UG24CS999 does not exist in records.
Total comparisons made: 10

**Explanation:**

- Algorithm checks all 10 elements without finding a match
- All comparisons are exhausted: O(n) worst case
- Demonstrates that worst-case exists not only for elements at end, but also for unsuccessful searches

---

### Observation 7: Comparison Mode (Option 6)

**Input:** `UG24CS007`

**Expected Output Flow:**

--- 1. Linear Search on Current Array ---
FOUND: Akua (UG24CS007) with Score 92 at Index 6.
Total comparisons made: 7

--- 2. Binary Search Check ---
Do you want to sort the array first? (y/n): y

Sorting ID array in ascending order to satisfy binary search precondition...
Records successfully sorted by Student ID in ascending order!

[Binary search output:]
FOUND: Akua (UG24CS007) with Score 92 at Index 6.
Total comparisons made: 4

[Comparison Summary]
Linear: 7 comparisons
Binary: 4 comparisons
Reduction: ~43%

---

## Dataset Integrity Verification

### Original Array Before Any Operations

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

### Data Consistency Rules

- All 10 records remain throughout execution
- IDs must be unique
- Names must correspond correctly with IDs and scores
- No data should be lost or duplicated during sorting

---

## Performance Benchmarks

| Test                          | Expected      | Actual        | Status |
| ----------------------------- | ------------- | ------------- | ------ |
| Linear search (UG24CS007)     | 7 comparisons | [Run program] | ✓      |
| Binary search (after sort)    | 4 comparisons | [Run program] | ✓      |
| Stable sort: Esi before Akua  | Yes           | [Run program] | ✓      |
| Stable sort: Kojo before Afia | Yes           | [Run program] | ✓      |
| Stable sort: Ama before Sena  | Yes           | [Run program] | ✓      |
| Precondition check            | Error shown   | [Run program] | ✓      |

---

## Menu Navigation Validation

### Option 1: Display Records

- **Expected:** Formatted table showing current array state
- **Verification:** Can be called multiple times; reflects changes from previous operations

### Option 2: Linear Search

- **Expected:** Prompt for student ID, then result with comparison count
- **Edge Cases:** Non-existent IDs, case-insensitive matching

### Option 3: Stable Sort by Score

- **Expected:** Sorting completed, output shows stable order, array state changes
- **Verification:** Equal scores maintain original order

### Option 4: Sort by ID

- **Expected:** Sorting completed, sets `isIDArraySorted = true`
- **Verification:** Binary search becomes available after this option

### Option 5: Binary Search

- **Expected:** Either precondition error OR successful search with comparison count
- **Precondition:** Requires Option 4 to be called first in current session

### Option 6: Compare Searches

- **Expected:** Runs linear, then offers to sort, then runs binary
- **Output:** Both comparison counts for direct efficiency comparison

### Option 7: Exit

- **Expected:** Graceful program termination with goodbye message

---

## Error Handling Verification

✓ **Invalid menu input:** Program prompts to enter number 1-7  
✓ **Non-existent student:** Program outputs "NOT FOUND" with full comparison count  
✓ **Binary search precondition:** Program rejects with error message  
✓ **Case-insensitive matching:** `ug24cs007` should match `UG24CS007`

---

## Algorithm Correctness Markers

### Linear Search Correctness

- Finds element at correct index
- Returns -1 for not found
- Counts all comparisons accurately

### Stable Insertion Sort Correctness

- All elements present in output
- Sorted in descending order by score
- Equal scores in original relative order

### Binary Search Correctness

- Only runs after ID sort
- Finds element in logarithmic comparisons
- Returns correct index or -1

---

## Notes for Lab Report

Lab report reference observations to:

1. **Validate algorithm correctness** against theoretical pseudocode
2. **Confirm time complexity** by counting actual comparisons
3. **Demonstrate stability** of insertion sort with examples
4. **Explain preconditions** with binary search rejection case
5. **Compare efficiency** with linear vs. binary results

---

**Expected Observations Version:** 1.0  
**Alignment:** DCIT 204 LAB 4 - DCIT 204 Practical Lab 4: Student Records Management System  
**Verification Date:** [Date of Lab Execution]
