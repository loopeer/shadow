# shadow

[Download Smaple]()

Screenshot
====
![](/screenshot/bottom_radius.jpg)
![](/screenshot/change_shadow_color.jpg)
![](/screenshot/shadow_foreground_change.gif)
![](/screenshot/with_no_top_shadow.jpg)

Installation
====
```groovy
dependencies {
    compile 'com.loopeer.lib:shadow:0.0.1'
}
```
Usages
====
```xml

    <com.loopeer.shadow.ShadowView
        android:id="@+id/shadow_view"
        android:elevation="10dp"
        android:padding="10dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="12dp"
        app:shadowMargin="20dp"
        app:shadowRadius="14dp"
        app:cornerRadius="4dp"
        android:foreground="?attr/selectableItemBackground"
        android:onClick="onShadowClickTest">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Google Developer Days Europe 2017 took place in Krakow, Poland. In this playlist, you can find all the recorded sessions from the event, across all tracks (Develop on Mobile, Mobile Web, Beyond Mobile, and Android)."/>
    </com.loopeer.shadow.ShadowView>
```

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
