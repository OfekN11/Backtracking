

public class Warmup {

    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        // TODO: implement your code here
        int index = 0;
        boolean found = false;
        int output = -1;
        while (index < arr.length & !found) {
            for (int i = 0; i < forward & index < arr.length & !found; i++) {
                myStack.push(arr[index]);
                found = x == arr[index];
                if (found) {
                    output = index;
                }
                index = index + 1;
            }
            for (int i = 0; i < back & index < arr.length & !found; i++) {

                found = (int) myStack.pop() == x;
                if (found) {
                    output = index;
                }
                index = index - 1;

            }
        }
        return output;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int output = -1;
        int low = 0;
        int high = arr.length - 1;
        int mid = (low + high) / 2;
        int inconsistencies = 0;


        while (low <= high) {
            myStack.push(low);
            myStack.push(high);
            myStack.push(mid);
            if (arr[mid] == x) {

                output = mid;
                break;
            } else if (x < arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }

            mid = (low + high) / 2;

            inconsistencies = Consistency.isConsistent(arr);
            for (int i = 0; i < inconsistencies; i++) {

                mid = (int) myStack.pop();
                high = (int) myStack.pop();
                low = (int) myStack.pop();
                if (arr[mid]==x)
                    return mid;

            }

        }

        return output;
    }
    

}


