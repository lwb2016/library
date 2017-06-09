package east2d.com.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 开启第三方手机助手的工具类
 * Created by Administrator on 2016/11/17 0017.
 *
 */
public class AppMarketUtil {


    /**
     * 启动到应用商店 MyApp详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名
     */
    public  static Intent launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return null;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //打开手机已有应用商店列表视图
    public static Intent phoneMarket() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    //指定应用市场
    public static Intent specifiedMarket(String marketPkg) {
        String str = "market://details?id=" + marketPkg;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        return intent;
    }


    /**
     * 过滤出已经安装的包名集合
     *
     * @param context     上下文对象
     * @param packageList 待过滤包名集合
     * @return 已安装的包名集合
     */
    public static ArrayList<String> filterInstalledPkgs(Context context, List<String> packageList) {
        ArrayList<String> empty = new ArrayList<>();
        if (context == null || packageList == null || packageList.size() == 0)
            return empty;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
        int li = installedPkgs.size();
        int lj = packageList.size();
        for (int j = 0; j < lj; j++) {
            for (int i = 0; i < li; i++) {
                String installPkg = "";
                String checkPkg = packageList.get(j);
                try {
                    installPkg = installedPkgs.get(i).applicationInfo.packageName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(installPkg))
                    continue;
                if (installPkg.equals(checkPkg)) {
                    empty.add(installPkg);
                    break;
                }
            }
        }
        return empty;
    }


    /**
     * 过滤出已经安装的包名
     *
     * @param context     上下文对象
     * @param packageName 需要过滤的包名
     * @return ...
     */
    public static String filterInstalledPkgs(Context context, String packageName) {
        if (context == null || packageName == null)
            return null;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
        int li = installedPkgs.size();

        for (int i = 0; i < li; i++) {
            String installPkg = "";
            try {
                installPkg = installedPkgs.get(i).applicationInfo.packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(installPkg))
                continue;
            if (installPkg.equals(packageName)) {
                return installPkg;
            }
        }
        return null;
    }




    /**
     * 获取已安装应用商店的包名列表
     *
     * @param context
     * @return
     */
    public static ArrayList<String> queryInstalledMarketPkgs(Context context) {
        ArrayList<String> pkgs = new ArrayList<String>();
        if (context == null)
            return pkgs;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        if (infos == null || infos.size() == 0)
            return pkgs;
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            String pkgName = "";
            try {
                ActivityInfo activityInfo = infos.get(i).activityInfo;
                pkgName = activityInfo.packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(pkgName))
                pkgs.add(pkgName);

        }
        return pkgs;
    }


    /**
     * 调用手机助手进行应用评分接口
     * @param context
     * @param pkgName
     */
    public static void launcherAppPraise(Context context,String pkgName){
        try {
            Uri uri = Uri.parse("market://details?id="+pkgName);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();  //存在 android.content.ActivityNotFoundException:
            // No Activity found to handle Intent
            // { act=android.intent.action.VIEW dat=market://details?id=com.qingman.comic flg=0x10000000 }
        }
    }

    /**
     * 调用系统的分享功能
     * @param context
     * @param text
     */
    public static void launcherAppShare(Context context,String text){
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/*");
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
