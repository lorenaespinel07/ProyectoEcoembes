package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.dao.ContenedorRepository;
import es.deusto.sd.ecoembes.entity.Contenedor;
import es.deusto.sd.ecoembes.entity.InfoContenedor;
import es.deusto.sd.ecoembes.entity.NivelLlenado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContenedorService {

    @Autowired
    private ContenedorRepository contenedorRepository;

    public List<Contenedor> getAllContenedores() {
        return contenedorRepository.findAll();
    }
    public void addContenedor(Contenedor c) {
        contenedorRepository.save(c);
    }

    public void addInfoContenedor(Contenedor c, ArrayList<InfoContenedor> lista) {
        Contenedor contenedorVivo = contenedorRepository.findById(c.getId()).orElse(c);

        // 2. Asignamos esa versión viva a cada InfoContenedor
        for (InfoContenedor info : lista) {
            info.setContenedor(contenedorVivo);
            contenedorVivo.getHistorial().add(info);
        }

        // 3. Guardamos la lista. Ahora Hibernate está feliz porque usa el objeto conectado.
        contenedorRepository.save(contenedorVivo);
    }
    //POST esto lo usara el sensor asiq no hace falta token
    public Optional<InfoContenedor> actualizarInfoContenedor(Long idContenedor, int numEnvases, String nivelLlenadoStr) {
        Optional<Contenedor> contenedor = contenedorRepository.findById(idContenedor);
        if (contenedor.isPresent()) {
            Contenedor contenedorActual = contenedor.get();
            NivelLlenado nivelLlenado = NivelLlenado.valueOf(nivelLlenadoStr);
            InfoContenedor infoContenedor = new InfoContenedor(contenedorActual,numEnvases, nivelLlenado,new Date());
            contenedorActual.getHistorial().add(infoContenedor);
            contenedorActual.setNivel(nivelLlenado);
            contenedorRepository.save(contenedorActual);
            return Optional.of(infoContenedor);
        } else {
            // El contenedor a actualizar no existe
            return Optional.empty();
        }
    }
    //POST
    public Optional<?> crearContenedor(long idContenedor, String ubicacion, String cp, int capacidadIni, String token){
        if (!contenedorRepository.existsById(idContenedor)) {
            Contenedor contenedor = new Contenedor(ubicacion, cp, capacidadIni);
            contenedorRepository.save(contenedor);
            return Optional.of(contenedor);
        } else {
            return Optional.empty(); // Ya existe
        }
    }
    //GET
    public Optional<?> getInfoContendorPorFecha(long idContenedor, Date fechaInicio, Date fechaFin, String token){
        // Eder del furturo tendria q mejorar esto con el boy
        Optional<Contenedor> contenedor = contenedorRepository.findById(idContenedor);

        if (contenedor.isPresent()) {
            List<InfoContenedor> historial = contenedor.get().getHistorial();

            List<InfoContenedor> resultado = historial.stream()
                    .filter(info -> info.getFechaActu().after(fechaInicio) && info.getFechaActu().before(fechaFin))
                    .collect(Collectors.toList());
            return Optional.of(resultado);
        } else {
            return Optional.empty();
        }
    }
    //GET
    public Optional<?> getInfoContenedorPorZona(String cp, Date fecha, String token){
        // Buscar contenedores por CP
        // Ideal: contenedorRepository.findByCp(cp)
        List<Contenedor> contenedoresZona = contenedorRepository.findAll().stream()
                .filter(c -> c.getCp().equals(cp))
                .toList();

        ArrayList<InfoContenedor> resultado = new ArrayList<>();

        for (Contenedor c : contenedoresZona) {
            c.getHistorial().stream()
                    .filter(info -> compraraDias(info.getFechaActu(), fecha))
                    .forEach(resultado::add);
        }
        return Optional.of(resultado);
    }


    private static synchronized boolean compraraDias(Date fecha1, Date fecha2) {
        LocalDate localDate1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.equals(localDate2);
    }

    public Optional<Contenedor> buscarPorId(Long id) {
        return contenedorRepository.findById(id);
    }

    public InfoContenedor getUltimaInfoContenedor(Long idContenedor) {
        Optional<Contenedor> c = contenedorRepository.findById(idContenedor);

        if (c.isPresent() && !c.get().getHistorial().isEmpty()) {
            List<InfoContenedor> historial = c.get().getHistorial();
            return historial.get(historial.size() - 1);
        }
        return null;
    }
}
