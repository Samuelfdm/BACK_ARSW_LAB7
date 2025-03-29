/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value =  "/blueprints")

public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bpp;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> allBlueprints(){
        try{
            Collection<Blueprint> blueprints = bpp.getAllBlueprints();
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        }catch (Exception e){            
            return new ResponseEntity<>("No se encontro los planos",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author) {
        try {
            Set <Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        }catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("No se encontro los planos del autor:"+author,HttpStatus.NOT_FOUND);
        }catch (Exception m){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String name) {
        try {
            Blueprint blueprint = bpp.getBlueprint(author, name);
            return new ResponseEntity<>(blueprint,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("No se encontro los planos",HttpStatus.NOT_FOUND);
        }catch (Exception m){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint blueprint)  {
        try {
            if (blueprint.getPoints() == null) {
                blueprint.setPoints(new ArrayList<>());
            }

            bpp.addNewBlueprint(blueprint);
            return new ResponseEntity<>(blueprint, HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No se guardo el plano",HttpStatus.NOT_FOUND);
        }catch (Exception m){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateBlueprint(@RequestBody Blueprint blueprint)  {
        try {
            bpp.updateBlueprint(blueprint);
            return new ResponseEntity<>(blueprint,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No se actualizo el plano",HttpStatus.NOT_FOUND);
        }catch (Exception m){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @DeleteMapping("/{author}/{name}")
    public ResponseEntity<?> deleteBlueprint(@PathVariable String author, @PathVariable String name) {
        try {
            bpp.deleteBlueprint(author, name);
            return new ResponseEntity<>(Map.of("message", "Blueprint deleted successfully"), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(
                    Map.of("error", "Blueprint not found", "details", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", "Internal server error", "details", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}

