package com.Practica2.rest.controller.dao;

import com.Practica2.rest.controller.dao.implement.AdapterDao;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Inversionista;
import com.Practica2.rest.models.Transaccion;
import com.Practica2.rest.controller.tda.list.Exception.ListEmptyException;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InversionistaDao extends AdapterDao<Inversionista> {
    private Inversionista inversionista;
    private LinkedList<Inversionista> listAll;
    protected Gson gson = new Gson();

    public InversionistaDao() {
        super(Inversionista.class);
    }

    public Inversionista getInversionista() {
        if (inversionista == null) {
            inversionista = new Inversionista();
        }
        return this.inversionista;
    }

    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }

    public LinkedList<Inversionista> getListAll() {
        if (listAll == null) {
            this.listAll = super.listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getInversionista().setIdInversionista(id);
        persist(getInversionista());
        registrarTransaccion("CREACION", "Inversionista creado con ID: " + id);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getInversionista(), getInversionista().getIdInversionista() - 1);
        this.listAll = super.listAll();
        registrarTransaccion("ACTUALIZACION", "Inversionista actualizado con ID: " + getInversionista().getIdInversionista());
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Inversionista> list = getListAll();
        list.delete(id - 1);
        String data = gson.toJson(list.toArray(new Inversionista[0]));
        saveFile(data);
        registrarTransaccion("ELIMINACION", "Inversionista eliminado con ID: " + id);
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

    public LinkedList<Inversionista> ordenarQuicksort(Integer type_order, String atributo) {
        LinkedList<Inversionista> lista = getListAll();
        if (!lista.isEmpty()) {
            Inversionista[] arreglo = lista.toArray(new Inversionista[0]);
            lista.reset();
            quickSort(arreglo, 0, arreglo.length - 1, type_order, atributo);
            lista.toList(arreglo);
        }
        return lista;
    }

    private void quickSort(Inversionista[] arr, int low, int high, Integer type_order, String atributo) {
        if (low < high) {
            int pi = partition(arr, low, high, type_order, atributo);
            quickSort(arr, low, pi - 1, type_order, atributo);  
            quickSort(arr, pi + 1, high, type_order, atributo); 
        }
    }

    private int partition(Inversionista[] arr, int low, int high, Integer type_order, String atributo) {
        Inversionista pivot = arr[high];
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

    private void swap(Inversionista[] arr, int i, int j) {
        Inversionista temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public LinkedList<Inversionista> ordenarMergeSort(Integer type_order, String atributo) {
        LinkedList<Inversionista> lista = getListAll();
        if (!lista.isEmpty()) {
            Inversionista[] arreglo = lista.toArray(new Inversionista[0]);
            lista.reset();
            mergeSort(arreglo, 0, arreglo.length - 1, type_order, atributo);
            lista.toList(arreglo);
        }
        return lista;
    }

    private void mergeSort(Inversionista[] arr, int left, int right, Integer type_order, String atributo) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, type_order, atributo);
            mergeSort(arr, mid + 1, right, type_order, atributo);
            merge(arr, left, mid, right, type_order, atributo);
        }
    }

    private void merge(Inversionista[] arr, int left, int mid, int right, Integer type_order, String atributo) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Inversionista[] leftArr = new Inversionista[n1];
        Inversionista[] rightArr = new Inversionista[n2];
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
    public LinkedList<Inversionista> ordenarShellSort(Integer type_order, String atributo) {
        LinkedList<Inversionista> lista = getListAll();
        if (!lista.isEmpty()) {
            Inversionista[] arreglo = lista.toArray(new Inversionista[0]);
            lista.reset();
            shellSort(arreglo, type_order, atributo);
            lista.toList(arreglo);
        }
        return lista;
    }
    private void shellSort(Inversionista[] arr, Integer type_order, String atributo) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Inversionista temp = arr[i];
                int j;
                for (j = i; j >= gap && comparar(arr[j - gap], temp, type_order, atributo); j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }
    private boolean comparar(Inversionista a, Inversionista b, Integer type_order, String atributo) {
        switch (atributo.toLowerCase()) {
            case "nombre":
                return type_order == 1 
                    ? a.getNombre().compareTo(b.getNombre()) <= 0
                    : a.getNombre().compareTo(b.getNombre()) >= 0;
            case "apellido":
                return type_order == 1 
                    ? a.getApellido().compareTo(b.getApellido()) <= 0
                    : a.getApellido().compareTo(b.getApellido()) >= 0;
            case "correo":
                return type_order == 1 
                    ? a.getCorreo().compareTo(b.getCorreo()) <= 0
                    : a.getCorreo().compareTo(b.getCorreo()) >= 0;
            default:
                throw new IllegalArgumentException("Atributo no valido: " + atributo);
        }
    }
    public LinkedList<Inversionista> buscarBinario(String criterio, String valor) throws ListEmptyException {
        LinkedList<Inversionista> lista = new LinkedList<>();
        LinkedList<Inversionista> listita = getListAll(); 

        if (listita == null || listita.isEmpty()) {
            throw new ListEmptyException("Error: La lista está vacía.");
        }
        Inversionista[] inversionistas = listita.toArray(new Inversionista[0]); 
        Arrays.sort(inversionistas, (p1, p2) -> comparar(p1, p2, 1, criterio) ? -1 : 1);
        int low = 0, high = inversionistas.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Inversionista midInversionista = inversionistas[mid];
            boolean match = false;
            switch (criterio.toLowerCase()) {
                case "nombre":
                    match = midInversionista.getNombre().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "apellido":
                    match = midInversionista.getApellido().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "correo":
                    match = midInversionista.getCorreo().toLowerCase().contains(valor.toLowerCase());
                    break;
                default:
                    throw new IllegalArgumentException("Criterio de búsqueda no válido: " + criterio);
            }
            if (match) {
                lista.add(midInversionista);
                break;
            } else if (comparar(midInversionista, new Inversionista(), 1, criterio)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return lista;
    }

    public LinkedList<Inversionista> buscarLineal(String criterio, String valor) throws ListEmptyException {
        LinkedList<Inversionista> lista = new LinkedList<>();
        LinkedList<Inversionista> listita = getListAll(); 
        if (listita == null || listita.isEmpty()) {
            throw new ListEmptyException("Error: La lista está vacía.");
        }
        Inversionista[] inversionistas = listita.toArray(new Inversionista[0]); 
        for (Inversionista inversionista : inversionistas) {
            boolean match = false;
            switch (criterio.toLowerCase()) {
                case "nombre":
                    match = inversionista.getNombre().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "apellido":
                    match = inversionista.getApellido().toLowerCase().contains(valor.toLowerCase());
                    break;
                case "correo":
                    match = inversionista.getCorreo().toLowerCase().contains(valor.toLowerCase());
                    break;
                default:
                    throw new IllegalArgumentException("Criterio de búsqueda no válido: " + criterio);
            }

            if (match) {
                lista.add(inversionista);
            }
        }
        return lista;
    }
}