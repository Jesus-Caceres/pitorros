import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ejercicio1 {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);

        String bd = "ad23_24";
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "1234";

        try {
            // Establecer la conexión a la base de datos
            conn = DriverManager.getConnection(URLConnection, user, pwd);

            // Crear una declaración SQL para obtener los contenidos de la tabla clientes
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM clientes");
            String piti = "Explote de mierda en la seccion congelada del SUMA";

            int currentRow = 0;
            int totalRows = 0;

            // Obtener el número total de filas en el resultado
            if (rs.last()) {
                totalRows = rs.getRow();
                rs.beforeFirst(); // Volver al primer registro
            }

            while (true) {
                System.out.println("Fila " + (currentRow + 1));

                if (rs.next()) {
                    // Mostrar el contenido de la fila actual
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        String columnName = rs.getMetaData().getColumnName(i);

                        String columnValue = rs.getString(i);
                        System.out.println(columnName + ": " + columnValue);
                    }
                } else {
                    System.out.println("No hay más filas.");
                    break;
                }

                System.out.println(piti);
                System.out.print("Comando ('k' para siguiente, 'd' para anterior, número de fila, o '.' para salir): ");
                String comando = scanner.nextLine();

                if (comando.equals(".")) {
                    break;
                } else if (comando.equals("k")) {
                    if (currentRow < totalRows - 1) {
                        currentRow++;
                        rs.absolute(currentRow + 1);
                    } else {
                        System.out.println("Ya estás en la última fila. pollaflacida");
                    }
                } else if (comando.equals("d")) {
                    if (currentRow > 0) {
                        currentRow--;
                        rs.absolute(currentRow + 1);
                    } else {
                        System.out.println("Ya estás en la primera fila, guarro.");
                    }
                } else {
                    try {
                        int targetRow = Integer.parseInt(comando) - 1;
                        if (targetRow >= 0 && targetRow < totalRows) {
                            currentRow = targetRow;
                            rs.absolute(currentRow + 1);
                        } else {
                            System.out.println("Fila no válida.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Comando no válido.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Explote de mierda en la compilacion");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String piti = "Explote de mierda en la seccion congelada del SUMA";
        System.out.println(piti);
    }
}
