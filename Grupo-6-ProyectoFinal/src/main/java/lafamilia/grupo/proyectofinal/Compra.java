/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lafamilia.grupo.proyectofinal;

/**
 *
 * @author Malware
 */
public class Compra {
    private static int contadorId = 1; // Contador para el ID único de cada compra
    private int id;
    private Cliente cliente;
    private Producto[] productosComprados;
    private double totalGastado;

    public Compra(Cliente cliente, Producto[] productosComprados) {
        this.id = contadorId++; // Asigna un ID único y lo incrementa para la siguiente compra
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        calcularTotalGastado(); // Calcula el total gastado al crear la compra
    }

    private void calcularTotalGastado() {
        totalGastado = 0.0;
        for (Producto producto : productosComprados) {
            if (producto != null) {
                totalGastado += producto.getPrecio();
            }
        }
    }

    // Método para obtener la cantidad total de productos comprados
    public int getCantidadProductos() {
        int cantidad = 0;
        for (Producto producto : productosComprados) {
            if (producto != null) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // Método para obtener el monto total gastado en la compra
    public double getMontoTotal() {
        return totalGastado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Producto[] getProductosComprados() {
        return productosComprados;
    }

    // Método para mostrar la información de la compra
    @Override
    public String toString() {
        StringBuilder infoCompra = new StringBuilder("Compra ID: " + id + "\nCliente: " + cliente.getNombre() +
                        " (" + cliente.getCedula() + ")\nProductos:\n");
        for (Producto producto : productosComprados) {
            if (producto != null) {
                infoCompra.append(producto.toString()).append("\n");
            }
        }
        infoCompra.append("Total Gastado: ").append(totalGastado);
        return infoCompra.toString();
    }
}