package com.net.mine.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.net.mine.R;
import com.net.mine.ViewClickObservable;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxTestActivity  extends AppCompatActivity {
    private Button send,jitter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_test_layout);
        inView();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify(send);
            }
        });
        //observableClick(jitter,2,);
    }
    private void observableClick(View view, long seconds, View.OnClickListener listener){
        new ViewClickObservable(view)
                .throttleFirst(seconds,TimeUnit.SECONDS)//一定时间内取第一次发送的事件
                ////将被观察者切换到io线程
                .subscribeOn(Schedulers.io())
                //将观察者切换到主线程  需要在Android环境下运行
                .observeOn(AndroidSchedulers.mainThread())
                //当dispose发生变化时调用
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Throwable {
                        if (view!=null){
                            view.setOnClickListener(null);
                        }
                    }
                }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                listener.onClick(view);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    private void verify(Button button){
        long count=60;//倒计时时间
        Observable.interval(0,1, TimeUnit.SECONDS)//定时器
                /**interval用于定时发送，初始延时1秒，每1秒发一个自增整数*/
                .take(count+1)////取定时器前60个，当前值：0,1,2,3
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Throwable {
                        return count-aLong;//返回倒计时剩余时间
                    }
                })
                /**将观察者切换到主线程  需要在Android环境下运行*/
                .observeOn(AndroidSchedulers.mainThread())//主线程更新UI
                .doOnSubscribe(new Consumer<Disposable>() {
                    /**当事件流被订阅时被调用*/
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        //监听订阅时，将按钮设置为不可点击
                        button.setEnabled(false);
                        button.setTextColor(Color.BLACK);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(@NonNull Long aLong) {
                        //设置倒计时文本
                        button.setText("剩余"+aLong+"秒");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //事件完成后恢复点击
                        button.setEnabled(true);
                        button.setText("发送验证码");
                    }
        });
    }
    private void inView(){
        send=findViewById(R.id.send);
        jitter=findViewById(R.id.jitter);
    }
}
