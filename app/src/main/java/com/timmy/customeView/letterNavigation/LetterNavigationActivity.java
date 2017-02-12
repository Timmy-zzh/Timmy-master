package com.timmy.customeView.letterNavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

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

    private List<User> userNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_navigation);
        initToolBar();
        initData();

        ListView listView = (ListView) findViewById(R.id.list_view);
        LetterNavAdapter adapter = new LetterNavAdapter(this, userNameList);
        listView.setAdapter(adapter);


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
}
