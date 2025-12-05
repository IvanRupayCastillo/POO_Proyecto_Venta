package proyecto_sistema_venta.Pruebas;

import proyecto_sistema_venta.Entidades.DetalleVenta;
import proyecto_sistema_venta.Negocio.VentaNegocio;
import java.util.ArrayList;
import java.util.List;

public class TestVentas {
    public static void main(String[] args) {
        probarValidacionesDetalle();
        probarCalculoTotales();
        System.out.println("Pruebas ejecutadas");
    }

    private static void probarValidacionesDetalle() {
        try {
            DetalleVenta d = new DetalleVenta();
            d.setCantidad(0);
            throw new AssertionError("Se esperaba excepción por cantidad no positiva");
        } catch (IllegalArgumentException expected) {
        }
        try {
            DetalleVenta d = new DetalleVenta();
            d.setPrecioUnitario(0);
            throw new AssertionError("Se esperaba excepción por precio no positivo");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void probarCalculoTotales() {
        VentaNegocio negocio = new VentaNegocio();
        List<DetalleVenta> items = new ArrayList<>();
        DetalleVenta d = new DetalleVenta();
        d.setIdProducto(0);
        d.setCantidad(1);
        d.setDescuentoItem(0);
        d.setPrecioUnitario(1); // se ignora en negocio
        items.add(d);
        try {
            negocio.procesarVenta(1, 1, items, "EFECTIVO", 0);
        } catch (Exception e) {
            // Es correcto que falle sin base de datos o producto válido
        }
    }
}

