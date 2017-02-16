package algo.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSort<T> implements ISortingStrategy<T> {

	private final Random random = new Random();

	@Override
	public void sort(List<T> data, Comparator<? super T> comparator) {
		if (data != null) {
			sortUsingHoarePartition(data, comparator);
		}
		// null is always sorted
	}

	private void sortUsingHoarePartition(List<T> data, Comparator<? super T> comparator) {
		if (data.size() >= 2) {

			int[] p = hoarePartition(data, comparator);

			sort(data.subList(0, p[0]), comparator);

			sort(data.subList(p[1], data.size()), comparator);
		}
	}

	private int[] hoarePartition(List<T> data, Comparator<? super T> comparator) {
		T pivot = data.get(getPivotIndex(data));
		int lowSideIndex = -1;
		int highSideIndex = data.size();
		while (true) {
			do {
				lowSideIndex++;
			} while (comparator.compare(data.get(lowSideIndex), pivot) < 0);

			do {
				highSideIndex--;
			} while (comparator.compare(data.get(highSideIndex), pivot) > 0);

			/*
			 * when lowSideIndex > highSideIndex : they would have gone one step
			 * extra. so we should have partitions [0, lowSideIndex - 1] and
			 * [highSideIndex + 1, data.length].
			 * 
			 * when lowSideIndex == highSideIndex : [0, lowSideIndex - 1] and
			 * [highSideIndex + 1, data.length] as this condition is true only
			 * when they meet at pivot
			 * 
			 * so we can return lowSideIndex - 1, highSideIndex + 1 from here or
			 * {lowSideIndex, highSideIndex + 1} from here since subList
			 * excludes toIndex if lowSideIndex == highSideIndex then we will
			 * basically exclude P from future if lowSideIndex > highSideIndex
			 * then we will divide at crossing point
			 * 
			 * so essentially in case of lowSideIndex > highSideIndex we are
			 * returning (highSideIndex + 1) back.
			 */

			/*
			 * if pivot is always 0 then we can always return {highSideIndex +
			 * 1, highSideIndex + 1} When lowSideIndex == highSideIndex it will
			 * mean pivot will be included in low side. So low side = [0,
			 * highSideIndex] high side = [highSideIndex + 1, data.length] so we
			 * can always return highSideIndex + 1. In java when toIndex and
			 * fromIndex are equal, sublist returns a empty list and we are
			 * saved from Array out of bound exception.
			 *
			 * if pivot is always last element then we cannot always return
			 * highSideIndex + 1 as data.length == highSideIndex + 1 it will
			 * result in low = [0, data.length - 1] and high = [data.length,
			 * data.length] which will lead us to spin on low side. If we start
			 * returning just highSideIndex then then we will spin on high side.
			 * Also we may end up with ArrayOutOfBound exception unlike previous
			 * case.
			 */

			/*
			 * As an alternate we can swap pivot to first place before starting
			 * partitioning. But returning two indexes seem nicer to me.
			 */

			if (lowSideIndex >= highSideIndex) {
				return new int[] { lowSideIndex, highSideIndex + 1 };
			}

			swap(data, lowSideIndex, highSideIndex);
		}
	}

	private void swap(List<T> data, int index1, int index2) {
		T holder = data.get(index1);
		data.set(index1, data.get(index2));
		data.set(index2, holder);
	}

	protected int getPivotIndex(List<T> data) {
		return random.nextInt(data.size());
	}

}
