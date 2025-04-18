/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import java.util.Collection;
import java.util.Set;
import edu.eci.arsw.blueprints.model.Blueprint;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;
    public Collection<Blueprint> allBlueprints();
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;
    public void updateBlueprint(Blueprint blueprint) throws BlueprintNotFoundException;

    /**
     * Delete a blueprint
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @throws BlueprintNotFoundException if the blueprint doesn't exist
     */
    public void deleteBlueprint(String author, String bprintname) throws BlueprintNotFoundException;
}
