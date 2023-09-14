package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleRepository theRoleRepository;
    @GetMapping("")
    public List<Role> index(){
        return this.theRoleRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role store(@RequestBody Role newRole){
        return this.theRoleRepository.save(newRole);
    }
    @GetMapping("{id}")
    public Role show(@PathVariable String id){
        Role theRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        return theRole;
    }

    @PutMapping("{id}")
    public Role update(@PathVariable String id,@RequestBody Role theNewRole){
        Role theActualRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theActualRole!=null){
            theActualRole.setName(theNewRole.getName());
            theActualRole.setDescription(theNewRole.getDescription());
            //theActualRole.setPassword(theNewRole.getPassword());
            return this.theRoleRepository.save(theActualRole);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        Role theRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theRole!=null){
            this.theRoleRepository.delete(theRole);
        }
    }

}
