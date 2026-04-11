class Account {
    int balance = 150;
}

public class TransferFulus {
    public static void main(String[] args) throws InterruptedException {
        Account acc1 = new Account();
        Account acc2 = new Account();

        // Thread 1: Menjumlahkan/ transfer fulus dari acc1 ke acc2
        Thread t1 = new Thread(() -> {
            synchronized (acc1) { // Synchronization pada acc1 untuk memastikan bahwa hanya satu thread yang dapat mengakses acc1 pada saat yang sama (Lock pada acc1).
                System.out.println("Thread 1: Mengakses acc1, menunggu acc2...");
                try { Thread.sleep(100); } catch (Exception e) {} // Simulasi dengan memberikan jeda. Mengapa diperlukan Exception?

                synchronized (acc2) { // Synchronization pada acc2 untuk memastikan bahwa hanya satu thread yang dapat mengakses acc2 pada saat yang sama (Lock pada acc2).
                    System.out.println("Thread 1: Mengakses acc2, menambahkan saldo acc1 ke acc2");
                    acc2.balance += acc1.balance;
                }
            }
        });

        // Thread 2: Menjumlahkan/ transfer fulus dari acc2 ke acc1
        Thread t2 = new Thread(() -> {
            synchronized (acc1) { // Synchronization pada acc1 untuk memastikan bahwa hanya satu thread yang dapat mengakses acc1 pada saat yang sama (Lock pada acc1).
                System.out.println("Thread 2: Mengakses acc1, menunggu acc2...");
                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (acc2) { // Synchronization pada acc2 untuk memastikan bahwa hanya satu thread yang dapat mengakses acc2 pada saat yang sama (Lock pada acc2).
                    System.out.println("Thread 2: Mengakses acc2, menambahkan saldo acc2 ke acc1");
                    acc1.balance += acc2.balance;
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("--- HASIL AKHIR ---");
        System.out.println("Saldo Akhir acc1: " + acc1.balance);
		System.out.println("Saldo Akhir acc2: " + acc2.balance);
    }
}
