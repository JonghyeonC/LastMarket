package com.jphr.lastmarket.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveViewModel extends ViewModel {
    public MutableLiveData<Long> nowPrice  = new MutableLiveData<Long>();

    public void setNowPrice(Long value) {
        nowPrice.postValue(value);
    }
    public Long getNowPrice(){
        return nowPrice.getValue();
    }
}
