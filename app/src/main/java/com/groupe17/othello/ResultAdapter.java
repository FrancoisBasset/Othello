package com.groupe17.othello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Result> {

    public ResultAdapter(Context context, List<Result> results) {
        super(context, 0, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(R.layout.row_layout, null);

        Result result = getItem(position);

        TextView result_textView = row.findViewById(R.id.result_textView);
        result_textView.setText(result.getPercent() + " %");

        switch (result.getDiskColor()) {
            case White:
                row.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                break;
            case Black:
                row.setBackgroundResource(R.color.black);
                result_textView.setTextColor(getContext().getResources().getColor(R.color.white));
                break;
        }

        return row;
    }
}