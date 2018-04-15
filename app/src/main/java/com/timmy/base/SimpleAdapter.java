package com.timmy.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.advance.customView.XiuViewActivity;
import com.timmy.customeView.clockView.ClockViewActivity;
import com.timmy.customeView.countdownTime.CountDownTimeActivity;
import com.timmy.customeView.guaguaWinning.GuaGuaWinningActivity;
import com.timmy.customeView.hotTag.HotTagActivity;
import com.timmy.customeView.imoocRipple.IMoocWaterRippleActivity;
import com.timmy.customeView.letterNavigation.LetterNavigationActivity;
import com.timmy.customeView.loadingLayout.LoadingLayoutActivity;
import com.timmy.customeView.myIndicator.MyIndicatorActivity;
import com.timmy.customeView.myPhotoView.MyPhotoViewActivity;
import com.timmy.customeView.myViewPager.MyViewPagerActivity;
import com.timmy.customeView.notePad.NotePadActivity;
import com.timmy.customeView.radarView.RadarViewActivity;
import com.timmy.framework.annotationCompile.CompileAnnotationActivity;
import com.timmy.framework.annotationRuntime.AnnotationsActivity;
import com.timmy.framework.database.DataBaseActivity;
import com.timmy.framework.downRefresh.DownRefreshActivity;
import com.timmy.framework.eventBusFw.EventBusActivity;
import com.timmy.framework.imageLoaderFw.ImageLoaderActivity;
import com.timmy.framework.mvp.MVPActivity;
import com.timmy.framework.netFw.NetWorkRequestActivity;
import com.timmy.framework.retrofit2.CustomRetrofit2Activity;
import com.timmy.framework.tinker.TinkerActivity;
import com.timmy.framework.vlayout.VLayoutActivity;
import com.timmy.highUI.animatoion.ViewAnimationActivity;
import com.timmy.highUI.cardView.CardViewActivity;
import com.timmy.highUI.collapsingToolbarLayout.CollapsingToolbarLayoutActivity;
import com.timmy.highUI.coordinatorLayout.CoordinatorLayoutActivity2;
import com.timmy.highUI.dialog.DialogActivity;
import com.timmy.highUI.drawerLayout.DrawerLayoutActivity;
import com.timmy.highUI.linearLayoutCompat.LinearLayoutCompatActivity;
import com.timmy.highUI.motionEvent.MotionEventActivity;
import com.timmy.highUI.palette.PaletteActivity;
import com.timmy.highUI.path.PathUseActicity;
import com.timmy.highUI.recyclerview.RecyclerViewActivity;
import com.timmy.highUI.searchView.SearchViewActivity;
import com.timmy.highUI.shader.ShaderUseActicity;
import com.timmy.highUI.snackBar.SnackBarActivity;
import com.timmy.highUI.stretchList.StretchListActivity;
import com.timmy.highUI.tabLayout.TabLayoutActivity;
import com.timmy.highUI.textInputLayout.TextInputLayoutActivity;
import com.timmy.highUI.toast.ToastActivity;
import com.timmy.highUI.toolbar.ToolbarActivity;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;
import com.timmy.project.aidlBinder.AIDLActivity;
import com.timmy.project.dataPersist.DataPersistActivity;
import com.timmy.project.inflate.InflateActivity;
import com.timmy.project.launch.SplashActivity;
import com.timmy.project.launch.WelcomeActivity;
import com.timmy.project.service.ServiceActivity;
import com.timmy.project.softKeyboard.SoftKeyboardActivity;
import com.timmy.project.svg.SVGActivity;
import com.timmy.project.twoCode.downLoad.TCDownLoadActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.TabHolder> {

    private List<String> dataList;
    private Context context;

    public SimpleAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public TabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_main_page, null);
        return new TabHolder(view);
    }

    @Override
    public void onBindViewHolder(TabHolder holder, final int position) {
        final String item = dataList.get(position);
        holder.mContent.setText(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(context, item, android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TabHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc)
        TextView mContent;

        private TabHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
