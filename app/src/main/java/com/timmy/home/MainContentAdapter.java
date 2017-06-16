package com.timmy.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.advance.customView.XiuViewActivity;
import com.timmy.customeView.clockView.ClockViewActivity;
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
import com.timmy.framework.annotationsFramework.AnnotationsActivity;
import com.timmy.framework.database.DataBaseActivity;
import com.timmy.framework.imageLoaderFw.ImageLoaderActivity;
import com.timmy.framework.netFw.NetWorkRequestActivity;
import com.timmy.framework.tinker.TinkerActivity;
import com.timmy.highUI.animatoion.ViewAnimationActivity;
import com.timmy.highUI.cardView.CardViewActivity;
import com.timmy.highUI.collapsingToolbarLayout.CollapsingToolbarLayoutActivity;
import com.timmy.highUI.coordinatorLayout.CoordinatorLayoutActivity2;
import com.timmy.highUI.linearLayoutCompat.LinearLayoutCompatActivity;
import com.timmy.highUI.motionEvent.MotionEventActivity;
import com.timmy.highUI.palette.PaletteActivity;
import com.timmy.highUI.path.PathUseActicity;
import com.timmy.highUI.recyclerview.RecyclerViewActivity;
import com.timmy.highUI.drawerLayout.DrawerLayoutActivity;
import com.timmy.highUI.searchView.SearchViewActivity;
import com.timmy.highUI.shader.ShaderUseActicity;
import com.timmy.highUI.snackBar.SnackBarActivity;
import com.timmy.highUI.stretchList.StretchListActivity;
import com.timmy.highUI.tabLayout.TabLayoutActivity;
import com.timmy.highUI.toast.ToastActivity;
import com.timmy.highUI.toolbar.ToolbarActivity;
import com.timmy.highUI.textInputLayout.TextInputLayoutActivity;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;
import com.timmy.project.aidlBinder.AIDLActivity;
import com.timmy.project.inflate.InflateActivity;
import com.timmy.project.launch.SplashActivity;
import com.timmy.project.launch.WelcomeActivity;
import com.timmy.project.service.ServiceActivity;
import com.timmy.project.svg.SVGActivity;
import com.timmy.highUI.dialog.DialogActivity;
import com.timmy.project.twoCode.downLoad.TCDownLoadActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.TabHolder> {

    private List<MainModel> dataList;
    private Context context;

    public MainContentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MainModel> dataList) {
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
        final MainModel model = dataList.get(position);
        holder.mContent.setText(model.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(context, dataList.get(position).getDesc(), android.widget.Toast.LENGTH_SHORT).show();
                switch (model.getTag()) {
                    case MainTag.TAG_XIUXIU:
                        Util.gotoNextActivity(context, XiuViewActivity.class);
                        break;
                    case MainTag.TAG_QQ_ZONE_STRETCH:
                        Util.gotoNextActivity(context, StretchListActivity.class);
                        break;
                    case MainTag.TAG_RECYCLER_VIEW:
                        Util.gotoNextActivity(context, RecyclerViewActivity.class);
                        break;
                    case MainTag.TAG_COLLAPSING_TOOLBAR_LAYOUT:
                        Util.gotoNextActivity(context, CollapsingToolbarLayoutActivity.class);
                        break;
                    case MainTag.TAG_SLIDESLIP:
                        Util.gotoNextActivity(context, DrawerLayoutActivity.class);
                        break;
                    case MainTag.TAG_SNACKBAR:
                        Util.gotoNextActivity(context, SnackBarActivity.class);
                        break;
                    case MainTag.TAG_TOAST:
                        Util.gotoNextActivity(context, ToastActivity.class);
                        break;
                    case MainTag.TAG_TEXT_INPUT_LAYOUT:
                        Util.gotoNextActivity(context, TextInputLayoutActivity.class);
                        break;
                    case MainTag.TAG_TOOLBAR:
                        Util.gotoNextActivity(context, ToolbarActivity.class);
                        break;
                    case MainTag.TAG_SEARCH_VIEW:
                        Util.gotoNextActivity(context, SearchViewActivity.class);
                        break;
                    case MainTag.TAG_LINEAR_LAYOUT_COMPAT:
                        Util.gotoNextActivity(context, LinearLayoutCompatActivity.class);
                        break;
                    case MainTag.TAG_TAB_LAYOUT:
                        Util.gotoNextActivity(context, TabLayoutActivity.class);
                        break;
                    case MainTag.TAG_PALETTE:
                        Util.gotoNextActivity(context, PaletteActivity.class);
                        break;
                    case MainTag.TAG_CARD_VIEW:
                        Util.gotoNextActivity(context, CardViewActivity.class);
                        break;
                    case MainTag.TAG_COORDINATOR_LAYOUT:
                        Util.gotoNextActivity(context, CoordinatorLayoutActivity2.class);
                        break;
                    case MainTag.TAG_ANIMATION_VIEW:
                        Util.gotoNextActivity(context, ViewAnimationActivity.class);
                        break;
                    case MainTag.TAG_MOTION_EVENT:
                        Util.gotoNextActivity(context, MotionEventActivity.class);
                        break;
                    case MainTag.TAG_PATH:
                        Util.gotoNextActivity(context, PathUseActicity.class);
                        break;
                    case MainTag.TAG_SHADER:
                        Util.gotoNextActivity(context, ShaderUseActicity.class);
                        break;
                    case MainTag.TAG_DIALOG:
                        Util.gotoNextActivity(context, DialogActivity.class);
                        break;


                    ///////////////////////////自定义控件

                    case MainTag.CUSTOMEVIEW.TAG_CLOCK_VIEW:
                        Util.gotoNextActivity(context, ClockViewActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_HOT_TAG:
                        Util.gotoNextActivity(context, HotTagActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_LETTER_NAVIGATION:
                        Util.gotoNextActivity(context, LetterNavigationActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_NOTE_PAD:
                        Util.gotoNextActivity(context, NotePadActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_MY_VIEWPAGER:
                        Util.gotoNextActivity(context, MyViewPagerActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_MY_INDICATOR:
                        Util.gotoNextActivity(context, MyIndicatorActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_GUAGUA_WINNING:
                        Util.gotoNextActivity(context, GuaGuaWinningActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_PHOTO_VIEW:
                        Util.gotoNextActivity(context, MyPhotoViewActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_LOADING_LAYOUT:
                        Util.gotoNextActivity(context, LoadingLayoutActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_RADAR_VIEW:
                        Util.gotoNextActivity(context, RadarViewActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_IMOOC_RIPPLE:
                        Util.gotoNextActivity(context, IMoocWaterRippleActivity.class);
                        break;

                    ///////////////////////////项目总结
                    case MainTag.PROJECT.TAG_ACTIVITY_LAUNCH:
                        Util.gotoNextActivity(context, WelcomeActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_ACTIVITY_SPLASH:
                        Util.gotoNextActivity(context, SplashActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_SVG:
                        Util.gotoNextActivity(context, SVGActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_INFLATE:
                        Util.gotoNextActivity(context, InflateActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_SERVICE:
                        Util.gotoNextActivity(context, ServiceActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_AIDL_BINDER:
                        Util.gotoNextActivity(context, AIDLActivity.class);
                        break;
                    case MainTag.PROJECT.TAG_TWOCODE_DOWNLOAD:
                        Util.gotoNextActivity(context, TCDownLoadActivity.class);
                        break;


                    ///////////////////////////框架学习
                    case MainTag.FRAMEWORK.TAG_ANNOTATIONS:
                        Util.gotoNextActivity(context, AnnotationsActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_NETWORK_REQUEST:
                        Util.gotoNextActivity(context,NetWorkRequestActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_TENCENT_TINKER:
                        Util.gotoNextActivity(context,TinkerActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_DATABASE:
                        Util.gotoNextActivity(context,DataBaseActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_IMAGE_LOADER:
                        Util.gotoNextActivity(context, ImageLoaderActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TabHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_desc)
        TextView mContent;

        public TabHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
