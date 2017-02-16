package sample;

import java.util.Arrays;
import java.util.List;

import algo.sort.ISortingStrategy;
import algo.sort.QuickSort;
import algo.sort.Sorter;

public class SorterTest {

	public static void main(String[] args) {
		
		ISortingStrategy<Integer> sortingStrategy = new QuickSort<Integer>();
		
		List<Integer> data = Arrays.asList(1, 5, 15, 19, 20, 11, 2, 4, 9, 5, 18, 6, 19, 20, 7);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));

		data = Arrays.asList(1, 1, 1, 1);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));

		data = Arrays.asList(5, 5, 5, 6, 6, 6);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));

		data = Arrays.asList(5, 15, 19, 20, 11, 2, 4, 9, 5, 18, 6, 19, 20, 7);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));
		
		data = Arrays.asList(5, 15);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));

		data = Arrays.asList(5);
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));
		
		data = Arrays.asList();
		Sorter.sort(data, sortingStrategy);
		System.out.println(Sorter.isSorted(data));
		
		System.out.println("Done!");
	}

}
