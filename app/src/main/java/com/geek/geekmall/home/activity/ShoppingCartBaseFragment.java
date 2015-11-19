package com.geek.geekmall.home.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 4/24/15.
 */
public class ShoppingCartBaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showProgressDialog(String message) {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(getActivity());
            this.mProgressDialog.setCancelable(true);
            this.mProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.mProgressDialog.setMessage(message);
        if ((!getActivity().isFinishing()) && (!this.mProgressDialog.isShowing()))
            this.mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if ((!getActivity().isFinishing()) && (this.mProgressDialog != null) && (this.mProgressDialog.isShowing()))
            this.mProgressDialog.dismiss();
    }

}
