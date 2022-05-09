package com.kenlib.util;

/**
 * 代码备份仓库
 */
//安卓项目不能运行java main函数，解决方案，
// 在主项目.idea/gradle.xml <GradleProjectSettings>节点下添加：
//<option name="delegatedBuild" value="false" />

//Context
//getApplicationContext()返回应用的上下文，生命周期是整个应用，应用摧毁它才摧毁
//Activity.this的context 返回当前activity的上下文，属于activity ，activity 摧毁他就摧毁
//getBaseContext()  返回由构造函数指定或setBaseContext()设置的上下文


// Intent.ACTION_VIEW（android.intent.action.VIEW）
// 比较通用，会根据用户的数据类型打开相应的Activity。
// Uri uri = Uri.parse("http://www.google.com"); //浏览器
// Uri uri =Uri.parse("tel:1232333"); //拨号程序


// 用于list分页，获取列表中指定范围的子列表
// list.subList(start, end)


//创建和使用动画，在view控件上使用
//    Animation animation = AnimationUtils.loadAnimation(this, R.anim.no_target_item);
//    animation.setInterpolator(new LinearInterpolator());
//
//     ((IconMenu)findViewById(R.id.a)).setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ((IconMenu)findViewById(R.id.a)).clearAnimation();
//            ((IconMenu)findViewById(R.id.a)).startAnimation(animation);
//        }
//    });


// 1.fragment 动态加载  遇到的坑：R.id.ll不可以是fragment标签的id。。。
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.ll, lxrFragment);
//                ft.commit();
// 2.fragment 静态加载，xml文件添加
//<fragment
//        android:id="@+id/fragment"
//                android:name="com.kenlib.fragmentviewpager.NewList"
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"></fragment>



//    图片缩放库调用 compile 'com.bm.photoview:library:1.4.1'
//    PhotoView photoView = (PhotoView) findViewById(R.id.img);
//    启用图片缩放功能
//    photoView.enable();

//Glide  图片加载库
//implementation('com.github.bumptech.glide:glide:3.7.0')
//Fresco 图片加载库,facebook


//代码添加view
//    LinearLayout layout = new LinearLayout(this);
//    LB lb = new LB(this,null);
//    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		layout.addView(lb,params);
//    setContentView(layout);


//    Android并发编程的未来
//ExecutorService和Handler


//动态修改控件的位置布局样式
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//        layoutParams.width=100;
//        layoutParams.height=100;
//        vh.checkBox.setLayoutParams(layoutParams);

//Java排序
//Collections.sort(fileInfoArrayList,new Comparator<FileInfo>() {
//@Override
//public int compare(FileInfo o1, FileInfo o2) {
//        if (o1.modifyTime == null || o1.modifyTime == null) return 0;
//        return o2.modifyTime.compareTo(o1.modifyTime);//降序
//        }
//        });



//安卓从源文件xml里获取字符串信息
//context.getString(R.string.PortalUrlBase)  


