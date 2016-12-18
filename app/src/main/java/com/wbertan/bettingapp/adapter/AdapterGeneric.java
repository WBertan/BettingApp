package com.wbertan.bettingapp.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbertan.bettingapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class AdapterGeneric<T> extends RecyclerView.Adapter<AdapterGeneric.ViewHolder> {
    private List<T> mDataset;
    private ViewHolderAction mViewHolderAction;
    private int mSelectedPosition = -1;
    private int mLayoutRes;
    private int mVariableId;

    public AdapterGeneric(@LayoutRes int aLayoutRes, int aVariableId) {
        mDataset = new ArrayList<>();
        mLayoutRes = aLayoutRes;
        mVariableId = aVariableId;
    }

    public AdapterGeneric(@LayoutRes int aLayoutRes, int aVariableId, ViewHolderAction aViewHolderAction) {
        this(aLayoutRes, aVariableId);
        this.mViewHolderAction = aViewHolderAction;
    }

    public void add(T aObject) {
        mDataset.add(aObject);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void addAll(Collection<T> aColletion) {
        mDataset.addAll(aColletion);
        notifyDataSetChanged();
    }

    public void remove(T aObject) {
        mDataset.remove(aObject);
    }

    public void remove(int aPosition) {
        mDataset.remove(aPosition);
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public T getSelectedItem() {
        if(mSelectedPosition > -1) {
            return mDataset.get(mSelectedPosition);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : -1;
    }

    @Override
    public AdapterGeneric.ViewHolder onCreateViewHolder(ViewGroup aParent, int aViewType) {
        View view = LayoutInflater.from(aParent.getContext()).inflate(mLayoutRes, aParent, false);
        return new ViewHolder(view);
    }

    public interface ViewHolderAction {
        void executeRecyclerItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ViewDataBinding binding;
        ViewHolder(View aView) {
            super(aView);
            mCardView = (CardView) aView.findViewById(R.id.card_view);
            binding = DataBindingUtil.bind(aView);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder aHolder, int aPosition) {
        Object object = mDataset.get(aPosition);
        aHolder.binding.setVariable(mVariableId, object);
        aHolder.binding.executePendingBindings();

        aHolder.mCardView.setCardBackgroundColor(mSelectedPosition == aPosition ? Color.LTGRAY : Color.WHITE);
        aHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedPosition = aHolder.getAdapterPosition();
                notifyDataSetChanged();
                if(mViewHolderAction != null) {
                    mViewHolderAction.executeRecyclerItemClick(view, aHolder.getAdapterPosition());
                }
            }
        });
    }
}