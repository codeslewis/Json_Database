import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int[] inputArray = readArrayFromConsole();
        int longestSequence = findLongestSequence(inputArray);
        System.out.println(longestSequence);
    }

    private static int findLongestSequence(int[] inputArray) {
        int longest = 0;
        int current = 1;
        // for each
        // count until the sequence breaks
        // if count is bigger than longest, update longest
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > inputArray[i - 1]) {
                current++;
            } else {
                longest = Math.max(longest, current);
                current = 1;
            }
        }
        return Math.max(longest, current);
    }

    private static int[] readArrayFromConsole() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        return Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}