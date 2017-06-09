package east2d.com.file;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储路径对象
 * Created by leo on 2017/4/26.
 */

public class StorageData implements Parcelable {
    private String label;

    private String path;

    public StorageData() {
    }

    public StorageData(String label, String path) {
        this.label = label;
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static List<StorageData> getAllStorageData(Context context){
        StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            Method getUserLabel = storageVolumeClazz.getMethod("getUserLabel");
            List<StorageData> dataList=new ArrayList<>();
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String userLabel = (String) getUserLabel.invoke(storageVolumeElement);
                String path = (String) getPath.invoke(storageVolumeElement);
                dataList.add(new StorageData(userLabel,path));
            }
            return dataList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.path);
    }

    protected StorageData(Parcel in) {
        this.label = in.readString();
        this.path = in.readString();
    }

    public static final Creator<StorageData> CREATOR = new Creator<StorageData>() {
        @Override
        public StorageData createFromParcel(Parcel source) {
            return new StorageData(source);
        }

        @Override
        public StorageData[] newArray(int size) {
            return new StorageData[size];
        }
    };
}
