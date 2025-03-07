package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubsamplingFiltering implements BlueprintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> filteredPoints = new ArrayList<>();
        List<Point> originalPoints = blueprint.getPoints();

        for (int i = 0; i < originalPoints.size(); i += 2) {
            filteredPoints.add(originalPoints.get(i)); // Agregar solo puntos en posiciones pares
        }
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), filteredPoints.toArray(new Point[0]));
    }
}