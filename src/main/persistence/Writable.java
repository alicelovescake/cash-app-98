package persistence;

import org.json.JSONObject;

//CITATION: Structure of this interface is modeled after Serialization demo
public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
