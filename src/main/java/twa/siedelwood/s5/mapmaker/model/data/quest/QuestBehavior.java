package twa.siedelwood.s5.mapmaker.model.data.quest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * QuestBehavior model
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class QuestBehavior implements Serializable {
    List<String> parameters;

    public QuestBehavior() {
        parameters = new ArrayList<>();
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public String getParameter(int index) {
        if (parameters.size() < index+1) {
            return null;
        }
        return parameters.get(index);
    }

    public void setParameter(int index, String value) {
        parameters.set(index, value);
    }

    public String getName() {
        return parameters.get(0);
    }

    public QuestBehavior clone() {
        try {
            return QuestBehavior.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return parameters.get(0);
    }

    public Json toJson() {
        JsonArray parametersArray = new JsonArray();
        for (String parameter : parameters) {
            parametersArray.add(parameter);
        }
        return parametersArray;
    }

    public String toLua() {
        StringBuilder lua = new StringBuilder("{");
        for (String parameter : parameters) {
            parameter = parameter.replaceAll("\"", "{qq}");
            if (parameter.equals("true") || parameter.equals("false") || Pattern.matches("^\\d+.\\d+$", parameter) || Pattern.matches("^\\d+$", parameter)) {
                lua.append(parameter).append(",");
            }
            else {
                lua.append("\"").append(parameter).append("\",");
            }
        }
        lua.append("}");
        return lua.toString();
    }

    public static QuestBehavior parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        QuestBehavior behavior = new QuestBehavior();
        JsonArray source = (JsonArray) parser.parse(json);
        for (int i=0; i<source.size(); i++) {
            behavior.addParameter(source.get(i).getStringValue());
        }
        return behavior;
    }
}
