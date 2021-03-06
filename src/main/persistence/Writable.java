package persistence;

import org.json.JSONObject;

//CITATION: Structure of this interface is modeled after JsonSerializationDemo
//          URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/
//Represents data that is written to an JSON object for persistence
public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
