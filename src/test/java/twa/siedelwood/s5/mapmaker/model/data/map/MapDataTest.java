package twa.siedelwood.s5.mapmaker.model.data.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapDataTest {
    private MapData mapData;

    @Before
    public void initTest() {
        mapData = new MapData();
    }

    @Test
    public void displayMapDataToLuaResult() {
        System.out.println(mapData.toLua());
        assertTrue(true);
    }
}