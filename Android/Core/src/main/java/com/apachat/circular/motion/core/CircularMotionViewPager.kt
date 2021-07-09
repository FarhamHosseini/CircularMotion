package com.apachat.circular.motion.core

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import com.apachat.circular.motion.core.`interface`.Layout
import com.apachat.circular.motion.core.utils.FixedSpeedScroller
import com.apachat.circular.motion.core.utils.Math.lerp
import kotlin.math.abs

class CircularMotionViewPager : ViewPager {
  constructor(context: Context) : super(context) {
    initialize(context, null)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    initialize(context, attrs)
  }

  private var _mode: CircularMotionViewPager.Mode = DEFAULT_MODE
  private var _revealCenterPoint: PointF = DEFAULT_REVEAL_CENTER_POINT
  private var _revealRadius: Int = DEFAULT_REVEAL_RADIUS
  private var _scaleXFactor: Float = DEFAULT_SCALE_FACTOR
  private var _scaleYFactor: Float = DEFAULT_SCALE_FACTOR
  private var _translationXFactor: Float = DEFAULT_TRANSLATION_X_FACTOR
  private var _translationYFactor: Float = DEFAULT_TRANSLATION_Y_FACTOR

  var mode: CircularMotionViewPager.Mode
    get() = _mode
    set(value) {
      _mode = value
      updatePageTransformer()
    }

  var revealCenterPoint: PointF?
    get() = _revealCenterPoint
    set(value) {
      if (value != null) {
        _revealCenterPoint = value
      } else {
        _revealCenterPoint = DEFAULT_REVEAL_CENTER_POINT
      }
      updatePageTransformer()
    }

  var revealRadius: Int
    get() = _revealRadius
    set(value) {
      _revealRadius = value
      updatePageTransformer()
    }

  var scaleXFactor: Float
    get() = _scaleXFactor
    set(value) {
      _scaleXFactor = value
      updatePageTransformer()
    }

  var scaleYFactor: Float
    get() = _scaleYFactor
    set(value) {
      _scaleYFactor = value
      updatePageTransformer()
    }

  var translationXFactor: Float
    get() = _translationXFactor
    set(value) {
      _translationXFactor = value
      updatePageTransformer()
    }

  var translationYFactor: Float
    get() = _translationYFactor
    set(value) {
      _translationYFactor = value
      updatePageTransformer()
    }

  private fun updatePageTransformer() {
    setPageTransformer(
      true,
      PageTransformer(
        mode,
        scaleXFactor,
        scaleYFactor,
        translationXFactor,
        translationYFactor,
        revealCenterPoint,
        revealRadius
      )
    )
  }

  private fun initialize(context: Context, attrs: AttributeSet?) {
    var scrollerDuration = DEFAULT_SCROLLER_DURATION
    attrs?.let {
      val typedArray =
        context.obtainStyledAttributes(it, R.styleable.CircularMotionViewPager, 0, 0)
      typedArray.apply {
        scrollerDuration = getInt(
          R.styleable.CircularMotionViewPager_scrollerDuration,
          DEFAULT_SCROLLER_DURATION
        )
        val modeInteger = getInt(
          R.styleable.CircularMotionViewPager_mode,
          0
        )
        _mode = when (modeInteger) {
          0 -> Mode.SLIDE
          1 -> Mode.REVEAL
          else -> DEFAULT_MODE
        }
        _scaleXFactor = getFloat(
          R.styleable.CircularMotionViewPager_scaleXFactor,
          DEFAULT_SCALE_FACTOR
        )
        _scaleYFactor = getFloat(
          R.styleable.CircularMotionViewPager_scaleYFactor,
          DEFAULT_SCALE_FACTOR
        )
        _translationXFactor = getFloat(
          R.styleable.CircularMotionViewPager_translationXFactor,
          DEFAULT_TRANSLATION_X_FACTOR
        )
        _translationYFactor = getFloat(
          R.styleable.CircularMotionViewPager_translationYFactor,
          DEFAULT_TRANSLATION_Y_FACTOR
        )
      }
    }
    setDuration(scrollerDuration)
    updatePageTransformer()
  }

  public fun setDuration(duration: Int) {
    val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
    mScroller.isAccessible = true
    val scroller = FixedSpeedScroller(context, DecelerateInterpolator())
    scroller.scrollerDuration = duration
    mScroller.set(this, scroller)
  }

  enum class Mode {
    SLIDE, REVEAL
  }

  internal companion object Constants {
    internal const val DEFAULT_SCROLLER_DURATION = 1000
    internal const val DEFAULT_SCALE_FACTOR = 0.5f
    internal const val DEFAULT_TRANSLATION_X_FACTOR = 2f
    internal const val DEFAULT_TRANSLATION_Y_FACTOR = 0.35f
    internal const val DEFAULT_REVEAL_RADIUS = 0

    internal val DEFAULT_REVEAL_CENTER_POINT = PointF(Float.MIN_VALUE, Float.MIN_VALUE)
    internal val DEFAULT_MODE = Mode.SLIDE
  }

  class PageTransformer(
    val mode: CircularMotionViewPager.Mode,
    val scaleXFactor: Float,
    val scaleYFactor: Float,
    val translationXFactor: Float,
    val translationYFactor: Float,
    val revealCenterPoint: PointF?,
    val revealRadius: Int
  ) :
    ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
      if (page is Layout) {
        page.mode = mode
        revealCenterPoint?.let {
          page.setCenterPoint(it)
        }
        page.setRadius(revealRadius)
        when {
          position < -1 -> {
            page.revealForPercentage(0f)
          }
          position < 0 -> {
            page.translationX = -(page.width * position)
            if (mode == Mode.SLIDE) {
              page.childrenTranslateX = (page.width * position) * translationXFactor
              page.childrenTranslateY = -(page.height * position) * translationYFactor
              page.childrenScaleX = lerp(1.0f, scaleXFactor, -position)
              page.childrenScaleY = lerp(1.0f, scaleYFactor, -position)
            } else {
              page.childrenTranslateX = 0f
              page.childrenTranslateY = 0f
              page.childrenScaleY = 1.0f
              page.childrenScaleX = 1.0f
            }
            page.invalidate()
            page.revealForPercentage(100 - abs(position * 100))
          }
          position <= 1 -> {
            page.translationX = -(page.width * position)
            if (mode == Mode.SLIDE) {
              page.childrenTranslateX = (page.width * position) * translationXFactor
              page.childrenTranslateY = (page.height * position) * translationYFactor
              page.childrenScaleX = lerp(1.0f, scaleXFactor, position)
              page.childrenScaleY = lerp(1.0f, scaleYFactor, position)
            } else {
              page.childrenTranslateX = 0f
              page.childrenTranslateY = 0f
              page.childrenScaleY = 1.0f
              page.childrenScaleX = 1.0f
            }
            page.invalidate()
            page.revealForPercentage(100f)
          }
        }
      }
    }
  }
}