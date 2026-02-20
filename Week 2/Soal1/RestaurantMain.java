import java.util.Scanner;

public class RestaurantMain {
    public static void main(String[] args) {
        Restaurant menu = new Restaurant();
        Scanner input = new Scanner(System.in);

        // Inisialisasi Menu
        menu.tambahMenuMakanan("Pizza", 250000, 20);
        Restaurant.nextId();
        menu.tambahMenuMakanan("Spaghetti", 80000, 20);
        Restaurant.nextId();
        menu.tambahMenuMakanan("Tenderloin", 60000, 30);
        Restaurant.nextId();
        menu.tambahMenuMakanan("Chicken", 45000, 30);
        Restaurant.nextId();

        menu.tampilMenuMakanan();

        // Proses Pemesanan dengan Pengulangan (Looping)
        boolean orderSelesai = false;
        while (!orderSelesai) {
            System.out.print("\nMasukkan nama makanan yang ingin dipesan: ");
            String namaOrder = input.nextLine();
            
            System.out.print("Masukkan jumlah pesanan: ");
            int jumlahOrder = input.nextInt();
            input.nextLine(); // Membersihkan buffer

            // Validasi sukses atau gagal
            boolean sukses = menu.pesanMenu(namaOrder, jumlahOrder);
            
            if (sukses) {
                orderSelesai = true; // Keluar loop jika berhasil
            } else {
                System.out.println("Silakan coba lagi.");
            }
        }

        // Tampilkan menu akhir untuk melihat perubahan stok
        menu.tampilMenuMakanan();
        input.close();
    }
}