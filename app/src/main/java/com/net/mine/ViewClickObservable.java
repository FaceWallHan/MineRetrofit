package com.net.mine;

import android.view.View;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class ViewClickObservable extends Observable<Object> {
    private View view;

    public ViewClickObservable(View view) {
        this.view = view;
    }
    //当这个观察者被订阅的时候，会执行下面的回调
    @Override
    protected void subscribeActual(@NonNull Observer<? super Object> observer) {
        if (view!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    observer.onNext(v);
                }
            });
        }
    }
}
