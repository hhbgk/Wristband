package com.hhbgk.wristband.util;

public class Constants {
    /** 下拉刷新列表的各个状态 */
    public static final int LIST = 0;
    public static final int EMPTY = 1;
    public static final int ERROR = 2;
    public static final int LOADING = 3;
    public static final int ALLOW_PULL_IN_EMPTY_PAGE = 4; // 没有内容，但是允许下拉刷新

    /** The type of list item */
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_BAND_STATE = 1;
    public static final int TYPE_BAND_POWER = 2;
    public static final int TYPE_BAND_ALERTED = 3;
    public static final int TYPE_BAND_ALARM = 4;
    public static final int TYPE_ABOUT = 5;
}
