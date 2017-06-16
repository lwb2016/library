package top.newmtx.clip;

/**
 * 监听
 * Created by leo on 2017/6/2.
 */

public interface OnExpendChildView {
    boolean doExpend(boolean toExpend);
    boolean isExpend();
    boolean canExpend();
}
