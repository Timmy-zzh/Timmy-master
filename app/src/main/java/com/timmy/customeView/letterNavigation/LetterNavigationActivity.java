package com.timmy.customeView.letterNavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 字母导航栏控件实现：
 * 1.左侧使用listview控件，数据包含用户姓名和第一个首字母
 * 2.右侧使用自定义控件处理
 * 3.右侧字母点击后，左侧listview滑动到该字母对应的位置
 */
public class LetterNavigationActivity extends BaseActivity {

    private String TAG = "LetterNavigationActivity";
    private List<User> userNameList;
    private TextView dialogText;
    private ListView listView;
    private LetterNavigationView letterNavigationView;
    private LetterNavAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_navigation);
        initToolBar();
        initData();

        dialogText = (TextView) findViewById(R.id.tv_dialog);
        letterNavigationView = (LetterNavigationView) findViewById(R.id.letter_nav_view);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new LetterNavAdapter(this, userNameList);
        listView.setAdapter(adapter);

        letterNavigationView.setOnLetterUpdateListener(new LetterNavigationView.OnLetterUpdateListener() {
            @Override
            public void letterUpdate(String letter, int position) {
                int positionForSection = adapter.getPositionForSection(letter.charAt(0));
                dialogText.setText(letter);
                listView.setSelection(positionForSection);
            }
        });

        //监听listview滑动，左边listview滑动，右侧的字母导航也要随着滑动而有所改变
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                User item = adapter.getItem(firstVisibleItem);
                String firstLetter = item.getFirstLetter();//首字母
                Logger.d(TAG, "firstLetter:" + firstLetter);
                int position = firstLetter.hashCode() - 65;
                letterNavigationView.updateLetrer(position);
            }
        });

    }

    //初始化数据-将所有需要的姓名放到list集合中
    private void initData() {
        //从string。xml文件中获取
        String[] userNames = getResources().getStringArray(R.array.arrUsernames);
        userNameList = new ArrayList<>();
        int size = userNames.length;
        for (int i = 0; i < size; i++) {
            User user = new User();
            String userNameStr = userNames[i];
            user.setUserName(userNameStr);
            user.setImg(R.mipmap.ic_launcher);

            String userNamePinyin = ChineseToPinyinHelper.getInstance().getPinyin(userNameStr).toUpperCase();
            user.setPinyin(userNamePinyin);

            //根据姓名拼音获取姓名的第一个字母
            String firstLetter = userNamePinyin.substring(0, 1);
            if (firstLetter.matches("[A-Z]")) {
                user.setFirstLetter(firstLetter);
            } else {
                user.setFirstLetter("#");
            }
            userNameList.add(user);
        }

        //排序
        Collections.sort(userNameList, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
    }


    public interface OnListViewScrollListener {
        void updateLetrer(int position);
    }


}
