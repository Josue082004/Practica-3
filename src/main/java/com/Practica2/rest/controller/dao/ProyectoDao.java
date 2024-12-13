package com.Practica2.rest.controller.dao;

import com.Practica2.rest.controller.dao.implement.AdapterDao;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Proyecto;
import com.Practica2.rest.models.Transaccion;
import com.google.gson.Gson;
import com.Practica2.rest.controller.tda.list.Exception.ListEmptyException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList<Proyecto> listAll;
    protected Gson gson = new Gson();

    public ProyectoDao() {
        super(Proyecto.class);
    }

    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LinkedList<Proyecto> getListAll() {
        if (this.listAll == null) {
            this.listAll = super.listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getProyecto().setIdProyecto(id);
        persist(getProyecto());
        registrarTransaccion("CREACION", "Proyecto creado con ID: " + id);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getIdProyecto() - 1);
        this.listAll = super.listAll();
        registrarTransaccion("ACTUALIZACION", "Proyecto actualizado con ID: " + getProyecto().getIdProyecto());
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Proyecto> list = getListAll();
        list.delete(id - 1);
        String data = gson.toJson(list.toArray(new Proyecto[0]));
        saveFile(data);
        registrarTransaccion("ELIMINACION", "Proyecto eliminado con ID: " + id);
        return true;
    }

    private void registrarTransaccion(String tipo, String descripcion) throws Exception {
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(tipo);
        transaccion.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        transaccion.setDescripcion(descripcion);
        TransaccionDao transaccionDao = new TransaccionDao();
        transaccionDao.setTransaccion(transaccion);
        transaccionDao.save();
    }

    public LinkedList<Proyecto> ordenarQuicksort(Integer type_order, String atributo) {
        LinkedList<Proyecto> lista = getListAll();
        if (!lista.isEmpty()) {
            Proyecto[] arreglo = lista.toArray(new Proyecto[0]);
            lista.reset();
            quickSort(arreglo, 0, arreglo.length - 1, type_order, atributo);
            lista.fromArray(arreglo);
        }
        return lista;
    }

    private void quickSort(Proyecto[] arr, int low, int high, Integer type_order, String atributo) {
        if (low < high) {
            int pi = partition(arr, low, high, type_order, atributo);
            quickSort(arr, low, pi - 1, type_order, atributo);  
            quickSort(arr, pi + 1, high, type_order, atributo); 
        }
    }

    private int partition(Proyecto[] arr, int low, int high, Integer type_order, String atributo) {
        Proyecto pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparar(arr[j], pivot, type_order, atributo)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(Proyecto[] arr, int i, int j) {
        Proyecto temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public LinkedList<Proyecto> ordenarMergeSort(Integer type_order, String atributo) {
        LinkedList<Proyecto> lista = getListAll();
        if (!lista.isEmpty()) {
            Proyecto[] arreglo = lista.toArray(new Proyecto[0]);
            lista.reset();
            mergeSort(arreglo, 0, arreglo.length - 1, type_order, atributo);
            lista.fromArray(arreglo);
        }
        return lista;
    }

    private void mergeSort(Proyecto[] arr, int left, int right, Integer type_order, String atributo) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, type_order, atributo);
            mergeSort(arr, mid + 1, right, type_order, atributo);
            merge(arr, left, mid, right, type_order, atributo);
        }
    }

    private void merge(Proyecto[] arr, int left, int mid, int right, Integer type_order, String atributo) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Proyecto[] leftArr = new Proyecto[n1];
        Proyecto[] rightArr = new Proyecto[n2];
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (comparar(leftArr[i], rightArr[j], type_order, atributo)) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }

    public LinkedList<Proyecto> ordenarShellSort(Integer type_order, String atributo) {
        LinkedList<Proyecto> lista = getListAll();
        if (!lista.isEmpty()) {
            Proyecto[] arreglo = lista.toArray(new Proyecto[0]);
            lista.reset();
            shellSort(arreglo, type_order, atributo);
            lista.fromArray(arreglo);
        }
        return lista;
    }

    private void shellSort(Proyecto[] arr, Integer type_order, String atributo) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Proyecto temp = arr[i];
                int j;
                for (j = i; j >= gap && comparar(arr[j - gap], temp, type_order, atributo); j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    private boolean comparar(Proyecto a, Proyecto b, Integer type_order, String atributo) {
        switch (atributo.toLowerCase()) {
            case "nombre":
                return type_order == 1 
                    ? a.getNombre().compareTo(b.getNombre()) <= 0
                    : a.getNombre().compareTo(b.getNombre()) >= 0;
            case "inversion":
                return type_order == 1 
                    ? a.getInversionTotal() <= b.getInversionTotal()
                    : a.getInversionTotal() >= b.getInversionTotal();
            case "coste":
                return type_order == 1 
                    ? a.getCosto() <= b.getCosto()
                    : a.getCosto() >= b.getCosto();
            case "producciondiaria":
                return type_order == 1 
                    ? a.getProduccionDiaria() <= b.getProduccionDiaria()
                    : a.getProduccionDiaria() >= b.getProduccionDiaria();
            case "fechainicio":
                return type_order == 1 
                    ? a.getFechaInicio().compareTo(b.getFechaInicio()) <= 0
                    : a.getFechaInicio().compareTo(b.getFechaInicio()) >= 0;
            case "fechafin":
                return type_order == 1 
                    ? a.getFechaFin().compareTo(b.getFechaFin()) <= 0
                    : a.getFechaFin().compareTo(b.getFechaFin()) >= 0;
            case "estado":
                return type_order == 1 
                    ? a.getEstado().compareTo(b.getEstado()) <= 0
                    : a.getEstado().compareTo(b.getEstado()) >= 0;
            default:
                throw new IllegalArgumentException("Atributo no válido: " + atributo);
        }
    }
    
    public LinkedList<Proyecto> buscarLineal(String criterio, String valor) throws ListEmptyException {
        LinkedList<Proyecto> lista = new LinkedList<>();
        LinkedList<Proyecto> listita = getListAll(); 
        if (listita == null || listita.isEmpty()) {
            throw new ListEmptyException("Error: La lista está vacía.");
        }
        Proyecto[] proyectos = listita.toArray(new Proyecto[0]); 
        for (Proyecto proyecto : proyectos) {
            boolean match = false;
            switch (criterio.toLowerCase()) {
                case "nombre":
                    match = proyecto.getNombre().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "inversion":
                    try {
                        float inversion = Float.parseFloat(valor);
                        match = (proyecto.getInversionTotal() == inversion || 
                                 String.valueOf(proyecto.getInversionTotal()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'inversion' debe ser un número válido.");
                    }
                    break;
                case "coste":
                    try {
                        float costo = Float.parseFloat(valor);
                        match = (proyecto.getCosto() == costo || 
                                 String.valueOf(proyecto.getCosto()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'coste' debe ser un número válido.");
                    }
                    break;
                case "producciondiaria":
                    try {
                        float produccionDiaria = Float.parseFloat(valor);
                        match = (proyecto.getProduccionDiaria() == produccionDiaria || 
                                 String.valueOf(proyecto.getProduccionDiaria()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'producciondiaria' debe ser un número válido.");
                    }
                    break;
                case "fechainicio":
                    match = proyecto.getFechaInicio().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "fechafin":
                    match = proyecto.getFechaFin().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "estado":
                    match = proyecto.getEstado().toLowerCase().contains(valor.toLowerCase());
                    break;
                default:
                    throw new IllegalArgumentException("Criterio de búsqueda no válido: " + criterio);
            }
            if (match) {
                lista.add(proyecto);
            }
        }

        return lista;
    }

    public LinkedList<Proyecto> buscarBinario(String criterio, String valor) throws ListEmptyException {
        LinkedList<Proyecto> lista = new LinkedList<>();
        LinkedList<Proyecto> listita = getListAll(); 
        if (listita == null || listita.isEmpty()) {
            throw new ListEmptyException("Error: La lista está vacía.");
        }
        Proyecto[] proyectos = listita.toArray(new Proyecto[0]); 
        Arrays.sort(proyectos, (p1, p2) -> comparar(p1, p2, 1, criterio) ? -1 : 1);
        int low = 0, high = proyectos.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Proyecto midProyecto = proyectos[mid];
            boolean match = false;
            switch (criterio.toLowerCase()) {
                case "nombre":
                    match = midProyecto.getNombre() != null && midProyecto.getNombre().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "inversion":
                    try {
                        float inversion = Float.parseFloat(valor);
                        match = (midProyecto.getInversionTotal() == inversion || 
                                 String.valueOf(midProyecto.getInversionTotal()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'inversion' debe ser un número válido.");
                    }
                    break;
                case "coste":
                    try {
                        float costo = Float.parseFloat(valor);
                        match = (midProyecto.getCosto() == costo || 
                                 String.valueOf(midProyecto.getCosto()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'coste' debe ser un número válido.");
                    }
                    break;
                case "producciondiaria":
                    try {
                        float produccionDiaria = Float.parseFloat(valor);
                        match = (midProyecto.getProduccionDiaria() == produccionDiaria || 
                                 String.valueOf(midProyecto.getProduccionDiaria()).startsWith(valor));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("El valor para 'producciondiaria' debe ser un número válido.");
                    }
                    break;
                case "fechainicio":
                    match = midProyecto.getFechaInicio() != null && midProyecto.getFechaInicio().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "fechafin":
                    match = midProyecto.getFechaFin() != null && midProyecto.getFechaFin().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "estado":
                    match = midProyecto.getEstado() != null && midProyecto.getEstado().toLowerCase().contains(valor.toLowerCase());
                    break;
                default:
                    throw new IllegalArgumentException("Criterio de búsqueda no válido: " + criterio);
            }
            if (match) {
                lista.add(midProyecto);
                break;
            } else if (comparar(midProyecto, new Proyecto(), 1, criterio)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return lista;
    }
}
