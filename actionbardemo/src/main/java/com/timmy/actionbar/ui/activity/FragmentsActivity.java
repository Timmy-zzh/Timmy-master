package com.timmy.actionbar.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.timmy.actionbar.R;
import com.timmy.actionbar.ui.fragment.CatalogFragment;
import com.timmy.actionbar.ui.fragment.ContentFragment;

/**
 * 在本Activity中添加目录Fragment,当选择了目录后,通过回调接口的方式通知Activity处理业务逻辑
 */
public class FragmentsActivity extends AppCompatActivity implements CatalogFragment.OnCatalogSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView() {
        CatalogFragment fragment = new CatalogFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, fragment).commit();
    }

    //回调方法--当选择了目录后调用的方法--Frgment替换成内容Fragment
    @Override
    public void onCatalogSelcted(int position) {
//        ContentFragment contentFragment = new ContentFragment();

//        if (contentFragment != null) {
//            // If article frag is available, we're in two-pane layout...
//
//            // Call a method in the contentFragmentment to update its content
//            contentFragment.updateArticleView(position);
//        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ContentFragment newFragment = new ContentFragment();
            Bundle args = new Bundle();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fl_fragment, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
//        }
    }
}
