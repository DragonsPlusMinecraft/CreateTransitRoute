package plus.dragons.createcommutenetwork.content.commute.trains.entity;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackGraph;

import java.util.List;
import java.util.UUID;

public class CommuteTrain extends Train {
    public CommuteTrain(UUID id, UUID owner, TrackGraph graph, List<Carriage> carriages, List<Integer> carriageSpacing, boolean doubleEnded) {
        super(id, owner, graph, carriages, carriageSpacing, doubleEnded);
    }
}
