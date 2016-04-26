package com.cy.yangbo.wardrobe.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.cy.yangbo.wardrobe.R;
import com.cy.yangbo.wardrobe.bean.Costume;
import com.cy.yangbo.wardrobe.bean.CostumeCategory;
import com.cy.yangbo.wardrobe.comm.AppConfig;
import com.cy.yangbo.wardrobe.comm.BaseActivity;
import com.cy.yangbo.wardrobe.presenter.CostumeCategoryPresenter;
import com.cy.yangbo.wardrobe.util.CameraProxy;
import com.cy.yangbo.wardrobe.util.CameraResult;
import com.cy.yangbo.wardrobe.util.LogUtils;
import com.cy.yangbo.wardrobe.view.inter.AddCostumeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/2/29.
 */
public class AddCostumeActivity extends BaseActivity implements AddCostumeView {

    private static final String TAG = AddCostumeActivity.class.getSimpleName();

    public static final int REQUEST_CAMERA_PERMISSION = 0x11;

    @Bind(R.id.sdv_add_costume_pic)
    SimpleDraweeView mPicIV;
    @Bind(R.id.rv_add_costume_sort_part)
    RecyclerView mSortRV;
    @Bind(R.id.btn_add_costume_create_time)
    Button mTimeBtn;
    @Bind(R.id.btn_add_costume_remark)
    Button mRemarkBtn;
    @Bind(R.id.fab_add_costume)
    FloatingActionButton mOKBtn;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rl_remark_bar)
    RelativeLayout mRemarkBarRL;
    @Bind(R.id.ll_button_bar)
    LinearLayout mButtonBarLL;
    @Bind(R.id.et_remark)
    EditText mRemarkET;
    @Bind(R.id.iv_remark_ok)
    ImageView mRemarkOKBtn;

    private BottomSheetDialog pickDialog;

    private CameraProxy cameraProxy;

    private CostumeCategoryPresenter ccPresenter;

    private Costume mCostume;

    private int picWidth;
    private int picHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCostume = new Costume();
        mCostume.setId(UUID.randomUUID().toString());
        mCostume.setCreate_time(new SimpleDateFormat("yyyy-MM").format(new Date()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraProxy.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
                cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
            } else {
                Snackbar.make(mPicIV, "没有权限无法进行拍照！", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.add_costume_activity);

        ButterKnife.bind(this);

        ccPresenter = new CostumeCategoryPresenter(this);

        cameraProxy = new CameraProxy(new CameraResult() {
            @Override
            public void onSuccess(String path) {
                Snackbar.make(mPicIV, "path:" + path, Snackbar.LENGTH_SHORT).show();
                //射入图片
                mCostume.setPic(path);

                //Uri uri = Uri.parse("http://p2.so.qhimg.com/t0115e9737ab2004822.jpg");
                //Uri uri = Uri.parse("file:///storage/emulated/0/Wardrobe/picture/test1.jpg");
                Uri uri = Uri.fromFile(new File(path));
                final int width = picWidth, height = picHeight;
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(width, height))
                        .setAutoRotateEnabled(true)
                        .build();
                AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(mPicIV.getController())
                        .setImageRequest(request)
                        .build();
                LogUtils.e(TAG, uri.toString());
                mPicIV.setController(controller);
            }

            @Override
            public void onFail(String message) {
                Snackbar.make(mPicIV, message, Snackbar.LENGTH_SHORT).show();
            }
        }, AddCostumeActivity.this);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        picWidth = screenWidth;
        int height = screenWidth * 3 / 4;
        picHeight = height;
        ViewGroup.LayoutParams params = mPicIV.getLayoutParams();
        params.height = height;
        mPicIV.setLayoutParams(params);

        initCostumeCategoryView();
        mTimeBtn.setText(new SimpleDateFormat("yyyy-MM").format(new Date()));
    }

    @Override
    protected void setListener() {
        super.setListener();
        RxView.clicks(mPicIV).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showPickDialog();
            }
        });

        RxView.clicks(mTimeBtn).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(context, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        if (month < 10) {
                            mTimeBtn.setText(year + "-0" + month);
                        } else {
                            mTimeBtn.setText(year + "-" + month);
                        }
                    }
                }).textConfirm("确 定")
                        .textCancel("取消")
                        .minYear(1990)
                        .maxYear(2100)
                        .dateChose(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                        .build();
                pickerPopWin.showPopWin(AddCostumeActivity.this);

            }
        });

        RxView.clicks(mRemarkBtn).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showRemarkBar();
            }
        });

        RxView.clicks(mRemarkOKBtn).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                hideRemarkBar();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ccPresenter.onDestory();
    }

    private void showRemarkBar() {
        mRemarkBarRL.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.show_remark_bar);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mRemarkBarRL.setVisibility(View.VISIBLE);
                mRemarkBarRL.bringToFront();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRemarkBarRL.startAnimation(animation);

        mButtonBarLL.clearAnimation();
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.hide_button_bar);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mButtonBarLL.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mButtonBarLL.startAnimation(animation1);
    }

    private void hideRemarkBar() {
        mRemarkBarRL.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hide_remark_bar);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mButtonBarLL.bringToFront();
                mRemarkBarRL.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRemarkBarRL.startAnimation(animation);

        mButtonBarLL.clearAnimation();
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.show_button_bar);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mButtonBarLL.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mButtonBarLL.startAnimation(animation1);
    }

    private void showPickDialog() {
        final DialogPlus dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_pick_picture))
                .setGravity(Gravity.TOP)
                .setExpanded(false)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
                        if (view.getId() == R.id.btn_photo) {
                            /**
                             * 适配M的动态权限
                             */
                            if (Build.VERSION.SDK_INT >= 23) {
                                int checkCameraPermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
                                if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(AddCostumeActivity.this, new String[]{Manifest.permission.CAMERA},
                                            REQUEST_CAMERA_PERMISSION);
                                    dialog.dismiss();
                                    return;
                                }
                            }
                            cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        } else if (view.getId() == R.id.btn_album) {
                            cameraProxy.getPhoto2Album(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        }
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }

    private void showBootomSheetPickDialog() {
        pickDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pick_picture, null);
        RxView.clicks(view.findViewById(R.id.btn_photo))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        pickDialog.dismiss();
                        String fileName = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()) + ".jpg";
                        cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                    }
                });
        RxView.clicks(view.findViewById(R.id.btn_album))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        pickDialog.dismiss();
                        String fileName = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()) + ".jpg";
                        cameraProxy.getPhoto2Album(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                    }
                });
        pickDialog.setContentView(view);

        pickDialog.show();
    }

    private void initCostumeCategoryView() {
       /* List datas = new ArrayList<CostumeCategory>();
        for(int i = 0; i < 100; i++){
            CostumeCategory category = new CostumeCategory();
            category.setId(i);
            category.setName("类别" + 1);
            category.setOrder(i);
            datas.add(category);
        }*/
        //Realm realm = Realm.getDefaultInstance();

        mSortRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

        CostumeCategoryAdapter adapter = new CostumeCategoryAdapter(ccPresenter.findAllCostumeCategory(), ccPresenter, context);
        MyItemTouchHelperCallback helperCallback = new MyItemTouchHelperCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(helperCallback);
        helper.attachToRecyclerView(mSortRV);
        mSortRV.setAdapter(adapter);
    }

    @Override
    public boolean submit() {
        return false;
    }

    public static class CostumeCategoryAdapter extends RecyclerView.Adapter<CostumeCategoryAdapter.CategoryVierHolder>
            implements MyItemTouchHelperCallback.OnItemMoveAndSwipedListener {

        public static final int ITEM_TYPE_COMMON = 0;
        public static final int ITEM_TYPE_ADD = 1;

        private Context mContext;
        private RealmResults<CostumeCategory> mDatas;
        private CostumeCategoryPresenter ccPresenter;

        public CostumeCategoryAdapter(@NonNull RealmResults<CostumeCategory> mDatas, @NonNull CostumeCategoryPresenter ccPresenter, @NonNull Context context) {
            this.mDatas = mDatas;
            if (this.mDatas.size() > 0) {
                this.mDatas.get(0).setIsSelected(1);
                this.mDatas.get(0).isSelected();
            }
            this.mContext = context;
            this.ccPresenter = ccPresenter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mDatas.size()) {
                return ITEM_TYPE_ADD;
            }

            return ITEM_TYPE_COMMON;
        }

        @Override
        public CategoryVierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_costume_category, null);
            if (viewType == ITEM_TYPE_COMMON) {
                return new CategoryVierHolder(view, ITEM_TYPE_COMMON);
            } else {
                TextView textView = (TextView) view.findViewById(R.id.tv_costume_category_name);
                textView.setBackgroundResource(R.drawable.item_costume_category_add_bg);
                textView.setTextColor(mContext.getResources().getColor(R.color.green));
                return new CategoryVierHolder(view, ITEM_TYPE_ADD);
            }
        }

        @Override
        public void onBindViewHolder(CategoryVierHolder holder, int position) {
            if (position == mDatas.size()) {
                holder.nameTV.setText("新分类");
            } else {
                holder.nameTV.setTag(mDatas.get(position));
                if (mDatas.get(position).isSelected() > 0) {
                    holder.nameTV.setActivated(true);
                    holder.nameTV.setTextColor(mContext.getResources().getColor(R.color.red));
                } else {
                    holder.nameTV.setActivated(false);
                    holder.nameTV.setTextColor(mContext.getResources().getColor(R.color.orange));
                }
                holder.nameTV.setText(mDatas.get(position).getName());
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size() + 1;
        }

        @Override
        public void onMove(int fromIndex, int toIndex) {
            LogUtils.e(TAG, "onMove: " + fromIndex + "<--->" + toIndex);
            if (fromIndex >= mDatas.size() || toIndex >= mDatas.size()) {
                return;
            }
            ccPresenter.swapOrder(mDatas.get(fromIndex), mDatas.get(toIndex));
            notifyItemMoved(fromIndex, toIndex);
        }

        @Override
        public void onSwiped(int index) {
            notifyItemRemoved(index);
        }

        public class CategoryVierHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_costume_category_name)
            public TextView nameTV;

            public CategoryVierHolder(View itemView, final int itemType) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                nameTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemType == ITEM_TYPE_COMMON) {
                            Observable.from(mDatas)
                                    .subscribe(new Action1<CostumeCategory>() {
                                        @Override
                                        public void call(CostumeCategory costumeCategory) {
                                            costumeCategory.setIsSelected(-1);
                                        }
                                    });
                            if (v.getTag() != null) {
                                ((CostumeCategory) v.getTag()).setIsSelected(1);
                            }
                            notifyDataSetChanged();
                        } else {

                        }
                    }
                });
            }
        }
    }

    public static class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

        private OnItemMoveAndSwipedListener mListener;

        public MyItemTouchHelperCallback(@NonNull OnItemMoveAndSwipedListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.START | ItemTouchHelper.END;
            int swipeFlag = 0;
            return makeMovementFlags(dragFlag, swipeFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mListener.onSwiped(viewHolder.getAdapterPosition());
        }

        public interface OnItemMoveAndSwipedListener {
            void onMove(int fromIndex, int toIndex);

            void onSwiped(int index);
        }
    }
}
