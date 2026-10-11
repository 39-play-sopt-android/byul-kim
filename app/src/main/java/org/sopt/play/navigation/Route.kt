package org.sopt.play.navigation

import kotlinx.serialization.Serializable

@Serializable //이거 이름표로 쓸 수 있게 해줘
data object Login //Login이라는 이름표 하나 object는 딱 하나만 존재하는 것.

@Serializable
data object Register

@Serializable
data object Home