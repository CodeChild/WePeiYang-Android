package com.twtstudio.repair.main.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.github.clans.fab.FloatingActionButton;
import com.twtstudio.repair.R;
import com.twtstudio.repair.main.MainBean;
import com.twtstudio.repair.main.MainContract;
import com.twtstudio.repair.main.presenter.MainPresenterImpl;
import com.twtstudio.repair.message.view.MessageActivity;

import butterknife.BindView;

import static com.twtstudio.repair.main.MainContract.*;


public class MainActivity extends MainContract.MainView {
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_main)
    FloatingActionButton floatingActionButton;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager layoutManager;
    MainContract.MainPresenter mainPresenter;
    MainBean mainBean = new MainBean();

    int mPreviousVisibleItem = 1;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbarView() {
        toolbar.setTitle("宿舍报修");
        return toolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(this, mainBean);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        floatingActionButton.show(true);
        floatingActionButton.hide(false);

        floatingActionButton.setOnClickListener(v -> MessageActivity.activityStart(MainActivity.this));

        refreshLayout.setOnRefreshListener(() -> {

        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem > mPreviousVisibleItem) {
                    floatingActionButton.hide(true);
                } else if (firstVisibleItem < mPreviousVisibleItem) {
                    floatingActionButton.show(true);
                }
                mPreviousVisibleItem = firstVisibleItem;
            }
        });

    }

    public void getData() {
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.getData();
    }

    public void setData() {

    }
}
