public class EmployeeTest {
    public static void main(String[] args) {
        // Mendeklarasikan dan mengalokasikan array untuk 3 objek Employee
        Employee[] staff = new Employee[3];
        
        // Inisialisasi data karyawan
        staff[0] = new Employee("Antonio Rossi", 2000000, 1, 10, 1989);
        staff[1] = new Employee("Maria Bianchi", 2500000, 1, 12, 1991);
        staff[2] = new Employee("Isabel Vidal", 3000000, 1, 11, 1993);
        
        System.out.println("=== Data sebelum sorting ===");
        for (int i = 0; i < 3; i++){
            staff[i].print();
        }

        // Menaikkan gaji setiap staf sebesar 5%
        for (int i = 0; i < 3; i++) {
            staff[i].raiseSalary(5);
        }

        Sortable.shell_sort(staff);
        // Mencetak data dari setiap staf (sudah terurut)
        System.out.println("=== Data setelah sorting by salary ===");
        for (int i = 0; i < 3; i++) {
            staff[i].print();
        }
    }
}