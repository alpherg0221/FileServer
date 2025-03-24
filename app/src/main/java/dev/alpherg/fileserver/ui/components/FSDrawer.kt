package dev.alpherg.fileserver.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

data class RouteInfo(
    val icon: ImageVector = Icons.Rounded.QuestionMark,
    val label: String = "",
    val route: String = "",
)

abstract class AppDest {
    val homeRoute: RouteInfo = RouteInfo(
        icon = Icons.Rounded.Home,
        label = "Home",
        route = "home",
    )
}

@Composable
fun <T : AppDest> DrawerApp(
    navController: NavHostController,
    drawerState: DrawerState,
    appDest: T,
    drawerHeader: @Composable () -> Unit = {},
    drawerItems: List<RouteInfo>,
    gesturesDisabledRoute: List<String> = listOf(),
    builder: NavGraphBuilder.() -> Unit,
) {
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: appDest.homeRoute.route

    // ナビゲーションドロワー
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                drawerHeader = drawerHeader,
                currentRoute = currentRoute,
                drawerItems = (listOf(appDest.homeRoute) + drawerItems).map { item ->
                    DrawerItem(
                        icon = item.icon,
                        label = item.label,
                        route = item.route,
                        action = { navController.navigate(item.route) },
                    )
                },
                closeDrawer = { scope.launch { drawerState.close() } },
            )
        },
        gesturesEnabled = currentRoute !in gesturesDisabledRoute,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        NavHost(
            navController = navController,
            startDestination = appDest.homeRoute.route,
            builder = builder,
        )
    }

    // ドロワーが開いているときはバックボタンでドロワーを閉じる
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }
}

data class DrawerItem(
    val icon: ImageVector = Icons.Rounded.QuestionMark,
    val label: String = "",
    val route: String = "",
    val action: () -> Unit = {}
)

@Composable
fun Drawer(
    drawerHeader: @Composable () -> Unit = {},
    currentRoute: String,
    drawerItems: List<DrawerItem>,
    closeDrawer: () -> Unit = {},
) {
    ModalDrawerSheet(modifier = Modifier.statusBarsPadding()) {
        Surface(
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = Color.Transparent,
            content = drawerHeader,
        )

        HorizontalDivider()

        Spacer(Modifier.height(24.dp))

        drawerItems.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label, fontWeight = FontWeight.Black) },
                selected = currentRoute == item.route,
                onClick = {
                    closeDrawer()
                    item.action()
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
