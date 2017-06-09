package com.oacg.ad;

/**
 * Created by leo on 2017/4/25.
 */

public class AdHttp {

    public static final String BASE_HTTP_URL="http://game.oacg.cn/game_center/v1/ad.php";

    public static final String MODEL_ACTION="?m=Index&a=ads&ad_group_id=";

    public static String getAdDataUrl(int ad_group_id){
        String url=BASE_HTTP_URL+MODEL_ACTION+ad_group_id;
        return url;
    }

}
