package com.apachat.circular.motion.core.abstract

import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.view.View

abstract class ClipPath {
  protected var path: Path = Path()

  var op: Region.Op = Region.Op.INTERSECT
    set(value) {
      require(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && op != Region.Op.INTERSECT && op != Region.Op.DIFFERENCE)) { "Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed" }
      field = value
    }

  abstract fun getPath(percent: Float, view: View): Path
}