package com.geek.geekmall.model;

import com.geek.geekmall.bean.StoreTitle;
import com.geek.geekmall.bean.Theme;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class ThemeData {
    private Page<Theme> data;
    private List<StoreTitle> mallTheme;

    public List<StoreTitle> getMallTheme() {
        return mallTheme;
    }

    public void setMallTheme(List<StoreTitle> mallTheme) {
        this.mallTheme = mallTheme;
    }

    public Page<Theme> getData() {
        return data;
    }

    public void setData(Page<Theme> data) {
        this.data = data;
    }
}
