
package com.policia.remote.response;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoliciaLoginResponse implements Serializable, Parcelable
{

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("expires_in")
    @Expose
    public int expiresIn;
    public final static Creator<PoliciaLoginResponse> CREATOR = new Creator<PoliciaLoginResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PoliciaLoginResponse createFromParcel(Parcel in) {
            return new PoliciaLoginResponse(in);
        }

        public PoliciaLoginResponse[] newArray(int size) {
            return (new PoliciaLoginResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3102785618733546525L;

    protected PoliciaLoginResponse(Parcel in) {
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.tokenType = ((String) in.readValue((String.class.getClassLoader())));
        this.expiresIn = ((int) in.readValue((int.class.getClassLoader())));
    }

    public PoliciaLoginResponse() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(accessToken);
        dest.writeValue(tokenType);
        dest.writeValue(expiresIn);
    }

    public int describeContents() {
        return  0;
    }

}
