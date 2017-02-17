package sample;

import java.util.Arrays;
import java.util.List;

import algo.sort.ISortingStrategy;
import algo.sort.QuickSort;
import algo.sort.Sorter;

public class SorterTest {

	// Create a copy before running any sorting
	public static Integer[][] TEST_DATA = new Integer[][] { 
			{ 1, 5, 15, 19, 20, 11, 2, 4, 9, 5, 18, 6, 19, 20, 7 },
			{ 3, 2, 67, 11, 9, 1, 1, 1, 1 }, 
			{ 1, 1, 1, 1 }, 
			{ 7, 7, 7, 6, 6, 6 },
			{ 5, 15, 19, 20, 11, 2, 4, 9, 5, 18, 6, 19, 20, 7 }, 
			{ 25, 15 }, { 5 }, 
			{}, 
			{ 1, 2, 3, 4, 5 },
			{ 5, 4, 3, 2, 1 }, 
			{ 1, 1, 2, 2, 3, 3, 4, 4 }, 
			{ 4, 4, 3, 3, 2, 2, 1, 2 } 
		};

	public static void testQuickSort() {
		for (QuickSort.PartitionMode p : QuickSort.PartitionMode.values()) {
			ISortingStrategy<Integer> sortingStrategy = new QuickSort<Integer>(p);
			for (Integer[] testData : TEST_DATA) {
				List<Integer> data = Arrays.asList(Arrays.copyOf(testData, testData.length));
				Sorter.sort(data, sortingStrategy);
				if (!Sorter.isSorted(data)) {
					throw new RuntimeException("Sorting failed.");
				}
			}
		}
	}

	public static void main(String[] args) {
		testQuickSort();
		System.out.println("Done!");
	}

}
