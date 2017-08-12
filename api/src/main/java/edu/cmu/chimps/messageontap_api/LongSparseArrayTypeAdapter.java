package edu.cmu.chimps.messageontap_api;


import android.util.LongSparseArray;

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
public class LongSparseArrayTypeAdapter<T> extends TypeAdapter<LongSparseArray<T>> {

    private final Gson gson = new Gson();
    private final Class<T> classOfT;
    private final String type;
    private final Type typeOfLongSparseArrayOfT = new TypeToken<LongSparseArray<T>>() {
    }.getType();
    private final Type typeOfLongSparseArrayOfObject = new TypeToken<LongSparseArray<Object>>() {
    }.getType();

    public LongSparseArrayTypeAdapter(Class<T> classOfT) {
        this.classOfT = classOfT;
        type = null;
    }

    public LongSparseArrayTypeAdapter(String type) {
        this.classOfT = null;
        this.type = type;

    }

    @Override
    public void write(JsonWriter jsonWriter, LongSparseArray<T> tLongSparseArray) throws IOException {
        if (tLongSparseArray == null) {
            jsonWriter.nullValue();
            return;
        }
        gson.toJson(gson.toJsonTree(tLongSparseArray, typeOfLongSparseArrayOfT), jsonWriter);
    }

    @Override
    public LongSparseArray<T> read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        LongSparseArray<Object> temp = gson.fromJson(jsonReader, typeOfLongSparseArrayOfObject);
        LongSparseArray<T> result = new LongSparseArray<>(temp.size());
        long key;
        JsonElement tElement;
        for (int i = 0; i < temp.size(); i++) {
            key = temp.keyAt(i);
            tElement = gson.toJsonTree(temp.get(key));
            if (this.type == null) {
                assert classOfT != null;
                result.put(key, gson.fromJson(tElement, classOfT));
            } else {
                //noinspection unchecked
                result.put(key, (T) JSONUtils.jsonToSimpleObject(tElement.toString(), type));
            }
        }
        return result;
    }

}