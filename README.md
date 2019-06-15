# ScheduleView

![image](https://github.com/jack-chong/ScheduleView/blob/master/app/gif/ScheduleView.gif)

  这是日程表控件, 效果请看上图的gif, 不过日程只是为了展现这个效果,  它其实是一个View的移动容器, 支持任意方向滑动, 支持双指缩放, 惯性滚动, 边缘效应.
  
  放上这个并不是让大家来使用这个日程, 而是给其他开发者一些借鉴, 因为在android并没有一个可以任意方向滑动 + 缩放的View,  通过集成BaseScrollView来获得任意方向移动和缩放的效果, 目前只支持使用Canvas绘制的View, 并不能装载其他子View, 因为缩放子View会导致重新measuration, 重新layout, 耗时较长, 会产生明显的卡顿.
  本人也比较热爱自定义View, 有技术问题或者有更好的实现思路可以互相交流.  本人QQ : 907755845, 常驻QQ群: 280891494
