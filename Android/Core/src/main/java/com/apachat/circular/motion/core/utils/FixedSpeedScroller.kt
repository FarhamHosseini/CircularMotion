package com.apachat.circular.motion.core.utils

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

internal class FixedSpeedScroller : Scroller {
  var scrollerDuration = 1000

  constructor(context: Context?, interpolator: Interpolator?) : super(context, interpolator)
  constructor(context: Context?, interpolator: Interpolator?, duration: Int) : this(
    context,
    interpolator
  ) {
    this.scrollerDuration = duration
  }

  constructor(context: Context?, interpolator: Interpolator?, flywheel: Boolean) : super(
    context,
    interpolator,
    flywheel
  )

  override fun startScroll(
    startX: Int,
    startY: Int,
    dx: Int,
    dy: Int,
    duration: Int
  ) {
    super.startScroll(startX, startY, dx, dy, this.scrollerDuration)
  }

  override fun startScroll(
    startX: Int,
    startY: Int,
    dx: Int,
    dy: Int
  ) {
    super.startScroll(startX, startY, dx, dy, scrollerDuration)
  }
}