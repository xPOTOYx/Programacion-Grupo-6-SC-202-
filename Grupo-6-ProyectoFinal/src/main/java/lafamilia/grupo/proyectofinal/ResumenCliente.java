/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lafamilia.grupo.proyectofinal;

/**
 *
 * @author Malware
 */
public class ResumenCliente {
    private Cliente cliente;
    private Compra[] compras; 
    private int indiceCompras; // Para llevar el control del número de compras

    public ResumenCliente(Cliente cliente) {
        this.cliente = cliente;
        this.compras = new Compra[50]; // Limitamos a 50 compras por cliente
        this.indiceCompras = 0;
    }

    public void agregarCompra(Compra compra) {
        // Asegurarse de no exceder el límite de compras
        if (indiceCompras < compras.length) {
            compras[indiceCompras++] = compra;
        } else {
            // Manejar el caso en que se alcance el límite de compras
            // Esto podría ser lanzar una excepción o simplemente ignorar la operación
        }
    }

    @Override
    public String toString() {
        // Acumula la información de compras, cantidad de productos y monto gastado
        int cantidadProductos = 0;
        double montoGastado = 0.0;
        int totalCompras = 0; // Contador para la cantidad de compras

        for (int i = 0; i < indiceCompras; i++) {
            // Asegurarse de que no se acceda a una compra nula
            if (compras[i] != null) {
                cantidadProductos += compras[i].getCantidadProductos();
                montoGastado += compras[i].getMontoTotal();
                totalCompras++;
            }
        }

        return "Nombre: " + cliente.getNombre() +
                "\nCédula: " + cliente.getCedula() +
                "\nCantidad de compras: " + totalCompras +
                "\nCantidad de productos: " + cantidadProductos +
                "\nMonto gastado total: " + montoGastado;
    }

    public Cliente getCliente() {
    return cliente;
}

}
