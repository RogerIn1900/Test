
---

## 天气 App 简介

这是一个仿系统天气 App 的 Android 应用，分为「城市」和「预测」两个主页面，通过底部导航切换，整体 UI 使用渐变背景与卡片式信息展示，支持城市切换与天气接口请求。

### 功能概览

<p align="center">
  <video src="https://github.com/user-attachments/assets/060d4185-2084-4e37-952a-1f3263f96f80"
         width="320"
         controls>
    您的浏览器不支持视频标签。
  </video>
</p>

- **实时天气（城市页）**

<p align="center">
  <img src="https://github.com/user-attachments/assets/c7886b4a-7e6c-42d2-964c-df2226270d3b"
       alt="城市页 UI"
       width="320" />
</p>

  - 显示当前选中城市的：
    - 天气情况（晴、多云等）
    - 实时温度、大/小温
    - 白天 / 夜晚卡片的天气、温度、风力信息
  - 底部 4 个城市 Tab：**北京 / 上海 / 广州 / 深圳**
    - 点击后：Tab 背景蓝色高亮
    - 整个页面动画淡入更新为对应城市的天气

- **未来一周预报（预测页）**

<p align="center">
  <img src="https://github.com/user-attachments/assets/aee7921d-711a-4b78-b522-11b7fb605f5c"
       alt="预测页 UI"
       width="320" />
</p>

  - 顶部显示当前城市名（与城市页同步）
  - 列表展示一周天气：
    - 左侧两行：
      - 上：今天 / 明天 / 星期一…（按日期计算）
      - 下：`MM-dd` 格式的日期
    - 右侧展示天气现象、最高温 / 最低温
  - 进入预测页时，会根据**当前城市的 cityId** 请求该城市的 7 日预报

- **页面联动逻辑**
  - `MainActivity` 作为导航宿主，通过接口 `WeatherNavHost` 与 Fragment 交互：
    - `onCitySelected(cityName, cityId)`：城市页每次切换城市都会回传
    - 切到预测页时使用最新的 `cityId` 加载数据
    - 从预测页返回城市页时，仍然保持之前的城市选中状态

### 网络与缓存

- 使用 **OkHttp + Gson** 调用 `v1.yiketianqi.com` 的天气接口（实时 + 一周）。
- 在 `GetWeather` 中实现**内存缓存**：
  - 每个城市的当天 / 一周数据缓存 **1 小时**
  - 1 小时内重复请求同一城市时直接返回缓存，减少网络请求
- 通过 `network_security_config.xml` 对 `v1.yiketianqi.com` 允许明文 HTTP（也可改为 HTTPS）。

### 主要模块说明

- **`ui/activity/MainActivity`**：底部导航 + Fragment 容器，维护当前城市信息。
- **`ui/fragment/WeatherTodayFragment`**：当前天气展示 + 城市切换与动画效果。
- **`ui/fragment/WeatherForecastFragment`**：未来一周列表展示，支持按城市加载数据。
- **`network/WeatherApi`**：底层 HTTP 请求与 JSON 解析封装。
- **`network/GetWeather`**：对外天气获取接口（带 1 小时缓存）。
- **`data/*`**：天气数据模型（当天响应、一周响应、列表 item 数据等）。


---

## 登录与主页模块说明

本项目包含一个简单的登录流程与个人主页，用于演示基础表单校验、数据持久化和 Activity 之间的数据传递。


<p align="center">
  <video src="https://github.com/user-attachments/assets/1dceb960-2961-4e6c-a0d3-826fac1f0fdf"
         width="320"
         controls>
    您的浏览器不支持视频标签。
  </video>
</p>







### 登录页：`LoginActivity`

<p align="center">
  <img src="https://github.com/user-attachments/assets/fb7f4db9-e93f-429f-9f48-e765ea9976bd"
       alt="预测页 UI"
       width="320" />
</p>

- **布局文件**：`activity_login.xml`
- **主要控件**：
  - `EditText etInputEmail`：邮箱输入框
  - `EditText etInputPassword`：密码输入框
  - `MaterialButton btLogin`：登录按钮
  - `LinearLayout btWechat`：微信登录入口（示意）
  - `LinearLayout btApple`：Apple 登录入口（示意）
- **核心逻辑**：
  - 使用 `SharedPreferences("UserInfoSP")` 存储默认账号：
    - 邮箱：`1750193717@qq.com`
    - 密码：`123456`
  - 对密码输入框添加 `TextWatcher`：
    - 长度小于 6 位时，显示错误 `"密码长度不能少于6位"`
    - 长度满足时清除错误提示
  - 点击「登录」按钮：
    - 读取输入的邮箱和密码，与 `SharedPreferences` 中保存的值比对
    - 成功：
      - `Toast` 提示“登录成功”
      - 跳转到 `HomeActivity`
      - 通过 `Bundle` 传递 `userEmail` 字段给主页
    - 失败：
      - `Toast` 提示“登录失败,请检查邮箱 密码是否正确”
  - 点击「微信登录」「苹果登录」：
    - 目前为占位逻辑，仅弹出对应 `Toast` 提示


### 主页：`HomeActivity`

<p align="center">
  <img src="https://github.com/user-attachments/assets/cb2b43de-2a8d-4337-9c93-de8409dc21bf"
       alt="预测页 UI"
       width="320" />
</p>

- **布局文件**：`activity_home.xml`
- **主要控件**：
  - `TextView tvUserName`：顶部显示当前用户邮箱
  - 六个 `MaterialCardView` 菜单：
    - `userInfo`：个人信息
    - `userSetting`：设置
    - `userLike`：我的收藏
    - `userHistory`：浏览历史
    - `aboutUs`：关于我们
    - `feedback`：意见反馈
- **核心逻辑**：
  - 通过 `Intent` 获取从 `LoginActivity` 传来的 `Bundle`：
    - 读取 `userEmail`，若为空则显示 `"Guest"`
    - 将值填充到 `tvUserName` 显示
  - 为六个卡片分别设置点击事件：
    - 当前实现为简单的 `Toast` 提示（如 `"userInfo"`、`"userSetting"` 等）
    - 便于后续扩展为具体页面导航（如进入个人资料、设置页等）
- **UI 特点**：
  - 使用 Material Design 卡片式布局，适合作为账号中心 / 个人主页的入口聚合页。
 

