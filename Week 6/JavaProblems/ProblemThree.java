package JavaProblems;

import java.util.Arrays;

public class ProblemThree {

    public static void main(String[] args) {
        int arr[] = {12, 4, 3, 1, 9, 657};
        int n = 3; // Mencari elemen terbesar ke-3

        int ans = Arrays.stream(arr)             // [1] Konversi ke Stream<Integer>
                .boxed()                         // [2] Konversi int ke Integer
                .sorted((a, b) -> Integer.compare(b, a)) // Urutkan descending
                .skip(n - 1)                     // [3] Lewati 2 elemen pertama
                .findFirst()                     // [4] Ambil elemen berikutnya
                .orElse(0);                // [5] Nilai default jika tidak ada

        System.out.println("The 3rd largest element is: " + ans);
    }
}