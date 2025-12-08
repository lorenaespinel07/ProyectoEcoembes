package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.dao.PersonalRepository;
import es.deusto.sd.ecoembes.entity.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private PersonalRepository personalRepository;
    public static Map<String, Personal> dbTokensActivos = new HashMap<>();

    public void addPersonal(Personal p) {
        personalRepository.save(p);
    }

    public Optional<String> login(String correo, String contrasena) {
        Optional<Personal> personal = personalRepository.findByEmail(correo);
        if (personal != null && personal.get().getPassword().equals(contrasena)) {
            String token = generateToken();
            dbTokensActivos.put(token, personal.get());
            return Optional.of(token);
        }else {
            return Optional.empty();
        }
    }

    public Optional<Boolean> logout (String token) {
        if (dbTokensActivos.containsKey(token)) {
            dbTokensActivos.remove(token);
            return Optional.of(true);
        }else {
            return Optional.empty();
        }
    }

    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

    public static synchronized boolean validarToken(String token) {return dbTokensActivos.containsKey(token);}


    public void addTokenActivo(String number, Personal p1) {
        dbTokensActivos.put(number, p1);
    }
    public Personal getPersonalByToken(String token) {
        return dbTokensActivos.get(token);
    }
}
