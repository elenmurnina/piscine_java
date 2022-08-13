package edu.school21.rush00.logic.minheap;

import edu.school21.rush00.logic.tile.Tile;

public class MinHeap {
    private Tile[] hArr;
    private int capacity;

    public int getHeapSize() {
        return heapSize;
    }

    private int heapSize;

    private int parent(int idx) {
        return ((idx - 1) / 2);
    }

    private int	left(int idx)
    {
        return (2 * idx + 1);
    }

    private int	right(int idx)
    {
        return (2 * idx + 2);
    }

    private void swap(int i, int j)
    {
        Tile temp = hArr[i];
        hArr[i] = hArr[j];
        hArr[j] = temp;
    }

    public MinHeap(int capacity) {
        heapSize = 0;
        this.capacity = capacity;
        hArr = new Tile[capacity];
    }

    public void insertKey(Tile newElem) {
        if (heapSize == capacity) {
            return;
        }
        heapSize++;
        int i = heapSize - 1;
        hArr[i] = newElem;
        hArr[i].setHeapIndex(i);
        while (i != 0 && hArr[parent(i)].getfScore()
                > hArr[i].getfScore()) {
            swap(i, parent(i));
            i = parent(i);
            hArr[i].setHeapIndex(i);
        }
    }

    private void minHeapify(int rootIndex) {
        int l = left(rootIndex);
        int r = right(rootIndex);
        int smallest = rootIndex;
        if (l < heapSize && hArr[l].getfScore() <
            hArr[rootIndex].getfScore())
            smallest = l;
        if (r < heapSize && hArr[r].getfScore() <
            hArr[smallest].getfScore())
            smallest = r;
        if (smallest != rootIndex) {
            swap(rootIndex, smallest);
            minHeapify(smallest);
        }
    }

    public Tile extractMin() {
        if (heapSize <= 0) {
            return (null);
        }
        if (heapSize == 1) {
            heapSize--;
            return hArr[0];
        }
        Tile root = hArr[0];
        hArr[0] = hArr[heapSize - 1];
        heapSize--;
        minHeapify(0);
        return (root);
    }

    public void decreaseKey(int index, int newValue) {
        hArr[index].setfScore(newValue);
        while (index != 0 && hArr[parent(index)].getfScore() >
            hArr[index].getfScore()) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public void deleteKey(int index) {
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }
}
