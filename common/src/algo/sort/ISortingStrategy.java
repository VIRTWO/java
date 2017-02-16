package algo.sort;

import java.util.Comparator;
import java.util.List;

public interface ISortingStrategy<T> {

	public void sort(List<T> data, Comparator<? super T> comparator);

}
