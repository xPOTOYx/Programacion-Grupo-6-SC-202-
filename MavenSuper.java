/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavensuper;
import javax.swing.JOptionPane;
/**
 *
 * @author Proyecto
 */
public class MavenSuper {

    public static void main(String[] args) {
        Producto[][] pasillos = new Producto[10][10];
        int contadorProductos = 1;

        while (contadorProductos <= 10) { 
            JOptionPane.showMessageDialog(null, "Registro del Producto " + contadorProductos + ":");

            String nombre = JOptionPane.showInputDialog("Nombre del producto:");
            String categoria = JOptionPane.showInputDialog("Categoría (Frescos, Granos, Carnicería, Abarrotes, Cereales, Limpieza, Lácteos, Panadería, Hogar o Congelados):");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
            int existencias = Integer.parseInt(JOptionPane.showInputDialog("Existencias:"));
            String fechaCaducidad = JOptionPane.showInputDialog("Fecha de Caducidad");

            String productoString = "Nombre: " + nombre + ", Categoría: " + categoria + ", Precio: $" + precio + ", Existencias: " + existencias + ", Caducidad: " + fechaCaducidad + "\n";
            JOptionPane.showMessageDialog(null, "Producto " + contadorProductos + ": " + productoString);

            // Organizar el producto en el pasillo correspondiente
            int categoriaIndex = obtenerIndiceCategoria(categoria);
            int posicionEnPasillo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la posición en el pasillo (0-9):"));

            if (categoriaIndex >= 0 && categoriaIndex < 10 && posicionEnPasillo >= 0 && posicionEnPasillo < 10) {
                if (pasillos[categoriaIndex][posicionEnPasillo] == null) {
                    pasillos[categoriaIndex][posicionEnPasillo] = new Producto(nombre, categoria, precio, existencias, fechaCaducidad);
                } else {
                    JOptionPane.showMessageDialog(null, "Esta posición en el pasillo ya está ocupada.");
                    contadorProductos--; // Descontar el contador si la organización falla
                }
            } else {
                JOptionPane.showMessageDialog(null, "La posición en el pasillo o la categoría son inválidas.");
                contadorProductos--; // Descontar el contador si la organización falla
            }

            contadorProductos++;
        }

        Cliente cliente = new Cliente();
        cliente.nombre = JOptionPane.showInputDialog("Nombre del cliente:");
        cliente.cedula = JOptionPane.showInputDialog("Cédula del cliente:");

        while (cliente.contadorCompras < 7) { // Cambia el 7 al número máximo de productos por compra
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione un pasillo para su compra:",
                    "Seleccionar Pasillo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, obtenerCategorias(), 0);

            if (opcion == -1) {
                break; 
            }

            int categoriaIndex = opcion;
            int contadorProductosPasillo = 0;
            int productosDisponibles = 0;
            for (Producto producto : pasillos[categoriaIndex]) {
                if (producto != null && producto.existencias > 0) {
                    productosDisponibles++;
                }
            }

            if (productosDisponibles > 0) {
                String[] opcionesProductos = new String[productosDisponibles];
                Producto[] productosSeleccionables = new Producto[productosDisponibles];
                int index = 0;
                for (int i = 0; i < pasillos[categoriaIndex].length; i++) {
                    if (pasillos[categoriaIndex][i] != null && pasillos[categoriaIndex][i].existencias > 0) {
                        opcionesProductos[index] = pasillos[categoriaIndex][i].nombre;
                        productosSeleccionables[index] = pasillos[categoriaIndex][i];
                        index++;
                    }
                }

                int opcionProducto = JOptionPane.showOptionDialog(null, "Seleccione un producto para comprar:",
                        "Seleccionar Producto", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesProductos, 0);

                if (opcionProducto == -1) {
                    break; // El usuario canceló la selección
                }

                Producto productoSeleccionado = productosSeleccionables[opcionProducto];
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a comprar (Existencias disponibles: " + productoSeleccionado.existencias + "):"));

                if (cantidad <= productoSeleccionado.existencias) {
                    cliente.agregarCompra(new Compra(productoSeleccionado, cantidad));
                    productoSeleccionado.existencias -= cantidad;
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficientes existencias disponibles para la compra.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles en este pasillo.");
            }
        }

        if (cliente.contadorCompras > 0) {
            double totalAPagar = cliente.calcularTotal();
            int opcionPago = JOptionPane.showConfirmDialog(null, "Total a pagar: $" + totalAPagar + "\n¿Desea continuar con el pago?", "Confirmar Pago", JOptionPane.YES_NO_OPTION);

            if (opcionPago == JOptionPane.YES_OPTION) {
                // Realizar el pago (aquí podrías agregar lógica adicional si fuera necesario)
                JOptionPane.showMessageDialog(null, "¡Pago realizado con éxito!");
            } else {
                // Cancelar la compra
                for (Compra compra : cliente.compras) {
                    Producto producto = compra.producto;
                    producto.existencias += compra.cantidad; // Devolver las unidades al inventario
                }
                JOptionPane.showMessageDialog(null, "Compra cancelada, existencias restablecidas.");
            }
        }

        
    }

    private static String[] obtenerCategorias() {
        return new String[]{"Frescos", "Granos", "Carnicería", "Abarrotes", "Cereales", "Limpieza", "Lácteos", "Panadería", "Hogar", "Congelados"};
    }

    private static int obtenerIndiceCategoria(String categoria) {
        String[] categorias = obtenerCategorias();
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].equalsIgnoreCase(categoria)) {
                return i;
            }
        }
        return -1; // Categoría no encontrada
    }
}

class Producto {
    String nombre;
    String categoria;
    double precio;
    int existencias;
    String fechaCaducidad;

    public Producto(String nombre, String categoria, double precio, int existencias, String fechaCaducidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
        this.fechaCaducidad = fechaCaducidad;
    }
}

class Cliente {
    String nombre;
    String cedula;
    Compra[] compras = new Compra[7];
    int contadorCompras = 0;

    public void agregarCompra(Compra compra) {
        if (contadorCompras < compras.length) {
            compras[contadorCompras] = compra;
            contadorCompras++;
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < contadorCompras; i++) {
            total += compras[i].calcularTotal();
        }
        return total;
    }
}

class Compra {
    Producto producto;
    int cantidad;

    public Compra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularTotal() {
        return producto.precio * cantidad;
    }
}
