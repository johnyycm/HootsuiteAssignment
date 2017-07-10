package com.example.chenminyao.hootsuiteassignment.Binding;

/**
 * Created by chenminyao on 2017-07-09.
 */

public class ItemBinderBase<T> implements ItemBinder<T>
{
    protected final int bindingVariable;
    protected final int layoutId;

    public ItemBinderBase(int bindingVariable, int layoutId)
    {
        this.bindingVariable = bindingVariable;
        this.layoutId = layoutId;
    }

    public int getLayoutRes(T model)
    {
        return layoutId;
    }

    public int getBindingVariable(T model)
    {
        return bindingVariable;
    }
}
