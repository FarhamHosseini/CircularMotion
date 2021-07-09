package com.apachat.circular.motion.core.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.apachat.circular.motion.core.CircularMotionViewPager
import com.apachat.circular.motion.core.`interface`.Layout
import com.apachat.circular.motion.core.abstract.ClipPath
import com.apachat.circular.motion.core.provider.ClipPathProvider

open class FrameLayout : FrameLayout, Layout {
  private var path: Path? = null

  private var _clipPath: ClipPath = ClipPathProvider()
  private var _currentRevealPercent: Float = 100f
  private var _childrenTranslateX: Float = 0f
  private var _childrenTranslateY: Float = 0f
  private var _childrenScaleX: Float = 1f
  private var _childrenScaleY: Float = 1f
  private var _mode: CircularMotionViewPager.Mode =
    CircularMotionViewPager.Constants.DEFAULT_MODE

  override var clipPath = _clipPath
  override var currentRevealPercent: Float
    get() = _currentRevealPercent
    set(value) {
      revealForPercentage(value)
    }
  override var childrenTranslateX: Float = _childrenTranslateX
  override var childrenTranslateY: Float = _childrenTranslateY
  override var childrenScaleX: Float = _childrenScaleX
  override var childrenScaleY: Float = _childrenScaleY
  override var mode: CircularMotionViewPager.Mode = _mode

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(context, attrs, defStyleAttr, defStyleRes)

  /**
   * Overriden from View
   */
  override fun draw(canvas: Canvas?) {
    try {
      canvas?.save()
      path?.let {
        canvas?.clipPath(it, clipPath.op)
      }
      super.draw(canvas)
    } finally {
      canvas?.restore()
    }
  }

  /**
   * Overriden from View
   */
  override fun dispatchDraw(canvas: Canvas?) {
    if (mode == CircularMotionViewPager.Mode.SLIDE) {
      try {
        canvas?.restore()
        canvas?.save()
        canvas?.translate(childrenTranslateX, childrenTranslateY)
        canvas?.scale(childrenScaleX, childrenScaleY)
        super.dispatchDraw(canvas)
      } finally {
        canvas?.restore()
        canvas?.save()
        path?.let {
          canvas?.clipPath(it, clipPath.op)
        }
      }
    } else {
      super.dispatchDraw(canvas)
    }
  }

  override fun revealForPercentage(percent: Float) {
    if (percent == _currentRevealPercent) return
    _currentRevealPercent = percent
    path = clipPath.getPath(percent, this@FrameLayout)
    invalidate()
  }
}