package org.sopt.play


import androidx.annotation.DrawableRes

// 상품 하나의 정보를 묶어두는 틀
data class Product(
    val id: Int, //상품 고유 번호 → 좋아요 누른 상품이 어떤 건지 구분하려고
    @DrawableRes val image: Int,   // 사진 주소
    val brand: String,             // 브랜드 이름
    val name: String,              // 상품 이름
    val discountRate: Int,         // 할인율 (36 → 화면에 "36%"로 보여줄 거)
    val price: Int,                // 가격 (28800 → 화면에 "28,800"으로 보여줄 거)
    val isFavorite: Boolean,       // 찜했는지
)

val productList = listOf(
    Product(
        id = 1,
        image = R.drawable.img_product1,
        brand = "트랜디어패럴",
        name = "[카프리1위🏆/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
        discountRate = 36,
        price = 28800,
        isFavorite = false,
    ),
    Product(
        id = 2,
        image = R.drawable.img_product2,
        brand = "슬로우앤드",
        name = "[🍁가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
        discountRate = 50,
        price = 26820,
        isFavorite = true,
    ),
    Product(
        id = 3,
        image = R.drawable.img_product3,
        brand = "어텀",
        name = "보울7부티셔츠",
        discountRate = 10,
        price = 17100,
        isFavorite = false,
    ),
    Product(
        id = 4,
        image = R.drawable.img_product4,
        brand = "슬로우앤드",
        name = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
        discountRate = 40,
        price = 35200,
        isFavorite = false,
    ),
)