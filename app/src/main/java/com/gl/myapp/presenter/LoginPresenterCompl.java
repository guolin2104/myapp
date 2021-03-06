package com.gl.myapp.presenter;

import com.gl.myapp.app.App;
import com.gl.myapp.model.IUser;
import com.gl.myapp.view.ILoginView;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by zft on 2019/1/7.
 */
public class LoginPresenterCompl implements ILoginPresenter {

    ILoginView iLoginView;
    IUser iUser;
    Handler handler;

    MyOkHttp myOkHttp;

    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void doLogin(String name, final String password) {
        if (myOkHttp == null) {
            myOkHttp = App.getInstance().getMyOkHttp();
        }
        String url = App.BASE_URL + "LoginServlet";
        Map<String, String> params = new HashMap<>();
        params.put("UserName", name);
        params.put("Password", password);

        myOkHttp.post()
                .url(url)
                .params(params)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        System.out.println("response = " + response.toString());
                        try {
                            JSONObject params = response.getJSONObject("params");
                            String result = params.getString("Result");
                            if ("success".equals(result)) {
                                iLoginView.onLoginResult(true, statusCode);
                            } else {
                                iLoginView.onLoginResult(false, statusCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        System.out.println("error_msg = " + error_msg);
                        iLoginView.onLoginResult(false, statusCode);
                    }
                });
    }
}
