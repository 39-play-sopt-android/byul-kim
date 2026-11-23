package org.sopt.play

// HomeScreen(화면)이랑 HomeUiState(상태) 둘 다 써서 화면 파일 밖으로 분리함
// → HomeScreen.kt는 화면 그리기만, 같이 쓰는 타입은 여기서 관리

// 메뉴 탭 목록
// enum class = 정해진 값만 쓸 수 있는 목록
// HOME = 코드에서 부르는 이름 / label("홈") = 화면에 보여줄 글자
enum class HomeMenu(val label: String) {
    DOUBLE_POINT("2배적립"),
    HOME("홈"),
    SELECTED("셀렉티드"),
    BRAND("브랜드"),
    BEAUTY("뷰티"),
    LIFE("라이프"),
    RANKING("랭킹"),
}

// 카테고리 칩 목록
enum class ProductCategory(val label: String) {
    ALL("전체"),
    TOP("상의"),
    PANTS("팬츠"),
    ONEPIECE("원피스"),
    OUTER("아우터"),
    SKIRT("스커트"),
    SHOES("슈즈"),
    BAG("가방"),
    INNERWEAR("이너웨어/잠옷"),
    PHONE_DECO("폰꾸미기"),
}
