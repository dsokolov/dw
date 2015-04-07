package tgn.rkvy.deep.data;

import tgn.rkvy.deep.entities.Door;

import java.util.List;

public class AllDataSeedFilter implements DataSeedFilter {

    @Override
    public List<Door> filterDoors(List<Door> doors) {
        return doors;
    }

}
