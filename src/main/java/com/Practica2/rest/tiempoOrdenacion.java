import com.Practica2.rest.controller.dao.services.ProyectoServices;
import com.Practica2.rest.models.Proyecto;
import com.Practica2.rest.controller.tda.list.LinkedList;
import java.util.Random;

public class tiempoOrdenacion {

    public static void main(String[] args) throws Exception {
        int size = 10000;
        Random random = new Random();
        ProyectoServices proyectoServices = new ProyectoServices();
        Proyecto[] proyectos = new Proyecto[size];
        for (int i = 0; i < size; i++) {
            proyectos[i] = new Proyecto(i, "Proyecto" + i, "2023-01-01", "2023-12-31", "Activo", random.nextDouble() * 10000, random.nextDouble() * 10000, random.nextDouble() * 10000);
        }
        LinkedList<Proyecto> listaProyectos = new LinkedList<>();
        listaProyectos.fromArray(proyectos);
        long startTime = System.currentTimeMillis();
        proyectoServices.ordenarShellSort(1, "nombre");
        long endTime = System.currentTimeMillis();
        long tiempoOrdenacion = endTime - startTime;
        System.out.println("Tamano: " + size);
        System.out.println("Tiempo de ordenacion por ShellSort: " + tiempoOrdenacion + " ms");
    }
}