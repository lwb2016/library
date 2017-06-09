package east2d.com.tool;

import android.content.Context;

/*
 * 获取资源工具
 */
public class ResUtils {
	private static final String RESOURCE_ID="id";
	private static final String RESOURCE_LAYOUT="layout";
	private static final String RESOURCE_STYLE="style";
	private static final String RESOURCE_DRAWABLE="drawable";
	private static final String RESOURCE_STRING="string";
	private static final String RESOURCE_COLOR="color";
	private static final String RESOURCE_MENU="menu";
	private static final String RESOURCE_DIMEN="dimen";
	private static final String RESOURCE_ANIM="anim";

	
	public static int getId(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_ID, activity.getPackageName());
	}
	public static int getAnim(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_ANIM, activity.getPackageName());
	}

	public static int getLayout(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_LAYOUT, activity.getPackageName());
	}

	public static int getStyle(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_STYLE, activity.getPackageName());
	}

	public static int getDrawable(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_DRAWABLE, activity.getPackageName());
	}

	public static int getStringId(String name, Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_STRING, activity.getPackageName());
	}

	public static String getString(String name, Context activity){
		return activity.getResources().getString(getStringId(name, activity));
	}

	public static int getColor(String name,Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_COLOR, activity.getPackageName());
	}

	public static int getMenu(String name,Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_MENU, activity.getPackageName());
	}

	public static int getDimensionSize(String name,Context activity){
		return activity.getResources().getDimensionPixelSize(getDimension(name, activity));
	}

	public static int getDimension(String name,Context activity){
		return activity.getResources().getIdentifier(name, RESOURCE_DIMEN, activity.getPackageName());
	}
}
