

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    int nextIndex = 0;
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    private boolean validIndex(int index) {
        return index >= 0 & index < nextIndex;
    }


    @Override
    public Integer get(int index) {
        if (!validIndex(index))
            throw new IllegalArgumentException("The Index is Illegal");
        return arr[index];
    }

    @Override
    public Integer search(int k) {
        int output = -1;
        int low = 0;
        int high = nextIndex - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == k) {
                output = mid;
                break;

            } else if (k < arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return output;

    }

    @Override
    public void insert(Integer x) {
        int index = findTrueIndex(x);
        if (index != -1) {
            if (nextIndex >= arr.length)
                throw new RuntimeException("The array is full");

            stack.push(x);

            insert(x, index);
            nextIndex = nextIndex + 1;
        }

    }

    // this function find the index that the integer should be in after the insertion
    private int findTrueIndex(Integer x) {
        int start = 0, end = nextIndex - 1;
        boolean smaller = true;
        int mid = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            if (arr[mid] == x) {
                mid = -1;
                break;
            } else if (arr[mid] > x) {
                end = mid - 1;
                smaller = true;
            } else {
                start = mid + 1;
                smaller = false;
            }
        }
        if (mid != -1) {
            if (smaller)
                return mid;
            else
                return mid + 1;

        } else
            return mid;

    }


    // // this function insert an Integer to the index place giving, and moving the other values
    private void insert(Integer x, int index) {
        for (int i = nextIndex-1; i >= index; i = i - 1)
            arr[i +1] = arr[i];

        arr[index] = x;

    }

    @Override
    public void delete(Integer index) {

        if (!validIndex(index))
            throw new IllegalArgumentException("The Index is Illegal");

        stack.push(get(index));

        for (int i = index; i < nextIndex - 1; i++) {
            arr[i] = arr[i + 1];
        }
        nextIndex = nextIndex - 1;
    }

    @Override
    public Integer minimum() {
        if (nextIndex == 0)
            throw new RuntimeException("There is no minimum without value");

        return 0;
    }


    @Override
    public Integer maximum() {
        if (nextIndex == 0)
            throw new RuntimeException("There is no maximum without value");

        return nextIndex - 1;
    }

    @Override
    public Integer successor(Integer index) {

        if (!validIndex(index))
            throw new IllegalArgumentException("The Index is Illegal");

        if (index == nextIndex - 1)
            throw new RuntimeException("This is your biggest  he has no successor");

        return index + 1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (!validIndex(index))
            throw new IllegalArgumentException("The Index is Illegal");

        if (index == 0)
            throw new RuntimeException("This is your smallest number he has no predecessor");

        return index - 1;
    }

    @Override
    public void backtrack() {
        if (stack.isEmpty())
            throw new RuntimeException("No operations to undo");

        int lastMove = (int) stack.pop();
        int indexOfLastMove =search(lastMove);
        if (indexOfLastMove == -1)
            insert(lastMove);
        else
            delete(indexOfLastMove);

        //because delete push the value in again
        stack.pop();
    }

    @Override
    public void retrack() {
        /////////////////////////////////////
        // Do not implement anything here! //
        /////////////////////////////////////
    }

    @Override
    public void print() {
        String toPrint = "";
        if (nextIndex>0) {
            for (int i = 0; i < nextIndex; i++)
                toPrint = toPrint + arr[i] + " ";
            System.out.println(toPrint.substring(0, toPrint.length() - 1));
        }
        else {
            System.out.println("");
        }
    }

}
