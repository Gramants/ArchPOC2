package com.ste.arch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.entities.ContributorDataModel;

import java.util.ArrayList;
import java.util.List;




public class ContributorDataAdapter extends RecyclerView.Adapter<ContributorDataAdapter.Holder> {

    private final LayoutInflater mInflator;
    private List<ContributorDataModel> mContributorList;

    public ContributorDataAdapter(LayoutInflater inflator) {
        mInflator = inflator;
        mContributorList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflator.inflate(R.layout.item_contributor, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mTextViewTitle.setText(mContributorList.get(position).getLogin());
        String id = String.valueOf(mContributorList.get(position).getId());
        holder.mTextViewId.setText(id);
        holder.mTextViewCreator.setText(mContributorList.get(position).getHtml_url());


    }

    @Override
    public int getItemCount() {
        return mContributorList.size();
    }

    public void addContributors(List<ContributorDataModel> issues) {
        mContributorList.addAll(issues);
        notifyDataSetChanged();
    }

    public void clearContributors() {
        mContributorList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewId;
        TextView mTextViewCreator;

        public Holder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.title);
            mTextViewId = (TextView) v.findViewById(R.id.issue_id);
            mTextViewCreator = (TextView) v.findViewById(R.id.creator_name);
        }
    }
}