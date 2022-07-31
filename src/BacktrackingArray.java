

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int nextIndex = 0;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index) {
        // TODO: implement your code here
        if (!validIndex(index))
            throw new IllegalArgumentException("The Index is Illegal");
        return arr[index]; // temporal return command to prevent compilation error
    }

    private boolean validIndex(int index) {
        return index >= 0 & index < nextIndex;
    }

    @Override
    public Integer search(int k) {
        int output = -1;
        boolean found = false;
        for (int i = 0; i < nextIndex & !found; i++) {
            if (arr[i] == k) {
                output = i;
                found = true;
            }
        }
        return output;
    }

    @Override
    public void insert(Integer x) {
        if (search(x) == -1) {
            if (nextIndex >= arr.length)
                throw new RuntimeException("There is no more room in the array");
            stack.push(nextIndex);
            stack.push(x);
            insert(x,nextIndex);
        }
    }
    private void insert(int x,int index){
        for (int i =nextIndex; i>index;i--){
            arr[i] = arr[i-1];
        }
        arr[index] = x;
        nextIndex = nextIndex +1;
    }


    @Override
    public void delete(Integer index) {

        if (!validIndex(index))
            throw new IllegalArgumentException("Index most be grater then zero and smaller then the array size");

        // if the input index they enter is higher "nextIndex" then it has no effect on the array
        if (index < nextIndex) {
            stack.push(index);
            stack.push(get(index));
            moveBack(index);
            nextIndex = nextIndex - 1;
        }
    }

    private void moveBack(int index){
        for (int i = index;i<nextIndex-1;i++){
            arr[i] = arr[i+1];
        }
    }

    @Override
    public Integer minimum() {
        int output = 0;
        if (nextIndex == 0)
            throw new RuntimeException("Without values in the array there is no minimum");

        for (int i = 1; i < nextIndex; i++) {
            if (arr[i] < arr[output])
                output = i;
        }
        return output;
    }

    @Override
    public Integer maximum() {
        int output = 0;

        if (nextIndex == 0)
            throw new RuntimeException("Without values in the array there is no maximum");

        for (int i = 1; i < nextIndex; i++) {
            if (arr[i] > arr[output])
                output = i;
        }
        return output;
    }

    @Override
    public Integer successor(Integer index) {

        int output = -1;

        if (!validIndex(index))
            throw new RuntimeException("entered Illigal index");

        boolean successorExist = false;
        for (int i = 0; i < nextIndex & !successorExist; i++) {
            if (arr[i] > arr[index]) {
                output = i;
                successorExist = true;
            }
        }

        for (int i = output; i < nextIndex; i++) {
            if (arr[output] > arr[i] & arr[i] > arr[index])
                output = i;
        }
        return output;
    }

    @Override
    public Integer predecessor(Integer index) {
        // TODO: implement your code here

        int output = -1;

        if (!validIndex(index))
            throw new RuntimeException("Without values in the array there is no minimum");

        boolean predecessorExist = false;
        for (int i = 0; i < nextIndex & !predecessorExist; i++) {
            if (arr[i] < arr[index]) {
                output = i;
                predecessorExist = true;
            }
        }

        for (int i = output; i < arr.length; i++) {
            if (arr[output] < arr[i] & arr[i] < arr[index])
                output = i;
        }
        return output;
    }

    @Override
    public void backtrack() {

        if (stack.isEmpty())
            throw new RuntimeException("No operations to undo");

        int lastMove = (int) stack.pop();
        int indexOfLastMove = search(lastMove);
        if (indexOfLastMove== -1)
            insert(lastMove,(int) stack.pop());
        else {
            backtrackDelete(indexOfLastMove);
            stack.pop();
        }

        // insert and delete put back the value in the stuck

    }

    private void backtrackDelete(int index) {
        if (!validIndex(index))
            throw new IllegalArgumentException("Index most be grater then zero and smaller then the array size");

        // if the input index they enter is higher "nextIndex" then it has no effect on the array
        if (index < nextIndex) {
            moveBack(index);
            nextIndex = nextIndex - 1;
        }
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
            for (int i=0;i<nextIndex;i++)
                toPrint += get(i) + " ";

            System.out.println(toPrint.substring(0, toPrint.length() - 1));
        }
        else
            System.out.println("");
    }

}
