/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package lafamilia.grupo.proyectofinal;
import javax.swing.JOptionPane;

/**
 *
 * @author Malware
 */
public class Grupo6ProyectoFinal {

    private static Supermercado laFamilia = new Supermercado();

    public static void main(String[] args) {
        String menuPrincipal = "Bienvenido al Sistema de Gestión de Supermercado 'La Familia'\n" +
                "1. Registrar Producto\n" +
                "2. Organizar Producto en Pasillo\n" +
                "3. Realizar Compra\n" +
                "4. Retirar Producto\n" +
                "5. Reportes\n" +
                "6. Salir\n\n" +
                "Seleccione una opción:";
        
        String opcionStr;
        do {
            opcionStr = JOptionPane.showInputDialog(null, menuPrincipal, "Menú Principal", JOptionPane.QUESTION_MESSAGE);
            if (opcionStr != null) {
                try {
                    int opcion = Integer.parseInt(opcionStr);
                    switch (opcion) {
                        case 1:
                            registrarProducto();
                            break;
                        case 2:
                            organizarProductoEnPasillo();
                            break;
                        case 3:
                            realizarCompra();
                            break;
                        case 4:
                            retirarProducto();
                            break;
                        case 5:
                            mostrarReportes();
                            break;
                        case 6:
                            JOptionPane.showMessageDialog(null, "Gracias por utilizar el sistema.");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                }
            }
        } while (opcionStr != null && !opcionStr.equals("6"));
    }

 private static void registrarProducto() {
    String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", "Registro de Producto", JOptionPane.QUESTION_MESSAGE);
    if (nombre == null) return; // Cancelado por el usuario

    String categoria = JOptionPane.showInputDialog(null, "Ingrese la categoría del producto:", "Registro de Producto", JOptionPane.QUESTION_MESSAGE);
    if (categoria == null) return; // Cancelado por el usuario

    String precioStr = JOptionPane.showInputDialog(null, "Ingrese el precio del producto:", "Registro de Producto", JOptionPane.QUESTION_MESSAGE);
    if (precioStr == null) return; // Cancelado por el usuario

    String existenciasStr = JOptionPane.showInputDialog(null, "Ingrese las existencias del producto:", "Registro de Producto", JOptionPane.QUESTION_MESSAGE);
    if (existenciasStr == null) return; // Cancelado por el usuario

    String fechaCaducidad = JOptionPane.showInputDialog(null, "Ingrese la fecha de caducidad (dd/MM/yyyy):", "Registro de Producto", JOptionPane.QUESTION_MESSAGE);
    if (fechaCaducidad == null) return; // Cancelado por el usuario

    try {
        double precio = Double.parseDouble(precioStr);
        int existencias = Integer.parseInt(existenciasStr);

        Producto nuevoProducto = new Producto(nombre, categoria, precio, existencias, fechaCaducidad);
        String resultado = laFamilia.registrarProducto(nuevoProducto); 
        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(null, "Producto registrado con éxito.", "Registro de Producto", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado, "Registro de Producto", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para precio y existencias.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
    }
}


private static void organizarProductoEnPasillo() {
    String idProductoStr = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a organizar:", "Organizar Producto en Pasillo", JOptionPane.QUESTION_MESSAGE);
    if (idProductoStr == null || idProductoStr.trim().isEmpty()) return; // Cancelado por el usuario o entrada vacía

    String pasilloStr = JOptionPane.showInputDialog(null, "Ingrese el número de pasillo (1-10):", "Organizar Producto en Pasillo", JOptionPane.QUESTION_MESSAGE);
    if (pasilloStr == null || pasilloStr.trim().isEmpty()) return; // Cancelado por el usuario o entrada vacía

    try {
        int idProducto = Integer.parseInt(idProductoStr);
        int numeroPasillo = Integer.parseInt(pasilloStr);

        // Comprobación de rangos válidos para pasillos.
        if (numeroPasillo < 1 || numeroPasillo > 10) {
            JOptionPane.showMessageDialog(null, "Número de pasillo inválido. Debe ser entre 1 y 10.", "Error de Pasillo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = laFamilia.organizarProductoEnPasillo(idProducto, numeroPasillo);
        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(null, "Producto organizado en el pasillo correctamente.", "Organizar Producto en Pasillo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado, "Organizar Producto en Pasillo", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para ID de producto y número de pasillo.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al organizar el producto en el pasillo: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
    }
}


private static void realizarCompra() {
    // Solicitar información del cliente
    String cedulaCliente = JOptionPane.showInputDialog(null, "Ingrese la cédula del cliente:", "Realizar Compra", JOptionPane.QUESTION_MESSAGE);
    if (cedulaCliente == null || cedulaCliente.trim().isEmpty()) return;
    
    String nombreCliente = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente:", "Realizar Compra", JOptionPane.QUESTION_MESSAGE);
    if (nombreCliente == null || nombreCliente.trim().isEmpty()) return;

    // Crear una instancia de Cliente
    Cliente cliente = new Cliente(nombreCliente, cedulaCliente);

    // Inicializar un arreglo de productos con una cantidad máxima de productos
    // Por ejemplo, si el máximo es 10, entonces:
    Producto[] productosSeleccionados = new Producto[10];
    int contadorProductos = 0;

    // Recoger IDs de los productos a comprar y agregarlos al arreglo
    while (contadorProductos < productosSeleccionados.length) {
        String idProductoStr = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a comprar (o deje en blanco para finalizar):", "Producto " + (contadorProductos + 1), JOptionPane.QUESTION_MESSAGE);
        if (idProductoStr == null || idProductoStr.trim().isEmpty()) {
            break;
        }

        try {
            int idProducto = Integer.parseInt(idProductoStr);
            Producto producto = laFamilia.obtenerProductoPorId(idProducto); // Suponiendo que tienes este método en tu clase Supermercado
            if (producto != null) {
                productosSeleccionados[contadorProductos++] = producto;
                JOptionPane.showMessageDialog(null, "Producto agregado a la compra.", "Realizar Compra", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error en la Compra", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el ID del producto.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    Compra nuevaCompra = new Compra(cliente, productosSeleccionados);

    // Registrar la compra en el supermercado
    String resultado = laFamilia.registrarCompra(nuevaCompra);
    if (resultado.equals("OK")) {
        JOptionPane.showMessageDialog(null, "Compra registrada con éxito.", "Realizar Compra", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "No se pudo registrar la compra: " + resultado, "Error en la Compra", JOptionPane.ERROR_MESSAGE);
    }
}

private static void retirarProducto() {
    String idProductoStr = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a retirar:", "Retirar Producto", JOptionPane.QUESTION_MESSAGE);
    if (idProductoStr == null || idProductoStr.trim().isEmpty()) return; // Cancelado por el usuario o entrada vacía

    try {
        int idProducto = Integer.parseInt(idProductoStr);
        String resultado = laFamilia.retirarProducto(idProducto);
        if (!resultado.equals("")) {
            int confirmacion = JOptionPane.showConfirmDialog(null, resultado + " ¿Está seguro que desea retirar este producto?", "Confirmar Retiro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Producto retirado con éxito.", "Producto Retirado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Retiro cancelado.", "Retiro Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado o ya retirado.", "Error de Retiro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el ID del producto.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al retirar el producto: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
    }
}

    private static void mostrarReportes() {
    String menuReportes = "Seleccione el reporte que desea visualizar:\n" +
            "1. Reporte de Productos Disponibles\n" +
            "2. Reporte de Organización de Pasillos\n" +
            "3. Reporte de Caducidad de Productos\n" +
            "4. Reporte de Resumen de Compras\n" +
            "5. Volver al Menú Principal";

    String opcionStr;
    do {
        opcionStr = JOptionPane.showInputDialog(null, menuReportes, "Menú de Reportes", JOptionPane.QUESTION_MESSAGE);
        if (opcionStr != null) {
            try {
                int opcion = Integer.parseInt(opcionStr);
                switch (opcion) {
                    case 1:
                        mostrarReporteProductosDisponibles();
                        break;
                    case 2:
                        mostrarReporteOrganizacionPasillos();
                        break;
                    case 3:
                        mostrarReporteCaducidadProductos();
                        break;
                    case 4:
                        mostrarReporteResumenCompras();
                        break;
                    case 5:
                        // Volver al menú principal
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            }
        }
    } while (opcionStr != null && !opcionStr.equals("5"));
}

private static void mostrarReporteProductosDisponibles() {
    String reporte = laFamilia.generarReporteProductosDisponibles();
    JOptionPane.showMessageDialog(null, reporte, "Reporte de Productos Disponibles", JOptionPane.INFORMATION_MESSAGE);
}

private static void mostrarReporteOrganizacionPasillos() {
    String reporte = laFamilia.generarReporteOrganizacionPasillos();
    JOptionPane.showMessageDialog(null, reporte, "Reporte de Organización de Pasillos", JOptionPane.INFORMATION_MESSAGE);
}

private static void mostrarReporteCaducidadProductos() {
    String reporte = laFamilia.generarReporteCaducidadProductos();
    JOptionPane.showMessageDialog(null, reporte, "Reporte de Caducidad de Productos", JOptionPane.INFORMATION_MESSAGE);
}

private static void mostrarReporteResumenCompras() {
    String reporte = laFamilia.generarReporteResumenCompras();
    JOptionPane.showMessageDialog(null, reporte, "Reporte de Resumen de Compras", JOptionPane.INFORMATION_MESSAGE);
}

}