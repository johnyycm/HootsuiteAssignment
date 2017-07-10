package com.example.chenminyao.hootsuiteassignment.Binding;

/**
 * Created by chenminyao on 2017-07-09.
 */

public interface ItemBinder<T>
{
    int getLayoutRes(T model);
    int getBindingVariable(T model);
}
