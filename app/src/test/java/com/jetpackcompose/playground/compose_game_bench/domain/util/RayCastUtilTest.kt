package com.jetpackcompose.playground.compose_game_bench.domain.util

import com.google.common.truth.Truth.assertThat
import com.jetpackcompose.playground.compose_game_bench.data.PlayerState
import com.jetpackcompose.playground.compose_game_bench.data.RaycastScreenColumnInfo
import com.jetpackcompose.playground.compose_game_bench.data.ScreenState
//import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class RayCastUtilTest {

    // Given
    val rayAngle = 0.0
    val screenInfo = ScreenState()
    val map1 = listOf(
        listOf(1, 1, 1, 1),
        listOf(1, 0, 0, 1),
        listOf(1, 0, 0, 1),
        listOf(1, 1, 1, 1)
    )
    var player = PlayerState(x = 2.0, y = 2.0)
    var collInfo = RaycastScreenColumnInfo()

    @Before
    fun start() {
        player = PlayerState(x = 2.0, y = 2.0)
        collInfo = RaycastScreenColumnInfo()
    }

    @Test
    fun `castRayInMap should return specific value when player in map range`() {
        // When
        val distanceToWall = RayCastUtil().castRayInMap(player, rayAngle, screenInfo, map1, collInfo)

        // Then
        // assertEquals(1.0, distanceToWall, 0.01)
        assertThat(distanceToWall).isWithin(0.1).of(1.0)
    }

    @Test
    fun `castRayInMap should return proper worldTextureOffset value when player in map range`() {
        // When
        RayCastUtil().castRayInMap(player, rayAngle, screenInfo, map1, collInfo)

        // Then
        // assertEquals(5.0f, collInfo.worldTextureOffset, 0.1f)
        assertThat(collInfo.worldTextureOffset).isWithin(0.1).of(5.0)
    }

    @Test
    fun `castRayInMap should return proper wallHitScale value when player in map range`() {
        RayCastUtil().castRayInMap(player, rayAngle, screenInfo, map1, collInfo)

        // assertEquals(1, collInfo.wallHitScale)
        assertThat(collInfo.hitWallNumber).isEqualTo(1)
    }

    @Test
    fun `castRayInMap should cast exception when player outside map`() {
        player = PlayerState(x = -200.0, y = -200.0)

        assertThrows(ArrayIndexOutOfBoundsException::class.java) {
            RayCastUtil().castRayInMap(player, rayAngle, screenInfo, map1, collInfo)
        }
    }
}