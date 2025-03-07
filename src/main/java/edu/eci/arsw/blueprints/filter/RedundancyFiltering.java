package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedundancyFiltering implements BlueprintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> filteredPoints = new ArrayList<>();
        List<Point> originalPoints = blueprint.getPoints();

        if (!originalPoints.isEmpty()) {
            filteredPoints.add(originalPoints.get(0)); // Siempre agregar el primer punto
            for (int i = 1; i < originalPoints.size(); i++) {
                Point currentPoint = originalPoints.get(i);
                Point lastAddedPoint = filteredPoints.get(filteredPoints.size() - 1);

                // Comparar las coordenadas x e y para evitar duplicados
                if (!currentPoint.equals(lastAddedPoint)) {
                    filteredPoints.add(currentPoint);
                }
            }
        }

        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), filteredPoints.toArray(new Point[0]));
    }
}