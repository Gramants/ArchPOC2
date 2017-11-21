package com.ste.arch.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.Utils;
import com.ste.arch.entities.IssueDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class IssueDataAdapterPaged extends PagedListAdapter<IssueDataModel, IssueDataAdapterPaged.Holder> {

    private final LayoutInflater mInflator;
    private List<IssueDataModel> mIssueList;

    public IssueDataAdapterPaged(LayoutInflater inflator) {
        super(IssueDataModel.DIFF_CALLBACK);
        mInflator = inflator;
        mIssueList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflator.inflate(R.layout.item_issue, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mTextViewTitle.setText(mIssueList.get(position).getTitle());
        String id = mIssueList.get(position).getNumber().toString();
        holder.mTextViewId.setText(id);
        holder.mTextViewCreator.setText(mIssueList.get(position).getUsername());


    }

    @Override
    public int getItemCount() {
        return mIssueList.size();
    }

    public void addIssues(List<IssueDataModel> issues) {
        
        ArrayList<IssueDataModel> result = new ArrayList<>();
        for (IssueDataModel issue : issues) {
            issue.setTitle(issue.getTitle().trim().toUpperCase() + " ORDERED");
            result.add(issue);
        }
        Collections.sort(result, new Utils.CustomComparator());

        mIssueList.addAll(result);
        notifyDataSetChanged();
    }

    public void clearIssues() {
        mIssueList.clear();

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