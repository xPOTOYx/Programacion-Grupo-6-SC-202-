/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lafamilia.grupo.proyectofinal;

/**
 *
 * @author Malware
 */
public class Pasillo {
    private Producto[] productos; // Arreglo para almacenar los productos en el pasillo
    private String categoria; // La categoría del pasillo

    public Pasillo(String categoria) {
        this.productos = new Producto[10]; // Suponemos que hay espacio para 10 productos por pasillo
        this.categoria = categoria;
    }

    // Método para verificar si el pasillo contiene un producto con el ID dado
    public boolean contieneProducto(int idProducto) {
        for (Producto producto : productos) {
            if (producto != null && producto.getId() == idProducto) {
                return true;
            }
        }
        return false;
    }
    
    // Método para agregar un producto al pasillo si hay espacio disponible
    public void agregarProducto(Producto producto) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null) { // Espacio disponible
                productos[i] = producto;
                break; // Salimos del bucle una vez que el producto se ha agregado
            }
        }
    }

    // Método para retirar un producto del pasillo por ID y devolver un mensaje
    public String retirarProducto(int idProducto) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null && productos[i].getId() == idProducto) {
                productos[i] = null; // Retiramos el producto
                return "Producto retirado con éxito del pasillo " + categoria;
            }
        }
        return ""; // Devuelve una cadena vacía si el producto no se encontró en este pasillo.
    }

   
    public String detalleProductos() {
        StringBuilder detalles = new StringBuilder();
        for (Producto producto : productos) {
            if (producto != null) {
                detalles.append(producto.toString()).append("\n"); // Asumiendo que Producto tiene un método toString adecuado
            }
        }
        return detalles.toString();
    }

    // Método para obtener una representación visual del pasillo y sus productos
    public String visualizarPasillo() {
        String visualizacion = "Pasillo de " + categoria + ":\n";
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                visualizacion += "[" + productos[i].getNombre() + "]";
            } else {
                visualizacion += "[ ]";
            }
            if (i < productos.length - 1) {
                visualizacion += " - ";
            }
        }
        return visualizacion;
    }

    // Getters y Setters
    public Producto[] getProductos() {
        return productos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}