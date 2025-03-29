package edu.eci.arsw.blueprints.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.arsw.blueprints.model.Point;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class PointController {
    
    private final List<Point> points = new CopyOnWriteArrayList<>();

    @MessageMapping("/newpoint")
    @SendTo("/topic/newpoint")
    public Point handleNewPoint(Point point) {
        points.add(point);
        return point;
    }

    public List<Point> getAllPoints() {
        return points;
    }
}

@RestController
@RequestMapping("/ws")
class PointRestController {
    
    private final PointController pointController;

    public PointRestController(PointController pointController) {
        this.pointController = pointController;
    }

    @GetMapping("/points")
    public List<Point> getPoints() {
        return pointController.getAllPoints();
    }
}
