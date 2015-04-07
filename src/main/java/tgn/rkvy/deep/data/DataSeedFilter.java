package tgn.rkvy.deep.data;

import tgn.rkvy.deep.entities.Door;

import java.util.List;

public interface DataSeedFilter {

    List<Door> filterDoors(List<Door> doors);

}
