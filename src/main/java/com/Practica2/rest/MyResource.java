package com.Practica2.rest;

import com.Practica2.rest.controller.dao.services.ProyectoServices;
import com.Practica2.rest.models.Proyecto;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("myresource")
public class MyResource {
    private static final Logger logger = Logger.getLogger(MyResource.class.getName());
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, Object> mapa = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        String aux = "Datos inicializados correctamente";
        try {
            Proyecto proyecto = new Proyecto();
            proyecto.setNombre("Proyecto 1");
            proyecto.setFechaInicio("2023-01-01");
            proyecto.setFechaFin("2023-12-31");
            proyecto.setEstado("Activo");
            proyecto.setCosto(100000);
            proyecto.setProduccionDiaria(500);
            proyecto.setInversionTotal(200000);
            ps.setProyecto(proyecto);
            ps.save();
            aux = "Lista vacía: " + ps.listAll().isEmpty();
            logger.info("Guardado exitoso. Lista: " + aux);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar: " + e.getMessage(), e);
            e.printStackTrace();
            mapa.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }
        mapa.put("msg", "Proyecto guardado");
        mapa.put("data", "Test " + aux);
        return Response.ok(mapa).build();
    }
}