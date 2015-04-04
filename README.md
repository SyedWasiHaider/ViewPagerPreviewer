# ViewPagerPreviewer
Shows a preview of offscreen fragments in a viewpager. This is highly experimental. Use at your own risk. Also, I encourage you to contribute with pull requests.

#Example

![ ](/sample.gif)

### Installation

Gradle compile command coming soon. For now you can import the library project itself.

###Basic Usage
In your layout file, put the previewer right below (or above if you like) the view pager.
```
 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent"  tools:context=".MainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:layout_width="fill_parent"/>

    <com.wasihaider.viewpagerpreviewer.ViewPagerPreviewer
        android:id="@+id/previewer"
        android:layout_width="fill_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

</LinearLayout>
```

In your activity:

```
 ViewPagerPreviewer previewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(YOURCUSTOMADAPTER);
        pager.setOffscreenPageLimit(4); //I recommend at least 3.
        previewer = (ViewPagerPreviewer)findViewById(R.id.previewer);
        previewer.setPager(pager);
    }

```

### Additional Options:

#####Custom Attributes
If you want to set the width of the preview items yourself or disable the item click events you can do so with the following custom attributes

```

 <com.wasihaider.viewpagerpreviewer.ViewPagerPreviewer
        android:id="@+id/previewer"
        android:layout_width="fill_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        custom:itemWidth="200dp"
        custom:enableOnClickEvent="false"
        />
```

#####Custom Item Click Listener

If you want to handle the click

```
  previewer.setListener(new PreviewItemListener() {
            @Override
            public void OnItemClicked(int position) {
                PagerAdapter adapter = pager.getAdapter();
                if (1 + position + pager.getCurrentItem() < adapter.getCount()) {
                    pager.setCurrentItem(1 + position + pager.getCurrentItem());
                }
            }
  });
```


### TODO

 - Make this a proper library so that people can use the compile statement in gradle. I really need help with this. It seems to be unnecessarily complicated.
 - Add more customization features and make it compatible with some other viewpager libaries.
 - Improve performance for high number of fragments 
