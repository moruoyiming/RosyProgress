# RosyProgress
Android 圆形进度条

#Preview
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel01.gif?raw=true)
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel02.gif?raw=true)
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel03.gif?raw=true)
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel04.gif?raw=true)
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel05.gif?raw=true)
![image](https://github.com/dalong982242260/AndroidCarrouselLayout/blob/master/gif/carrousel06.gif?raw=true)

#Example

[app-debug.apk](http://fir.im/bj45?release_id=5827dbc0959d697e2d001263)

#How to Use

gradle

        compile 'com.dalong:carrousellayout:1.0.0'   
          
xml:

         <com.calypso.rosyprogress.RosyProgress
                    android:id="@+id/rosyProgress"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:padding="5px"
                    app:antiAlias="true"
                    app:circle_radius="@dimen/circle_radius"
                    app:circle_solide_color="@color/circle_solide_color"
                    app:circle_stroke_color="@color/circle_stroke_color"
                    app:circle_stroke_width="@dimen/circle_stroke_width"
                    app:is_show_small="false"
                    app:progress_width="@dimen/progress_width"
                    app:small_circle_radius="@dimen/small_circle_radius"
                    app:small_circle_solide_color="@color/small_circle_solide_color"
                    app:small_circle_stroke_color="@color/small_circle_stroke_color"
                    app:small_circle_stroke_width="@dimen/small_circle_stroke_width"
                    app:text_color="@color/home_text_color"
                    android:background="@color/colorAccent"
                    app:text_size="@dimen/text_size_30"></com.calypso.rosyprogress.RosyProgress>

                    app:antiAlias="true"//是否抗锯齿
                    app:circle_radius="@dimen/circle_radius"//圆环半径
                    app:circle_solide_color="@color/circle_solide_color"//圆环边框颜色
                    app:circle_stroke_color="@color/circle_stroke_color"//圆环内部颜色
                    app:circle_stroke_width="@dimen/circle_stroke_width"//圆环边框的宽度
                    app:is_show_small="false"//是否显示小球标记
                    app:progress_width="@dimen/progress_width"//进度条的宽度
                    app:small_circle_radius="@dimen/small_circle_radius"//小圆标记半径
                    app:small_circle_solide_color="@color/small_circle_solide_color"//小圆边框颜色
                    app:small_circle_stroke_color="@color/small_circle_stroke_color"//小圆内部颜色
                    app:small_circle_stroke_width="@dimen/small_circle_stroke_width"//小圆边框的宽度
                    app:text_color="@color/home_text_color"//中间字体颜色
                    android:background="@color/colorAccent"
                    app:text_size="@dimen/text_size_30"//中间字体大小
java:

      RosyProgress rosyProgress = (RosyProgress) findViewById(R.id.rosyProgress);
      rosyProgress.setValue(80f);//设置进度条
      rosyProgress.setmAnimatorTime(time);//设置动画时间间隔
      rosyProgress.setmCircleRadius(r);//设置半径
      rosyProgress.setmCircleStrokeWidth(r);//设置小球半径
      rosyProgress.ismIsShowSmall(true);//是否显示小球标记

#License

Copyright 2016 jianz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.