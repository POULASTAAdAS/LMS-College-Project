package com.poulastaa.lms.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poulastaa.lms.R
import com.poulastaa.lms.data.model.auth.LocalUser
import com.poulastaa.lms.presentation.home.components.HeadLine
import com.poulastaa.lms.presentation.home.components.Profile
import com.poulastaa.lms.presentation.home.components.SACTTopBar
import com.poulastaa.lms.ui.theme.TestThem
import com.poulastaa.lms.ui.theme.dimens

@Composable
fun HomeWrapperWithAppBar(
    time: String,
    user: LocalUser,
    cookie: String,
    isPrincipal: Boolean = false,
    isHeadClark: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    onEvent: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            SACTTopBar()
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onBackground
                        )
                    )
                )
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.medium1)
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))

            HeadLine()

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large1))

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(
                        bottom = MaterialTheme.dimens.small3,
                        start = MaterialTheme.dimens.small2
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(30.dp)
                        .width(4.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )

                Spacer(modifier = Modifier.width(MaterialTheme.dimens.medium1))

                Text(
                    text = stringResource(id = R.string.profile),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Text(
                text = stringResource(id = R.string.add_profile),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = MaterialTheme.dimens.small3)
                    .alpha(.6f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Profile(
                    url = user.profilePicUrl,
                    sex = user.sex,
                    cookie = cookie,
                    onClick = onEvent
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.dimens.medium1),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = user.name,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (isPrincipal) Text(
                        text = stringResource(id = R.string.principle),
                        color = MaterialTheme.colorScheme.background,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Medium,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        letterSpacing = 1.sp
                    )
                    else if (user.isDepartmentInCharge) Text(
                        text = stringResource(id = R.string.department_in_charge),
                        color = MaterialTheme.colorScheme.background,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    ) else if (isHeadClark) Text(
                        text = stringResource(id = R.string.head_clark),
                        color = MaterialTheme.colorScheme.background,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )

                    if (!isHeadClark && !isPrincipal) Text(
                        text = "${user.designation} (${user.department})",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large1))
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large1))

            content()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TestThem {
        HomeWrapperWithAppBar(
            time = "Good Morning",
            user = LocalUser(
                name = "Poulastaa Das",
                designation = "Assistant Professor",
                department = "Computer Science",
                isDepartmentInCharge = true,
                profilePicUrl = ""
            ),
            isPrincipal = false,
            cookie = "",
            onEvent = { /*TODO*/ }
        ) {

        }
    }
}