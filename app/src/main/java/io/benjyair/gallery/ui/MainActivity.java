package io.benjyair.gallery.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.concurrent.TimeUnit;

import io.benjyair.gallery.R;
import io.benjyair.gallery.net.NetworkApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int FIRST_PAGE_INDEX = 1;
    private static final int PAGE_SIZE = 24;

    private EditText ed_search;
    private XRecyclerView xrv_gallery;
    private GalleryAdapter adapter;

    private int pageIndex = FIRST_PAGE_INDEX;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        xrv_gallery = findViewById(R.id.xrv_gallery);
        ed_search = findViewById(R.id.ed_search);
        xrv_gallery.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        xrv_gallery.addItemDecoration(new ItemDecoration());

        adapter = new GalleryAdapter();
        xrv_gallery.setAdapter(adapter);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        xrv_gallery.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                addDisposable(Observable.just(ed_search.getText().toString())
                        .flatMap(key -> loadData(key, FIRST_PAGE_INDEX))
                        .subscribe(result -> Log.i(TAG, "Load() result: " + result)));
            }

            @Override
            public void onLoadMore() {
                addDisposable(Observable.just(ed_search.getText().toString())
                        .flatMap(key -> loadData(key, pageIndex + 1))
                        .subscribe(result -> Log.i(TAG, "Load() result: " + result)));
            }
        });

        addDisposable(RxTextView.editorActions(ed_search)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(unit -> {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null && imm.isActive()) {
                        imm.hideSoftInputFromWindow(ed_search.getApplicationWindowToken(), 0);
                    }
                    ed_search.clearFocus();
                    return ed_search.getText().toString();
                })
                .flatMap(key -> loadData(key, FIRST_PAGE_INDEX))
                .subscribe(result -> Log.i(TAG, "Load() result: " + result)));
    }

    private Observable<Boolean> loadData(String key, int index) {
        Log.i(TAG, "loadData() page: " + index);
        return NetworkApi.getInstance().getGallery(key, index, PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .map(data -> {
                    xrv_gallery.refreshComplete();
                    if (data.getList() != null && !data.getList().isEmpty()) {
                        adapter.addData(data.getList(), index == FIRST_PAGE_INDEX);
                        adapter.notifyDataSetChanged();
                        pageIndex = index;
                        return true;
                    } else {
                        return false;
                    }
                });
    }


    private static class ItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(20, 20, 20, 20);
        }
    }
}
