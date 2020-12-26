package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ConfigureOrthusComponentsModel {
    private String version;
    private String baseDirectory;
    private List<Entry> entryList = new ArrayList<>();

    public void load(String path) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        ConfigureOrthusComponentsModel data = ConfigureOrthusComponentsModel.parse(content);
        version = data.getVersion();
        baseDirectory = data.getBaseDirectory();
        entryList = data.getEntryList();
    }

    private static ConfigureOrthusComponentsModel parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        ConfigureOrthusComponentsModel data = new ConfigureOrthusComponentsModel();
        data.version = source.get("Version").getStringValue();
        data.baseDirectory = source.get("BaseDirectory").getStringValue();

        List filesList = source.get("Files").getArray();
        for (Object o : filesList) {
            JsonObject entry = (JsonObject) o;
            data.entryList.add(new Entry(
                data.baseDirectory + entry.get("Source").getStringValue(),
                "maps/externalmap/" + entry.get("Destination").getStringValue()
            ));
        }
        return data;
    }

    @Data
    @AllArgsConstructor
    public static class Entry {
        private String source;
        private String destination;
    }
}
