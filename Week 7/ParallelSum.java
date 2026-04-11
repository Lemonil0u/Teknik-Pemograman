import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelSum {

    // Variabel shared untuk menyimpan total
    private static long totalSum = 0;

    // Method synchronized untuk menambahkan hasil parsial secara aman
    private static final ReentrantLock
    lock = new ReentrantLock();

    public static void addToTotal(long p) {
        lock.lock();
        try {
            totalSum += p;
        } finally {
            lock.unlock();  // selalu dilepas!
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan jumlah thread: ");
        int numThreads = scanner.nextInt();

        System.out.print("Masukkan angka akhir (n): ");
        int n = scanner.nextInt();

        if (numThreads <= 0 || n <= 0) {
            System.out.println("Input harus lebih besar dari 0!");
            scanner.close();
            return;
        }

        int chunkSize = n / numThreads;
        int remainder = n % numThreads;

        System.out.println("\n=== PROGRAM PENJUMLAHAN PARALEL ===");
        System.out.println("Jumlah Thread : " + numThreads);
        System.out.println("Angka Akhir   : " + n);
        System.out.println("Ukuran per chunk : " + chunkSize + " (sisa " + remainder + ")\n");

        // Siapkan array untuk thread dan task
        Thread[] threads = new Thread[numThreads];
        SumTask[] tasks = new SumTask[numThreads];

        int currentStart = 1;

        // Buat dan jalankan setiap thread
        for (int i = 0; i < numThreads; i++) {
            int start = currentStart;
            int end = (i == numThreads - 1) ? n : start + chunkSize - 1;

            String threadName = "Thread-" + (i + 1);

            tasks[i] = new SumTask(start, end, threadName);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();

            currentStart = end + 1;
        }

        // Tunggu semua thread selesai
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
        System.out.println("[ERROR] ...");
        Thread.currentThread().interrupt();
        scanner.close();
        return;
        }

        // Kumpulkan semua hasil parsial ke total
        for (SumTask task : tasks) {
            addToTotal(task.getPartialSum());
        }

        // Tampilkan hasil akhir
        System.out.println("\n=== HASIL AKHIR ===");
        System.out.println("Total penjumlahan dari 1 sampai " + n + " adalah: " + totalSum);

        // Verifikasi dengan rumus
        long expected = (long) n * (n + 1) / 2;
        System.out.println("Hasil yang diharapkan (rumus): " + expected);

        if (totalSum == expected) {
            System.out.println("Hasil benar");
        } else {
            System.out.println("Ada kesalahan perhitungan");
        }
    }

    // ==================== CLASS SUMTASK DI DALAM FILE YANG SAMA ====================
    static class SumTask implements Runnable {
        private final int start;
        private final int end;
        private final String threadName;
        private long partialSum = 0;

        public SumTask(int start, int end, String threadName) {
            this.start = start;
            this.end = end;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            System.out.println(threadName + " mulai menghitung dari " + start + " sampai " + end);
            
            for (int i = start; i <= end; i++) {
                partialSum += i;
            }
            
            System.out.println(threadName + " selesai. Hasil parsial = " + partialSum);
        }

        public long getPartialSum() {
            return partialSum;
        }
    }
}