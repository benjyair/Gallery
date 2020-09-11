package io.benjyair.gallery.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1) setContentView(getLayoutId());
        try {
            initData(savedInstanceState);
            findViews();
            setListeners();
        } catch (Exception e) {
            Log.e(TAG, "onCreate()", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @LayoutRes
    public abstract int getLayoutId();

    protected void initData(Bundle savedInstanceState) {
    }

    protected void findViews() {

    }

    protected void setListeners() {
    }

    protected CompositeDisposable getDisposable() {
        return compositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null) compositeDisposable.add(disposable);
    }

    protected void clearDisposable() {
        if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }

    protected void requestFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
