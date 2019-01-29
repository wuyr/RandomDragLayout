## 任意拖布局 （扩展自QQ空间的列表Header效果）
### 博客详情： https://blog.csdn.net/u011387817/article/details/84136291

### 使用方式:
#### 添加依赖：
```
implementation 'com.wuyr:randomdraglayout:1.2.0'
```

### APIs:
|Method|Description|
|------|-----------|
|boolean reset()|重置状态 (重新初始化)|
|setChildRefreshPeriod(long period)|设置子View的重绘间隔时长 **默认：0 (不重绘)**<br/>一般是内容会不断更新的View才需要设置此参数，<br/>静态的View无需设置|
|setAlphaAnimationDuration(long duration)|设置透明渐变动画时长 **默认: 200L**|
|setFlingDuration(long duration)|设置位移动画时长 **默认: 800L**|
|setScrollAvailabilityRatio(float ratio)|设置惯性移动的利用率 **范围: 0~1 默认: 0.8F**|
|setOnDragListener(OnDragListener onDragListener)|监听拖动 **参数: 绝对X, 绝对Y, 绝对旋转角度**|
|setOnStateChangeListener(OnStateChangeListener listener)|监听状态变化 **状态:**<br/>STATE_NORMAL (普通状态)<br/>STATE_DRAGGING (正在拖拽中)<br/>STATE_FLINGING (惯性移动中（有滑动速率）)<br/>STATE_FLEEING (手指松开后，动画移动中（无速率）)<br/>STATE_OUT_OF_SCREEN (已经移动到屏幕外面)<br/>STATE_GONE (在屏幕内慢慢消失掉（透明渐变）)|
|int getState()|获取当前状态 **状态: 见上**|
|int getTargetOrientation()|获取当前位移动画前进的方向 **方向:**<br/>ORIENTATION_LEFT (向左移动)<br/>ORIENTATION_RIGHT (向右移动)<br/>ORIENTATION_TOP (向上移动)<br/>ORIENTATION_BOTTOM (向下移动)|
|RectF getBounds()|获取映射后的Bitmap边界 (即：包括了旋转之后的宽高)|

## 使用示例:
**在目标View外面直接套一层RandomDragLayout: (可以作用到任意View上)**

```xml
<com.wuyr.randomdraglayout.RandomDragLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#88F"
        android:padding="16dp"
        android:text="RandomDragLayoutTest" />
</com.wuyr.randomdraglayout.RandomDragLayout>
```

**宽高可直接使用wrap_content，RandomDragLayout会根据里面的View调整自身大小。**<br/>**OK，现在已经可以正常运行了，还可以在代码里面去设置一些属性或者监听各种状态。**

<br/>

## Demo下载: [app-debug.apk](https://github.com/wuyr/RandomDragLayout/raw/master/app-debug.apk)
## 库源码地址: https://github.com/Ifxcyr/RandomDragLayout
## 效果图 (图1: QQ空间效果):
![preview](https://github.com/wuyr/RandomDragLayout/raw/master/previews/preview.gif) ![preview](https://github.com/wuyr/RandomDragLayout/raw/master/previews/preview2.gif)
![preview](https://github.com/wuyr/RandomDragLayout/raw/master/previews/preview3.gif) ![preview](https://github.com/wuyr/RandomDragLayout/raw/master/previews/preview4.gif)
