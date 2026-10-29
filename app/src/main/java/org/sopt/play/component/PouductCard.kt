package org.sopt.play.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.Product
import org.sopt.play.R
import org.sopt.play.productList
import org.sopt.play.ui.theme.M14
import org.sopt.play.ui.theme.PlaySoptTheme

// 상품 카드 하나
@Composable
fun ProductCard(
    product: Product,              // 상품 정보 (사진, 브랜드, 이름, 할인율, 가격, 찜)
    onFavoriteClick: () -> Unit, //하트 눌렀을 때 할 일
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) { // 사진 위, 글자 아래로 세로 배치

        // 사진 + 하트 (겹쳐서 놓기)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 5f),   // 세로는 가로의 5/4배 (피그마 160×200 = 4:5 비율)
        ) {
            // 상품 사진
            Image(
                painter = painterResource(product.image),
                contentDescription = product.name,   // 상품 사진이라 이름을 설명으로
                contentScale = ContentScale.Crop,    // 칸에 꽉 채우고 넘치는 부분은 자르기
                modifier = Modifier.fillMaxSize(),
            )

            // Icon = 아이콘 전용 컴포넌트, tint로 색을 코드에서 입힐 수 있음
            Icon(
                painter = painterResource(
                    if (product.isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                    // 찜했으면 꽉 찬 하트, 아니면 빈 하트
                ),
                contentDescription = if (product.isFavorite) "찜 해제" else "찜하기",
                // 피그마: 찜 X = 흰 테두리, 찜 O = 빨간 하트
                tint = if (product.isFavorite) PlaySoptTheme.colors.red else PlaySoptTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.BottomEnd)   // Box 안에서 오른쪽 아래로
                    .clickable(onClick = onFavoriteClick)
                    // 하트 클릭 → 밖으로 알림 (상태는 직접 안 바꿈)
                    .padding(8.dp)
                    .size(24.dp),
            )
        }

        // 글자 영역
        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            // 브랜드
            Text(
                text = product.brand,
                style = PlaySoptTheme.typography.m14,
                maxLines = 1,                         // 최대 1줄
                overflow = TextOverflow.Ellipsis,     // 넘치면 "…"으로
            )

            // 상품 이름
            Text(
                text = product.name,
                style = PlaySoptTheme.typography.r14,      // 아까 추가한 Regular 폰트!
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            // 할인율 + 가격
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {   // 사이 4
                Text(
                    text = "${product.discountRate}%",   // 36 → "36%"
                    style = M14,
                    color = PlaySoptTheme.colors.red,
                )
                Text(
                    text = "%,d".format(product.price),  // 28800 → "28,800" (세 자리마다 쉼표)
                    style = M14,
                )
            }
        }
    }
}

// 찜 O / 찜 X, 이름 긴 것 / 짧은 것 확인
@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    PlaySoptTheme {
        Row {
            ProductCard(
                product = productList[1],
                onFavoriteClick = {},
                modifier = Modifier.width(176.dp)
            )   // 찜 O, 이름 김
            ProductCard(
                product = productList[2],
                onFavoriteClick = {},
                modifier = Modifier.width(176.dp)
            )   // 찜 X, 이름 짧음
        }
    }
}