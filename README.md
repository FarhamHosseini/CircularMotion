# CircularMotion
Android Circular Motion Onboarding library

Circular motion is a [ViewPager](https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager) library that can be used to make Awesome Onboarding designs.

If you like this, you'll like [LiquidSwipe](https://github.com/FarhamHosseini/LiquidSwipe) as well.

## Usage
#### Set up the dependency
1. Add the mavenCentral() repository to your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		mavenCentral()
	}
}
```
2. Add the CircularMotion dependency in the build.gradle:
```
implementation group: 'com.apachat', name: 'circularmotion-android', version: '1.0.4'
```


#### Use `CircularMotionViewPager` instead of the normal `ViewPager`
```
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.apachat.circular.motion.core.CircularMotionViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### Use a `Layout` as the base container in the fragment layouts
```
<?xml version="1.0" encoding="utf-8"?>
<com.apachat.circular.motion.core.layout.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".DummyFragment">

    <!--  Fill with your views, just like you would in a normal ConstraintLayout  -->

</com.apachat.circular.motion.core.layout.ConstraintLayout>

<!--  Also supports FrameLayout & LinearLayout  -->
```

### And you're done, easy-peasy. ^_^

## Customization
### Via XML
```
<com.apachat.circular.motion.core.CircularMotionViewPager
    ...
    app:mode="reveal" // Default is slide
    app:scrollerDuration="2000" // Default is 1000
    app:translationXFactor="3" // Default is 2
    app:translationYFactor="0.5" // Default is 0.35
    app:scaleXFactor="0.75" // Default is 0.5
    app:scaleYFactor="0.75" // Default is 0.5
    />
```
### Via Programmatically
```
viewpager.mode = CircularMotionViewPager.Mode.REVEAL // Default is SLIDE
viewpager.setDuration(3000) // Default is 1000
viewpager.translationXFactor = 1.5f // Default is 2f
viewpager.translationYFactor = 1f // Default is 0.35f
viewpager.scaleXFactor = 2f // Default is 0.5f
viewpager.scaleYFactor = 2f // Default is 0.5f
viewpager.revealCenterPoint = PointF(centerX, centerY) // Default is screen center
viewpager.revealRadius = radius // Default is 0
```

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/FarhamHosseini/CircularMotion/issues).
