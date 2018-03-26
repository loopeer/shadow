# shadow

This library draw android view shadow by ShadowLayer. You may don't like the CardView's black shadow which we can't change it's color. Same to CardView wrap content and set shadow radius. You can change shadow color, foreground color and corner radius everywhere. Because the shadow is draw in the view. So you must add space to draw the shadow. The ShadowView add one param shadowMargin which must be set before you add shadow radius to the view.  

**Caveat:** `Margin In UI~` = `layout_margin` + `shadow_margin(Add by ShadowView)`
  
[Download Sample](https://github.com/loopeer/shadow/releases/download/v0.0.1/app-debug.apk)

Screenshot
====

|H|H|H|
|---|---|---|
|Change Radius|Change foreground|Change Corners|
|![](/screenshot/shadow_radius.gif)|![](/screenshot/shadow_foreground.gif)|![](/screenshot/shadow_corners.gif)|
|Change shadow color|Change shadow margin|Demo|
|![](/screenshot/shadow_color.gif)|![](/screenshot/shadow_margin_hide.gif)|![](/screenshot/shadow_demo.gif)|


Installation
====
```groovy
dependencies {
    compile 'com.loopeer.lib:shadow:0.0.4-beta3'
}
```
Usages
====
```xml
<com.loopeer.shadow.ShadowView
    android:id="@+id/shadow_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="12dp"
    android:elevation="10dp"
    android:foreground="?attr/selectableItemBackground"
    android:onClick="onShadowClickTest"
    android:padding="10dp"
    app:cornerRadius="4dp"
    app:shadowMargin="20dp"
    app:shadowRadius="14dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Google Developer Days Europe 2017 took place in Krakow, Poland. In this playlist, you can find all the recorded sessions from the event, across all tracks (Develop on Mobile, Mobile Web, Beyond Mobile, and Android)."/>
</com.loopeer.shadow.ShadowView>
```

Attribute
====
```
android:foreground
shadowMargin
shadowMarginTop
shadowMarginLeft
shadowMarginRight
shadowMarginBottom
cornerRadius
cornerRadiusTL
cornerRadiusTR
cornerRadiusBL
cornerRadiusBR
foregroundColor
shadowColor
shadowDx
shadowDy
shadowRadius
backgroundColor
```

Donate
====
<img src="/screenshot/donate.jpeg" width="300">

License
====
<pre>
Copyright 2017 Loopeer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
