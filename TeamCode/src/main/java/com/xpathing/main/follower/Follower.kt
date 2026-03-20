package com.xpathing.main.follower

import com.xpathing.main.follower.paths.Path

abstract class Follower() {
    abstract fun FollowPath(path: Path)
}