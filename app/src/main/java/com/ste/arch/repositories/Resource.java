package com.ste.arch.repositories;


        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;


public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable public final String message;
    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> successfromdb(@NonNull T data) {
        return new Resource<>(Status.SUCCESSFROMDB, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public static <T> Resource<T> successfromui(@NonNull T data) {
        return new Resource<>(Status.SUCCESSFROMUI, data, null);
    }


}