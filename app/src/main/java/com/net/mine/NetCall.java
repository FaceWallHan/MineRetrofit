package com.net.mine;

import org.json.JSONObject;

public interface NetCall {
    void onSuccess(JSONObject jsonObject);
    void onFailure(Throwable t);
}
