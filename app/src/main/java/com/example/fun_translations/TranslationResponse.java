package com.example.fun_translations;

import android.os.Parcel;
import android.os.Parcelable;

/*
    Por algum motivo essa classe tinha que ser Parcelable, fiz bem por cima,
    não consegui entender totalmente o funcionamento de uma classe parcelable.
 */

public class TranslationResponse implements Parcelable {
    public Success success;
    public Contents contents;

    public TranslationResponse() {}

    protected TranslationResponse(Parcel in) {
        success = in.readParcelable(Success.class.getClassLoader());
        contents = in.readParcelable(Contents.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(success, flags);
        dest.writeParcelable(contents, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TranslationResponse> CREATOR = new Creator<TranslationResponse>() {
        @Override
        public TranslationResponse createFromParcel(Parcel in) {
            return new TranslationResponse(in);
        }

        @Override
        public TranslationResponse[] newArray(int size) {
            return new TranslationResponse[size];
        }
    };

    public static class Success implements Parcelable {
        public int total;

        public Success() {}

        protected Success(Parcel in) {
            total = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(total);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Success> CREATOR = new Creator<Success>() {
            @Override
            public Success createFromParcel(Parcel in) {
                return new Success(in);
            }

            @Override
            public Success[] newArray(int size) {
                return new Success[size];
            }
        };
    }

    public static class Contents implements Parcelable {
        public String translated;   // Tradução
        public String text;         // Texto Original
        public String translation;  // Tipo de tradução

        public Contents() {}

        protected Contents(Parcel in) {
            translated = in.readString();
            text = in.readString();
            translation = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(translated);
            dest.writeString(text);
            dest.writeString(translation);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Contents> CREATOR = new Creator<Contents>() {
            @Override
            public Contents createFromParcel(Parcel in) {
                return new Contents(in);
            }

            @Override
            public Contents[] newArray(int size) {
                return new Contents[size];
            }
        };
    }
}
