package edu.cmu.chimps.messageontap_api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageData implements Parcelable {
    private static final String KEY_FID = "flow_id";
    private static final String KEY_SID = "sequence_id";
    private static final String KEY_REQUEST = "request";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_METHOD = "method";


    private long mFID = 0;
    private long mSID = 0;
    private String mRequest = null;
    private String mResponse = null;
    private String mMethod = null;

    public MessageData() {

    }

    /**
     * Returns the flow ID
     * Default 0.
     */
    public long fid() {
        return mFID;
    }

    /**
     * Sets the flow ID. Default 0.
     */
    public MessageData fid(long fid) {
        mFID = fid;
        return this;
    }

    /**
     * Returns the sequence ID
     * Default 0.
     */
    public long sid() {
        return mSID;
    }

    /**
     * Sets the sequence ID. Default 0.
     */
    public MessageData sid(long sid) {
        mSID = sid;
        return this;
    }

    /**
     * Returns the request info
     * Default null.
     */
    public String request() {
        return mRequest;
    }

    /**
     * Sets the request info. Default null.
     */
    public MessageData request(String request) {
        mRequest = request;
        return this;
    }

    /**
     * Returns the response info
     * Default null.
     */
    public String response() {
        return mResponse;
    }

    /**
     * Sets the response info. Default null.
     */
    public MessageData response(String response) {
        mResponse = response;
        return this;
    }

    /**
     * Returns the method
     * Default null.
     */
    public String method() {
        return mMethod;
    }

    /**
     * Sets the method. Default null.
     */
    public MessageData method(String method) {
        mMethod = method;
        return this;
    }

    /**
     * Serializes the contents of this object to JSON.
     */
    public JSONObject serialize() throws JSONException {
        JSONObject data = new JSONObject();
        data.put(KEY_FID, mFID);
        data.put(KEY_SID, mSID);
        data.put(KEY_REQUEST, mRequest);
        data.put(KEY_RESPONSE, mResponse);
        data.put(KEY_METHOD, mMethod);
        return data;
    }

    /**
     * Deserializes the given JSON representation of plugin data, populating this
     * object.
     */
    public void deserialize(JSONObject data) throws JSONException {
        this.mFID = data.optLong(KEY_FID);
        this.mSID = data.optLong(KEY_SID);
        this.mRequest = data.optString(KEY_REQUEST);
        this.mResponse = data.optString(KEY_RESPONSE);
        this.mMethod = data.optString(KEY_METHOD);
    }

    /**
     * Serializes the contents of this object to a {@link Bundle}.
     */
    public Bundle toBundle() {
        Bundle data = new Bundle();
        data.putLong(KEY_FID, mFID);
        data.putLong(KEY_SID, mSID);
        data.putString(KEY_REQUEST, mRequest);
        data.putString(KEY_RESPONSE, mResponse);
        data.putString(KEY_METHOD, mMethod);
        return data;
    }

    /**
     * Deserializes the given {@link Bundle} representation of plugin data, populating this
     * object.
     */
    public void fromBundle(Bundle src) {
        this.mFID = src.getLong(KEY_FID);
        this.mSID = src.getLong(KEY_SID);
        this.mRequest = src.getString(KEY_REQUEST);
        this.mResponse = src.getString(KEY_RESPONSE);
        this.mMethod = src.getString(KEY_METHOD);
    }

    /**
     * @see Parcelable
     */
    public static final Creator<MessageData> CREATOR
            = new Creator<MessageData>() {
        public MessageData createFromParcel(Parcel in) {
            return new MessageData(in);
        }

        public MessageData[] newArray(int size) {
            return new MessageData[size];
        }
    };

    private MessageData(Parcel in) {
        int parcelableSize = in.readInt();
        int startPosition = in.dataPosition();
        this.mFID = in.readLong();
        this.mSID = in.readLong();
        this.mRequest = in.readString();
        this.mResponse = in.readString();
        this.mMethod = in.readString();
        if (TextUtils.isEmpty(this.mRequest)) {
            this.mRequest = null;
        }
        if (TextUtils.isEmpty(this.mResponse)) {
            this.mResponse = null;
        }
        if (TextUtils.isEmpty(this.mMethod)) {
            this.mMethod = null;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Inject a placeholder that will store the parcel size from this point on
        // (not including the size itself).
        int sizePosition = parcel.dataPosition();
        parcel.writeInt(0);
        int startPosition = parcel.dataPosition();
        parcel.writeLong(mFID);
        parcel.writeLong(mSID);
        parcel.writeString(TextUtils.isEmpty(mRequest) ? "" : mRequest);
        parcel.writeString(TextUtils.isEmpty(mResponse) ? "" : mResponse);
        parcel.writeString(TextUtils.isEmpty(mMethod) ? "" : mMethod);
        // Go back and write the size
        int parcelableSize = parcel.dataPosition() - startPosition;
        parcel.setDataPosition(sizePosition);
        parcel.writeInt(parcelableSize);
        parcel.setDataPosition(startPosition + parcelableSize);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        try {
            MessageData other = (MessageData) o;
            return other.mFID == mFID
                    && other.mSID == mSID
                    && TextUtils.equals(other.mRequest, mRequest)
                    && TextUtils.equals(other.mResponse, mResponse)
                    && TextUtils.equals(other.mMethod, mMethod);

        } catch (ClassCastException e) {
            return false;
        }
    }

    private static boolean objectEquals(Object x, Object y) {
        if (x == null || y == null) {
            return x == y;
        } else {
            return x.equals(y);
        }
    }

    /**
     * Returns true if the two provided data objects are equal (or both null).
     */
    public static boolean equals(MessageData x, MessageData y) {
        if (x == null || y == null) {
            return x == y;
        } else {
            return x.equals(y);
        }
    }

    @Override
    public String toString() {
        try {
            return serialize().toString();
        } catch (JSONException e) {
            return super.toString();
        }
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public void clean() {
        //TODO: length limit
    }
}
