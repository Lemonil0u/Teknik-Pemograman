import id.ac.polban.employee.model.*;
import id.ac.polban.employee.service.EmployeeService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        Scanner input = new Scanner(System.in);

        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n=== MENU MANAJEMEN KARYAWAN ===");
            System.out.println("1. Tambah Karyawan Baru");
            System.out.println("2. Tampilkan Daftar Karyawan");
            System.out.println("3. Berikan Kenaikan Gaji");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID Karyawan (Angka): ");
                    int idBaru = input.nextInt();
                    input.nextLine();
                    
                    System.out.print("Masukkan Nama Karyawan: ");
                    String namaBaru = input.nextLine();
                    
                    System.out.print("Masukkan Nama Departemen: ");
                    String namaDept = input.nextLine();
                    
                    System.out.print("Masukkan Tipe Pekerjaan (Full-Time/Contract): ");
                    String tipeKerja = input.nextLine();
                    
                    System.out.print("Masukkan Gaji Awal: ");
                    double gajiBaru = input.nextDouble();

                    // Membuat objek pendukung (Aggregation)
                    Department dept = new Department(namaDept);
                    EmploymentType type = new EmploymentType(tipeKerja);
                    
                    // Membuat objek Employee (Object Construction)
                    Employee empBaru = new Employee(idBaru, namaBaru, dept, type, gajiBaru);
                    
                    // Menyimpan ke service (Dependency)
                    service.addEmployee(empBaru);
                    System.out.println("Karyawan berhasil ditambahkan!");
                    break;

                case 2:
                    tampilkanSemuaKaryawan(service);
                    break;

                case 3:
                    System.out.print("Masukkan ID Karyawan yang akan naik gaji: ");
                    int idCari = input.nextInt();
                    System.out.print("Masukkan Persentase Kenaikan (misal 10 untuk 10%): ");
                    double persen = input.nextDouble();
                    
                    service.raiseSalary(idCari, persen);
                    System.out.println("Proses kenaikan gaji selesai.");
                    break;

                case 4:
                    berjalan = false;
                    System.out.println("Moga dinaikin gajinya!");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        }
        input.close();
    }

    private static void tampilkanSemuaKaryawan(EmployeeService service) {
        System.out.println("\n--- DAFTAR KARYAWAN ---");
        System.out.println("ID\tNama\t\tDept\t\tTipe\t\tGaji");
        System.out.println("-------------------------------------------------------------------------");
    
        for (int i = 1; i <= 1000; i++) {
            Employee e = service.getEmployee(i);
            if (e != null) {
                System.out.println(
                    e.getId() + "\t" + 
                    e.getName() + "\t\t" + 
                    e.getDepartment().getName() + "\t\t" + 
                    e.getType().getType() + "\t\tRp " + 
                    String.format("%.2f", e.getSalary())
                );
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total Employee: " + Employee.getTotalEmployees());
    }
}