/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lafamilia.grupo.proyectofinal;
import java.time.LocalDate; // Importa la clase LocalDate para manejar fechas con precisión de días sin zona horaria.
import java.time.format.DateTimeFormatter; // Importa la clase DateTimeFormatter para formatear y analizar fechas y tiempos.
import javax.swing.JOptionPane;

/**
 *
 * @author Malware
 */
public class Supermercado {
    private Producto[] productos; // Arreglo para almacenar los productos
    private Producto[] inventarioProductos; // Arreglo para almacenar el inventario de los productos
    private Pasillo[] pasillos; // Arreglo para almacenar los pasillos
    private Compra[] comprasRealizadas; // Arreglo para almacenar las compras realizadas
    private int cantidadCompras; // Contador de compras realizadas
    
    // Constructor
    public Supermercado() {
        this.productos = new Producto[100]; // Límite de 100 productos
        this.inventarioProductos = new Producto[100]; // Límite de 100 productos en el inventario
        this.pasillos = new Pasillo[10]; // 10 pasillos para las categorías
        this.comprasRealizadas = new Compra[50]; // Límite de 50 compras
        this.cantidadCompras = 0;

        // Inicialización de pasillos con sus categorías respectivas
        String[] categorias = new String[]{"Frescos", "Granos", "Carnicería", "Abarrotes", "Cereales", "Limpieza", "Lácteos", "Panadería", "Hogar", "Congelados"};
        for (int i = 0; i < pasillos.length; i++) {
            pasillos[i] = new Pasillo(categorias[i]);
        }
    }

    public Producto obtenerProductoPorId(int idProducto) {
    for (Producto producto : productos) {
        if (producto != null && producto.getId() == idProducto) {
            return producto;
        }
    }
    return null; // Si no se encuentra el producto, se retorna null.
}

    
    // Método para agregar un producto al arreglo de productos
    public String registrarProducto(Producto producto) {
    for (int i = 0; i < productos.length; i++) {
        if (productos[i] == null) {
            productos[i] = producto;
            return "OK"; // Salir del método después de agregar el producto
        }
    }
    return "Error: No hay espacio para más productos."; // Si no hay espacio para el producto
}

    // Método para organizar un producto en un pasillo específico
public String organizarProductoEnPasillo(int idProducto, int numeroPasillo) {
    if (numeroPasillo < 1 || numeroPasillo > pasillos.length) {
        return "Número de pasillo inválido.";
    }
    Pasillo pasillo = pasillos[numeroPasillo - 1];
    Producto producto = obtenerProductoPorId(idProducto);
    if (producto == null) {
        return "Producto no encontrado.";
    }
    // Verificar si el pasillo corresponde a la categoría del producto
    if (!producto.getCategoria().equals(pasillo.getCategoria())) {
        return "El producto no corresponde a la categoría del pasillo.";
    }
    // Verificar si hay espacio en el pasillo
    if (pasillo.contieneProducto(idProducto)) {
        return "El producto ya está en el pasillo.";
    }
    // Verificar si el pasillo está lleno
    for (Producto prod : pasillo.getProductos()) {
        if (prod == null) { // Encontró un espacio vacío
            pasillo.agregarProducto(producto);
            return "Producto organizado con éxito en el pasillo.";
        }
    }
    return "El pasillo está lleno.";
}

    // Método para realizar una compra
    public void realizarCompra(Cliente cliente, int[] idsProductos) {
        if (cantidadCompras >= comprasRealizadas.length) {
            System.out.println("Se ha alcanzado el límite de compras.");
            return;
        }
        Producto[] productosComprados = new Producto[idsProductos.length];
        for (int i = 0; i < idsProductos.length; i++) {
            for (Producto producto : productos) {
                if (producto != null && producto.getId() == idsProductos[i] && producto.getExistencias() > 0) {
                    productosComprados[i] = producto;
                    System.out.println("Antes de la compra, existencias de " + producto.getNombre() + ": " + producto.getExistencias());
                    producto.setExistencias(producto.getExistencias() - 1); // Disminuir existencias
                    System.out.println("Después de la compra, existencias de " + producto.getNombre() + ": " + producto.getExistencias());
                    break;
                }
            }
        }
        Compra nuevaCompra = new Compra(cliente, productosComprados);
        comprasRealizadas[cantidadCompras++] = nuevaCompra;
    sincronizarInventario();
}
    
    // Método para vender un producto (disminuir existencias)
    public String venderProducto(int idProducto) {
        for (Producto producto : productos) {
            if (producto != null && producto.getId() == idProducto) {
                if (producto.getExistencias() > 0) {
                    producto.setExistencias(producto.getExistencias() - 1);
                    return "OK";
                } else {
                    return "Error: Producto sin existencias.";
                }
            }
        }
        return "Error: Producto no encontrado.";
    }
    
    // Método para registrar una compra
    public String registrarCompra(Compra compra) {
        if (cantidadCompras < comprasRealizadas.length) {
            comprasRealizadas[cantidadCompras++] = compra;
            return "OK";
        } else {
            return "Error: No se pueden registrar más compras.";
        }
    }

