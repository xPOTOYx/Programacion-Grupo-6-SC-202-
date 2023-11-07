/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lafamilia.grupo.proyectofinal;
import javax.swing.JOptionPane;

/**
 *
 * @author Malware
 */
public class Producto {
    private static int contadorId = 1; // Generador automático de ID
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int existencias;
    private String fechaCaducidad; // La fecha de caducidad como String
    private int cantidadComprada;
    
// Constructor
    public Producto(String nombre, String categoria, double precio, int existencias, String fechaCaducidad) {
        this.id = contadorId++; // Asigna un ID único y lo incrementa para el siguiente producto
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
        this.fechaCaducidad = fechaCaducidad;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
     public int getCantidadComprada() {
        return cantidadComprada;
    }
     
     public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    // Método para crear un Producto desde la entrada del usuario
    public static Producto crearProductoDesdeInput() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");

        // Definir una lista de categorías
        String[] categorias = {"Frutas y Verduras", "Carnes", "Lácteos", "Panadería", "Bebidas"};
        StringBuilder categoriasMensaje = new StringBuilder("Seleccione la categoría del producto:\n");
        for (int i = 0; i < categorias.length; i++) {
            categoriasMensaje.append(i + 1).append(". ").append(categorias[i]).append("\n");
        }

        int categoriaIndex = -1;
        String categoria = "";
        while (categoriaIndex < 0 || categoriaIndex >= categorias.length) {
            try {
                // Solicitar al usuario que elija una categoría
                String categoriaStr = JOptionPane.showInputDialog(categoriasMensaje.toString());
                categoriaIndex = Integer.parseInt(categoriaStr) - 1; // -1 porque los arrays comienzan en 0
                categoria = categorias[categoriaIndex];
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un número de la lista.");
            }
        }

        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));
        int existencias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese las existencias del producto:"));
        String fechaCaducidad = JOptionPane.showInputDialog("Ingrese la fecha de caducidad (dd/MM/yyyy):");
        
        return new Producto(nombre, categoria, precio, existencias, fechaCaducidad);
    }

    // Método para mostrar la información del producto
    @Override
    public String toString() {
        return "ID: " + id + "\nNombre: " + nombre + "\nCategoría: " + categoria + "\nPrecio: " + precio +
               "\nExistencias: " + existencias + "\nFecha de Caducidad: " + fechaCaducidad;
    }
}