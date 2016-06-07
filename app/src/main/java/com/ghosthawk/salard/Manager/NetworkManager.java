package com.ghosthawk.salard.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ghosthawk.salard.Data.CommentResult;
import com.ghosthawk.salard.Data.FollowerResult;
import com.ghosthawk.salard.Data.FollowingResult;
import com.ghosthawk.salard.Data.MapResult;
import com.ghosthawk.salard.Data.MapResultResult;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.MessageResult;
import com.ghosthawk.salard.Data.MyPageModifyResult;
import com.ghosthawk.salard.Data.MyPageModifyResultResult;
import com.ghosthawk.salard.Data.MyPageResult;
import com.ghosthawk.salard.Data.MyPageResultResult;
import com.ghosthawk.salard.Data.MyResult;
import com.ghosthawk.salard.Data.OtherProfileResult;
import com.ghosthawk.salard.Data.OtherProfileResultResult;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.PackageProductResult;
import com.ghosthawk.salard.Data.ProductResult;
import com.ghosthawk.salard.Data.ProductResultResult;
import com.ghosthawk.salard.Data.ProfileResult;
import com.ghosthawk.salard.Data.ProfileResultResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Data.UpdateProductResult;
import com.ghosthawk.salard.Data.UpdateProductResultResult;
import com.ghosthawk.salard.Data.WishList;
import com.ghosthawk.salard.Data.WishListResult;
import com.ghosthawk.salard.Map.AddressInfo;
import com.ghosthawk.salard.Map.AddressInfoResult;
import com.ghosthawk.salard.Map.PoiSearchResult;
import com.ghosthawk.salard.Map.SearchPOIInfo;
import com.ghosthawk.salard.MyApplication;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private static final int DEFAULT_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String DEFAULT_CACHE_DIR = "miniapp";
    OkHttpClient mClient;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        File dir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFail(Request request, IOException exception);
    }

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result = (NetworkResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    result.listener.onSuccess(result.request, result.result);
                    break;
                case MESSAGE_FAIL:
                    result.listener.onFail(result.request, result.excpetion);
                    break;
            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class NetworkResult<T> {
        Request request;
        OnResultListener<T> listener;
        IOException excpetion;
        T result;
    }

    Gson gson = new Gson();

    private static final String TMAP_SERVER = "https://apis.skplanetx.com";
    private static final String TMAP_REVERSE_GEOCODING = TMAP_SERVER + "/tmap/geo/reversegeocoding?coordType=WGS84GEO&addressType=A02&lat=%s&lon=%s&version=1";
    public Request getTMapReverseGeocoding(Object tag, double lat, double lng, OnResultListener<AddressInfo> listener) {
        String url = String.format(TMAP_REVERSE_GEOCODING, "" + lat, ""+lng);
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","458a10f5-c07e-34b5-b2bd-4a891e024c2a")
                .build();
        final NetworkResult<AddressInfo> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    AddressInfoResult data = gson.fromJson(response.body().charStream(), AddressInfoResult.class);
                    result.result = data.addressInfo;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    private static final String TMAP_SEARCH_POI = TMAP_SERVER + "/tmap/pois?searchKeyword=%s&resCoordType=WGS84GEO&version=1";
    public Request getTMapSearchPOI(Object tag, String keyword, OnResultListener<SearchPOIInfo> listener) throws UnsupportedEncodingException {
        String url = String.format(TMAP_SEARCH_POI, URLEncoder.encode(keyword,"utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","458a10f5-c07e-34b5-b2bd-4a891e024c2a")
                .build();
        final NetworkResult<SearchPOIInfo> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    PoiSearchResult data = gson.fromJson(response.body().charStream(), PoiSearchResult.class);
                    result.result = data.searchPoiInfo;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String SALARD_SERVER="http://52.79.156.18:3000";

    private static final String SALARD_PRODUCT_URL = SALARD_SERVER + "/salard_home/";

    public Request getHomeProductList(Object tag, String my_xloca, String my_yloca, String my_id,
                                                OnResultListener<PackageProductResult> listener) {
        String url = String.format(SALARD_PRODUCT_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_xloca",my_xloca)
                .add("my_yloca",my_yloca)
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<PackageProductResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    PackageProductResult data = gson.fromJson(response.body().charStream(), PackageProductResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_PRODUCT_DETAIL_URL = SALARD_SERVER + "/salard_home/info/";

    public Request getProductDetail(Object tag, String _id, String my_id, OnResultListener<ProductResult> listener) {
        String url = String.format(SALARD_PRODUCT_DETAIL_URL);

        RequestBody body = new FormBody.Builder()
                .add("_id",_id)
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<ProductResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ProductResultResult data = gson.fromJson(response.body().charStream(), ProductResultResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_MYPAGE_URL = SALARD_SERVER + "/salard_mypage/";

    public Request getMyPage(Object tag, String my_id, OnResultListener<MyPageResult> listener) {
        String url = String.format(SALARD_MYPAGE_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id", my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MyPageResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MyPageResultResult data = gson.fromJson(response.body().charStream(), MyPageResultResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    private static final String SALARD_WISHLIST_URL = SALARD_SERVER + "/salard_wishlist/";

    public Request getWishList(Object tag, String my_id, OnResultListener<WishList> listener) {
        String url = String.format(SALARD_WISHLIST_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id", my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<WishList> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    WishListResult data = gson.fromJson(response.body().charStream(), WishListResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
       private static final String SALARD_ADDLIST_URL = SALARD_SERVER + "/salard_addlist/";

    public Request getAddList(Object tag, String my_id, OnResultListener<WishList> listener) {
        String url = String.format(SALARD_ADDLIST_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<WishList> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    WishListResult data = gson.fromJson(response.body().charStream(), WishListResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;



    }

    private static final String SALARD_FOLLOWING_LIST_URL = SALARD_SERVER + "/salard_showfollowing/";

    public Request getFollowingList(Object tag, String my_id, OnResultListener<FollowingResult> listener) {
        String url = String.format(SALARD_FOLLOWING_LIST_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<FollowingResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    FollowingResult data = gson.fromJson(response.body().charStream(), FollowingResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_FOLLOWER_URL = SALARD_SERVER + "/salard_showfollower/";

    public Request getFollowerList(Object tag, String my_id, OnResultListener<FollowerResult> listener) {
        String url = String.format(SALARD_FOLLOWER_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<FollowerResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    FollowerResult data = gson.fromJson(response.body().charStream(), FollowerResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_PROFILE_URL = SALARD_SERVER + "/salard_profile/";

    public Request getProfile(Object tag, String person_id, String my_id,  OnResultListener<OtherProfileResult> listener) {
        String url = String.format(SALARD_PROFILE_URL);

        RequestBody body = new FormBody.Builder()
                .add("person_id",my_id)
                .add("my_id",person_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<OtherProfileResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    OtherProfileResultResult data = gson.fromJson(response.body().charStream(), OtherProfileResultResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_PROFILE_PACKAGE_URL = SALARD_SERVER + "/salard_profile/package";

    public Request getProfilePackage(Object tag,String person_id, OnResultListener<PackageProductResult> listener) {
        String url = String.format(SALARD_PROFILE_PACKAGE_URL);

        RequestBody body = new FormBody.Builder()
                .add("person_id",person_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<PackageProductResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    PackageProductResult data = gson.fromJson(response.body().charStream(), PackageProductResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_PROFILE_COMMENT_URL = SALARD_SERVER + "/salard_profile/comment";

    public Request getProfileComment(Object tag,String person_id, OnResultListener<CommentResult> listener) {
        String url = String.format(SALARD_PROFILE_COMMENT_URL);

        RequestBody body = new FormBody.Builder()
                .add("person_id",person_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<CommentResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    CommentResult data = gson.fromJson(response.body().charStream(), CommentResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

























    private static final String SALARD_MESSAGE_URL = SALARD_SERVER + "/message/";

    public Request getMessage(Object tag, String my_id, OnResultListener<MessageResult> listener) {
        String url = String.format(SALARD_MESSAGE_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MessageResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MessageResult data = gson.fromJson(response.body().charStream(), MessageResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_MAP_URL = SALARD_SERVER + "/salard_home/map";

    public Request getMap(OnResultListener<MapResult> listener) {
        String url = String.format(SALARD_MAP_URL);

        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .build();

        final NetworkResult<MapResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MapResultResult data = gson.fromJson(response.body().charStream(), MapResultResult.class);
                    result.result = data.mapresult;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_FOLLOW_URL = SALARD_SERVER + "/following/";

    public Request getFollow(Object tag, String my_id, String partner_id, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_FOLLOW_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .add("partner_id",partner_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_UNFOLLOW_URL = SALARD_SERVER + "/following/cancel";

    public Request getUnFollow(Object tag, String my_id, String partner_id, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_UNFOLLOW_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .add("partner_id",partner_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }





    private static final String SALARD_LIKE_URL = SALARD_SERVER + "/like/";

    public Request getLike(Object tag, String _id, String my_id, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_LIKE_URL);

        RequestBody body = new FormBody.Builder()
                .add("package_num",_id)
                .add("mem_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_DISLIKE_URL = SALARD_SERVER + "/dislike/";

    public Request getDisLike(Object tag, String _id, String my_id, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_DISLIKE_URL);

        RequestBody body = new FormBody.Builder()
                .add("package_num",_id)
                .add("mem_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_SOLDOUT_URL = SALARD_SERVER + "/salard_soldout/";

    public Request getSoldout(Object tag, String package_num, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_SOLDOUT_URL);

        RequestBody body = new FormBody.Builder()
                .add("package_num",package_num)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_SOLDOUTCANCEL_URL = SALARD_SERVER + "/salard_soldout/cancel";

    public Request getSoldoutCancel(Object tag, String package_num, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_SOLDOUTCANCEL_URL);

        RequestBody body = new FormBody.Builder()
                .add("package_num",package_num)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }



    private static final String SALARD_ADDFOOD_URL = SALARD_SERVER + "/salard_addfood";

    public Request getAddFood(Object tag, String mem_id, String food_name, String food_detailinfo, String food_subdetailinfo, String food_recipeinfo, String food_price,
                              String food_count, List<File> food, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_ADDFOOD_URL);

        MultipartBody.Builder myBuilder = new MultipartBody.Builder();
        myBuilder.setType(MultipartBody.FORM)
                .addFormDataPart("mem_id",mem_id)
                .addFormDataPart("food_name",food_name)
                .addFormDataPart("food_detailinfo",food_detailinfo)
                .addFormDataPart("food_subdetailinfo",food_subdetailinfo)
                .addFormDataPart("food_recipeinfo",food_recipeinfo)
                .addFormDataPart("food_price",food_price)
                .addFormDataPart("food_count",food_count);

        for(int i = 0; i < food.size(); i++)
            myBuilder.addFormDataPart("food", food.get(i).getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"),food.get(i)));
        RequestBody body = myBuilder
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_PACKAGE_UPDATE_URL = SALARD_SERVER + "/package_update";

    public Request getPackageUpdate(Object tag, String _id, OnResultListener<UpdateProductResult> listener) {
        String url = String.format(SALARD_PACKAGE_UPDATE_URL);

        RequestBody body = new FormBody.Builder()
                .add("_id",_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final NetworkResult<UpdateProductResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    UpdateProductResultResult data = gson.fromJson(response.body().charStream(), UpdateProductResultResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }




    private static final String SALARD_PACKAGE_UPDATE_COMPLETE_URL = SALARD_SERVER + "/package_updatecomplete";

    public Request getPackageUpdateComplete(Object tag, String _id, String package_name, List<File> food, String package_price, String package_recipeinfo,
                                            String package_detailinfo, String package_subdetailinfo, String package_count, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_PACKAGE_UPDATE_COMPLETE_URL);

        MultipartBody.Builder myBuilder = new MultipartBody.Builder();
        myBuilder.setType(MultipartBody.FORM)
                .addFormDataPart("_id",_id)
                .addFormDataPart("package_name",package_name)
                .addFormDataPart("food", food.get(0).getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),food.get(0)))
                .addFormDataPart("food", food.get(1).getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),food.get(1)))
                .addFormDataPart("package_price",package_price)
                .addFormDataPart("package_recipeinfo",package_recipeinfo)
                .addFormDataPart("package_detailinfo",package_detailinfo)
                .addFormDataPart("package_subdetailinfo",package_subdetailinfo)
                .addFormDataPart("package_count",package_count);

        RequestBody body = myBuilder
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }




    private static final String SALARD_FOOD_REMOVE_URL = SALARD_SERVER + "/salard_foodremove";

    public Request getFoodRemove(Object tag, String package_num, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_FOOD_REMOVE_URL);

        RequestBody body = new FormBody.Builder()
                .add("package_num",package_num)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_MYPAGE_UPDATE_URL = SALARD_SERVER + "/mypage_update";

    public Request getMyPageUpdate(Object tag, String my_id, OnResultListener<MyPageModifyResult> listener) {
        String url = String.format(SALARD_MYPAGE_UPDATE_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MyPageModifyResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MyPageModifyResultResult data = gson.fromJson(response.body().charStream(), MyPageModifyResultResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String SALARD_MYPAGE_UPDATE_COMPLETE_URL = SALARD_SERVER + "/mypage_updatecomplete";

    public Request getMyPageUpdateComplete(Object tag, String mem_name, String mem_statemsg, File mem_picture, String mem_id, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_MYPAGE_UPDATE_COMPLETE_URL);

        MultipartBody.Builder myBuilder = new MultipartBody.Builder();
        myBuilder.setType(MultipartBody.FORM)
                .addFormDataPart("mem_name",mem_name)
                .addFormDataPart("mem_statemsg",mem_statemsg)
                .addFormDataPart("member", mem_picture.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),mem_picture))
                .addFormDataPart("mem_id",mem_id);

        RequestBody body = myBuilder
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }







    private static final String SALARD_SEND_DEAL_URL = SALARD_SERVER + "/send_deal";

    public Request getSendDeal(Object tag, String my_id, String partner_id, String package_num, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_SEND_DEAL_URL);

        RequestBody body = new FormBody.Builder()
                .add("my_id",my_id)
                .add("partner_id",partner_id)
                .add("package_num",package_num)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    private static final String SALARD_SIGNUP_URL = SALARD_SERVER + "/email_signup";

    public Request getSignUp(Object tag,String my_id, String my_name, String my_statemsg, String my_pwd, String my_email, String my_phone, File my_picture, OnResultListener<SuccessCode> listener) {
        String url = String.format(SALARD_SIGNUP_URL);

        MultipartBody.Builder myBuilder = new MultipartBody.Builder();
        myBuilder.setType(MultipartBody.FORM)
                .addFormDataPart("my_id",my_id)
                .addFormDataPart("my_name",my_name)
                .addFormDataPart("my_statemsg",my_statemsg)
                .addFormDataPart("my_pwd",my_pwd)
                .addFormDataPart("my_email",my_email)
                .addFormDataPart("my_phone",my_phone)
                .addFormDataPart("member", my_picture.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),my_picture));


        RequestBody body = myBuilder
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<SuccessCode> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SuccessCode data = gson.fromJson(response.body().charStream(), SuccessCode.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String SALARD_LOGIN_URL = SALARD_SERVER + "/email_login";

    public Request getLogin(Object tag, String my_id, String my_pwd, OnResultListener<MyResult> listener) {
        String url = String.format(SALARD_LOGIN_URL);

        MultipartBody.Builder myBuilder = new MultipartBody.Builder();
        RequestBody body = new FormBody.Builder()
                .add("mem_id",my_id)
                .add("mem_pwd",my_pwd)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MyResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MyResult data = gson.fromJson(response.body().charStream(), MyResult.class);
                    result.result = data;


                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

}