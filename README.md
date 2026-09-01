# test_an

类似微信底部 Tab 布局的 Android 示例项目，使用 Java + 原生 View 实现。

## 功能

- 4 个底部一级菜单：微信、通讯录、发现、我
- 点击底部 Tab 切换对应的 Fragment 占位页
- 菜单图标使用 Material 风格的矢量图标

## 环境要求

| 项 | 版本 |
|---|---|
| JDK | 17 或更高（推荐 21） |
| Gradle | 9.4.1 |
| Android Gradle Plugin | 8.7.3 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |
| Android SDK | 35 (platform) |

## 构建

```bash
cd D:\test_an
gradle assembleDebug
```

首次构建会从 Google Maven / Maven Central 下载依赖，请确保网络畅通。如需配置代理，可在 `~/.gradle/gradle.properties` 中添加：

```
systemProp.http.proxyHost=<proxy-host>
systemProp.http.proxyPort=<proxy-port>
systemProp.https.proxyHost=<proxy-host>
systemProp.https.proxyPort=<proxy-port>
```

## 构建产物

- `app/build/outputs/apk/debug/app-debug.apk`

## 在 Android Studio 中打开

1. 启动 Android Studio
2. 选择 **Open**
3. 选择 `D:\test_an` 目录
4. 等待 Gradle Sync 完成
5. 连接设备或启动模拟器，点击运行按钮即可

## 项目结构

```
D:\test_an\
├── build.gradle              # 顶层构建配置
├── settings.gradle           # 模块声明 + 仓库
├── gradle.properties         # JVM 参数 + AndroidX
├── local.properties          # SDK 路径
└── app\
    ├── build.gradle          # app 模块配置
    └── src\main\
        ├── AndroidManifest.xml
        ├── java\com\example\testan\
        │   ├── MainActivity.java
        │   ├── MainPagerAdapter.java
        │   └── ui\
        │       ├── WeixinFragment.java
        │       ├── ContactsFragment.java
        │       ├── DiscoverFragment.java
        │       └── MeFragment.java
        └── res\
            ├── drawable\        # 4 个矢量图标 + 启动图素材
            ├── layout\          # 根布局 + Fragment 占位布局
            ├── menu\            # BottomNavigationView 菜单
            ├── mipmap\          # 启动图标（API24-25）
            ├── mipmap-anydpi-v26\ # 自适应启动图标（API26+）
            └── values\          # colors / strings / themes
```