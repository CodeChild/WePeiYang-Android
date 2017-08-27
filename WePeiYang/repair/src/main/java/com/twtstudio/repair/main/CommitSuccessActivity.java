package com.twtstudio.repair.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.twtstudio.repair.R;
import com.twtstudio.repair.commons.BaseActivity;

import butterknife.BindView;

public class CommitSuccessActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_success_commit;
    }

    @Override
    protected Toolbar getToolbarView() {
        toolbar.setTitle("报修成功");
        return toolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static void activityStart(Context context) {
        Intent intent = new Intent(context, CommitSuccessActivity.class);
        context.startActivity(intent);
    }
}
