package com.timmy.customeView.letterNavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timmy.R;

import java.util.List;

/**
 * Created by timmy1 on 17/2/12.
 */
public class LetterNavAdapter extends BaseAdapter {

    private List<User> list;
    private Context context;
    private final LayoutInflater layoutInflater;

    public LetterNavAdapter(Context context, List<User> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public User getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_letter, parent, false);
            holder = new MyViewHolder();
            holder.letter = (TextView) convertView.findViewById(R.id.tv_letter);
            holder.userName = (TextView) convertView.findViewById(R.id.tv_user_name);

            convertView.setTag(holder);

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        User user = list.get(position);
        holder.userName.setText(user.getUserName());
        holder.letter.setText(user.getFirstLetter());
        if (position > 0 && user.getFirstLetter().equals(list.get(position - 1).getFirstLetter())) {
            holder.letter.setVisibility(View.GONE);
        } else {
            holder.letter.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    //传入一个分组值[A....Z],获得该分组的第一项的position
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }


    class MyViewHolder {
        TextView letter, userName;
    }
}
