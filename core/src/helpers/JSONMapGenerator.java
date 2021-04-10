package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class JSONMapGenerator {
    JsonReader reader;

    public JSONMapGenerator() {
        reader = new JsonReader();
    }

    public ArrayList<JSONMapObject> getDefaultMap(){
        ArrayList<JSONMapObject> result = new ArrayList<>();
        JsonValue base = reader.parse(Gdx.files.internal("Maps/Map 1.json"));

        for (JsonValue value : base.get("values")) {
            result.add(new JSONMapObject(value.getInt("id"),
                    value.getString("type"),
                    value.getInt("firstValue"),
                    value.getInt("secondValue")
            ));
        }

        return result;
    }
}
