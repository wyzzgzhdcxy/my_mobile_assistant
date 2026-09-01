package com.myassistant.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myassistant.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PackageManager pm = requireContext().getPackageManager();
        String pkg = requireContext().getPackageName();

        String versionName = "未知";
        long versionCode = -1L;
        long firstInstallTime = 0L;
        long lastUpdateTime = 0L;
        try {
            PackageInfo info = pm.getPackageInfo(pkg, 0);
            versionName = info.versionName;
            versionCode = info.getLongVersionCode();
            firstInstallTime = info.firstInstallTime;
            lastUpdateTime = info.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            // ignore
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        ((TextView) view.findViewById(R.id.tv_app_name)).setText(getString(R.string.app_name));
        ((TextView) view.findViewById(R.id.tv_version_name)).setText("版本名称：" + versionName);
        ((TextView) view.findViewById(R.id.tv_version_code)).setText("版本号：" + versionCode);
        ((TextView) view.findViewById(R.id.tv_package_name)).setText("包名：" + pkg);

        String install = firstInstallTime > 0 ? sdf.format(new Date(firstInstallTime)) : "未知";
        String update = lastUpdateTime > 0 ? sdf.format(new Date(lastUpdateTime)) : "未知";
        ((TextView) view.findViewById(R.id.tv_build_time)).setText("安装：" + install + "\n更新：" + update);
    }
}