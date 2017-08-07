/*
 * Copyright 2017 CHIMPS Lab, Carnegie Mellon University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.cmu.chimps.messageontap_api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class TaskData implements Parcelable {
    private static final String KEY_SID = "flow_id";
    private static final String KEY_TID = "sequence_id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TYPE = "type";
    private static final String KEY_METHOD = "method";


    private long mSID = 0;
    private long mTID = 0;
    private String mContent = null;
    private String mType = null;
    private String mMethod = null;

    public TaskData() {

    }

    /**
     * Returns the session ID
     * Default 0.
     */
    public long sid() {
        return mSID;
    }

    /**
     * Sets the session ID. Default 0.
     */
    public TaskData sid(long sid) {
        mSID = sid;
        return this;
    }

    /**
     * Returns the task ID
     * Default 0.
     */
    public long tid() {
        return mTID;
    }

    /**
     * Sets the task ID. Default 0.
     */
    public TaskData tid(long tid) {
        mTID = tid;
        return this;
    }

    /**
     * Returns the content info
     * Default null.
     */
    public String content() {
        return mContent;
    }

    /**
     * Sets the content info. Default null.
     */
    public TaskData content(String content) {
        mContent = content;
        return this;
    }

    /**
     * Returns the type
     * Default null.
     */
    public String type() {
        return mType;
    }

    /**
     * Sets the type. Default null.
     */
    public TaskData type(String type) {
        mType = type;
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
    public TaskData method(String method) {
        mMethod = method;
        return this;
    }

    /**
     * Serializes the contents of this object to JSON.
     */
    public JSONObject serialize() throws JSONException {
        JSONObject data = new JSONObject();
        data.put(KEY_SID, mSID);
        data.put(KEY_TID, mTID);
        data.put(KEY_CONTENT, mContent);
        data.put(KEY_TYPE, mType);
        data.put(KEY_METHOD, mMethod);
        return data;
    }

    /**
     * Deserializes the given JSON representation of plugin data, populating this
     * object.
     */
    public void deserialize(JSONObject data) throws JSONException {
        this.mSID = data.optLong(KEY_SID);
        this.mTID = data.optLong(KEY_TID);
        this.mContent = data.optString(KEY_CONTENT);
        this.mType = data.optString(KEY_TYPE);
        this.mMethod = data.optString(KEY_METHOD);
    }

    /**
     * Serializes the contents of this object to a {@link Bundle}.
     */
    public Bundle toBundle() {
        Bundle data = new Bundle();
        data.putLong(KEY_SID, mSID);
        data.putLong(KEY_TID, mTID);
        data.putString(KEY_CONTENT, mContent);
        data.putString(KEY_TYPE, mType);
        data.putString(KEY_METHOD, mMethod);
        return data;
    }

    /**
     * Deserializes the given {@link Bundle} representation of plugin data, populating this
     * object.
     */
    public void fromBundle(Bundle src) {
        this.mSID = src.getLong(KEY_SID);
        this.mTID = src.getLong(KEY_TID);
        this.mContent = src.getString(KEY_CONTENT);
        this.mType = src.getString(KEY_TYPE);
        this.mMethod = src.getString(KEY_METHOD);
    }

    /**
     * @see Parcelable
     */
    public static final Creator<TaskData> CREATOR
            = new Creator<TaskData>() {
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    private TaskData(Parcel in) {
        int parcelableSize = in.readInt();
        int startPosition = in.dataPosition();
        this.mSID = in.readLong();
        this.mTID = in.readLong();
        this.mContent = in.readString();
        this.mType = in.readString();
        this.mMethod = in.readString();
        if (TextUtils.isEmpty(this.mContent)) {
            this.mContent = null;
        }
        if (TextUtils.isEmpty(this.mType)) {
            this.mType = null;
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
        parcel.writeLong(mSID);
        parcel.writeLong(mTID);
        parcel.writeString(TextUtils.isEmpty(mContent) ? "" : mContent);
        parcel.writeString(TextUtils.isEmpty(mType) ? "" : mType);
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
            TaskData other = (TaskData) o;
            return other.mSID == mSID
                    && other.mTID == mTID
                    && TextUtils.equals(other.mContent, mContent)
                    && TextUtils.equals(other.mType, mType)
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
    public static boolean equals(TaskData x, TaskData y) {
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
