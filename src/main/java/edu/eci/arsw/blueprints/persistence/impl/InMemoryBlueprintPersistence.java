/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.concurrent.ConcurrentHashMap;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */

@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts1=new Point[]{new Point(150, 170),new Point(15, 105)};
        Blueprint bp1=new Blueprint("Camil", "plano0",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);

        Point[] pts2=new Point[]{new Point(40, 149),new Point(85, 12)};
        Blueprint bp2=new Blueprint("Maria", "plano1",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);

        Point[] pts3=new Point[]{new Point(80, 149),new Point(85, 32)};
        Blueprint bp3=new Blueprint("Maria", "plano2",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
    }    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (blueprint == null){
            throw new BlueprintNotFoundException("The blueprint not found " + bprintname);
        }
        return blueprint;
    } 
    
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        ArrayList<Blueprint> blueprint = new ArrayList<Blueprint>();
        Set<Tuple<String, String>> keys =blueprints.keySet();
        for (Tuple<String,String> e : keys ){
            if(author.equals(e.o1)){
                blueprint.add(blueprints.get(e));
            }
        }
        if (blueprint.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return new HashSet<>(blueprint);
    }

    public Collection<Blueprint> allBlueprints(){
        Collection<Blueprint> b = blueprints.values();
        return b;
    }

    public void updateBlueprint(Blueprint blueprint) throws BlueprintNotFoundException{
        Blueprint update = getBlueprint(blueprint.getAuthor(),blueprint.getName());
        if(update == null){
            throw new BlueprintNotFoundException("The blueprint not found " + blueprint.getName()+ "of the author "+blueprint.getAuthor() );
        }
        update.setPoints(blueprint.getPoints());
    }
    
}
