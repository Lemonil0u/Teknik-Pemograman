public class Restaurant {
    // Atribut privat (Encapsulation)
    private String[] nama_makanan;
    private double[] harga_makanan;
    private int[] stok;
    private static byte id = 0;

    public Restaurant() {
        nama_makanan = new String[10];
        harga_makanan = new double[10];
        stok = new int[10];
    }

    // Method untuk menambah menu
    public void tambahMenuMakanan(String nama, double harga, int stok) {
        this.nama_makanan[id] = nama;
        this.harga_makanan[id] = harga;
        setStok(id, stok); // Menggunakan setter untuk validasi
    }

    // Setter dengan validasi 
    public void setStok(int index, int jumlah) {
        if (jumlah >= 0) {
            this.stok[index] = jumlah;
        } else {
            System.out.println("Error: Stok tidak boleh negatif!");
        }
    }

    // Getter untuk nama makanan
    public String getNamaMakanan(int index) {
        return nama_makanan[index];
    }

    public void tampilMenuMakanan() {
        System.out.println("\n--- DAFTAR MENU ---");
        for (int i = 0; i < id; i++) {
            if (!isOutOfStock(i)) {
                System.out.println(nama_makanan[i] + " [" + stok[i] + "]\tRp. " + harga_makanan[i]);
            }
        }
    }

    public boolean isOutOfStock(int index) {
        return stok[index] == 0;
    }

    public static void nextId() {
        id++;
    }

    // Fitur Pemesanan
    public boolean pesanMenu(String nama, int jumlah) {
        for (int i = 0; i < id; i++) {
            // Cek apakah nama makanan cocok (ignore case agar fleksibel)
            if (nama_makanan[i].equalsIgnoreCase(nama)) {
                if (stok[i] >= jumlah) {
                    stok[i] -= jumlah; // Stok otomatis berkurang
                    System.out.println("Pemesanan Berhasil: " + nama + " sebanyak " + jumlah);
                    return true;
                } else {
                    System.out.println("Maaf, stok tidak mencukupi!");
                    return false;
                }
            }
        }
        System.out.println("Menu tidak ditemukan!");
        return false;
    }
}