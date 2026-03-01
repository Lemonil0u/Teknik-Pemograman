abstract class Sortable {
    // Metode abstrak yang harus diimplementasikan oleh Employee
    public abstract int compare(Sortable b);

    // Implementasi Algoritma Shell Sort
    public static void shell_sort(Sortable[] a) {
        int n = a.length;
        // Memulai dengan gap besar, lalu dikurangi
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Sortable temp = a[i];
                int j;
                // Membandingkan elemen menggunakan metode compare() kustom
                for (j = i; j >= gap && a[j - gap].compare(temp) > 0; j -= gap) {
                    a[j] = a[j - gap];
                }
                a[j] = temp;
            }
        }
    }
}