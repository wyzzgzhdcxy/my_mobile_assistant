# my_mobile_assistant

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
cd D:\code\my_mobile_assistant
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

- Debug：`app/build/outputs/apk/debug/app-debug.apk`
- Release：`app/build/outputs/apk/release/app-release.apk`（使用仓库内 `app/release.keystore` 签名）

## 在 Android Studio 中打开

1. 启动 Android Studio
2. 选择 **Open**
3. 选择 `D:\code\my_mobile_assistant` 目录
4. 等待 Gradle Sync 完成
5. 连接设备或启动模拟器，点击运行按钮即可

## CI/CD

项目使用 GitHub Actions 自动构建 APK：

- 推送到 `main` / `master` 或创建 Pull Request 时自动构建 Debug APK
- 创建形如 `v*` 的 Tag 时自动构建并发布 Release APK 到 GitHub Releases

配置位于 `.github/workflows/android-build.yml`，详见 [GitHub Actions 自动打包配置](#github-actions-自动打包配置)。

### GitHub Actions 自动打包配置

#### 触发条件

| 事件 | 分支 / Tag | 产物 |
|---|---|---|
| `push` | `main`、`master` | Debug APK（作为 artifact 上传） |
| `pull_request` | 任意 | Debug APK（作为 artifact 上传） |
| `push` Tag | `v*`（例如 `v1.0.0`） | Debug + Release APK，并自动发布到 GitHub Release |

#### 工作流步骤

1. **Checkout 源码** —— `actions/checkout@v4`
2. **配置 JDK 17** —— `actions/setup-java@v4`，启用 Gradle 缓存
3. **设置 Android SDK** —— `android-actions/setup-android@v3`，安装 `platforms;android-35` 与 `build-tools;35.0.0`
4. **授予构建目录写权限** —— 修复 `/opt/hostedtoolcache` 的权限问题
5. **构建 Debug APK** —— `./gradlew assembleDebug`
6. **仅在 Tag 触发时**：构建 Release APK 并创建 GitHub Release 上传 APK

#### 本地复用

如果需要在本地用与 CI 完全一致的命令构建，可运行：

```bash
# Linux / macOS
chmod +x gradlew
./gradlew assembleDebug
./gradlew assembleRelease

# Windows
gradlew.bat assembleDebug
gradlew.bat assembleRelease
```

> 提示：项目当前未自带 `gradlew` 包装器脚本，如需在 CI 与本地之间保持完全一致，可执行 `gradle wrapper --gradle-version 9.4.1` 生成。

## 项目结构

```
D:\code\my_mobile_assistant\
├── build.gradle              # 顶层构建配置
├── settings.gradle           # 模块声明 + 仓库
├── gradle.properties         # JVM 参数 + AndroidX
├── local.properties          # SDK 路径
├── architecture.puml          # PlantUML 架构图
└── app\
    ├── build.gradle          # app 模块配置
    ├── release.keystore      # Release 签名密钥
    ├── proguard-rules.pro    # ProGuard 规则
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

## License

仅作示例用途。