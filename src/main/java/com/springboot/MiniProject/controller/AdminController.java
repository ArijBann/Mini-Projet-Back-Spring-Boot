package com.springboot.MiniProject.controller;


import com.springboot.MiniProject.dto.UserAdminDTO;
import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.dto.UserEtudiantDTO;
import com.springboot.MiniProject.entity.ArchiveUsers;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.serivce.EnseignantService;
import com.springboot.MiniProject.serivce.EtudiantService;
import com.springboot.MiniProject.serivce.JwtService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issatso/admin")
public class AdminController {

    @Autowired
    private UserService service;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EtudiantService etudiantService;

    //cette PAGE est accessible par les admins seulement
    @GetMapping("/welcome/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeAdmin() {
        return "Welcome ens";
    }
    @PostMapping("/add/enseignant")
    public String addNewEns(@RequestBody UserEnseigantDTO userEnseigantDTO){
        return service.addEns(userEnseigantDTO);
    }

    @PostMapping("/add/etudiant")
    public String addNewEtud(@RequestBody UserEtudiantDTO userEtudiantDTO){
        return service.addEtud(userEtudiantDTO);
    }
    @PostMapping("/add/admin")
    public String addNewAdm(@RequestBody UserAdminDTO userAdminDTO){
        return service.addAdmin(userAdminDTO);
    }

    @DeleteMapping("/delete/enseignant/{numProf}/{desc}")
    public String deleteEnsegnant(@PathVariable int numProf,@PathVariable String desc){
        return service.deleteEns(numProf,desc);
    }
  @DeleteMapping("/delete/etudiant/{numinscri}/{desc}")
    public String deleteEtudiant(@PathVariable int numinscri,@PathVariable String desc){
        return service.deleteEtud(numinscri,desc);
    }
    @DeleteMapping("/delete/admin/{id}/{desc}")
    public String deleteAdmin(@PathVariable int id,@PathVariable String desc){
        return service.deleteAdmin(id,desc);
    }
   // @PutMapping("/update/enseignant")
    //public Enseignant updateEnseignant(@RequestBody Enseignant Enseigant){return enseignantService.updateEnseignant(Enseigant);}

    @PutMapping("/update/user")
    public User updateUser(@RequestBody User user){return service.updateUser(user);}

   // @PutMapping("/update/etudiant")
    //public Etudiant updateEtudiant(@RequestBody Etudiant etudiant){return etudiantService.updateEtudiant(etudiant);}
 /*   @PutMapping("/update/admin")
    public UserAdminDTO updateAdmin(@RequestBody UserAdminDTO userAdminDTO){return service.updateAdmin(userAdminDTO);}
*/

    @GetMapping("/allEnseignants")
    public List<UserEnseigantDTO> getAllEnseignants() {
        return service.getAllEnseignants();
    }

    @GetMapping("/Enseignantbyemail/{email}")
    public ResponseEntity<UserEnseigantDTO> getEnseignantByEmail(@PathVariable String email) {
        UserEnseigantDTO enseignantDTO = service.getEnseignantByEmail(email);
        if (enseignantDTO != null) {
            return ResponseEntity.ok(enseignantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/numProf/{numProf}")
    public ResponseEntity<UserEnseigantDTO> getEnseignantByNumProf(@PathVariable int numProf) {
        UserEnseigantDTO enseignantDTO = service.getEnseignantByNumProf(numProf);
        if (enseignantDTO != null) {
            return ResponseEntity.ok(enseignantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/allArchiveUsers")
    public List<ArchiveUsers> getAllArchiveUsers() {
        return service.getAllArchiveUsers();
    }


}
