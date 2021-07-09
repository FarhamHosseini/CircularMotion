package com.apachat.circular.motion.core.`interface`

import android.graphics.PointF
import com.apachat.circular.motion.core.CircularMotionViewPager
import com.apachat.circular.motion.core.abstract.ClipPath
import com.apachat.circular.motion.core.provider.ClipPathProvider

interface Layout {
  var clipPath: ClipPath

  var currentRevealPercent: Float

  var mode: CircularMotionViewPager.Mode

  var childrenTranslateX: Float

  var childrenTranslateY: Float

  var childrenScaleX: Float

  var childrenScaleY: Float

  fun revealForPercentage(percent: Float): Unit

  fun setCenterPoint(centerPoint: PointF): Boolean {
    return if (clipPath is ClipPathProvider) {
      (clipPath as ClipPathProvider).centerPoint = centerPoint
      true
    } else {
      false
    }
  }

  fun setRadius(radius: Int) {
    if (clipPath is ClipPathProvider) {
      (clipPath as ClipPathProvider).radius = radius
    }
  }
}