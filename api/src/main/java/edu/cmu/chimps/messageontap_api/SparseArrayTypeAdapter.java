package edu.cmu.chimps.messageontap_api;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Dario Marcato
 * @author Adam Yi
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SparseArrayTypeAdapter<T> extends TypeAdapter<SparseArray<T>> {

    private final Gson gson = new Gson();
    private final Type typeOfT;
    private final Type typeOfSparseArrayOfT = new TypeToken<SparseArray<T>>() {
    }.getType();
    private final Type typeOfSparseArrayOfObject = new TypeToken<SparseArray<Object>>() {
    }.getType();

    public SparseArrayTypeAdapter(Type t) {
        typeOfT = t;
    }

    @Override
    public void write(JsonWriter jsonWriter, SparseArray<T> tSparseArray) throws IOException {
        if (tSparseArray == null) {
            jsonWriter.nullValue();
            return;
        }
        gson.toJson(gson.toJsonTree(tSparseArray, typeOfSparseArrayOfT), jsonWriter);
    }

    @Override
    public SparseArray<T> read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        SparseArray<Object> temp = gson.fromJson(jsonReader, typeOfSparseArrayOfObject);
        SparseArray<T> result = new SparseArray<>(temp.size());
        int key;
        JsonElement tElement;
        for (int i = 0, size = temp.size(); i < size; ++i) {
            key = temp.keyAt(i);
            tElement = gson.toJsonTree(temp.get(key));
            result.put(key, (T) JSONUtils.jsonToSimpleObject(tElement.toString(), typeOfT));
        }
        return result;
    }

}