    // Método para retirar un producto de un pasillo
    public String retirarProducto(int idProducto) {
    boolean encontrado = false;
    for (Pasillo pasillo : pasillos) {
        String resultado = pasillo.retirarProducto(idProducto);
        if (!resultado.isEmpty()) {
            encontrado = true;
            // Si el producto se encontró y fue retirado, se puede devolver el mensaje directamente o seguir buscando en otros pasillos.
            // Esto depende de si el producto puede estar en más de un pasillo al mismo tiempo.
        }
    }
    if (encontrado) {
        return "Producto retirado con éxito.";
    } else {
        return "Producto no encontrado en ningún pasillo.";
    }
}

public void sincronizarInventario() {
        for (int i = 0; i < productos.length; i++) {
            Producto producto = productos[i];
            if (producto != null) {
                for (int j = 0; j < inventarioProductos.length; j++) {
                    Producto productoInventario = inventarioProductos[j];
                    if (productoInventario != null && producto.getId() == productoInventario.getId()) {
                        productoInventario.setExistencias(producto.getExistencias());
                        break; // Salimos una vez actualizado el producto
                    }
                }
            }
        }
    }

    // Métodos para reportes
    public String generarReporteProductosDisponibles() {
        StringBuilder reporte = new StringBuilder();
        for (Producto producto : productos) { // Cambiado de inventarioProductos a productos
            if (producto != null) {
                reporte.append("ID: ")
                       .append(producto.getId())
                       .append(", Nombre: ")
                       .append(producto.getNombre())
                       .append(", Existencias: ")
                       .append(producto.getExistencias())
                       .append("\n");
            }
        }
        return reporte.toString();
    }

    public String generarReporteOrganizacionPasillos() {
    StringBuilder reporteVisual = new StringBuilder("Organización de los Pasillos:\n");
    StringBuilder reporteDetallado = new StringBuilder();

    for (Pasillo pasillo : pasillos) {
        reporteVisual.append(pasillo.visualizarPasillo()).append("\n");
        reporteDetallado.append(pasillo.detalleProductos()).append("\n");
    }

    // Mostrar el reporte visual
    JOptionPane.showMessageDialog(null, reporteVisual.toString(), "Reporte Visual de Pasillos", JOptionPane.INFORMATION_MESSAGE);

    return reporteDetallado.toString();
}

// Necesitas agregar el método detalleProductos en la clase Pasillo

    public String generarReporteCaducidadProductos() {
    StringBuilder reporte = new StringBuilder("Reporte de Caducidad de Productos:\n");
    // Define una fecha límite que es un mes posterior a la fecha actual.
    LocalDate fechaLimite = LocalDate.now().plusMonths(1); 
    // Crea un formateador de fecha para convertir la cadena de texto de la fecha de caducidad a LocalDate.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 

    for (Pasillo pasillo : pasillos) {
        for (Producto producto : pasillo.getProductos()) {
            if (producto != null) {
                // Analiza la fecha de caducidad del producto desde una cadena usando el formateador definido previamente.
                LocalDate fechaCaducidadProducto = LocalDate.parse(producto.getFechaCaducidad(), formatter);
                // Comprueba si la fecha de caducidad es anterior a la fecha límite establecida.
                if (fechaCaducidadProducto.isBefore(fechaLimite)) {
                    reporte.append(producto.toString()).append("\n");
                }
            }
        }
    }
    return reporte.toString();
}

   public String generarReporteResumenCompras() {
        ResumenCliente[] resumenClientes = new ResumenCliente[50]; // Suponiendo que hay un máximo de 50 clientes diferentes
        int contadorResumen = 0;

        for (Compra compra : comprasRealizadas) {
            if (compra != null) {
                Cliente cliente = compra.getCliente();
                ResumenCliente resumenCliente = buscarResumenCliente(resumenClientes, cliente);
                if (resumenCliente == null) {
                    resumenCliente = new ResumenCliente(cliente);
                    resumenClientes[contadorResumen++] = resumenCliente;
                }
                resumenCliente.agregarCompra(compra);
            }
        }

        StringBuilder reporte = new StringBuilder("Resumen de Compras:\n");
        for (ResumenCliente resumenCliente : resumenClientes) {
            if (resumenCliente != null) {
                reporte.append(resumenCliente.toString()).append("\n");
            }
        }
        return reporte.toString();
    }

    private ResumenCliente buscarResumenCliente(ResumenCliente[] resumenClientes, Cliente cliente) {
        for (ResumenCliente resumenCliente : resumenClientes) {
            if (resumenCliente != null && resumenCliente.getCliente().getCedula().equals(cliente.getCedula())) {
                return resumenCliente;
            }
        }
        return null;
    }

private boolean productoEstaEnPasillo(int idProducto) {
    for (Pasillo pasillo : pasillos) {
        if (pasillo.contieneProducto(idProducto)) {
            return true;
        }
    }
    return false;
}

    // Getters y Setters
    public Producto[] getProductos() {
        return productos;
    }

    public Pasillo[] getPasillos() {
        return pasillos;
    }

    public Compra[] getComprasRealizadas() {
        return comprasRealizadas;
    }

}