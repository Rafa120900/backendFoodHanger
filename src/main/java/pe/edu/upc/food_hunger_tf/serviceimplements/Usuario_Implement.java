package pe.edu.upc.food_hunger_tf.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.food_hunger_tf.dtos.Usuario_DTO;
import pe.edu.upc.food_hunger_tf.entities.Roles;
import pe.edu.upc.food_hunger_tf.entities.Tipo_alimento;
import pe.edu.upc.food_hunger_tf.entities.Tipo_donacion;
import pe.edu.upc.food_hunger_tf.entities.Usuario;
import pe.edu.upc.food_hunger_tf.repositories.IRoles;
import pe.edu.upc.food_hunger_tf.repositories.ITipo_DonacionRepository;
import pe.edu.upc.food_hunger_tf.repositories.IUsuarioRepository;
import pe.edu.upc.food_hunger_tf.serviceinterfaces.IUsuario_Service;

import javax.management.relation.Role;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Usuario_Implement implements IUsuario_Service, UserDetailsService {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    private ITipo_DonacionRepository tp;

    @Autowired
    private IRoles rp;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void registrar(Usuario_DTO udto){
        Roles roles = rp.findById(udto.getIdRoles())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el rol con ID " + udto.getIdRoles()));
        Tipo_donacion tdonacion = tp.findById(udto.getIdTipoDonacion())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el rol con ID " + udto.getIdTipoDonacion()));

        Usuario usuario = new Usuario();
        usuario.setRoles(roles);
        usuario.setTipoDonacion(tdonacion);
        usuario.setNombreU(udto.getNombreU());
        usuario.setApelidoU(udto.getApelidoU());
        usuario.setDonacionU(udto.getDonacionU());
        usuario.setContrasena(passwordEncoder.encode(udto.getContrasena()));
        iUsuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario_DTO> list() {
        List<Usuario> usuarios = iUsuarioRepository.findAll();
        List<Usuario_DTO> soloDTO = new ArrayList<>();
        Usuario_DTO elem;

        for (Usuario usuario : usuarios) {
            elem = new Usuario_DTO();
            elem.setIdU(usuario.getIdU());
            elem.setNombreU(usuario.getNombreU());
            elem.setApelidoU(usuario.getApelidoU());
            elem.setDonacionU(usuario.getDonacionU());
            elem.setContrasena(usuario.getContrasena());
            elem.setIdRoles(usuario.getRoles().getId_Roles());
            elem.setIdTipoDonacion(usuario.getTipoDonacion().getIdTipoDonacion());
            soloDTO.add(elem);
        }
        return soloDTO;
    }
    @Override
    public void delete(int idUsuario) {iUsuarioRepository.deleteById(idUsuario);}

    public void modificar(int id, Usuario_DTO udto){
        Usuario usuarioE = iUsuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID " + id));

        Roles roles = rp.findById(udto.getIdRoles())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el rol con ID " + udto.getIdRoles()));
        Tipo_donacion tdonacion = tp.findById(udto.getIdTipoDonacion())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tipo de donación con ID " + udto.getIdTipoDonacion()));
        usuarioE.setRoles(roles);
        usuarioE.setTipoDonacion(tdonacion);

        usuarioE.setNombreU(udto.getNombreU());
        usuarioE.setApelidoU(udto.getApelidoU());
        usuarioE.setDonacionU(udto.getDonacionU());
        iUsuarioRepository.save(usuarioE);
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario =  iUsuarioRepository
                .findByNombreU(nombre)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con correo electronico" + nombre + "no existe"));
        return usuario;
    }

    @Override
    public List<String[]> TotaldeAlimentosDonadosPorUsuario() {
        return iUsuarioRepository.TotaldeAlimentosDonadosPorUsuario();
    }

}

