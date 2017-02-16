package algo.sort;

import java.util.Comparator;
import java.util.List;

public class Sorter {

	public static <T> void sort(List<T> data, Comparator<? super T> comparator, ISortingStrategy<T> sortingStrategy) {
		sortingStrategy.sort(data, comparator);
	}

	public static <T extends Comparable<? super T>> void sort(List<T> data, ISortingStrategy<T> sortingStrategy) {
		sortingStrategy.sort(data, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}

	public static <T> boolean isSorted(List<T> data, Comparator<? super T> comparator) {
		if (data == null) {
			return true;
		}
		for (int i = 0; i < data.size() - 1; i++) {
			if (comparator.compare(data.get(i), data.get(i + 1)) > 0) {
				return false;
			}
		}
		return true;
	}

	public static <T extends Comparable<? super T>> boolean isSorted(List<T> data) {
		return isSorted(data, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}

}
