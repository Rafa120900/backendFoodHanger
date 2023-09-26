package pe.edu.upc.food_hunger_tf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.food_hunger_tf.dtos.TotalAlimentosDonadosPorUsuario;
import pe.edu.upc.food_hunger_tf.dtos.Usuario_DTO;
import pe.edu.upc.food_hunger_tf.serviceinterfaces.IUsuario_Service;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth/Usuario")
public class Usuario_Controller {
    @Autowired
    private IUsuario_Service IUsuario_Service;

    @PostMapping("/Registrar")
    public void registrar(@RequestBody Usuario_DTO usuario_dto){
      IUsuario_Service.registrar(usuario_dto);
    }

    @GetMapping("/Listar")
    public ResponseEntity<List<Usuario_DTO>> listar(){
        return new ResponseEntity<>(IUsuario_Service.list(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") int id){IUsuario_Service.delete(id);}

    @PutMapping("/{id}")
    public void modificar(@PathVariable("id") int id,@RequestBody Usuario_DTO usuario_dto){
    IUsuario_Service.modificar(id,usuario_dto);
    }

    @GetMapping("/TotalAlimentosDonadosPorUsuario")
    public List<TotalAlimentosDonadosPorUsuario> cantidadTotal() {
        List<String[]> lista = IUsuario_Service.TotaldeAlimentosDonadosPorUsuario();
        List<TotalAlimentosDonadosPorUsuario> listaDTO = new ArrayList<>();
        for (String[] data : lista) {
            TotalAlimentosDonadosPorUsuario dto = new TotalAlimentosDonadosPorUsuario();
            dto.setNombre(data[0]);
            dto.setCantidadTotal(Integer.parseInt(data[1]));
            listaDTO.add(dto);
        }
        return listaDTO;
    }
}
