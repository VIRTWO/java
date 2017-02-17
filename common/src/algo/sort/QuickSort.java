package algo.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSort<T> implements ISortingStrategy<T> {

	public static enum PartitionMode {
		LOMUTO, HOARE
	}

	public static interface IPivotSelector {
		public int selectIndex(List<?> data);
	}

	private final PartitionMode partitionMode;
	private final IPivotSelector pivotSelector;

	public QuickSort(PartitionMode partitionMode, IPivotSelector pivotSelector) {
		this.partitionMode = partitionMode;
		this.pivotSelector = pivotSelector;
	}

	public QuickSort(PartitionMode partitionMode) {
		this(partitionMode, new IPivotSelector() {
			private final Random random = new Random();

			@Override
			public int selectIndex(List<?> data) {
				return random.nextInt(data.size());
			}
		});
	}

	public QuickSort() {
		this(PartitionMode.LOMUTO);
	}

	@Override
	public void sort(List<T> data, Comparator<? super T> comparator) {
		if (data != null) {
			switch (partitionMode) {
			case HOARE:
				sortUsingHoarePartition(data, comparator);
				break;
			case LOMUTO:
				sortUsingLomutoPartition(data, comparator);
				break;
			default:
				throw new IllegalStateException("Partition mode " + partitionMode + " is not supported.");
			}
		}
		// null is always sorted
	}

	private void sortUsingLomutoPartition(List<T> data, Comparator<? super T> comparator) {
		if (data.size() >= 2) {
			int p = lomutoPartition(data, comparator);
			sortUsingLomutoPartition(data.subList(0, p), comparator);
			sortUsingLomutoPartition(data.subList(p + 1, data.size()), comparator);
		}
	}

	private void sortUsingHoarePartition(List<T> data, Comparator<? super T> comparator) {
		if (data.size() >= 2) {
			int[] p = hoarePartition(data, comparator);
			sortUsingHoarePartition(data.subList(0, p[0]), comparator);
			sortUsingHoarePartition(data.subList(p[1], data.size()), comparator);
		}
	}

	private int lomutoPartition(List<T> data, Comparator<? super T> comparator) {
		int pivotPosition = -1; // no position
		int pivotIndex = pivotSelector.selectIndex(data);
		// move pivot element to end
		swap(data, pivotIndex, data.size() - 1);
		T pivot = data.get(data.size() - 1);
		for (int i = 0; i < data.size() - 1; i++) {
			if (comparator.compare(data.get(i), pivot) <= 0) {
				pivotPosition = pivotPosition + 1;
				swap(data, pivotPosition, i);
			}
		}
		// move pivot to its position
		// which is after the elements smaller than pivot
		pivotPosition = pivotPosition + 1;
		swap(data, pivotPosition, data.size() - 1);
		// when we create low and high side we will ignore element at this
		// position
		return pivotPosition;
	}

	private int[] hoarePartition(List<T> data, Comparator<? super T> comparator) {
		T pivot = data.get(pivotSelector.selectIndex(data));
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
			 * [highSideIndex + 1, data.length - 1].
			 * 
			 * when lowSideIndex == highSideIndex : [0, lowSideIndex - 1] and
			 * [highSideIndex + 1, data.length - 1] as this condition is true
			 * only when they meet at pivot
			 * 
			 * so we can return lowSideIndex - 1, highSideIndex + 1 from here or
			 * {lowSideIndex, highSideIndex + 1} from here since subList
			 * excludes toIndex
			 * 
			 * if lowSideIndex == highSideIndex then we will basically exclude P
			 * from future iterations
			 * 
			 * if lowSideIndex > highSideIndex then we will divide at crossing
			 * point
			 * 
			 * in case of lowSideIndex > highSideIndex we are essentially
			 * returning (highSideIndex + 1) back.
			 */

			/*
			 * if pivot is always 0 then we can always return {highSideIndex +
			 * 1, highSideIndex + 1} When lowSideIndex == highSideIndex it will
			 * mean pivot will be included in one of the sides. So low side =
			 * [0, highSideIndex] high side = [highSideIndex + 1, data.length]
			 * so we can always return highSideIndex + 1.
			 *
			 * In above case we may end up returning highSideIndex + 1 ==
			 * data.size() but subList method that we use in recursion fails
			 * only for (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
			 * so we are safe.
			 *
			 * if pivot is always last element then we cannot always return
			 * highSideIndex + 1 as data.length == highSideIndex + 1 it will
			 * result in low = [0, data.length - 1] and high = [data.length,
			 * data.length] which will lead us to spin on low side. If we start
			 * returning just highSideIndex then then we will spin on high side.
			 */

			/*
			 * Instead of returning 2 values we can swap pivot to 0 position and
			 * take advantage of the way subList works. But I like it this way.
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

}
