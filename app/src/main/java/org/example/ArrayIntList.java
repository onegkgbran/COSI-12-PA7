package org.example;

import java.util.Arrays;

/**
 * ArrayIntList
 */
public class ArrayIntList {
    protected int[] internal;
    protected int lastNum = 0;

    /**
     * Constructs an ArrayIntList with an internal array size equal to the provided
     * capacity.
     * 
     * @param capacity the internal array size
     * @throws IllegalArgumentException Throws if capacity is less than 0.
     */
    public ArrayIntList(int capacity) throws IllegalArgumentException {
        if (capacity < 0) {
            throw new IllegalArgumentException("No negative numbers");
        } else {
            this.internal = new int[capacity];
        }
    }

    /**
     * Constructs an ArrayIntList with an internal array size of 10.
     */
    public ArrayIntList() {
        new ArrayIntList(10);
    }


    /**
     * Appends an int to the end of the array, if more space is needed, more space
     * is made.
     * 
     * @param value the int to append
     */
    public void add(int value) {
        internal = migrate(internal);
        internal[lastNum] = value;
        lastNum++;
    }

    /**
     * Adds an int at the provided index, shifting the int at that index and all
     * subsequent indexes to the right, makes more room in the array if necessary.
     * 
     * @param index the index at which the int will be added, must be <= size() and
     *              >= 0
     * @param value the value to be added to the array
     * @throws ArrayIndexOutOfBoundsException Throws if an invalid value of index is
     *                                        provided.
     */
    public void add(int index, int value) throws ArrayIndexOutOfBoundsException {
        if (index > size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int[] output = copy(migrate(internal));
        for (int i = 0; i < internal.length; i++) {
            if (i == index) {
                output[i] = value;
            }
            if (i > index) {
                output[i] = internal[i - 1];
            }
        }
        internal = output;
        lastNum++;
    }

    /**
     * Returns the value at the provided index.
     * 
     * @param index the index to be checked, must be <= size() and >= 0
     * @return an int which is the value at the provided index
     * @throws ArrayIndexOutOfBoundsException Throws if an invalid value of index is
     *                                        provided.
     */
    public int get(int index) throws ArrayIndexOutOfBoundsException {
        if (index > size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return internal[index];
    }

    /**
     * Returns the index of the first occurence of the provided value or -1 if the
     * value was not found.
     * 
     * @param value the value to search for
     * @return the index of the first occurence of the value or -1 if value is not
     *         present in the array
     */
    public int indexOf(int value) {
        for (int i = 0; i < internal.length; i++) {
            if (internal[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes the value at the provided index and shifts all subsequent indeces to
     * the left.
     * 
     * @param index the index to remove; must be <= size() and >= 0
     * @throws ArrayIndexOutOfBoundsException Throws if an invalid value of index is
     *                                        provided.
     */
    public void remove(int index) throws ArrayIndexOutOfBoundsException {
        if (index > size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int[] output = copy(internal);
        for (int i = 0; i < internal.length; i++) {
            if (i < index) {
                // do nothing
            }
            if (i >= index && i < internal.length - 1) {
                output[i] = internal[i + 1];
            }
        }
        lastNum--;
        internal = output;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayIntList)) {
            return false;
        }
        ArrayIntList a = (ArrayIntList) o;
        if (size() != a.size()) {
            return false;
        }
        int[] aInternal = new int[size()];
        for (int i = 0; i < a.size(); i++) {
            aInternal[i] = a.get(i);
        }
        if (Arrays.equals(trim(), aInternal)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String output = "[";
        for (int i = 0; i < size(); i++) {
            if (i < size() - 1) {
                output = output + internal[i] + ", ";
            } else {
                output = output + internal[i] + "]";
            }
        }
        return output;
    }

    /**
     * Removes all values from the array.
     */
    public void clear() {
        internal = new int[internal.length];
        lastNum = 0;
    }

    /**
     * Checks whether the array contains the given value
     * 
     * @param value the value to check against the array
     * @return returns true if the array contains the value, returns false if the
     *         array does not contain the value
     */
    public boolean contains(int value) {
        for (int i = 0; i < internal.length; i++) {
            if (internal[i] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resizes the array to ensure that it can fit the provided capacity
     * 
     * @param capacity an int representing the minimum value the array must have
     *                 after the function runs.
     */
    public void ensureCapacity(int capacity) {
        if (internal.length < capacity) {
            int[] output = new int[capacity];
            for (int i = 0; i < size(); i++) {
                output[i] = internal[i];
            }
            internal = output;
        }
    }

    /**
     * Checks if the array is empty.
     * 
     * @return true if the array is empty and false if the array is not
     *         empty
     */
    public boolean isEmpty() {
        if (lastNum == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the number of values in the array.
     * 
     * @return the number of values in the array
     */
    public int size() {
        return lastNum;
    }

    public int getInternalSize() {
        return internal.length;
    }

    /**
     * Throws an ArrayIndexOutOfBoundsException if index is not between min and max,
     * inclusive.
     * 
     * @param index the index to be checked
     * @param min   the minimum value of index that won't throw an exception
     * @param max   the maximum value of index that won't throw an exception
     * @throws ArrayIndexOutOfBoundsException throws if index is not between min and
     *                                        max
     */
    @SuppressWarnings("unused")
    private void checkIndex(int index, int min, int max) throws ArrayIndexOutOfBoundsException {
        if (index < min || index > max) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * If input is full, returns an array with the contents of the given array but
     * with one extra index, otherwise, returns input.
     * 
     * @param input the array to be copied over.
     * @return if input was full, returns an array with the same contents of the
     *         input array but with
     *         an extra index at the end, otherwise, returns input.
     */
    private int[] migrate(int[] input) {
        if (lastNum >= input.length) {
            int[] output = new int[input.length + 1];
            for (int i = 0; i < input.length; i++) {
                output[i] = input[i];
            }
            return output;
        } else {
            return input;
        }
    }

    /**
     * Returns a copy of the provided array.
     * 
     * @param input the array to be copied.
     * @return a shallow copy of the provided array.
     */
    private static int[] copy(int[] input) {
        int[] output = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }
        return output;
    }

    private int[] trim() {
        int[] output = new int[size()];
        for (int i = 0; i < size(); i++) {
            output[i] = internal[i];
        }
        return output;
    }
}
